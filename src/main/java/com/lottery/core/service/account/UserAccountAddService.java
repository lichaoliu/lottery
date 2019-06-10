package com.lottery.core.service.account;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.pay.AuditStatus;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.common.contains.pay.PayStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountAddDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.dao.UserTransactionDao;
import com.lottery.core.domain.UserTransaction;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountAdd;
import com.lottery.core.domain.account.UserAccountDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;


@Service
public class UserAccountAddService {
	private static Logger logger = LoggerFactory.getLogger(UserAccountAddService.class);
	@Autowired
	private UserAccountAddDAO userAccountAddDAO;
	@Autowired
	private IdGeneratorDao idGeneratorDao;
	@Autowired
	private UserAccountDAO userAccountDAO;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDAO;
	@Autowired
	private UserTransactionDao userTransactionDao;

	@Transactional
	public void save(UserAccountAdd userAccountAdd) {
		String id = idGeneratorDao.getUserAccountAddId();
		userAccountAdd.setId(id);
		userAccountAddDAO.insert(userAccountAdd);
	}

	/**
	 * 对加款进行审核
	 * 
	 * @param userAccountAddId
	 *            加款id
	 * @param aduiter
	 *            审核人
	 * @param flag
	 *            是否通过 true通过,false 不通过
	 * @param errorMessage
	 *            不通过时的 错误描述
	 * */
	@Transactional
	public UserAccountAdd audit(String userAccountAddId, String aduiter, boolean flag, String errorMessage) {
		UserAccountAdd userAccountAdd = userAccountAddDAO.findWithLock(userAccountAddId, true);
		if (userAccountAdd == null) {
			throw new LotteryException("加款记录id=" + userAccountAddId + "不存在");
		}
		if (userAccountAdd.getAuditStatus() != AuditStatus.autid_not.value) {
			throw new LotteryException("加款记录id=" + userAccountAddId + "的状态不是未审核");
		}
		if (flag) {
			userAccountAdd.setAuditStatus(AuditStatus.autid_pass.value);
		} else {
			userAccountAdd.setAuditStatus(AuditStatus.autid_fail.value);
			userAccountAdd.setErrorMessage(errorMessage);
		}
		userAccountAdd.setAduiter(aduiter);
		userAccountAdd.setAuditTime(new Date());
		userAccountAddDAO.update(userAccountAdd);
		if (flag) {
			String userno = userAccountAdd.getUserno();
			BigDecimal amount = userAccountAdd.getAmount();
			UserAccount account = userAccountDAO.findWithLock(userno, true);
			if (account == null) {
				logger.error("账号{}不存在", userno);
				throw new LotteryException("账户+" + userno + ",不存在");
			}

			if (userAccountAdd.getIsAdd() == YesNoStatus.yes.value) {
				boolean isDraw = false;// 是否可提现
				if (userAccountAdd.getForDraw() == YesNoStatus.yes.getValue()) {
					isDraw = true;
				}
				account.setBalance(account.getBalance().add(amount));
				if (isDraw) {
					account.setDrawbalance(account.getDrawbalance().add(amount));
					if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
						account.setDrawbalance(account.getBalance());
					}
				}
				account.setLastTradeTime(new Date());
				account.setLastTradeamt(amount);
				account.setTotalRecharge(account.getTotalRecharge().add(amount));
				account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
				userAccountDAO.update(account);
				// 生成一条交易记录
				String id = idGeneratorDao.getUserAccountDetailId();
				UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(), userAccountAddId, new Date(), amount, AccountType.charge, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), userAccountAdd.getMemo(), SignStatus.in);
				uad.setLotteryType(LotteryType.ALL.value);
				uad.setPhase("1011");
                uad.setOtherid(userAccountAddId);
				userAccountDetailDAO.insert(uad);


				UserTransaction userTransaction=new UserTransaction();
				userTransaction.setCardId(userAccountAddId);
				userTransaction.setAmount(amount);
				userTransaction.setDescription("人工充值");
				if (isDraw){
					userTransaction.setNotDrawPerset(BigDecimal.ZERO);
				}else {
					userTransaction.setNotDrawPerset(BigDecimal.ONE);
				}
				userTransaction.setChannel(PayChannel.handpay.value);
				userTransaction.setRealAmount(amount);
				userTransaction.setStatus(PayStatus.PAY_SUCCESS.value);
                userTransaction.setUserno(userno);
				userTransaction.setFee(BigDecimal.ZERO);
				String tractionid=idGeneratorDao.getTransactionId();
				userTransaction.setId(tractionid);
				userTransaction.setPayType(PayChannel.handpay.value);
				userTransaction.setFee(BigDecimal.ZERO);
				userTransaction.setFinishTime(new Date());
				userTransaction.setCreateTime(new Date());
				userTransactionDao.insert(userTransaction);

			} else {
				BigDecimal lownBalance = account.getBalance().subtract(amount);

				if (lownBalance.compareTo(BigDecimal.ZERO) < 0) {
					logger.error("用户:{},可用余额:{},扣除金额不足:{}",userno,account.getBalance(),account);
					throw new LotteryException("余额不足");
				}
                boolean isDraw=false;
				if (userAccountAdd.getForDraw() == YesNoStatus.yes.getValue()) {
					isDraw = true;
				}
				account.setBalance(account.getBalance().subtract(amount));//扣款

				if (isDraw) {//是否扣可提现
					BigDecimal draw=account.getDrawbalance().subtract(amount);
					if (draw.compareTo(BigDecimal.ZERO)<0){
						logger.error("用户:{},可提现金额:{},扣除金额不足:{}",userno,account.getDrawbalance(),account);
						throw new LotteryException("提现余额不足");
					}
					account.setDrawbalance(account.getDrawbalance().subtract(amount));
					if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
						account.setDrawbalance(account.getBalance());
					}
				}
				if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
					account.setDrawbalance(account.getBalance());
				}

				if (account.getDrawbalance().compareTo(BigDecimal.ZERO)<0){
					account.setDrawbalance(BigDecimal.ZERO);
				}
				account.setLastTradeTime(new Date());
				account.setLastTradeamt(amount);
				account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
				userAccountDAO.update(account);
				// 生成一条交易记录
				String id = idGeneratorDao.getUserAccountDetailId();
				UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(), userAccountAddId, new Date(), amount, AccountType.deduct, CommonStatus.success, account.getBalance(), AccountDetailType.deduct, account.getDrawbalance(), account.getFreeze(), userAccountAdd.getMemo(), SignStatus.out);
				uad.setLotteryType(LotteryType.ALL.value);
				uad.setPhase("1011");
				userAccountDetailDAO.insert(uad);
			}

		}
		return userAccountAdd;

	}

	@Transactional
	public void directAddmoney(String userno, BigDecimal amt, String detailid, Integer isDraw) {
		UserAccount userAccount=userAccountDAO.findWithLock(userno, true);
    	if(userAccount==null){
    		return;
    	}
    	UserAccountDetail uad2 = userAccountDetailDAO.getByPayIdAndType(detailid, AccountType.agency, AccountDetailType.add);
    	if(uad2!=null){
    		logger.error("{}扣款记录不存在",detailid);
    		return;
    	}
    	if(isDraw == 1){
    		userAccount.setDrawbalance(userAccount.getDrawbalance().add(amt));
    	}
    	userAccount.setBalance(userAccount.getBalance().add(amt));
    	userAccount.setGiveBalance(userAccount.getGiveBalance().add(amt));
    	userAccount.setLastTradeTime(new Date());
    	userAccount.setTotalBalance(userAccount.getTotalBalance().add(amt));
    	userAccount.setLastTradeamt(amt);
    	userAccountDAO.merge(userAccount);
		String id = idGeneratorDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,userAccount.getUserName(), detailid, 
				new Date(),amt, AccountType.agency, CommonStatus.success, userAccount.getBalance(), AccountDetailType.add, 
				userAccount.getDrawbalance(), userAccount.getFreeze(), "代理返款", SignStatus.in);
		userAccountDetailDAO.insert(uad);		
	}
	
}
