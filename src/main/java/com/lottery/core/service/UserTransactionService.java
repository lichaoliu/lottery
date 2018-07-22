package com.lottery.core.service;

import com.lottery.common.PageBean;
import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserTransactionDao;
import com.lottery.core.dao.give.UserRechargeGiveDetailDAO;
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.give.UserRechargeGiveDetail;
import com.lottery.core.domain.give.UserRechargeGiveDetailPK;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserTransactionService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IdGeneratorDao dao;
	@Autowired
	UserTransactionDao userTransactionDao;
	@Autowired
	private UserAccountDAO userAccountDAO;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDAO;
	@Autowired
	private UserRechargeGiveDetailDAO userRechargeGiveDetailDAO;
	@Value("${notDrawPrecentage}")
	private BigDecimal notDrawPrecentage;

	@Transactional
	public void save(UserTransaction userTransaction) {
        userTransaction.setId(dao.getTransactionId());
        userTransactionDao.insert(userTransaction);
	}


    @Transactional
    public  void update(UserTransaction transaction){
        userTransactionDao.merge(transaction);
    }



	/**
	 * 查询支付中的充值订单
	 * 
	 * @param max 最大值
	 * @return
	 */
	@Transactional
	public List<UserTransaction> findTransactions(int max, String payType) {
		try {
			String whereSql = "status in (:status) and payType=:payType and createTime<=:createTime";
			List<Integer> statusList = new ArrayList<Integer>();
			statusList.add(PayStatus.NOT_PAY.getValue());
			statusList.add(PayStatus.ALREADY_PAY.getValue());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", statusList);
			map.put("payType", payType);
			map.put("createTime", CoreDateUtils.getBeforeTime(new Date(), 3));
			return userTransactionDao.findByCondition(max, whereSql, map);
		} catch (Exception e) {
			logger.error("查询充值中订单出错", e);
			return null;
		}

	}

	/**
	 * 更改充值状态
	 * 
	 * @param id 主键
	 * @param status 状态
	 */
	@Transactional
	public void updateStatus(String id,int status) {
		userTransactionDao.updateStatus(id, status);
	}

	/**
	 * 充值结果确认
	 * 
	 * @param transactionId
	 *            交易表id
	 * @param trade_no
	 *            充值渠道订单号
	 * @param amount
	 *            渠道充值金额(单位:分)
	 * @param flag
	 *            充值结果 true 成功 false 失败
	 * @param memo
	 *            信息描述
	 * */
	@Transactional
	public UserTransaction chargeResult(String transactionId, String trade_no, BigDecimal amount, boolean flag, String memo) {
		UserTransaction userTransaction = userTransactionDao.findWithLock(transactionId, true);
		if (userTransaction == null) {
			throw new LotteryException(ErrorCode.no_usertransaction, "充值订单id=" + transactionId + "不存在");
		}
		if (userTransaction.getStatus() == PayStatus.PAY_SUCCESS.getValue() || userTransaction.getStatus() == PayStatus.PAY_FAILED.getValue()) {
			logger.error("transactionId={}的充值状态已确认，状态是:{}", new Object[] { transactionId, userTransaction.getStatus() });
			return userTransaction;
		}

		BigDecimal decimalAmount=userTransaction.getAmount();
		
		if (amount.compareTo(decimalAmount)!=0) {
			logger.error("充值订单{}交易金额有误,amount={},数据amount={}", transactionId,amount,decimalAmount);
			userTransaction.setFinishTime(new Date());
			userTransaction.setDescription("充值金额有误,"+memo);
			userTransactionDao.merge(userTransaction);
			userTransaction.setStatus(PayStatus.PAY_FAILED.getValue());

			return null;
		}

		try {
			String userno = userTransaction.getUserno();
			String giveId = userTransaction.getGiveId();
			if (flag) {
				userTransaction.setStatus(PayStatus.PAY_SUCCESS.getValue());
				userTransaction.setFinishTime(new Date());
				userTransaction.setTradeNo(trade_no);
				userTransaction.setDescription(PayStatus.PAY_SUCCESS.getName());
				boolean drawflag=incomeDraw(transactionId, userno, userTransaction.getRealAmount(), userTransaction.getGiveAmount(), userTransaction.getNotDrawPerset(), giveId);
				if (drawflag){//充值成功
                    userTransactionDao.merge(userTransaction);
                }
			} else {
				userTransaction.setStatus(PayStatus.PAY_FAILED.getValue());
				userTransaction.setDescription(memo);
				userTransaction.setTradeNo(trade_no);
				userTransaction.setFinishTime(new Date());
				userTransactionDao.merge(userTransaction);
			}
			return userTransaction;
		} catch (Exception e) {
			logger.error("修改充值信息:{}出错", transactionId, e);
			throw new LotteryException(ErrorCode.Faile, "修改充值信息失败");
		}
	}

	/**
	 * 充值表数据封装
	 * 
	 * @param userno
	 *            充值账号
	 * @param channel
	 *            渠道
	 * @param fee
	 *            费率
	 * @param amount
	 *            充值金额
	 * @param giveAmount
	 *            赠送金额
	 * @param notDrawPerset
	 *            不可提现百分比
	 * @return
	 */
	@Transactional
	public UserTransaction getUserTransactionBean(String userno, BigDecimal amount, PayChannel channel, BigDecimal fee, BigDecimal giveAmount, String giveId, BigDecimal notDrawPerset) {
		BigDecimal amountDou = amount.multiply(new BigDecimal(100));
		BigDecimal realAmount = amountDou.subtract(amountDou.multiply(fee));
		String uuid = dao.getTransactionId();
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setCreateTime(new Date());
		userTransaction.setPayType(channel.getValue());
		userTransaction.setUserno(userno);
		userTransaction.setGiveAmount(giveAmount);
		userTransaction.setFee(fee);
		userTransaction.setAmount(amountDou);
		userTransaction.setRealAmount(realAmount);
		userTransaction.setId(uuid);
		userTransaction.setStatus(PayStatus.NOT_PAY.getValue());
		userTransaction.setDescription(PayStatus.NOT_PAY.getName());
		userTransaction.setGiveId(giveId);
		if (notDrawPerset == null || notDrawPerset.compareTo(new BigDecimal(1)) > 0)
			notDrawPerset = notDrawPrecentage;
		userTransaction.setNotDrawPerset(notDrawPerset);
		userTransaction.setChannel(channel.value);
		userTransactionDao.insert(userTransaction);
		return userTransaction;
	}

	/**
	 * 充值账户加钱
	 * 
	 * @param transactionId
	 *            交易id
	 * @param userno
	 *            用户编号
	 * @param amount
	 *            实际充值金额
	 * @param giveAmount
	 *            赠送 金额
	 * @param notDrawPerset
	 *            不可提现比例
	 * @param giveId
	 *            赠送
	 * */

	protected boolean incomeDraw(String transactionId, String userno, BigDecimal amount, BigDecimal giveAmount, BigDecimal notDrawPerset, String giveId) {

		UserAccountDetail detail = userAccountDetailDAO.getByPayIdAndType(transactionId, AccountType.charge, AccountDetailType.add);
		if (detail != null) {
			logger.error("订单{}充值记录已存在", transactionId);
			return false;
		}

		UserAccount account = userAccountDAO.findWithLock(userno, true);
		if (account == null) {
			logger.error("账号{}不存在", userno);
			return false;
		}
        if (notDrawPerset==null){
            notDrawPerset=new BigDecimal(1);
        }
        if (notDrawPerset.compareTo(new BigDecimal(1)) > 0){
            notDrawPerset=new BigDecimal(1);
        }
		BigDecimal bigD = amount.multiply(notDrawPerset);
		bigD = bigD.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal canDrawMoney = amount.subtract(bigD);
		account.setDrawbalance(account.getDrawbalance().add(canDrawMoney));
		account.setBalance(account.getBalance().add(amount));
		account.setLastTradeTime(new Date());
		account.setTotalBalance(account.getTotalBalance().add(amount));
		account.setLastTradeamt(amount);
		account.setTotalRecharge(account.getTotalRecharge().add(amount));
		userAccountDAO.update(account);
		int size = 1;
		if (giveAmount != null && giveAmount.compareTo(BigDecimal.ZERO) > 0 && StringUtils.isNotBlank(giveId)) {
			size = 2;
		}
		// 生成一条交易记录
		Long id = dao.getUserAccountDetailId(size);
		String tid1 = dao.getId(id);
		UserAccountDetail uad = new UserAccountDetail(tid1, userno,account.getUserName(),transactionId, new Date(), amount, AccountType.charge, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "账户充值", SignStatus.in);
		uad.setOtherid(tid1);
		
		userAccountDetailDAO.insert(uad);
		// 判断是否有赠送
		if (giveAmount != null && giveAmount.compareTo(BigDecimal.ZERO) > 0 && StringUtils.isNotBlank(giveId)) {
			UserAccountDetail reDetail = userAccountDetailDAO.getByPayIdAndType(transactionId, AccountType.give, AccountDetailType.add);
			if (reDetail != null) {
				logger.error("订单{}充值赠送记录已存在", transactionId);
				return false;
			}
			UserRechargeGiveDetail rechargeGiveDetail = userRechargeGiveDetailDAO.find(new UserRechargeGiveDetailPK(giveId, userno));

			if (rechargeGiveDetail != null) {
				if (rechargeGiveDetail.getCommonStatus() != CommonStatus.success.value) {
					account.setBalance(account.getBalance().add(giveAmount));
					account.setLastTradeTime(new Date());
					account.setTotalBalance(account.getTotalBalance().add(giveAmount));
					account.setLastTradeamt(giveAmount);
					id = id + 1;
					account.setTotalgiveamt(account.getTotalgiveamt().add(giveAmount));

					if (account.getGiveBalance() == null) {
						account.setGiveBalance(giveAmount);
					} else {
						account.setGiveBalance(account.getGiveBalance().add(giveAmount));
					}

					userAccountDAO.update(account);
					String tid2 = dao.getId(id);
					UserAccountDetail uad2 = new UserAccountDetail(tid2, userno,account.getUserName(),transactionId, new Date(), giveAmount, AccountType.give, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "充值送彩金", SignStatus.in);
					uad2.setOtherid(giveId);
					uad2.setLotteryType(LotteryType.ALL.value);
					uad2.setPhase("1011");
					userAccountDetailDAO.insert(uad2);
					rechargeGiveDetail.setCommonStatus(CommonStatus.success.value);
					rechargeGiveDetail.setFinishTime(new Date());
					userRechargeGiveDetailDAO.update(rechargeGiveDetail);
					if (!transactionId.equals(rechargeGiveDetail.getTransationId())) {
						logger.error("充值订单号{}和赠送标记的订单号{}不一致，请注意", transactionId, rechargeGiveDetail.getTransationId());
					}
				}
			} else {
				account.setBalance(account.getBalance().add(giveAmount));
				account.setLastTradeTime(new Date());
				account.setTotalBalance(account.getTotalBalance().add(giveAmount));
				account.setLastTradeamt(giveAmount);
				if (account.getGiveBalance() == null) {
					account.setGiveBalance(giveAmount);
				} else {
					account.setGiveBalance(account.getGiveBalance().add(giveAmount));
				}
				id = id + 1;
				account.setTotalgiveamt(account.getTotalgiveamt().add(giveAmount));
				userAccountDAO.update(account);
				String tid2 = dao.getId(id);
				UserAccountDetail uad2 = new UserAccountDetail(tid2, userno,account.getUserName(),transactionId, new Date(), giveAmount, AccountType.give, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "充值送彩金", SignStatus.in);
				uad2.setOtherid(giveId);
				uad2.setLotteryType(LotteryType.ALL.value);
				uad2.setPhase("1011");
				userAccountDetailDAO.insert(uad2);
			}

		}

		return true;
	}


	/**
	 * 查询交易记录
	 * 
	 * @param userno
	 *            用户编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param page
	 *            分页
	 * 
	 * */
	@Transactional
	public List<UserTransaction> getTransationList(String userno, String startTime, String endTime, PageBean<UserTransaction> page) {
		BetweenDate bd=SpecialDateUtils.getBetweenDate(3, startTime, endTime);
		Date startDate=bd.getStartDate();
		Date endDate=bd.getEndDate();
		return userTransactionDao.getTransationList(userno, startDate, endDate, page);
	}
	@Transactional
	public UserTransaction get(String id){
		return userTransactionDao.find(id);
	}
}