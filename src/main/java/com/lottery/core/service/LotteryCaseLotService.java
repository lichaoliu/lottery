package com.lottery.core.service;

import com.lottery.common.contains.*;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.contains.lottery.caselot.*;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.dto.CaseLotRequest;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.*;
import com.lottery.core.domain.*;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.bet.BetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LotteryCaseLotService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${hemaiUserno}")
    private String hemaiUserno;
    @Autowired
    private IdGeneratorDao idDao;
    @Autowired
    private LotteryOrderDAO lotteryOrderDao;
    @Autowired
    private LotteryCaseLotDao lotteryCaseLotDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private LotteryCaseLotBuyDao lotteryCaseLotBuyDao;
    @Autowired
    private BetService betService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountDAO userAccountDAO;
    @Autowired
    private PrizeErrorLogDAO prizeErrorLogDAO;
    @Autowired
    private UserAccountDetailDAO userAccountDetailDAO;
    @Autowired
    private LotteryPhaseService lotteryPhaseService;

    /**
     * 创建订单和合买方案
     */
    @Transactional
    public LotteryCaseLot createCaseLot(CaseLotRequest caseLotRequest, String buyagencyno) {
        betService.lotteryTypeValidate(caseLotRequest.getLotteryTypeNo(),BetType.hemai.value);
        int amount = (int) caseLotRequest.getTotalAmount();
        int prizeOptimize = caseLotRequest.getPrizeOptimize()==YesNoStatus.yes.value?YesNoStatus.yes.value:YesNoStatus.no.value;

        betService.codeValidate(caseLotRequest.getLotteryTypeNo(),caseLotRequest.getPhaseNo(),caseLotRequest.getBetcode(),caseLotRequest.getLotmulti(),caseLotRequest.getOneAmount(),
                new BigDecimal(amount),prizeOptimize);


        UserInfo starter = userInfoDao.find(caseLotRequest.getStarter());
        if (starter == null) {
            throw new LotteryException(ErrorCode.no_user, "合买发起人不存在");
        }

        if (amount < 600) {
            throw new LotteryException(ErrorCode.caselot_totalamt_lessthan_requirements, "合买金额小于要求金额");
        }
        String orderId = idDao.getOrderId();

        int codeFilter = caseLotRequest.getCodeFilter()==YesNoStatus.yes.value?YesNoStatus.yes.value:YesNoStatus.no.value;
        LotteryOrder lotteryorder = betService.createLotteryOrder(hemaiUserno, caseLotRequest.getBetcode(), orderId,
                caseLotRequest.getLotteryTypeNo(), caseLotRequest.getPhaseNo(), caseLotRequest.getLotmulti()
                , new BigDecimal(amount), caseLotRequest.getOneAmount(), BetType.hemai, buyagencyno, orderId,prizeOptimize,codeFilter);

        LotteryCaseLot caseLot = this.createCaseLot(caseLotRequest, orderId, lotteryorder.getDeadline(), starter);
        caseLot.setPhase(lotteryorder.getPhase());
        lotteryorder.setHemaiId(caseLot.getId());
        lotteryorder.setAgentId(caseLot.getId());
        lotteryorder.setOrderStatus(OrderStatus.NOT_SPLIT.value);
        lotteryorder.setPayStatus(PayStatus.PAY_SUCCESS.value);
        lotteryorder.setAccountType(AccountType.hemai.getValue());
        lotteryorder.setMemo("合买投注");
        lotteryOrderDao.update(lotteryorder);

        caseLotToBuy(caseLot, starter.getUserno(), caseLotRequest.getBuyAmt(), caseLotRequest.getSafeAmt(), CaseLotBuyType.starter, false);
        return caseLot;
    }

    /**
     * 外部合买投注
     */
    @Transactional
    public LotteryCaseLotBuy betCaselot(String caseId, String userno, long num, long safeAmt) {
        LotteryCaseLot caseLot = lotteryCaseLotDao.findWithLock(caseId, true);
        if (caseLot == null) {
            throw new LotteryException(ErrorCode.caselot_not_exist, "合买方案不存在");
        }
        LotteryPhase lotteryphase=lotteryPhaseService.getByTypeAndPhase(caseLot.getLotteryType(),caseLot.getPhase());
        if (lotteryphase==null){
            throw new LotteryException(ErrorCode.no_phase, "彩期不存在");
        }
        if (lotteryphase.getForSale()==YesNoStatus.no.value&&caseLot.getState()==CaseLotState.processing.value){
            throw new LotteryException(ErrorCode.not_sale_phase, "彩期未销售");
        }
        CaseLotBuyType caselotbuytype=CaseLotBuyType.follower;
        if(userno.equals(caseLot.getStarter())){
            caselotbuytype=CaseLotBuyType.starter;
        }
        return caseLotToBuy(caseLot, userno, num, safeAmt, caselotbuytype, true);
    }

    /**
     * 购买一定金额的合买
     *
     * @param caseLot        合买方案
     * @param userno         投注用户编号
     * @param num            认购金额
     * @param safeAmt        保底金额
     * @param buyType        购买类型。CaseLotBuyType
     * @param isSendOrderjms 是否发送订单拆票消息
     */
    public LotteryCaseLotBuy caseLotToBuy(LotteryCaseLot caseLot, String userno, long num, long safeAmt, CaseLotBuyType buyType, boolean isSendOrderjms) {
        if (caseLot == null) {
            throw new LotteryException(ErrorCode.caselot_not_exist, "合买方案不存在");
        }
        if ((caseLot.getState().intValue() != CaseLotState.processing.value)&&(caseLot.getState().intValue() != CaseLotState.alreadyBet.value)) {
            throw new LotteryException(ErrorCode.caselot_full, "合买方案已满");
        }



        UserInfo userinfo = userInfoDao.find(userno);
        if (userinfo == null) {
            throw new LotteryException(ErrorCode.no_user, "用户不存在");
        }


        if (num == 0 && safeAmt == 0) {
            throw new LotteryException(ErrorCode.caseLot_safe_not_zero, "合买金额不能都为零");
        }
        if (num < 0 || safeAmt < 0) {
            throw new LotteryException(ErrorCode.caseLot_safe_not_zero, "合买金额不能都为零" + safeAmt);
        }
        if ((num > 0 && num < 100) || (safeAmt > 0 && safeAmt < 100)) {
            throw new LotteryException(ErrorCode.caseLot_safe_not_zero, "合买金额必须大于1元");
        }
        String caselotId = caseLot.getId();
        
        long buyamtByStarter = caseLot.getBuyAmtByStarter();
        long buyamtByFollower = caseLot.getBuyAmtByFollower();
        long totalAmt = caseLot.getTotalAmt();
        if (num + buyamtByStarter + buyamtByFollower > totalAmt) {
			throw new LotteryException(ErrorCode.caselot_full,"合买方案已满");
		}
        //当前总冻结
        long allfrezzSafeAmt = lotteryCaseLotDao.computeFreezeSafeAmt(caselotId).longValue();
        //购买前总金额
        long beforeAllbuyAmt = buyamtByStarter + buyamtByFollower + allfrezzSafeAmt;
        //计算的 购买后的剩余保底金额
        long remainsafeAmt = totalAmt - beforeAllbuyAmt - num;
        if ((remainsafeAmt >= 0 && safeAmt > remainsafeAmt) || (remainsafeAmt < 0 && safeAmt > 0)) {
            throw new LotteryException(ErrorCode.caselot_safeamt_error, "保底金额异常");
        }

        Integer lotteryType = caseLot.getLotteryType();
        String phase = caseLot.getPhase();
        LotteryCaseLotBuy caseLotBuy = createCaseLotBuy(lotteryType, phase, userinfo, num,
                caselotId, safeAmt, buyType, caseLot.getOrderid());
        caseLotBuy.setTotalAmount(new BigDecimal(totalAmt));
        caseLotBuy.setLotsType(caseLot.getLotsType());
        if (num > 0) {
            logger.info("合买扣款caselotId:{},caseLotBuyId:{},num:{}",
                    new Object[]{caselotId, caseLotBuy.getId(), num});
            BigDecimal drawAmt = userAccountService.deductMoney(userno, String.valueOf(caseLotBuy.getId()), AccountType.hemai, AccountDetailType.hemai_deduct,
                    caselotId, new BigDecimal(num), "合买扣款", lotteryType, phase, AgencyType.all.value);
            caseLotBuy.setBuyDrawAmt(drawAmt);


            if (!caseLot.getStarter().equals(userno)) {
                caseLot.setBuyAmtByFollower(buyamtByFollower + num);
            } else {
                caseLot.setBuyAmtByStarter(buyamtByStarter + num);
            }
        }

        if (safeAmt > 0) {
            logger.info("合买保底冻结caselotId:{},caseLotBuyId:{},safeAmt:{}",
                    new Object[]{caselotId, caseLotBuy.getId(), safeAmt});
            BigDecimal drawAmt = userAccountService.freezeAmount(userno, caseLotBuy.getId().toString(), new BigDecimal(safeAmt), AccountType.hemai, AccountDetailType.hemai_safeFreeze,
                    "合买冻结保底", caselotId, lotteryType, phase, "0", true);
            caseLotBuy.setFreezDrawAmt(drawAmt);


            caseLot.setSafeAmt(caseLot.getSafeAmt() + safeAmt);
        }
        lotteryCaseLotBuyDao.merge(caseLotBuy);


        Long computeParticipantCount = lotteryCaseLotDao.computeParticipantCount(caselotId);// 计算参与人数
        caseLot.setParticipantCount(computeParticipantCount);

        long nowAllbuyAmt = beforeAllbuyAmt + num + safeAmt;
        //可投注金额
        long needbetamt = new BigDecimal(totalAmt)
                .multiply(CaseLotConsts.lotteryFillRate)
                .divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP).longValue();

        if (nowAllbuyAmt >= needbetamt) {
            if (isSendOrderjms&&caseLot.getState()==CaseLotState.processing.value) {
                betService.sendJms(caseLot.getOrderid());
                caseLot.setState(CaseLotState.alreadyBet.value);
            }else{
                caseLot.setState(CaseLotState.alreadyBet.value);
            }
        }
        boolean unfreez = false;
        if (caseLot.getBuyAmtByStarter() + caseLot.getBuyAmtByFollower() == totalAmt) {//满员
            caseLot.setState(CaseLotState.finished.value);
            caseLot.setDisplayState(CaseLotDisplayState.full.value());
            caseLot.setDisplayStateMemo(CaseLotDisplayState.full.memo());
            if (caseLot.getSortState() != CaseLotSortState.normal.value) {
                caseLot.setSortState(CaseLotSortState.normal.value);
            }
            unfreez = true;
        }

        //要解冻的金额
        long unfreezeAmount = nowAllbuyAmt - totalAmt;
        if (unfreezeAmount > 0) {
            List<LotteryCaseLotBuy> caselotBuys = lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndState(caselotId, CaseLotBuyState.processing.value());
            unfreezeBuyersWithAmt(caseLot, caselotBuys, new BigDecimal(unfreezeAmount), unfreez);
        }

        lotteryCaseLotDao.merge(caseLot);
        return caseLotBuy;
    }

    /**
     * 创建合买
     */
    private LotteryCaseLot createCaseLot(CaseLotRequest caseLotRequest, String orderid, Date hemaiEndtime, UserInfo userinfo) {
        LotteryCaseLot caseLot = new LotteryCaseLot();
        caseLot.setId(idDao.getLotteryCaselotId());
        caseLot.setBuyAmtByStarter(0L);
        caseLot.setBuyAmtByFollower(0L);
        caseLot.setSafeAmt(0L);
        caseLot.setWinBigAmt(0L);
        caseLot.setWinPreAmt(0L);
        caseLot.setParticipantCount(0L);
        caseLot.setIsCommission(YesNoStatus.no.value);
        caseLot.setCommisionRatio(caseLotRequest.getCommisionRatio());
        if(caseLotRequest.getCommisionRatio()>0){
            caseLot.setIsCommission(YesNoStatus.yes.value);
        }
        caseLot.setDescription(caseLotRequest.getDesc());
        caseLot.setTitle(caseLotRequest.getTitle());
        caseLot.setVisibility(caseLotRequest.getVisibility());
        caseLot.setStarter(userinfo.getUserno());
        caseLot.setStartName(userinfo.getUsername());
        caseLot.setCaselotinfo(caseLotRequest.getCaselotinfo());
        caseLot.setLotteryType(caseLotRequest.getLotteryTypeNo());
        caseLot.setPhase(caseLotRequest.getPhaseNo());
        caseLot.setMinAmt(caseLotRequest.getMinAmt());
        caseLot.setStartTime(new Date());
        caseLot.setTotalAmt(caseLotRequest.getTotalAmount());
        caseLot.setState(CaseLotState.processing.value);
        caseLot.setDisplayState(CaseLotDisplayState.subscribing.value());
        caseLot.setDisplayStateMemo(CaseLotDisplayState.subscribing.memo());
        caseLot.setSortState(CaseLotSortState.normal.value);
        caseLot.setEndTime(hemaiEndtime);
        caseLot.setOrderid(orderid);
        caseLot.setIsExchanged(YesNoStatus.no.value);
        caseLot.setLotsType(caseLotRequest.getLotsType());
        caseLot.setOrderResultStatus(OrderResultStatus.not_open.value);
        lotteryCaseLotDao.insert(caseLot);
        return caseLot;
    }


    /**
     * 计算认购+保底的进度
     *
     * @param caseLot
     * @return 百分比整数
     */
    private BigDecimal computePlan(LotteryCaseLot caseLot) {
        // 认购金额进度
        BigDecimal subscribeRate = (new BigDecimal(caseLot.getBuyAmtByStarter()).add(new BigDecimal(caseLot.getBuyAmtByFollower())))
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(caseLot.getTotalAmt()), 0, BigDecimal.ROUND_HALF_UP);
        // 保底进度
        BigDecimal safeRate = new BigDecimal(caseLot.getSafeAmt())
                .multiply(new BigDecimal(100))
                .divide(new BigDecimal(caseLot.getTotalAmt()), 0, BigDecimal.ROUND_HALF_UP);
        return subscribeRate.add(safeRate);
    }


    /**
     * 根据金额解冻，后买的先解冻
     *
     * @param caseLot
     * @param unfreezeAmt
     * @param flag        解冻金额小于冻结金额时，是否解冻
     */
    @Transactional
    public void unfreezeBuyersWithAmt(LotteryCaseLot caseLot, List<LotteryCaseLotBuy> caselotBuys, BigDecimal unfreezeAmt, boolean flag) {
        for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
            BigDecimal freezeSafeAmt = caseLotBuy.getFreezeSafeAmt();
            if (freezeSafeAmt.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            if (unfreezeAmt.compareTo(freezeSafeAmt) >= 0) {// 如果要解冻的金额大于等于冻结金额
                //将金额解冻
                unfreeze(freezeSafeAmt, caseLotBuy, AccountDetailType.hemai_retUnfreeze);

                caseLot.setSafeAmt(caseLot.getSafeAmt() - freezeSafeAmt.longValue());

                unfreezeAmt = unfreezeAmt.subtract(freezeSafeAmt);
                if (unfreezeAmt.compareTo(BigDecimal.ZERO) <= 0) {
                    break;
                }
            } else {// 如果要解冻的金额小于冻结金额
                if (flag) {
                    //将剩的冻结解冻
                    unfreeze(unfreezeAmt, caseLotBuy, AccountDetailType.hemai_retUnfreeze);

                    caseLot.setSafeAmt(caseLot.getSafeAmt() - unfreezeAmt.longValue());
                }
                break;
            }
        }
    }

    /**
     * 解冻保底金额
     *
     * @param unfreezeAmt

     */
    @Transactional
    public void unfreeze(BigDecimal unfreezeAmt, LotteryCaseLotBuy caseLotBuy, AccountDetailType accountDetailType) {
        if (unfreezeAmt.compareTo(caseLotBuy.getFreezeSafeAmt()) > 0) {
            throw new LotteryException(ErrorCode.caselot_unfree_error, "合买解冻异常");
        }
        caseLotBuy.setFreezeSafeAmt(caseLotBuy.getFreezeSafeAmt().subtract(unfreezeAmt));
        BigDecimal reTurnDrawAmt = BigDecimal.ZERO;
        //如果可提现金额  大于 解冻金额   可提现金额变成 可提现-解冻     返现是 解冻
        if (caseLotBuy.getFreezDrawAmt().compareTo(unfreezeAmt) > 0) {
            reTurnDrawAmt = unfreezeAmt;
            caseLotBuy.setFreezDrawAmt(caseLotBuy.getFreezDrawAmt().subtract(unfreezeAmt));
        } else {
            //可提现金额小于等于 解冻金额   可提现是0  返现是可提现
            reTurnDrawAmt = caseLotBuy.getFreezDrawAmt();
            caseLotBuy.setFreezDrawAmt(BigDecimal.ZERO);
        }

        lotteryCaseLotBuyDao.merge(caseLotBuy);
        userAccountService.unfreezeAmount(caseLotBuy.getUserno(), caseLotBuy.getId() + "", caseLotBuy.getCaselotId(), unfreezeAmt, AccountType.hemai, AccountDetailType.hemai_safeFreeze, accountDetailType, "解冻保底金额", reTurnDrawAmt);
        ;
    }

    /**
     * 创建CaseLotBuy
     *
     * @param lotteryType
     * @param amt
     * @param caseId
     * @param safeAmt
     * @return
     */
    private LotteryCaseLotBuy createCaseLotBuy(Integer lotteryType, String phaseNo, UserInfo userInfo, long amt, String caseId, long safeAmt, CaseLotBuyType buyType, String channel) {
        LotteryCaseLotBuy caseLotBuy = new LotteryCaseLotBuy();
        String id = idDao.getLotteryCaselotBuyId();
        caseLotBuy.setId(id);
        caseLotBuy.setLotteryType(lotteryType);
        caseLotBuy.setPhase(phaseNo);
        caseLotBuy.setBuyTime(new Date());
        caseLotBuy.setCaselotId(caseId);
        caseLotBuy.setFlag(CaseLotBuyState.processing.value());
        caseLotBuy.setNum(new BigDecimal(amt));
        caseLotBuy.setSafeAmt(new BigDecimal(safeAmt));
        caseLotBuy.setFreezeSafeAmt(new BigDecimal(safeAmt));
        caseLotBuy.setUserno(userInfo.getUserno());
        caseLotBuy.setBuyType(buyType.value);
        caseLotBuy.setPrizeAmt(BigDecimal.ZERO);
        caseLotBuy.setCommisionPrizeAmt(BigDecimal.ZERO);
        caseLotBuy.setIsExchanged(YesNoStatus.no.value);
        caseLotBuy.setUserName(userInfo.getUsername());
        caseLotBuy.setRealName(userInfo.getRealName());
        lotteryCaseLotBuyDao.insert(caseLotBuy);
        return caseLotBuy;
    }

    /**
     * 合买撤单
     *
     * @param isvalidata 是否验证
     * @param userno
     * @param caselotId
     * @param canceledBy
     * @param buyState
     */
    @Transactional
    public void cancelCaseLot(boolean isvalidata, String userno, String caselotId, CaseLotDisplayState canceledBy, CaseLotBuyState buyState) {
        LotteryCaseLot caselot = lotteryCaseLotDao.findWithLock(caselotId, true);
        if (caselot == null) {
            throw new LotteryException(ErrorCode.caselot_cancel_error, "合买撤销异常");
        }
        if (isvalidata) {
            if (!userno.equals(caselot.getStarter())) {
                throw new LotteryException(ErrorCode.caseLot_not_starter, "只有发起者才能取消");
            }
            if (caselot.getState().intValue() != CaseLotState.processing.value) {
                throw new LotteryException(ErrorCode.caselot_cancel_error, "合买撤销异常");
            }
            if (computePlan(caselot).compareTo(CaseLotConsts.cancelCaseLotRate) >= 0) {
                throw new LotteryException(ErrorCode.caseLot_rate_bigger50, "撤销的合买进度大于50%");
            }
        }
        cancelCaseLot(caselot, canceledBy, buyState);
    }

    /**
     * 系统进行撤单处理
     */
    @Transactional
    public void syscancelCaslot(String caseLotId) {
        LotteryCaseLot caseLot = lotteryCaseLotDao.findWithLock(caseLotId, true);
        cancelCaseLot(caseLot, CaseLotDisplayState.canceledBySystem, CaseLotBuyState.systemRetract);

    }

    /**
     * 合买撤单
     *
     * @param caselot    合买
     * @param canceledBy
     */
    @Transactional
    protected void cancelCaseLot(LotteryCaseLot caselot, CaseLotDisplayState canceledBy, CaseLotBuyState buyState) {
        if (caselot == null) {
            return;
        }
        if (caselot.getState() == CaseLotState.canceled.value) {
            return;
        }



        // 返还合买金额
        List<LotteryCaseLotBuy> caselotBuys = lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndState(caselot.getId(), CaseLotBuyState.processing.value());
        if (caselotBuys==null||caselotBuys.size()==0){
            logger.error("合买:{},没有购买记录,请检查",caselot.getId());
            return;
        }
        for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
            // 解冻保底金额
            if (caseLotBuy.getFreezeSafeAmt().compareTo(BigDecimal.ZERO) > 0) {
                unfreeze(caseLotBuy.getFreezeSafeAmt(), caseLotBuy, AccountDetailType.hemai_retUnfreeze);
            }
            //返款
            if (caseLotBuy.getNum().compareTo(BigDecimal.ZERO) > 0) {
                userAccountService.hemaiReturnAmt(caseLotBuy.getId(), caseLotBuy.getCaselotId(), caseLotBuy.getUserno(), caseLotBuy.getNum(), buyState, caseLotBuy.getBuyDrawAmt());
                //caseLotBuy.setNum(BigDecimal.ZERO);
                caseLotBuy.setFlag(buyState.value());
                lotteryCaseLotBuyDao.update(caseLotBuy);
            }
        }


        caselot.setState(CaseLotState.canceled.value);
        caselot.setDisplayState(canceledBy.value());
        caselot.setFinishTime(new Date());
        caselot.setDisplayStateMemo(canceledBy.memo());
        if (caselot.getSortState() != CaseLotSortState.normal.value) {
            caselot.setSortState(CaseLotSortState.normal.value);
        }
        lotteryCaseLotDao.merge(caselot);

        // 订单状态设置为取消或流单
        LotteryOrder order = lotteryOrderDao.findWithLock(caselot.getOrderid(), true);
        if(order==null){
        	throw new LotteryException("合买订单不存在,caseid="+caselot.getId());
        }
        if(order.getOrderStatus()==OrderStatus.PRINTED.value){
            throw new LotteryException("订单已出票,合买不能撤销,caseid="+caselot.getId());
        }
        if (canceledBy == CaseLotDisplayState.canceledByUser) {
            order.setOrderStatus(OrderStatus.CANCELLED.value);
        } else {
            //流单
            order.setOrderStatus(OrderStatus.REVOKED.value);
        }
        order.setPayStatus(PayStatus.REFUNDED.getValue());
        lotteryOrderDao.update(order);
    }


    /**
     * 合买出票失败修改方案状态
     *
     * @param caselotId 合买id
     */
    @Transactional
    public void orderFailCancelCaseLot(String caselotId) {
        logger.info("合买出票失败返款 caselotId:{}", caselotId);
        LotteryCaseLot caselot = lotteryCaseLotDao.find(caselotId);
        if (caselot == null) {
            logger.error("合买方案不存在 caselotid:{}", caselotId);
            return;
        }
        if (caselot.getState() == CaseLotState.canceled.value) {
            logger.error("合买方案已经是撤销状态 caselotid:{}", caselotId);
            return;
        }
        caselot.setState(CaseLotState.canceled.value);
        caselot.setDisplayState(CaseLotDisplayState.fail.value());
        caselot.setDisplayStateMemo(CaseLotDisplayState.fail.memo());
        if (caselot.getSortState() != CaseLotSortState.normal.value) {
            caselot.setSortState(CaseLotSortState.normal.value);
        }
        lotteryCaseLotDao.merge(caselot);
    }

    /**
     * 合买撤资
     * @param userno    用户编号
     * @param caseLotId 合买ID
     */
    @Transactional
    public void cancelCaseLot(String userno, String caseLotId) {
        LotteryCaseLot caseLot = lotteryCaseLotDao.findWithLock(caseLotId, true);
        if (caseLot.getStarter().equals(userno)) {
            throw new LotteryException(ErrorCode.caselot_starter_not_cancel_buy, "合买发起人不能撤资");
        }

        if (caseLot.getState() != CaseLotState.processing.value) {
            throw new LotteryException(ErrorCode.caselot_cancel_buy_error, "合买撤资异常");
        }
        if (computePlan(caseLot).compareTo(CaseLotConsts.cancelCaseLotBuyRate) >= 0) {
            throw new LotteryException(ErrorCode.caselot_retract_bigger20, "撤资的合买进度大于20%");
        }

        LotteryOrder lotteryOrder=lotteryOrderDao.findWithLock(caseLot.getOrderid(), true);
        if (lotteryOrder!=null&&lotteryOrder.getOrderStatus()==OrderStatus.PRINTED.value){
            throw new LotteryException(ErrorCode.caselot_cancel_buy_error, "合买订单已出票");
        }

        List<LotteryCaseLotBuy> caselotBuys = lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndUserno(caseLot.getId(), userno);
        BigDecimal cancelAmt = BigDecimal.ZERO;
        for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
            cancelAmt = cancelAmt.add(caseLotBuy.getNum());
            if (caseLotBuy.getNum().compareTo(BigDecimal.ZERO) > 0) {
                userAccountService.hemaiReturnAmt(caseLotBuy.getId(), caseLotBuy.getCaselotId(), caseLotBuy.getUserno(), caseLotBuy.getNum(), CaseLotBuyState.handRetract, caseLotBuy.getBuyDrawAmt());
                //caseLotBuy.setNum(BigDecimal.ZERO);
                caseLotBuy.setFlag(CaseLotBuyState.handRetract.value());
                lotteryCaseLotBuyDao.update(caseLotBuy);
            }
        }


        Long participantCount = lotteryCaseLotDao.computeParticipantCount(caseLot.getId());
        caseLot.setParticipantCount(participantCount);
        if (userno.equals(caseLot.getStarter())) {
            caseLot.setBuyAmtByStarter(caseLot.getBuyAmtByStarter() - cancelAmt.longValue());
        } else {
            caseLot.setBuyAmtByFollower(caseLot.getBuyAmtByFollower() - cancelAmt.longValue());
        }

        lotteryCaseLotDao.update(caseLot);
    }
    @Transactional
    public void canleCaseLotBuy(String caseLotBuyId){
        BigDecimal cancelAmt = BigDecimal.ZERO;
        LotteryCaseLotBuy caseLotBuy=lotteryCaseLotBuyDao.findWithLock(caseLotBuyId,true);
        if (caseLotBuy==null||caseLotBuy.getFlag()==CaseLotBuyState.handRetract.value()){
            logger.error("订单跟单合买:{}不存在或已撤销",caseLotBuyId);
            return;
        }
        if (caseLotBuy.getNum().compareTo(BigDecimal.ZERO) > 0) {
            userAccountService.hemaiReturnAmt(caseLotBuy.getId(), caseLotBuy.getCaselotId(), caseLotBuy.getUserno(), caseLotBuy.getNum(), CaseLotBuyState.handRetract, caseLotBuy.getBuyDrawAmt());
            //caseLotBuy.setNum(BigDecimal.ZERO);
            caseLotBuy.setFlag(CaseLotBuyState.handRetract.value());
            lotteryCaseLotBuyDao.update(caseLotBuy);
        }
        LotteryCaseLot caseLot = lotteryCaseLotDao.findWithLock(caseLotBuy.getCaselotId(), true);
        if (caseLot==null||caseLot.getState() == CaseLotState.canceled.value||caseLot.getState() == CaseLotState.end.value) {
            throw new LotteryException(ErrorCode.caselot_cancel_buy_error, "合买不存在或已是终结状态");
        }
        Long participantCount = lotteryCaseLotDao.computeParticipantCount(caseLot.getId());
        caseLot.setParticipantCount(participantCount);
        if (caseLotBuy.getUserno().equals(caseLot.getStarter())) {
            caseLot.setBuyAmtByStarter(caseLot.getBuyAmtByStarter() - cancelAmt.longValue());
        } else {
            caseLot.setBuyAmtByFollower(caseLot.getBuyAmtByFollower() - cancelAmt.longValue());
        }
        lotteryCaseLotDao.update(caseLot);
    }

    /**
     * 合买结期
     *
     * @param caselotId
     */
    @Transactional
    public void endCaseLot(String caselotId) {
        logger.info("合买截止 caselotId:{}", caselotId);
        LotteryCaseLot caselot = lotteryCaseLotDao.findWithLock(caselotId, true);
        if (caselot == null) {
            return;
        }
        if (caselot.getState() == CaseLotState.end.value) {
            logger.error("合买已截止caselotId:{}", caselotId);
            return;
        }
        if (caselot.getSortState() != CaseLotSortState.normal.value) {// 更新合买申请置顶为普通
            caselot.setSortState(CaseLotSortState.normal.value);
        }
        if (caselot.getState().intValue() == CaseLotState.alreadyBet.value) {
            long allFreezeSafeAmt = lotteryCaseLotDao.computeFreezeSafeAmt(caselot.getId()).longValue();
            long requireAmt = caselot.getTotalAmt() - caselot.getBuyAmtByStarter() - caselot.getBuyAmtByFollower() - allFreezeSafeAmt;

            List<LotteryCaseLotBuy> caselotBuys = lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndState(caselotId, CaseLotBuyState.processing.value());
            if (requireAmt > 0) {
                logger.info("网站用户合买兜底  caselotId:{}, requireLotteryFillAmt:{}", caselotId, requireAmt);
                caseLotToBuy(caselot, hemaiUserno, requireAmt, 0, CaseLotBuyType.follower, true);
            } else if (requireAmt < 0) {// 可以独自完成
                BigDecimal unfrezzamt = new BigDecimal(-requireAmt);
                logger.info("计算可以解冻金额 caselotId:{}, unfrezzamt:{}", caselotId, unfrezzamt);
                unfreezeBuyersWithAmt(caselot, caselotBuys, unfrezzamt, true);
            }

            //投注冻结保底金额
            for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
                // 需要解冻的保底。拿这部分保底参加合买
                BigDecimal freezeSafeAmt = caseLotBuy.getFreezeSafeAmt();
                if (freezeSafeAmt.compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
                // 解冻
                unfreeze(freezeSafeAmt, caseLotBuy, AccountDetailType.hemai_safebetUnfreeze);

                caselot.setSafeAmt(caselot.getSafeAmt() - freezeSafeAmt.longValue());

                //投注冻结金额
                caseLotToBuy(caselot, caseLotBuy.getUserno(), freezeSafeAmt.longValue(), 0, CaseLotBuyType.follower, true);
            }
        }
        if(caselot.getState().intValue() == CaseLotState.alreadyBet.value||caselot.getState().intValue() == CaseLotState.finished.value){
            caselot.setState(CaseLotState.end.value);
            caselot.setFinishTime(new Date());
            caselot.setDisplayState(CaseLotDisplayState.success.value());
            caselot.setDisplayStateMemo(CaseLotDisplayState.success.memo());
            lotteryCaseLotDao.merge(caselot);
            lotteryCaseLotBuyDao.updateStateByCaselotId(caselotId,CaseLotBuyState.success.value);


        }

    }


    /**
     * 更新合买中奖状态
     *
     * @param caseid
     * @param prizeamt
     * @param preprizeamt
     * @return
     */
    @Transactional
    public LotteryCaseLot updateCaseLotWin(String caseid, BigDecimal prizeamt, BigDecimal preprizeamt) {
        LotteryCaseLot caselot = lotteryCaseLotDao.findWithLock(caseid,true);
        if (caselot == null) {
            logger.error("该合买方案不存在caselotid:{}", caseid);
            return null;
        }

        if (caselot.getIsExchanged() != null && caselot.getIsExchanged() == YesNoStatus.yes.value) {
            logger.error("该合买方案已派奖caselotid:{}", caseid);
            return caselot;
        }
        if (prizeamt == null || preprizeamt == null) {
            return null;
        }
        caselot.setWinBigAmt(prizeamt.longValue());
        caselot.setWinPreAmt(preprizeamt.longValue());
        caselot.setDisplayState(CaseLotDisplayState.win.value());
        caselot.setDisplayStateMemo(CaseLotDisplayState.win.memo());
        caselot.setIsExchanged(YesNoStatus.yes.value);
        caselot.setWinStartTime(new Date());
        lotteryCaseLotDao.merge(caselot);
        return caselot;
    }


    /*
     * 取消 合买购买记录派奖
     */
    @Transactional
    public void cancelCaseLotbuyPrize(LotteryCaseLotBuy caseLotBuy) {
        UserAccountDetail uad = userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId().toString(), AccountType.hemai, AccountDetailType.add);
        BigDecimal prizeAmt = caseLotBuy.getPrizeAmt();
        caseLotBuy.setPrizeAmt(BigDecimal.ZERO);
        caseLotBuy.setIsExchanged(YesNoStatus.no.value);
        lotteryCaseLotBuyDao.merge(caseLotBuy);
        if (uad == null || prizeAmt == null || prizeAmt.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("{}派奖不存在或中奖金额不存在", caseLotBuy.getId());
            return;
        }
        String userno = caseLotBuy.getUserno();
        UserAccount account = userAccountDAO.findWithLock(userno, true);
        if (account == null) {
            throw new LotteryException("参与合买账号不存在");
        }

        PrizeErrorLog errorLog = new PrizeErrorLog();
        errorLog.setOrderId(caseLotBuy.getId());
        errorLog.setTransactionId(uad.getId());
        errorLog.setBetType(BetType.hemai.getValue());
        errorLog.setUserno(caseLotBuy.getUserno());
        errorLog.setLotteryType(caseLotBuy.getLotteryType());
        errorLog.setPhase(caseLotBuy.getPhase());
        errorLog.setAmt(prizeAmt);
        errorLog.setBalance(account.getBalance());
        errorLog.setDrawBalance(account.getDrawbalance());

        account.setTotalBalance(account.getTotalBalance().subtract(prizeAmt));
        account.setBalance(account.getBalance().subtract(prizeAmt));
        account.setDrawbalance(account.getDrawbalance().subtract(prizeAmt));
        account.setTotalprizeamt(account.getTotalprizeamt().subtract(prizeAmt));


        if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
            account.setDrawbalance(account.getBalance());
        }

        UserAccountDetail accountDetail=userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId(), AccountType.drawprize, AccountDetailType.add);


        UserAccountDetail newUad = userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId(), AccountType.hemai, AccountDetailType.error_add);

        if (newUad != null) {
            newUad.setFreeze(account.getFreeze());
            newUad.setBalance(account.getBalance());
            newUad.setDrawbalance(account.getDrawbalance());
            newUad.setAmt(prizeAmt);
            newUad.setCreatetime(new Date());
            userAccountDetailDAO.merge(newUad);
        } else {
            UserAccountDetail newUad1 = new UserAccountDetail(idDao.getUserAccountDetailId(), userno, account.getUserName(), caseLotBuy.getId(), new Date(), prizeAmt, AccountType.hemai, CommonStatus.success, account.getBalance(), AccountDetailType.error_add, account.getDrawbalance(), account.getFreeze(), "开奖错误,扣回奖金", SignStatus.flat);
            newUad1.setLotteryType(caseLotBuy.getLotteryType());
            newUad1.setPhase(caseLotBuy.getPhase());
            newUad1.setGiveAmount(BigDecimal.ZERO);
            newUad1.setNotDrawAmount(BigDecimal.ZERO);
            newUad1.setDrawAmount(prizeAmt);
            newUad1.setAgencyNo(uad.getAgencyNo());
            newUad1.setOtherid(caseLotBuy.getCaselotId());
            userAccountDetailDAO.insert(newUad1);
        }
        userAccountDetailDAO.remove(uad);
        userAccountDAO.merge(account);


        errorLog.setAfterBalance(account.getBalance());
        errorLog.setAfterDrawBalance(account.getDrawbalance());
        errorLog.setCreateTime(new Date());
        prizeErrorLogDAO.insert(errorLog);


        if (accountDetail!=null){
            userAccountDetailDAO.remove(accountDetail);
        }
    }


    @Transactional
    public void cancelCaseLotBuyCommisionPrize(LotteryCaseLotBuy caseLotBuy) {
        BigDecimal commisionPrize = caseLotBuy.getCommisionPrizeAmt();
        if (commisionPrize.compareTo(BigDecimal.ZERO) > 0) {
            UserAccountDetail uad = userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId().toString(), AccountType.hemai, AccountDetailType.hemai_yongjin);
            if (uad == null) {
                return;
            }
            String userno = caseLotBuy.getUserno();
            UserAccount account = userAccountDAO.findWithLock(userno, true);
            if (account == null) {
                throw new LotteryException("参与合买账号不存在");
            }
            BigDecimal prizeAmt = uad.getAmt();
            userAccountDetailDAO.remove(uad);
            PrizeErrorLog errorLog = new PrizeErrorLog();
            errorLog.setOrderId(caseLotBuy.getId());
            errorLog.setTransactionId(uad.getId());
            errorLog.setUserno(caseLotBuy.getUserno());
            errorLog.setLotteryType(caseLotBuy.getLotteryType());
            errorLog.setPhase(caseLotBuy.getPhase());
            errorLog.setAmt(prizeAmt);
            errorLog.setBalance(account.getBalance());
            errorLog.setDrawBalance(account.getDrawbalance());
            errorLog.setBetType(BetType.hemai.getValue());

            account.setTotalBalance(account.getTotalBalance().subtract(prizeAmt));
            account.setBalance(account.getBalance().subtract(prizeAmt));
            account.setDrawbalance(account.getDrawbalance().subtract(prizeAmt));
            account.setTotalprizeamt(account.getTotalprizeamt().subtract(prizeAmt));
            if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
                account.setDrawbalance(account.getBalance());
            }

            UserAccountDetail newUad = userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId().toString(), AccountType.hemai, AccountDetailType.hemai_yongjin_error);


            if (newUad != null) {
                newUad.setFreeze(account.getFreeze());
                newUad.setBalance(account.getBalance());
                newUad.setDrawbalance(account.getDrawbalance());
                newUad.setAmt(prizeAmt);
                newUad.setCreatetime(new Date());
                userAccountDetailDAO.merge(newUad);
            } else {
                UserAccountDetail newUad1 = new UserAccountDetail(idDao.getUserAccountDetailId(), userno, account.getUserName(), caseLotBuy.getId(), new Date(), prizeAmt, AccountType.hemai, CommonStatus.success, account.getBalance(), AccountDetailType.hemai_yongjin_error, account.getDrawbalance(), account.getFreeze(), "开奖错误,扣回佣金", SignStatus.flat);
                newUad1.setLotteryType(caseLotBuy.getLotteryType());
                newUad1.setPhase(caseLotBuy.getPhase());
                newUad1.setGiveAmount(BigDecimal.ZERO);
                newUad1.setNotDrawAmount(BigDecimal.ZERO);
                newUad1.setDrawAmount(prizeAmt);
                newUad1.setAgencyNo(uad.getAgencyNo());
                newUad1.setOtherid(caseLotBuy.getCaselotId());
                userAccountDetailDAO.insert(newUad1);
            }
            userAccountDetailDAO.remove(uad);
            userAccountDAO.merge(account);

            caseLotBuy.setCommisionPrizeAmt(BigDecimal.ZERO);
            lotteryCaseLotBuyDao.merge(caseLotBuy);

            errorLog.setAfterBalance(account.getBalance());
            errorLog.setAfterDrawBalance(account.getDrawbalance());
            errorLog.setCreateTime(new Date());
            prizeErrorLogDAO.insert(errorLog);

            UserAccountDetail accountDetail=userAccountDetailDAO.getByPayIdAndType(caseLotBuy.getId(), AccountType.hemai, AccountDetailType.hemai_yongjin);
            if (accountDetail!=null){
                userAccountDetailDAO.remove(accountDetail);
            }
        }

    }

    /**
     * 判断是否公开方案
     *
     * @param userno 用户编号
     * @return
     */
    public boolean judgeVisibility(String userno, LotteryCaseLot caseLot) {
    	if (caseLot.getStarter().equals(userno)) {
			return true;
		}
		Integer vstate = caseLot.getVisibility();
		// 对所有人立即公开
		if (vstate == CaseLotVisibility.open.value) {
			return true;
		}
		// 对所有人截止后公开
		if (vstate == CaseLotVisibility.endOpen.value && caseLot.getState() == CaseLotState.end.value) {
			return true;
		}
		if (StringUtil.isNotEmpt(userno)) {
			List<LotteryCaseLotBuy> caselotBuys = lotteryCaseLotBuyDao.findCaseLotBuysByCaselotIdAndUserno(caseLot.getId(), userno);
			if (caselotBuys.size() == 0) {
				return false;
			} else {
				// 对跟单者立即公开
				if (vstate == CaseLotVisibility.open2Follower.value) {
					return true;
				}
				// 对跟单者截止后公开
				if (vstate == CaseLotVisibility.endOpern2Follower.value && caseLot.getState() == CaseLotState.end.value) {
					return true;
				}
			}
		}
        return false;
    }

    @Transactional
    public void sortCaseLot(String caseid, Integer sortState) {
        LotteryCaseLot caseLot = lotteryCaseLotDao.find(caseid);
        caseLot.setSortState(sortState);
        lotteryCaseLotDao.merge(caseLot);
    }

    /**
     * 根据id查询
     * *
     */
    @Transactional
    public LotteryCaseLot get(String id) {
        return lotteryCaseLotDao.find(id);
    }

    @Transactional
    public void update(LotteryCaseLot caseLot) {
        lotteryCaseLotDao.merge(caseLot);
    }



    /**
     * 根据状态查询
     *
     * @param max    最大条数
     * @param status 状态
     */
    @Transactional
    public List<LotteryCaseLot> getByStatus(int max, int status) {
        return lotteryCaseLotDao.getByStatus(max, status);
    }


}
