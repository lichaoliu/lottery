package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.ChaseStatus;
import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.ChaseBuyStatus;
import com.lottery.common.contains.lottery.ChaseType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.dto.ChaseRequest;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.cache.model.LotteryTicketConfigCacheModel;
import com.lottery.core.dao.LotteryChaseBuyDAO;
import com.lottery.core.dao.LotteryChaseDao;
import com.lottery.core.dao.LotteryPhaseDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserInfoDao;
import com.lottery.core.domain.LotteryChase;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.bet.BetService;

@Service
public class LotteryChaseService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Autowired
    private LotteryChaseDao chaseDao;
    @Autowired
    private IdGeneratorDao idDao;
    @Autowired
    private UserInfoDao userinfoDao;


    @Autowired
    private LotteryPhaseDAO lotteryPhaseDAO;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountDetailDAO userAccountDetailDAO;
    @Autowired
    private UserAccountDAO userAccountDAO;

    @Autowired
    private LotteryChaseBuyDAO lotteryChaseBuyDAO;
    @Autowired
    private BetService betService;
    @Autowired
    private LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;

    /**
     * 取消追号
     *
     * @param chaseid 追号编号
     */
    @Transactional
    public void giveupChase(String chaseid,int cancelBy,String memo) {
        LotteryChase chase = chaseDao.findWithLock(chaseid, true);
        if (chase == null) {
            throw new LotteryException("id为" + chaseid + "的追号不存在");
        }
        canel(chase, ChaseStatus.CANCEL.value, cancelBy, memo);
    }

    /**
     * 获取追号
     *
     * @param chaseid 追号编号
     * @return
     */
    @Transactional
    public LotteryChase getLotteryChase(String chaseid) {
        return chaseDao.find(chaseid);
    }

    /**
     * 撤销
     *
     * @param chase       追号类
     * @param chaseStatus 修改状态
     * @param cancelBy    由谁撤销 0:系统，1：用户
     */
    private void canel(LotteryChase chase, int chaseStatus, int cancelBy, String memo) {
        logger.info("取消追号chaseid:{},userno:{},返还金额:{}", new Object[]{chase.getId(), chase.getUserno(), chase.getRemainAmount() + ""});
        if (chase.getState() != ChaseStatus.NORMAL.value) {
            throw new LotteryException("追号" + chase.getId() + "的状态为" + chase.getState() + "不允许撤销");
        }
        chase.setState(chaseStatus);
        chase.setEndTime(new Date());
        chase.setChangeTime(new Date());
        chase.setCancelBy(cancelBy);
        chase.setMemo(memo);
        List<LotteryChaseBuy> lotteryBuys = lotteryChaseBuyDAO.getByChaseIdAndStatus(chase.getId(), ChaseBuyStatus.chasebuy_no.value);
        if (lotteryBuys == null || lotteryBuys.size() == 0) {
            throw new LotteryException("追号:" + chase.getId() + "的执行追号不存在");
        }
        int remain = chase.getRemainNum();
        if (remain != lotteryBuys.size()) {
            logger.error("执行追号与剩余总数不相等,剩余总数:{}，执行追号数:{}", new Object[]{remain, lotteryBuys.size()});
            throw new LotteryException("执行追号总数与剩余总数不相等");
        }

        BigDecimal remainAmount = BigDecimal.ZERO;
        for (LotteryChaseBuy lotteryChaseBuy : lotteryBuys) {
            lotteryChaseBuy.setStatus(ChaseBuyStatus.chasebuy_cancel.value);
            lotteryChaseBuy.setMemo(memo);
            lotteryChaseBuy.setFinishTime(new Date());
            lotteryChaseBuyDAO.merge(lotteryChaseBuy);
            remainAmount = remainAmount.add(lotteryChaseBuy.getAmount());
        }
        if (remainAmount.compareTo(chase.getRemainAmount()) != 0) {
            logger.error("剩余总金额与执行总金额不符,剩余总额:{}，执行总金额:{}", new Object[]{chase.getRemainAmount(), remainAmount});
            throw new LotteryException("剩余总金额与执行总金额不符");
        }

        // 解冻
        userAccountService.unfreezeAmount(chase.getUserno(), chase.getId(), chase.getId(), remainAmount, AccountType.Chase, AccountDetailType.chase_freeze_process, AccountDetailType.unfreeze, "取消剩余追号解冻", BigDecimal.ZERO);

        chase.setRemainAmount(BigDecimal.ZERO);
        chaseDao.update(chase);
    }

    /**
     * 创建追号
     *
     * @param userno        用户编号
     * @param lotteryType   彩种
     * @param betcode       注码
     * @param amt           单倍金额
     * @param chaseType     追号类型
     * @param amount        一次追号总金额
     * @param num           追号期数
     * @param beginPhase    开始期号
     * @param multiple      倍数
     * @param advancedChase 高级追号
     * @param totalPrize    中奖总金额
     * @param addition      追加(0否，1是)
     */
    @Transactional
    public LotteryOrder createChase(String userno, int lotteryType, String beginPhase, String betcode, int chaseType, int totalPrize, int amt, int amount, int multiple, int num, String advancedChase, int addition, String buyAgencyno) throws Exception {

        UserInfo userInfo = userinfoDao.find(userno);
        if (userInfo == null) {
            throw new LotteryException(ErrorCode.no_user, "用户:" + userno + "不存在");
        }
        Map<String,LotteryChaseBuy> chaseBuyMap=new HashMap<String, LotteryChaseBuy>();
        int ontAmount = 200;
        LotteryChase chase = new LotteryChase();
        String chaseId = idDao.getLotteryChaseId();
        chase.setUserno(userno);
        chase.setUserName(userInfo.getUsername());
        chase.setId(chaseId);
        chase.setLotteryType(lotteryType);
        chase.setBatchNum(num);
        chase.setBetCode(betcode);
        chase.setBetNum(0);
        chase.setBuyAgencyno(buyAgencyno);
        chase.setCreateTime(new Date());
        chase.setState(ChaseStatus.NORMAL.value);
        chase.setChaseType(chaseType);
        chase.setAlreadyPrize(BigDecimal.ZERO);
        chase.setChangeTime(new Date());
        if (chaseType == ChaseType.total_prize_end.value) {
            if (totalPrize == 0) {
                throw new LotteryException("中奖停止追号金额不能为0");
            }
        }
        chase.setPrizeTotal(new BigDecimal(totalPrize));
        chase.setBeginPhase(beginPhase);
        if (StringUtils.isNotBlank(advancedChase)) {// 高级追号
            List<ChaseRequest> list = JsonUtil.fromJsonToAarryList(advancedChase, ChaseRequest.class);
            if (list == null || list.size() == 0) {
                throw new LotteryException("高级追号内容为空或出现错误，不能进行追号");
            }

            // 对期号进行排序
            Collections.sort(list, new Comparator<ChaseRequest>() {
                @Override
                public int compare(ChaseRequest o1, ChaseRequest o2) {
                    return Long.valueOf(o1.getPhase()).compareTo(Long.valueOf(o2.getPhase()));
                }

            });

            num = list.size();
            BigDecimal total = new BigDecimal(0);
            Long batchIndex = 1l; // 票在包中的序号
            Long id = idDao.getLotteryChaseBuyId(num);
            List<LotteryPhase> phaseList = new ArrayList<LotteryPhase>();
            for (ChaseRequest chaseRequest : list) {

                int phaseAmount = chaseRequest.getAmount();
                total = total.add(new BigDecimal(phaseAmount));
                int phaseMultiple = chaseRequest.getMultiple();
                int phaseAmt = chaseRequest.getAmt();
                addition = chaseRequest.getAddition();
                String phase = chaseRequest.getPhase();
                LotteryPhase lotteryPhase = null;


                if (chaseRequest.getAddition() == YesNoStatus.yes.value) {
                    ontAmount = 300;
                }

                betService.codeValidate(lotteryType,phase,betcode,phaseMultiple,ontAmount,new BigDecimal(phaseAmount),YesNoStatus.no.value);

                try {
                    lotteryPhase= lotteryPhaseDAO.getByTypeAndPhase(lotteryType,phase);
                } catch (Exception e) {

                }
                if (lotteryPhase == null) {
                    throw new LotteryException(ErrorCode.no_phase, "该彩期不存在,phaseNo=" + phase);
                }

                if (lotteryPhase.getEndTicketTime().before(new Date())) {
                    throw new LotteryException(ErrorCode.phase_outof, "彩期已过期：" + phase);
                }

                phaseList.add(lotteryPhase);
                LotteryChaseBuy lotteryChaseBuy = new LotteryChaseBuy();
                String buyId = idDao.getLotteryChaseBuyId(id);
                id++;
                lotteryChaseBuy.setId(buyId);
                lotteryChaseBuy.setBetCode(betcode);
                lotteryChaseBuy.setOrderStatus(OrderStatus.NOT_SPLIT.value);
                lotteryChaseBuy.setOrderResultStatus(OrderResultStatus.not_open.value);
                lotteryChaseBuy.setChaseId(chaseId);
                lotteryChaseBuy.setAddition(addition);
                lotteryChaseBuy.setUserno(userno);
                lotteryChaseBuy.setUserName(userInfo.getUsername());
                lotteryChaseBuy.setChaseIndex(batchIndex);
                batchIndex++;
                lotteryChaseBuy.setLotteryType(lotteryType);
                lotteryChaseBuy.setPhase(lotteryPhase.getPhase());
                lotteryChaseBuy.setPhaseEndTime(lotteryPhase.getEndSaleTime());
                lotteryChaseBuy.setPhaseStartTime(lotteryPhase.getStartSaleTime());
                lotteryChaseBuy.setAmount(new BigDecimal(phaseAmount));
                lotteryChaseBuy.setMultiple(phaseMultiple);
                lotteryChaseBuy.setStatus(ChaseBuyStatus.chasebuy_no.value);
                lotteryChaseBuy.setRemainNum(num--);
                lotteryChaseBuy.setCreateTime(new Date());
                lotteryChaseBuy.setChaseType(chaseType);
                lotteryChaseBuy.setRemainAmount(BigDecimal.ZERO);
                lotteryChaseBuyDAO.insert(lotteryChaseBuy);
                chaseBuyMap.put(phase,lotteryChaseBuy);
            }
            // 生成追号详情
            // 要对期进行排序
            Collections.sort(phaseList, new Comparator<LotteryPhase>() {
                @Override
                public int compare(LotteryPhase o1, LotteryPhase o2) {
                    // 按照截止时间升序排列
                    if (o1.getEndSaleTime() != null && o2.getEndSaleTime() != null && o1.getEndSaleTime().getTime() != o2.getEndSaleTime().getTime()) {
                        return o1.getEndSaleTime().compareTo(o2.getEndSaleTime());
                    }
                    // 否则按照ID升序排列
                    return o1.getId().compareTo(o2.getId());
                }
            });
            if (phaseList.size() != list.size()) {
                throw new LotteryException("追号期数与期数不相等");
            }
            chase.setBeginPhase(phaseList.get(0).getPhase());
            chase.setEndPhase(phaseList.get(phaseList.size() - 1).getPhase());
            chase.setEndTime(phaseList.get(phaseList.size() - 1).getEndSaleTime());
            chase.setTotalAmount(total);
            chase.setRemainAmount(total);
            chase.setRemainNum(list.size());
            chase.setBatchNum(list.size());
            chaseDao.insert(chase);
            userAccountService.freezeAmount(userno, chase.getId(), total, AccountType.Chase, AccountDetailType.chase_freeze_process, "追号总冻结", chase.getId(), lotteryType, phaseList.get(0).getPhase(), userInfo.getAgencyNo(), true);


        } else {// 普通追号

            // 校验注码和金额 把追的所有期的倍数相加 和总金额做验证
            boolean validate = false;
            if (addition == YesNoStatus.yes.value) {
                ontAmount = 300;
            }

            betService.codeValidate(lotteryType,beginPhase,betcode,multiple,ontAmount,new BigDecimal(amount),YesNoStatus.no.value);

            List<LotteryPhase> phaseList = lotteryPhaseDAO.getNextPhaseWithCurrent(lotteryType, beginPhase, num);
            if (phaseList == null || phaseList.size() != num) {
                int realnum = 0;
                if (phaseList != null)
                    realnum = phaseList.size();
                logger.error("追号彩期不足,彩种：{},开始期号:{},追号期数:{},实际存在期数是:{}", new Object[]{lotteryType, beginPhase, num, realnum});
                throw new LotteryException(ErrorCode.not_enough_phase, "追号彩期不足");
            }



            LotteryPhase phase = null;
            try {
                phase=lotteryPhaseDAO.getByTypeAndPhase(lotteryType,beginPhase);
            } catch (Exception e) {

            }
            if (phase == null) {
                throw new LotteryException(ErrorCode.no_phase, "该彩期不存在,phaseNo=" + beginPhase);
            }

            if (phase.getEndTicketTime().before(new Date())) {
                throw new LotteryException(ErrorCode.phase_outof, "彩期已过期：" + beginPhase);
            }


            BigDecimal total = new BigDecimal(amount).multiply(new BigDecimal(num));

            chase.setBeginPhase(beginPhase);

            chase.setAmount(new BigDecimal(amount));
            chase.setTotalAmount(total);
            chase.setRemainAmount(total);
            chase.setRemainNum(num);

            // 生成追号详情
            // 要对期进行排序
            Collections.sort(phaseList, new Comparator<LotteryPhase>() {
                @Override
                public int compare(LotteryPhase o1, LotteryPhase o2) {
                    // 按照截止时间升序排列
                    if (o1.getEndSaleTime() != null && o2.getEndSaleTime() != null && o1.getEndSaleTime().getTime() != o2.getEndSaleTime().getTime()) {
                        return o1.getEndSaleTime().compareTo(o2.getEndSaleTime());
                    }
                    // 否则按照ID升序排列
                    return o1.getId().compareTo(o2.getId());
                }
            });
            chase.setMultiple(multiple);
            Long batchIndex = 1l; // 票在包中的序号
            Long id = idDao.getLotteryChaseBuyId(num);
            for (LotteryPhase lotteryPhase : phaseList) {
                LotteryChaseBuy lotteryChaseBuy = new LotteryChaseBuy();
                String buyId = idDao.getLotteryChaseBuyId(id);
                id++;
                lotteryChaseBuy.setBetCode(betcode);
                lotteryChaseBuy.setOrderStatus(OrderStatus.NOT_SPLIT.value);
                lotteryChaseBuy.setOrderResultStatus(OrderResultStatus.not_open.value);
                lotteryChaseBuy.setId(buyId);
                lotteryChaseBuy.setChaseId(chaseId);
                lotteryChaseBuy.setChaseIndex(batchIndex);
                batchIndex++;
                lotteryChaseBuy.setLotteryType(lotteryType);
                lotteryChaseBuy.setPhase(lotteryPhase.getPhase());
                lotteryChaseBuy.setPhaseEndTime(lotteryPhase.getEndSaleTime());
                lotteryChaseBuy.setPhaseStartTime(lotteryPhase.getStartSaleTime());
                lotteryChaseBuy.setAmount(new BigDecimal(amount));
                lotteryChaseBuy.setMultiple(multiple);
                lotteryChaseBuy.setStatus(ChaseBuyStatus.chasebuy_no.value);
                lotteryChaseBuy.setRemainNum(num--);
                lotteryChaseBuy.setAddition(addition);
                lotteryChaseBuy.setUserno(userno);
                lotteryChaseBuy.setCreateTime(new Date());
                lotteryChaseBuy.setUserName(userInfo.getUsername());
                lotteryChaseBuy.setChaseType(chaseType);
                lotteryChaseBuy.setRemainAmount(BigDecimal.ZERO);
                lotteryChaseBuyDAO.insert(lotteryChaseBuy);

                chaseBuyMap.put(lotteryPhase.getPhase(),lotteryChaseBuy);

            }

            chase.setEndTime(phaseList.get(phaseList.size() - 1).getEndSaleTime());
            chase.setEndPhase(phaseList.get(phaseList.size() - 1).getPhase());
            chaseDao.insert(chase);
            userAccountService.freezeAmount(userno, chase.getId(), total, AccountType.Chase, AccountDetailType.chase_freeze_process, "追号总冻结", chase.getId(), lotteryType, "1001", userInfo.getAgencyNo(), true);


        }

        String begain=chase.getBeginPhase();
        LotteryChaseBuy begainchasebuy=chaseBuyMap.get(begain);
        LotteryPhase startphase=null;

       try{
           startphase=lotteryPhaseDAO.getByTypeAndPhase(lotteryType,begain);
       }catch (Exception e){

       }
        if (startphase==null){
            throw new LotteryException("彩种:"+lotteryType+",期号:"+begain+",不存在");
        }

        LotteryTicketConfig lotteryTicketConfig=null;
        try{
            lotteryTicketConfig=lotteryTicketConfigCacheModel.get(LotteryType.get(lotteryType));
        }catch (Exception e){

        }
        if (lotteryTicketConfig!=null&&lotteryTicketConfig.getChaseEndSaleForward()!=null){
            long timeout=startphase.getEndSaleTime().getTime()-(new Date()).getTime();
            if (timeout<lotteryTicketConfig.getChaseEndSaleForward().longValue()){
                throw new LotteryException("彩种:"+lotteryType+",期号:"+begain+",追号时间已过期");
            }
        }


        if (startphase.getForCurrent()==YesNoStatus.yes.value&&startphase.getForSale()==YesNoStatus.yes.value){
            String orderid=idDao.getOrderId();
            return betService.toChase(orderid,begainchasebuy);
        }

        return null;
    }

    /**
     * 修改中奖后追号
     *
     * @param chaseId
     * @param prizeAmount
     */
    @Transactional
    public void updatePrzieChase(String chaseId, BigDecimal prizeAmount) {
        LotteryChase lotteryChase = chaseDao.findWithLock(chaseId, true);
        if (lotteryChase == null) {
            return;
        }
        if (prizeAmount == null || prizeAmount.compareTo(BigDecimal.ZERO) == 0) {

            return;
        }
        lotteryChase.setAlreadyPrize(lotteryChase.getAlreadyPrize().add(prizeAmount));
        if (lotteryChase.getState() == ChaseStatus.END.value||lotteryChase.getState() == ChaseStatus.CANCEL.value) {
            chaseDao.merge(lotteryChase);
            return;
        }

        if (prizeAmount != null && prizeAmount.compareTo(BigDecimal.ZERO) > 0) {
            if (lotteryChase.getChaseType() == ChaseType.prize_end.value) {
                String memo = "中奖停止追号,中奖" + prizeAmount.intValue() / 100 + "元";

                canel(lotteryChase, ChaseStatus.END.value, 0, memo);
                return;
            }
            if (lotteryChase.getChaseType() == ChaseType.total_prize_end.value) {

                if (lotteryChase.getAlreadyPrize().compareTo(lotteryChase.getPrizeTotal()) >= 0) {
                    String memo = "中奖" + lotteryChase.getPrizeTotal().intValue() / 100 + "元,停止追号,实际中奖金额：" + lotteryChase.getAlreadyPrize().intValue() / 100;
                    canel(lotteryChase, ChaseStatus.END.value, 0, memo);
                } else {
                    chaseDao.merge(lotteryChase);
                    lotteryChase.setChangeTime(new Date());

                }
            }
        }


    }

    /**
     * 取消某个追号
     * @param chaseBuyId 追号购买编号
     */
    @Transactional
    public void giveupChaseBuy(String chaseBuyId,String memo) {
        LotteryChaseBuy lotteryChaseBuy = lotteryChaseBuyDAO.findWithLock(chaseBuyId,true);
        if (lotteryChaseBuy == null || lotteryChaseBuy.getStatus() == ChaseBuyStatus.chasebuy_cancel.value || lotteryChaseBuy.getStatus() == ChaseBuyStatus.chasebuy_yes.value) {
            throw new LotteryException("追号:" + chaseBuyId + "不存在或已撤销");
        }
        String chaseid=lotteryChaseBuy.getChaseId();
        LotteryChase chase = chaseDao.findWithLock(chaseid, true);
        if (chase == null) {
            throw new LotteryException("id为" + chaseid + "的追号不存在");
        }

        logger.info("取消追号chaseid:{},userno:{},返还金额:{}", new Object[]{chaseid, chase.getUserno(), chase.getRemainAmount() + ""});
        if (chase.getState() != ChaseStatus.NORMAL.value || chase.getRemainNum() == 0) {
            throw new LotteryException("追号" + chaseid + "执行追号" + chaseBuyId + "的状态为+" + chase.getState() + "不允许撤销或已结束");
        }

        BigDecimal amount = lotteryChaseBuy.getAmount();
        lotteryChaseBuy.setStatus(ChaseBuyStatus.chasebuy_cancel.value);
        lotteryChaseBuy.setMemo(memo);
        lotteryChaseBuy.setFinishTime(new Date());
        lotteryChaseBuyDAO.merge(lotteryChaseBuy);
        chase.setRemainAmount(chase.getRemainAmount().subtract(amount));
        chase.setRemainNum(chase.getRemainNum() - 1);
        chase.setChangeTime(new Date());
        if (chase.getRemainNum() == 0) {
            chase.setState(ChaseStatus.END.value);
        }
        chaseDao.merge(chase);
        // 解冻
        UserAccount userAccount = userAccountDAO.findWithLock(chase.getUserno(), true);
        userAccount.setBalance(userAccount.getBalance().add(amount));
        userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
        userAccount.setLastTradeTime(new Date());
        userAccount.setLastTradeamt(amount);
        userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));
        if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
            userAccount.setDrawbalance(userAccount.getBalance());
        }
        userAccountDAO.update(userAccount);
        String id = idDao.getUserAccountDetailId();

        UserAccountDetail uad = new UserAccountDetail(id, userAccount.getUserno(), userAccount.getUserName(), chaseBuyId, new Date(), amount, AccountType.Chase, CommonStatus.success, userAccount.getBalance(), AccountDetailType.unfreeze, userAccount.getDrawbalance(), userAccount.getFreeze(), "取消追号解冻", SignStatus.flat);
        uad.setOtherid(chase.getId());
        userAccountDetailDAO.insert(uad);

    }

    /**
     * 查询追号
     * @param lotteryType 彩种
     * @param state 状态
     * @param page 分页
     * */
    @Transactional
    public List<LotteryChase> get(int lotteryType,int state,PageBean<LotteryChase> page){
        return chaseDao.get(lotteryType, state, page);
    }

    /**
     * 追号查询
     *
     * @param userno      用户编码
     * @param lotteryType 彩种
     * @param startTime   开始时间
     * @param endTime     结束时间
     */
    @Transactional
    public List<LotteryChase> get(String userno, Integer lotteryType, String startTime, String endTime,int state, PageBean<LotteryChase> page) {
        BetweenDate bd = SpecialDateUtils.getBetweenDate(3, startTime, endTime);
        Date startDate = bd.getStartDate();
        Date endDate = bd.getEndDate();
        return chaseDao.get(userno, lotteryType, startDate, endDate,state, page);
    }

    @Transactional
    public void save(LotteryChase entity) {
        chaseDao.insert(entity);
    }

    @Transactional
    public void update(LotteryChase entity) {
        chaseDao.merge(entity);
    }
    @Transactional
    public LotteryChase get(String id){
        return chaseDao.find(id);
    }



}
