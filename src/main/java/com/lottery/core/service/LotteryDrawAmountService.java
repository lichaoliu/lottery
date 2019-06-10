package com.lottery.core.service;

import com.lottery.common.PageBean;
import com.lottery.common.contains.*;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.mail.MailTool;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryDrawAmountDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserDrawBankDAO;
import com.lottery.core.domain.LotteryDrawAmount;
import com.lottery.core.domain.UserDrawBank;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.service.account.UserAccountService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LotteryDrawAmountService {
    @Autowired
    private LotteryDrawAmountDAO lotteryDrawAmountDAO;
    @Autowired
    private IdGeneratorDao idDao;
    @Autowired
    private UserAccountService useAccountService;
    @Autowired
    private UserAccountDetailDAO userAccountDetailDao;
    @Autowired
    private UserAccountDAO userAccountDao;
    @Autowired
    private UserDrawBankDAO userDrawBankDAO;
    @Autowired
    private MailTool mailToolImpl;

    protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
    
    /**
     * 根据提现结果操作数据库
     *
     * @param id            提现id
     * @param flag          true 成功，false 失败
     * @param memo          充值结果描述
     * @param drawAccountNo 提现账号
     */
    @Transactional
    public void doResult(String id, String drawAccountNo, String money, boolean flag, String memo, Date finishTime) {

        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.findWithLock(id, true);
        if (drawAmount == null) {
            throw new LotteryException(ErrorCode.account_error, "提现记录不存在:" + id);
        }
        if (drawAmount.getStatus() == DrawAmountStatus.success.getValue() || drawAmount.getStatus() == DrawAmountStatus.failure.getValue()) {
            logger.error("提现记录{}已完成", id);
            return;
        }


        if (Double.parseDouble(money) != drawAmount.getDrawAmount().divide(new BigDecimal(100)).doubleValue()) {
            //mailToolImpl.sendEmail("支付宝提现订单" + id + "交易金额有误");
            throw new LotteryException(ErrorCode.account_error, "提现订单" + id + "交易金额有误,money="+money+",amount="+drawAmount.getDrawAmount().divide(new BigDecimal(100)).doubleValue());
        }
        if (flag) {
        	if (!drawAccountNo.trim().equals(drawAmount.getBankId().trim())){
                throw new LotteryException(ErrorCode.account_error, "提现订单" + id + "账户有误,drawaccountno="+drawAccountNo+",bankid="+drawAmount.getBankId());
            }
        }

        UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.freeze);
        if (useraccountDetail == null) {
            throw new LotteryException(ErrorCode.account_error, "提现冻结记录不存在:" + id);
        }
        if (drawAmount.getDrawAmount().compareTo(useraccountDetail.getAmt()) != 0) {
            throw new LotteryException(ErrorCode.system_error, "提现记录和冻结记录的金额不一样:" + id);
        }
        String userno = drawAmount.getUserno();
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }


        try {
            if (flag) {
                drawAmount.setStatus(DrawAmountStatus.success.getValue());
                drawAmount.setFinishTime(finishTime);
                drawAmount.setDescription(memo);
                lotteryDrawAmountDAO.merge(drawAmount);
                BigDecimal amount = drawAmount.getDrawAmount();

                userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
                userAccount.setLastTradeTime(new Date());
                userAccount.setLastTradeamt(amount);

                userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
                userAccountDao.update(userAccount);
                String transactioinid = idDao.getUserAccountDetailId();
                UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.deduct, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现成功扣款", SignStatus.out);
                userAccountDetailDao.insert(uad);
            } else {
                drawAmount.setStatus(DrawAmountStatus.failure.getValue());
                drawAmount.setFinishTime(finishTime);
                drawAmount.setDescription(memo);
                lotteryDrawAmountDAO.merge(drawAmount);
                BigDecimal amount = drawAmount.getDrawAmount();

                userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
                userAccount.setLastTradeTime(new Date());
                userAccount.setLastTradeamt(amount);
                userAccount.setBalance(userAccount.getBalance().add(amount));
                userAccount.setDrawbalance(userAccount.getDrawbalance().add(amount));
                if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
                    userAccount.setDrawbalance(userAccount.getBalance());
                }
                userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
                userAccountDao.update(userAccount);
                String transactioinid = idDao.getUserAccountDetailId();
                if(StringUtils.isEmpty(memo)){
                    memo="提现失败解冻";
                }
                UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.unfreeze, userAccount.getDrawbalance(), userAccount.getFreeze(), memo, SignStatus.flat);

                uad.setOtherid(transactioinid);
                uad.setLotteryType(1);
                uad.setPhase("1");
                uad.setDrawAmount(amount);
                userAccountDetailDao.insert(uad);
            }
        } catch (Exception e) {
            throw new LotteryException(ErrorCode.system_error, id + ":提现结果操作错误");
        }
    }

    /**
     * 提现成功手动做失败处理
     *
     * @param id   提现id
     * @param memo 失败原因
     */
    @Transactional
    public void handFaile(String id, String memo) {
        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.findWithLock(id, true);
        if (drawAmount == null) {
            throw new LotteryException(ErrorCode.account_error, "提现记录不存在:" + id);
        }
        if (drawAmount.getStatus() == DrawAmountStatus.failure.getValue()) {
            logger.error("提现记录{}已完成手动处理", id);
            return;
        }
        UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.freeze);
        if (useraccountDetail == null) {
            throw new LotteryException(ErrorCode.account_error, "提现冻结记录不存在:" + id);
        }
        if (drawAmount.getDrawAmount().compareTo(useraccountDetail.getAmt()) != 0) {
            throw new LotteryException(ErrorCode.system_error, "提现记录和冻结记录的金额不一样:" + id);
        }
        UserAccountDetail useraccountDetail2 = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.deduct);
        if (useraccountDetail2 == null) {
            throw new LotteryException(ErrorCode.account_error, "提现扣款记录不存在:" + id);
        }
        UserAccountDetail useraccountDetail3 = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.unfreeze);
        if (useraccountDetail3 != null) {
            throw new LotteryException(ErrorCode.account_error, "提现失败返款已完成:" + id);
        }
        String userno = drawAmount.getUserno();
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        try {
            drawAmount.setStatus(DrawAmountStatus.failure.getValue());
            drawAmount.setFinishTime(new Date());
            drawAmount.setDescription(memo);
            lotteryDrawAmountDAO.merge(drawAmount);
            BigDecimal amount = drawAmount.getDrawAmount();

            userAccount.setBalance(userAccount.getBalance().add(amount));
            userAccount.setLastTradeTime(new Date());
            userAccount.setLastTradeamt(amount);
            userAccount.setDrawbalance(userAccount.getDrawbalance().add(amount));
            if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
                userAccount.setDrawbalance(userAccount.getBalance());
            }
            userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
            userAccountDao.update(userAccount);
            String transactioinid = idDao.getUserAccountDetailId();
            UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.unfreeze, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现失败返款", SignStatus.in);
            uad.setOtherid(transactioinid);
            uad.setLotteryType(1);
            uad.setPhase("1");
            uad.setDrawAmount(amount);
            userAccountDetailDao.insert(uad);
        } catch (Exception e) {
            throw new LotteryException(ErrorCode.system_error, id + "提现失败返款操作失败");
        }

    }

    /**
     *
     *将提现成功的驳回
     * @param id   提现id
     * @param memo 失败原因
     */
    @Transactional
    public void handReject(String id, String memo) {
        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.findWithLock(id, true);
        if (drawAmount == null) {
            throw new LotteryException(ErrorCode.account_error, "提现记录不存在:" + id);
        }
        if (drawAmount.getStatus() == DrawAmountStatus.failure.getValue() || drawAmount.getStatus() == DrawAmountStatus.success.getValue()) {
            logger.error("提现记录{}已完成手动处理", id);
            return;
        }

        UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.freeze);
        if (useraccountDetail == null) {
            throw new LotteryException(ErrorCode.account_error, "提现冻结记录不存在:" + id);
        }
        if (drawAmount.getDrawAmount().compareTo(useraccountDetail.getAmt()) != 0) {
            throw new LotteryException(ErrorCode.system_error, "提现记录和冻结记录的金额不一样:" + id);
        }
        UserAccountDetail useraccountDetail3 = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.unfreeze);
        if (useraccountDetail3 != null) {
            throw new LotteryException(ErrorCode.account_error, "提现失败返款已完成:" + id);
        }
        String userno = drawAmount.getUserno();
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        try {
            drawAmount.setStatus(DrawAmountStatus.failure.getValue());
            drawAmount.setFinishTime(new Date());
            drawAmount.setDescription(memo);
            lotteryDrawAmountDAO.merge(drawAmount);
            BigDecimal amount = drawAmount.getDrawAmount();
            userAccount.setBalance(userAccount.getBalance().add(amount));
            userAccount.setDrawbalance(userAccount.getDrawbalance().add(amount));
            userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
            userAccount.setLastTradeTime(new Date());
            userAccount.setLastTradeamt(amount);
            if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
                userAccount.setDrawbalance(userAccount.getBalance());
            }
            userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
            userAccountDao.update(userAccount);
            String transactioinid = idDao.getUserAccountDetailId();
            UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.unfreeze, userAccount.getDrawbalance(), userAccount.getFreeze(), "金额存在差异，驳回 ", SignStatus.in);

            userAccountDetailDao.insert(uad);
        } catch (Exception e) {
            throw new LotteryException(ErrorCode.system_error, id + "手动驳回操作失败");
        }

    }



    @Transactional
    public String updateZfbAutoDrawStatus(List<LotteryDrawAmount> lotteryDrawList,int operateType) {

        try {

            String batchNo = idDao.getDrawBatchId();
            for (LotteryDrawAmount lotteryDraw : lotteryDrawList) {
                lotteryDraw.setOperateType(operateType);
                lotteryDraw.setStatus(DrawAmountStatus.handing.value);
                lotteryDraw.setBatchId(batchNo);
                lotteryDraw.setSubmitTime(new Date());
                lotteryDrawAmountDAO.merge(lotteryDraw);
            }
            return batchNo;
        } catch (Exception e) {
            logger.error("提现数据异常", e);
            throw new LotteryException(ErrorCode.system_error, "提现更新数据库异常");
        }

    }

    /**
     * 查询指定id审核成功的记录
     *
     * @param drawType
     * @param id
     * @return
     */
    @Transactional
    public List<LotteryDrawAmount> queryDrawAmountList(int drawType, String id) {
        return lotteryDrawAmountDAO.queryDrawAmountList(drawType, id);
    }

    @Transactional
    public void audit(String id, Integer status, String memo) {
        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.find(id);
        //未处理
        if(drawAmount.getStatus() == DrawAmountStatus.notdo.getValue()){
        	//审核
        	if(status == DrawAmountStatus.haschecked.getValue()){
        		drawAmount.setStatus(DrawAmountStatus.haschecked.getValue());
                lotteryDrawAmountDAO.update(drawAmount);
            //驳回
        	}else if(status == DrawAmountStatus.failure.getValue()){
        		doResult(id, drawAmount.getBankId(), drawAmount.getDrawAmount().divide(new BigDecimal(100)).toString(), false, memo, new Date());
        	}
        }else if(drawAmount.getStatus() == DrawAmountStatus.haschecked.getValue()){
        	//已审核过 重复审核
        	if(status == DrawAmountStatus.haschecked.getValue()){
        		throw new LotteryException("请注意 已经被人审核过，千万不要重复打钱");
        	//审核成功之后还可以驳回
        	}else if(status == DrawAmountStatus.failure.getValue()){
        		doResult(id, drawAmount.getBankId(), drawAmount.getDrawAmount().divide(new BigDecimal(100)).toString(), false, memo, new Date());
        	}
        }else{
        	  //不是未处理 已审核不让再操作
        	throw new LotteryException("提现状态已经是:"+drawAmount.getStatus()+"，不允许再操作");
        }
    }
    
    /**
     * 根据提现类型，状态，批次号查询
     * @param  drawType 提现类型
     * @param  status 状态
     * @param  batchId 批次编号
     * */

    @Transactional
    public List<LotteryDrawAmount> queryDrawAmountList(int drawType, int status, String batchId) {
        return lotteryDrawAmountDAO.queryDrawAmountList(drawType, status, batchId);
    }

    @Transactional
    public List<LotteryDrawAmount> getHasAuditAndUpdate(String batchId) {
        List<LotteryDrawAmount> list = lotteryDrawAmountDAO.getByStatusAndDrawType(500, DrawAmountStatus.haschecked.value, DrawType.bank_draw.value);
        for (LotteryDrawAmount lotteryDrawAmount : list) {
            lotteryDrawAmount.setBatchId(batchId);
            lotteryDrawAmount.setStatus(DrawAmountStatus.handing.value);
            lotteryDrawAmount.setOperateType(DrawOperateType.zfb_bankdraw_auto.value);
            lotteryDrawAmount.setSubmitTime(new Date());
            lotteryDrawAmountDAO.update(lotteryDrawAmount);
        }
        return list;
    }

    /**
     * 提现记录查询
     *
     * @param userno    用户名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param page      分页
     *                  *
     */
    @Transactional
    public List<LotteryDrawAmount> getDrawAmountList(String userno, Date startTime, Date endTime, PageBean<LotteryDrawAmount> page) {
        return lotteryDrawAmountDAO.getDrawAmountList(userno, startTime, endTime, page);
    }

    /**
     * 提现成功修改
     *
     * @param id 提现记录id
     */
    @Transactional
    public void handDrawAmont(String id) {
        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.findWithLock(id, true);
        if (drawAmount.getStatus() == DrawAmountStatus.success.value) {
            logger.error("提现:{}已完成", id);
            return;
        }

        if (drawAmount.getStatus() != DrawAmountStatus.handing.value) {
            logger.error("提现:{}状态不是处理中", id);
            return;
        }

        String userno = drawAmount.getUserno();
        UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.freeze);
        if (useraccountDetail == null) {
            throw new LotteryException(ErrorCode.account_error, "提现冻结记录不存在:" + id);
        }
        if (drawAmount.getDrawAmount().compareTo(useraccountDetail.getAmt()) != 0) {
            throw new LotteryException(ErrorCode.system_error, "提现记录和冻结记录的金额不一样:" + id);
        }
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        drawAmount.setStatus(DrawAmountStatus.success.getValue());
        drawAmount.setFinishTime(new Date());
        drawAmount.setDescription("成功");
        lotteryDrawAmountDAO.merge(drawAmount);
        BigDecimal amount = drawAmount.getDrawAmount();

        userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
        userAccount.setLastTradeTime(new Date());
        userAccount.setLastTradeamt(amount);
        userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
        userAccountDao.update(userAccount);
        String transactioinid = idDao.getUserAccountDetailId();
        UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.deduct, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现成功扣款", SignStatus.out);
        uad.setOtherid(transactioinid);
        uad.setLotteryType(1);
        uad.setPhase("1");
        uad.setDrawAmount(amount);
        userAccountDetailDao.insert(uad);
    }


    /**
     * 提现成功修改
     *
     * @param id 提现记录id
     */
    @Transactional
    public void handDrawAmonthaschecked(String id) {
        LotteryDrawAmount drawAmount = lotteryDrawAmountDAO.findWithLock(id, true);
        if (drawAmount.getStatus() == DrawAmountStatus.success.value) {
            logger.error("提现:{}已完成", id);
            return;
        }

        if (drawAmount.getStatus() != DrawAmountStatus.haschecked.value) {
            logger.error("提现:{}状态不是审核完成", id);
            return;
        }

        String userno = drawAmount.getUserno();
        UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(id, AccountType.withdraw, AccountDetailType.freeze);
        if (useraccountDetail == null) {
            throw new LotteryException(ErrorCode.account_error, "提现冻结记录不存在:" + id);
        }
        if (drawAmount.getDrawAmount().compareTo(useraccountDetail.getAmt()) != 0) {
            throw new LotteryException(ErrorCode.system_error, "提现记录和冻结记录的金额不一样:" + id);
        }
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        drawAmount.setStatus(DrawAmountStatus.success.getValue());
        drawAmount.setFinishTime(new Date());
        drawAmount.setDescription("成功");
        lotteryDrawAmountDAO.merge(drawAmount);
        BigDecimal amount = drawAmount.getDrawAmount();

        userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
        userAccount.setLastTradeTime(new Date());
        userAccount.setLastTradeamt(amount);
        userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金
        userAccountDao.update(userAccount);
        String transactioinid = idDao.getUserAccountDetailId();
        UserAccountDetail uad = new UserAccountDetail(transactioinid, userno, userAccount.getUserName(), id, new Date(), amount, AccountType.withdraw, CommonStatus.success, userAccount.getBalance(), AccountDetailType.deduct, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现成功扣款", SignStatus.out);
        uad.setOtherid(transactioinid);
        uad.setLotteryType(1);
        uad.setPhase("1");
        uad.setDrawAmount(amount);
        userAccountDetailDao.insert(uad);
    }

    @Transactional
    public List<LotteryDrawAmount> getByBatchId(String batchId) {
        return lotteryDrawAmountDAO.getByBatchId(batchId);
    }

    /**
     * 根据批次id更新状态
     *
     * @param batchId
     * @param status
     */
    @Transactional
    public void updateStatusByBatchId(String batchId, int status) {
        lotteryDrawAmountDAO.updateStatusByBatchId(batchId, status);
    }

    /**
     * 更新状态
     *
     * @param id     主键
     * @param status 状态
     */
    @Transactional
    public void updateStatusById(String id, int status) {
        lotteryDrawAmountDAO.updateStatusById(id, status);
    }

    /**
     * 用户提现操作
     *
     * @param userno     用户编号
     * @param cardid     绑定账号id(userdrawbank)
     * @param drawAmount 提现金额
     */
    @Transactional
    public LotteryDrawAmount drawAmount(String userno, String cardid, String drawAmount) {
        UserDrawBank drawBank = userDrawBankDAO.find(cardid);
        if (drawBank == null) {
            throw new LotteryException(ErrorCode.card_bind_noexits, "账号信息不存在,cardid="+cardid);
        }
        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
            throw new LotteryException(ErrorCode.account_drawamt_not_enough, "可提现金额大于余额");
        }
        BigDecimal amount = new BigDecimal(drawAmount).multiply(new BigDecimal(100));
        if (userAccount.getDrawbalance().compareTo(amount) < 0) {
            throw new LotteryException(ErrorCode.account_drawamt_not_enough, "可以体现金额不足,可体现金额:" + userAccount.getDrawbalance() + ",执行金额" + drawAmount);
        }
        try {
            LotteryDrawAmount lotteryDrawAmount = new LotteryDrawAmount();
            String id = idDao.getDrawId();
            lotteryDrawAmount.setId(id);
            lotteryDrawAmount.setBankId(drawBank.getBankCard());
            lotteryDrawAmount.setCreateTime(new Date());
            lotteryDrawAmount.setStatus(DrawAmountStatus.notdo.getValue());
            lotteryDrawAmount.setDrawAmount(amount);
            lotteryDrawAmount.setDescription("提现申请");
            lotteryDrawAmount.setUserno(userno);
            lotteryDrawAmount.setFee(new BigDecimal("0"));
            lotteryDrawAmount.setRealAmount(amount);
            lotteryDrawAmount.setUserName(drawBank.getRealname());
            lotteryDrawAmount.setDrawType(drawBank.getDrawType());
            lotteryDrawAmount.setDrawBankId(cardid);
            if (drawBank.getDrawType() == DrawType.bank_draw.value) {
                lotteryDrawAmount.setBankAddress(drawBank.getBranch());
                lotteryDrawAmount.setBankType(drawBank.getBankType());
                lotteryDrawAmount.setProvince(drawBank.getProvince());
                lotteryDrawAmount.setCity(drawBank.getCity());
                lotteryDrawAmount.setBankName(drawBank.getBankName());
            }
            
            lotteryDrawAmountDAO.insert(lotteryDrawAmount);

            userAccount.setLastfreeze(amount);
            userAccount.setBalance(userAccount.getBalance().subtract(amount));
            userAccount.setDrawbalance(userAccount.getDrawbalance().subtract(amount));
            userAccount.setFreeze(userAccount.getFreeze().add(amount));
            userAccount.setLastTradeTime(new Date());
            userAccount.setLastTradeamt(amount);
            if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
                userAccount.setDrawbalance(userAccount.getBalance());
            }
            userAccountDao.merge(userAccount);
            String tid = idDao.getUserAccountDetailId();
            UserAccountDetail uad = new UserAccountDetail(tid, lotteryDrawAmount.getUserno(), userAccount.getUserName(), lotteryDrawAmount.getId(), new Date(), amount, AccountType.withdraw, CommonStatus.success,
                    userAccount.getBalance(), AccountDetailType.freeze, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现冻结", SignStatus.flat);
            uad.setOtherid(tid);
            uad.setLotteryType(1);
            uad.setPhase("1");
            uad.setDrawAmount(amount);
            userAccountDetailDao.insert(uad);
            return lotteryDrawAmount;
        } catch (Exception e) {
            logger.error("发起提现失败", e);
            throw new LotteryException("提现失败");
        }

    }
    /**
     * 出票商提现
     * */
    @Transactional
    public LotteryDrawAmount merchantDrawAmount(String userno, String drawAmount) {

        UserAccount userAccount = useAccountService.getWithLock(userno, true);
        if (userAccount == null) {
            throw new LotteryException(ErrorCode.no_account, "账号不存在" + userno);
        }
        if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
            throw new LotteryException(ErrorCode.account_drawamt_not_enough, "可提现金额大于余额");
        }
        BigDecimal amount = new BigDecimal(drawAmount).multiply(new BigDecimal(100));
        if (userAccount.getDrawbalance().compareTo(amount) < 0) {
            throw new LotteryException(ErrorCode.account_drawamt_not_enough, "可以体现金额不足,可体现金额:" + userAccount.getDrawbalance() + ",执行金额" + drawAmount);
        }
        try {
            LotteryDrawAmount lotteryDrawAmount = new LotteryDrawAmount();
            String id = idDao.getDrawId();
            lotteryDrawAmount.setId(id);
            lotteryDrawAmount.setBankId(userno);
            lotteryDrawAmount.setCreateTime(new Date());
            lotteryDrawAmount.setStatus(DrawAmountStatus.notdo.getValue());
            lotteryDrawAmount.setDrawAmount(amount);
            lotteryDrawAmount.setDescription("接入商提现申请");
            lotteryDrawAmount.setUserno(userno);
            lotteryDrawAmount.setFee(new BigDecimal("0"));
            lotteryDrawAmount.setRealAmount(amount);
            lotteryDrawAmount.setUserName(userno);
            lotteryDrawAmount.setDrawType(DrawType.hand_raw.value);
            lotteryDrawAmount.setDrawBankId(userno);


            lotteryDrawAmountDAO.insert(lotteryDrawAmount);

            userAccount.setLastfreeze(amount);
            userAccount.setBalance(userAccount.getBalance().subtract(amount));
            userAccount.setDrawbalance(userAccount.getDrawbalance().subtract(amount));
            userAccount.setFreeze(userAccount.getFreeze().add(amount));
            userAccount.setLastTradeTime(new Date());
            userAccount.setLastTradeamt(amount);
            if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
                userAccount.setDrawbalance(userAccount.getBalance());
            }
            userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金

            userAccountDao.merge(userAccount);
            String tid = idDao.getUserAccountDetailId();
            UserAccountDetail uad = new UserAccountDetail(tid, lotteryDrawAmount.getUserno(), userAccount.getUserName(), lotteryDrawAmount.getId(), new Date(), amount, AccountType.withdraw, CommonStatus.success,
                    userAccount.getBalance(), AccountDetailType.freeze, userAccount.getDrawbalance(), userAccount.getFreeze(), "提现冻结", SignStatus.flat);
            uad.setOtherid(tid);
            uad.setLotteryType(1);
            uad.setPhase("1");
            uad.setDrawAmount(amount);
            userAccountDetailDao.insert(uad);
            return lotteryDrawAmount;
        } catch (Exception e) {
            logger.error("发起提现失败", e);
            throw new LotteryException("提现失败");
        }

    }

}
