package com.lottery.core.service.account;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户红包操作
 * */
@Service
public class UserAccountProcess {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private UserAccountDAO dao;
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDao;
	/**
	 * 红包赠送
	 * 
	 * @param redWalletId
	 *            红包id
	 * @param amount
	 *            赠送金额
	 * @param userno 用户名
	 * */
	@Transactional
	public void redWalletGive(String userno, String redWalletId, BigDecimal amount) {
		// 如果解冻记录已存在
		UserAccountDetail uad2 = userAccountDetailDao.getByPayIdAndType(redWalletId, AccountType.give, AccountDetailType.add);
		if (uad2 != null) {
			logger.error("红包{}已赠送", redWalletId);
			return;
		}
		UserAccount account = dao.findWithLock(userno, true);
		if (account == null) {
			return;
		}
		account.setBalance(account.getBalance().add(amount));
		account.setLastTradeTime(new Date());
		account.setTotalBalance(account.getTotalBalance().add(amount));
		account.setLastTradeamt(amount);
		account.setTotalgiveamt(account.getTotalgiveamt().add(amount));
		
		if(account.getGiveBalance()==null){
			account.setGiveBalance(amount);
		}else{
			account.setGiveBalance(account.getGiveBalance().add(amount));
		}
		
		dao.update(account);
		// 生成一条交易记录
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(),redWalletId, new Date(), amount, AccountType.give, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "红包赠送", SignStatus.in);
		uad.setGiveAmount(amount);
		uad.setNotDrawAmount(amount);
		uad.setAgencyNo("100");
		uad.setLotteryType(10);
		uad.setPhase("100");
		uad.setOtherid(redWalletId);
		userAccountDetailDao.insert(uad);
	}
	/**
	 * 渠道返点
	 *
	 * @param payid
	 *            返点id
	 * @param amount
	 *            返点金额
	 * @param userno 用户名
	 * */
	@Transactional
	public void agencyPoinTlocation(String userno, String payid, BigDecimal amount) {
		// 如果解冻记录已存在
		UserAccountDetail uad2 = userAccountDetailDao.getByPayIdAndType(payid, AccountType.agency, AccountDetailType.add);
		if (uad2 != null) {
			logger.error("代理{}已返点", payid);
			return;
		}
		UserAccount account = dao.findWithLock(userno, true);
		if (account == null) {
			return;
		}
		account.setBalance(account.getBalance().add(amount));
		account.setLastTradeTime(new Date());
		account.setTotalBalance(account.getTotalBalance().add(amount));
		account.setLastTradeamt(amount);
		account.setTotalgiveamt(account.getTotalgiveamt().add(amount));
		dao.update(account);
		// 生成一条交易记录
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(),payid, new Date(), amount, AccountType.agency, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "用户代理返点", SignStatus.in);
		uad.setGiveAmount(amount);
		uad.setNotDrawAmount(amount);
		uad.setAgencyNo("100");
		uad.setLotteryType(10);
		uad.setPhase("100");
		uad.setOtherid(payid);
		userAccountDetailDao.insert(uad);
	}
}
