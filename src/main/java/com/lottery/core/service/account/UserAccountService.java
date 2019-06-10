package com.lottery.core.service.account;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserAccountService {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private UserAccountDAO dao;
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDao;


	

	/**
	 * 账户订单交易失败解冻
	 * */
	@Transactional
	public void unfreeze(String userno,String orderId,int accounttype,BigDecimal amount,BigDecimal successAmount) {

		AccountType accountType = AccountType.getAccountType(accounttype);
		if (accountType == AccountType.hemai) {
			logger.error("该订单orderid={}的投注类型是合买,不进行普通解冻处理", orderId);
			return;
		}
		UserAccountDetail uad1 = userAccountDetailDao.getByPayIdAndType(orderId, accountType, AccountDetailType.freeze);
		if (uad1 == null) {
			logger.error("订单{}冻结记录不存在", orderId);
			return;
		}
		UserAccount account = dao.findWithLock(userno, true);
		if (account == null) {
			logger.error("账号{}不存在", userno);
			return;
		}

		try {
			amount = amount.subtract(successAmount);
			account.setBalance(account.getBalance().add(amount));
			account.setFreeze(account.getFreeze().subtract(amount));
			account.setLastTradeTime(new Date());
			account.setLastTradeamt(amount);


			BigDecimal giveAmt = uad1.getGiveAmount();
			BigDecimal notDrawAmt = uad1.getNotDrawAmount();
			BigDecimal drawAmt = uad1.getDrawAmount();
			BigDecimal remainAmt = notDrawAmt.subtract(drawAmt);
			if (remainAmt.compareTo(successAmount) >= 0) {
				account.setDrawbalance(account.getDrawbalance().add(drawAmt));
				notDrawAmt = successAmount;
				if (giveAmt.compareTo(successAmount) >= 0) {
					giveAmt = successAmount;
				}
			} else {
				drawAmt = drawAmt.subtract(successAmount.subtract(notDrawAmt));
				account.setDrawbalance(account.getDrawbalance().add(drawAmt));
			}


			if (account.getDrawbalance().compareTo(account.getBalance()) > 0) {
				account.setDrawbalance(account.getBalance());
			}
			int size = 1;
			if (successAmount.compareTo(BigDecimal.ZERO) > 0) {
				size = 2;
			}
			Long id = idDao.getUserAccountDetailId(size);
			// 生成一条交易记录
			
			String tid = idDao.getId(id);
			UserAccountDetail uad = new UserAccountDetail(tid, userno,account.getUserName(), orderId, new Date(), amount, accountType, CommonStatus.success, account.getBalance(), AccountDetailType.unfreeze, account.getDrawbalance(), account.getFreeze(), accountType.getName() + AccountDetailType.unfreeze.getName(),
					SignStatus.flat);
			uad.setLotteryType(uad1.getLotteryType());
			uad.setPhase(uad1.getPhase());
			uad.setGiveAmount(uad1.getGiveAmount());
			uad.setNotDrawAmount(uad1.getNotDrawAmount());
			uad.setDrawAmount(uad1.getDrawAmount());
			uad.setAgencyNo(uad1.getAgencyNo());
			uad.setOtherid(uad1.getOtherid());
			userAccountDetailDao.insert(uad);
			if (successAmount.compareTo(BigDecimal.ZERO) > 0) {

				account.setFreeze(account.getFreeze().subtract(successAmount));
				account.setLastTradeTime(new Date());
				account.setLastTradeamt(successAmount);
				id = id + 1;
				account.setTotalbetamt(account.getTotalbetamt().add(successAmount));
				UserAccountDetail newUad = new UserAccountDetail(idDao.getId(id), userno,account.getUserName(),orderId, new Date(), successAmount, accountType, CommonStatus.success, account.getBalance(), AccountDetailType.half_deduct, account.getDrawbalance(), account.getFreeze(), accountType.getName()
						+ AccountDetailType.half_deduct.getName(), SignStatus.out);

				newUad.setLotteryType(uad1.getLotteryType());
				newUad.setPhase(uad1.getPhase());
				newUad.setGiveAmount(giveAmt);
				newUad.setNotDrawAmount(notDrawAmt);
				newUad.setDrawAmount(drawAmt);
				newUad.setAgencyNo(uad1.getAgencyNo());
				newUad.setOtherid(uad1.getOtherid());
				userAccountDetailDao.insert(newUad);
			}
			account.setTotalBalance(account.getBalance().add(account.getFreeze()));
			dao.update(account);

		} catch (Exception e) {
			logger.error("订单{}解冻失败,失败原因是:", orderId, e);
			throw new LotteryException("解冻失败", e);
		}
	}

	/**
	 * 账户订单交易成功解冻,扣款
	 * */
	@Transactional
	public boolean deduct(String userno,String orderId,BigDecimal amount,int accounttype,int lotteryType,String phase) {
		try {

			AccountType accountType = AccountType.getAccountType(accounttype);
			UserAccountDetail uad1 = userAccountDetailDao.getByPayIdAndType(orderId, accountType, AccountDetailType.freeze);
			if (uad1 == null) {
				logger.error("订单{}冻结记录不存在", orderId);
				return false;
			}

			UserAccount account = dao.findWithLock(userno, true);
			if (account == null) {
				logger.error("账号{}不存在", userno);
				return false;
			}

			account.setFreeze(account.getFreeze().subtract(amount));
			account.setLastTradeTime(new Date());
			account.setLastTradeamt(amount);
			account.setTotalbetamt(account.getTotalbetamt().add(amount));
			account.setTotalBalance(account.getBalance().add(account.getFreeze()));


			dao.update(account);
			// 生成一条交易记录
			String id = idDao.getUserAccountDetailId();
			UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(), orderId, new Date(), amount, accountType, CommonStatus.success, account.getBalance(), AccountDetailType.deduct, account.getDrawbalance(), account.getFreeze(), accountType.getName() + AccountDetailType.deduct.getName(),
					SignStatus.out);
			userAccountDetailDao.insert(uad);
			uad.setLotteryType(lotteryType);
			uad.setPhase(phase);
			uad.setGiveAmount(uad1.getGiveAmount());
			uad.setDrawAmount(uad1.getDrawAmount());
			uad.setNotDrawAmount(uad1.getNotDrawAmount());
			uad.setAgencyNo(uad1.getAgencyNo());
			uad.setOtherid(uad1.getOtherid());
		} catch (Exception e) {
			
			throw new LotteryException(orderId+":扣款失败", e);
		}
		return true;
	}

	private void amountInteger(UserAccount account){
		account.setBalance(account.getBalance().setScale(0,BigDecimal.ROUND_HALF_UP));
		account.setDrawbalance(account.getDrawbalance().setScale(0,BigDecimal.ROUND_HALF_UP));
		account.setTotalprizeamt(account.getTotalprizeamt().setScale(0,BigDecimal.ROUND_HALF_UP));
	}



	@Transactional
	public UserAccount get(String userno) {
		return dao.find(userno);
	}

	@Transactional
	public UserAccount getWithLock(String userno, boolean flag) {
		return dao.findWithLock(userno, flag);
	}

	/**
	 * 冻结金额
	 * 
	 * @param userno
	 *            用户编号
	 * @param payid
	 *            用户交易id
	 * @param amount
	 *            冻结金额
	 * @param accountType
	 *            扣款类型
	 * @param accountDetailType
	 *            交易类型
	 * @param memo
	 *            交易说明
	 * @param otherid
	 *            合买或追号id
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param agencyNo
	 *            渠道号
	 * @param balanceFlag 是否余额判断
	 * @exception LotteryException
	 */
	@Transactional
	public BigDecimal freezeAmount(String userno, String payid, BigDecimal amount, AccountType accountType, AccountDetailType accountDetailType, String memo, String otherid, int lotteryType, String phase, String agencyNo,boolean balanceFlag) {

	
		UserAccount userAccount = dao.findWithLock(userno, true);
		if (userAccount == null) {
			throw new LotteryException(ErrorCode.no_account, "账户:" + userno + "不存在");
		}
		
		BigDecimal balance = userAccount.getBalance();

		if(balanceFlag){//是否判断余额
			if (balance.compareTo(amount) < 0) {
				throw new LotteryException(ErrorCode.account_no_enough, "账户:" + userno + "余额不足,payid="+payid);
			}	
		}
		
		try {
			userAccount.setLastfreeze(amount);
			userAccount.setBalance(userAccount.getBalance().subtract(amount));
			userAccount.setFreeze(userAccount.getFreeze().add(amount));
			userAccount.setLastTradeTime(new Date());
			userAccount.setLastTradeamt(amount);
			BigDecimal drawAmt = BigDecimal.ZERO;

			if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
				drawAmt = userAccount.getDrawbalance().subtract(userAccount.getBalance());
				userAccount.setDrawbalance(userAccount.getBalance());
			}
			BigDecimal notDrawAmt = amount.subtract(drawAmt);
			BigDecimal giveAmt = BigDecimal.ZERO;
			//暂时不考虑赠送金额
//
//			if (userAccount.getGiveBalance() != null) {
//				if (userAccount.getGiveBalance().compareTo(notDrawAmt) > 0) {
//					userAccount.setGiveBalance(userAccount.getGiveBalance().subtract(notDrawAmt));
//					giveAmt = notDrawAmt;
//				} else {
//					giveAmt = userAccount.getGiveBalance();
//					userAccount.setGiveBalance(BigDecimal.ZERO);
//				}
//			} else {
//				userAccount.setGiveBalance(BigDecimal.ZERO);
//			}
			userAccount.setGiveBalance(BigDecimal.ZERO);
            //可提现金额为负的时候
			if (userAccount.getDrawbalance().compareTo(BigDecimal.ZERO)<0){
				userAccount.setDrawbalance(BigDecimal.ZERO);
			}
			//赠送金额为负的时候
			if (userAccount.getGiveBalance().compareTo(BigDecimal.ZERO)<0){
				userAccount.setGiveBalance(BigDecimal.ZERO);
			}


			userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额

			amountInteger(userAccount);

			dao.update(userAccount);
			String id = idDao.getUserAccountDetailId();
			if (StringUtils.isBlank(memo)) {
				memo = accountType.getName() + accountDetailType.getName();
			}

			UserAccountDetail uad = new UserAccountDetail(id, userAccount.getUserno(),userAccount.getUserName(), payid, new Date(), amount, accountType, CommonStatus.success, userAccount.getBalance(), accountDetailType, userAccount.getDrawbalance(), userAccount.getFreeze(), memo, SignStatus.flat);
			uad.setOtherid(otherid);
			uad.setLotteryType(lotteryType);
			uad.setPhase(phase);
			uad.setGiveAmount(giveAmt);
			uad.setDrawAmount(drawAmt);
			uad.setNotDrawAmount(notDrawAmt);
			uad.setAgencyNo(agencyNo);
			uad.setOrderId(payid);
			userAccountDetailDao.insert(uad);
			return drawAmt;
		} catch (Exception e) {
			logger.error("支付订单({})冻结出错",payid, e);
			throw new LotteryException(ErrorCode.system_error, e.getMessage());
		}
	}

	/**
	 * 解冻金额
	 * 
	 * @param userno
	 *            用户编号
	 * @param payId
	 *            用户交易id
	 * @param amount
	 *            冻结金额
	 * @param accountType
	 *            交易类型
	 * @param fromAccountDetailType
	 *            上次交易明细类型
	 * @param accountDetailType
	 *            交易明细类型
	 * @param memo
	 *            交易说明
	 * @param otherid
	 *            合买或追号id
	 * @param drawAmt
	 *            可提现金额(通常情况下与amount相同)
	 * @exception LotteryException
	 */
	@Transactional
	public void unfreezeAmount(String userno, String payId, String otherid, BigDecimal amount, AccountType accountType, AccountDetailType fromAccountDetailType, AccountDetailType accountDetailType, String memo, BigDecimal drawAmt) {
		UserAccountDetail uad1 = userAccountDetailDao.getByPayIdAndType(payId, accountType, fromAccountDetailType);
		if (uad1 == null) {
			logger.error("扣款类型{},交易类型:{},payid={}冻结记录不存在", new Object[] { accountType, fromAccountDetailType, payId });
			return;
		}
		// 如果解冻记录已存在
		UserAccountDetail uad2 = userAccountDetailDao.getByPayIdAndType(payId, accountType, accountDetailType);
		if (uad2 != null) {
			logger.error("交易：{}解冻记录已存在", payId);
			return;
		}
		try {
			UserAccount userAccount = dao.findWithLock(userno, true);
			userAccount.setBalance(userAccount.getBalance().add(amount));
			userAccount.setDrawbalance(userAccount.getDrawbalance().add(drawAmt));
			userAccount.setFreeze(userAccount.getFreeze().subtract(amount));
			userAccount.setLastTradeTime(new Date());
			userAccount.setLastTradeamt(amount);
			if (userAccount.getBalance().compareTo(userAccount.getDrawbalance()) < 0) {
				userAccount.setDrawbalance(userAccount.getBalance());
			}
			userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
			dao.update(userAccount);
			String id = idDao.getUserAccountDetailId();
			UserAccountDetail uad = new UserAccountDetail(id, userno,userAccount.getUserName(), payId, new Date(), amount, accountType, CommonStatus.success, userAccount.getBalance(), accountDetailType, userAccount.getDrawbalance(), userAccount.getFreeze(), memo, SignStatus.flat);
			uad.setOtherid(otherid);
			uad.setLotteryType(uad1.getLotteryType());
			uad.setPhase(uad1.getPhase());
			uad.setAgencyNo(uad1.getAgencyNo());
			uad.setOrderId(payId);
			userAccountDetailDao.insert(uad);
		} catch (Exception e) {
			logger.error("解冻金额出错", e);
			throw new LotteryException(ErrorCode.system_error, e.getMessage());
		}
	}

	/**
	 * 直接扣款
	 * 
	 * @param userno
	 *            用户编号
	 * @param payid
	 *            交易id
	 * @param accountType
	 *            交易类型
	 * @param accountDetailType
	 *            交易明细类型
	 * @param otherId
	 *            合买或追号id
	 * @param amount
	 *            交易金额
	 * @param memo
	 *            交易描述
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * */
	@Transactional
	public BigDecimal deductMoney(String userno, String payid, AccountType accountType, AccountDetailType accountDetailType, String otherId, BigDecimal amount, String memo, int lotteryType, String phase, String agencyNo) {
		UserAccount account = dao.findWithLock(userno, true);
		if (account.getBalance().compareTo(amount) < 0) { // 判断用户余额减去冻结金额是否小于投注金额
			logger.error("用户账户余额小于投注金额  : " + account.getDrawbalance());
			throw new LotteryException(ErrorCode.account_no_enough, "用户可用余额不足");
		}

		account.setBalance(account.getBalance().subtract(amount));
		account.setLastTradeTime(new Date());
		account.setLastTradeamt(amount);
		account.setTotalbetamt(account.getTotalbetamt().add(amount));
		BigDecimal drawAmt = BigDecimal.ZERO;
		if (account.getBalance().compareTo(account.getDrawbalance()) < 0) {
			drawAmt = account.getDrawbalance().subtract(account.getBalance());
			account.setDrawbalance(account.getBalance());
		}

		BigDecimal notDrawAmt = amount.subtract(drawAmt);
		BigDecimal giveAmt = BigDecimal.ZERO;
		if (account.getGiveBalance() != null) {
			if (account.getGiveBalance().compareTo(notDrawAmt) > 0) {
				account.setGiveBalance(account.getGiveBalance().subtract(notDrawAmt));
				giveAmt = notDrawAmt;
			} else {
				giveAmt = account.getGiveBalance();
				account.setGiveBalance(BigDecimal.ZERO);
			}
		} else {
			account.setGiveBalance(BigDecimal.ZERO);
		}
		account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
		dao.update(account);
		// 生成一条交易记录
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, account.getUserno(), account.getUserName(),payid, new Date(), amount, accountType, CommonStatus.success, account.getBalance(), accountDetailType, account.getDrawbalance(), account.getFreeze(), memo, SignStatus.out);
		uad.setOtherid(otherId);
		uad.setLotteryType(lotteryType);
		uad.setPhase(phase);
		uad.setAgencyNo(agencyNo);
		uad.setGiveAmount(giveAmt);
		uad.setDrawAmount(drawAmt);
		uad.setNotDrawAmount(notDrawAmt);
		uad.setOrderId(payid);
		userAccountDetailDao.insert(uad);
		return drawAmt;
	}

	/**
	 * 合买返还金额
	 */
	@Transactional
	public void hemaiReturnAmt(String caselotbuyid, String caselotid, String userno, BigDecimal amount, CaseLotBuyState canceledBy, BigDecimal buyDrawAmt) {
		UserAccountDetail accountdetail = userAccountDetailDao.getByPayIdAndType(caselotbuyid, AccountType.hemai, AccountDetailType.hemai_deduct);
		if (accountdetail == null) {
			logger.info("合买购买记录没有扣款记录,caselotbuyid:" + caselotbuyid);
			return;
		}
		UserAccountDetail accountdetail2 = userAccountDetailDao.getByPayIdAndType(caselotbuyid, AccountType.hemai, AccountDetailType.hemai_canncelAdd);
		if (accountdetail2 != null) {
			logger.info("合买购买记录已经返过款,caselotbuyid:" + caselotbuyid);
			return;
		}
		long accountAmount = accountdetail.getAmt().longValue();
		if (amount.longValue() != accountAmount) {
			logger.info("合买返回金额有误，amount：" + amount + "，accountAmount：" + accountAmount);
			return;
		}

		logger.info("撤单返款userno:{},caselotbuyid:{},amount:{},canceledBy:{}", new Object[] { userno, caselotbuyid, amount, canceledBy.memo() });
		if (new BigDecimal(0).compareTo(amount) > 0) {
			return;
		}
		UserAccount account = dao.findWithLock(userno, true);

		account.setBalance(account.getBalance().add(amount.abs()));
		account.setDrawbalance(account.getDrawbalance().add(buyDrawAmt.abs()));
		account.setTotalbetamt(account.getTotalbetamt().subtract(amount));
		account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
		dao.update(account);
		String id = idDao.getUserAccountDetailId();
		UserAccountDetail uad = new UserAccountDetail(id, account.getUserno(),account.getUserName(), caselotbuyid, new Date(), amount, AccountType.hemai, CommonStatus.success, account.getBalance(), AccountDetailType.hemai_canncelAdd, account.getDrawbalance(), account.getFreeze(), canceledBy.memo(), SignStatus.in);
		uad.setOtherid(caselotid);
		uad.setLotteryType(accountdetail.getLotteryType());
		uad.setPhase(accountdetail.getPhase());
		userAccountDetailDao.insert(uad);
	}

	/**
	 * 合买中奖加款
	 * */
	@Transactional
	public void hemaiEncash(String userno, String payId, String otherid, AccountType accountType, AccountDetailType accountDetailType, BigDecimal amt, BigDecimal drawAmt, String memo,int lotteryType,String phase) {
		UserAccountDetail userAccountDetail = userAccountDetailDao.getByPayIdAndType(payId, accountType, accountDetailType);
		if (userAccountDetail != null) {
			logger.error("合买{}加款交易记录已存在", payId);
			return;
		}
		
		UserAccount userAccount = dao.findWithLock(userno, true);
		if(userAccount==null){
			throw new LotteryException(getUserNotExitsMessage(userno));
		}

		userAccount.setBalance(userAccount.getBalance().add(amt));
		userAccount.setDrawbalance(userAccount.getDrawbalance().add(drawAmt));
		userAccount.setTotalprizeamt(userAccount.getTotalprizeamt().add(amt));
		userAccount.setLastTradeTime(new Date());
		userAccount.setLastTradeamt(amt);
		userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
		dao.merge(userAccount);
		UserAccountDetail accountDetail = new UserAccountDetail();
		String id = idDao.getUserAccountDetailId();
		accountDetail.setId(id);
		accountDetail.setAmt(amt);
		accountDetail.setBalance(userAccount.getBalance());
		accountDetail.setCommonStatus(CommonStatus.success.getValue());
		accountDetail.setCreatetime(new Date());
		accountDetail.setDetailType(accountDetailType.getValue());
		accountDetail.setDrawbalance(userAccount.getDrawbalance());
		accountDetail.setFreeze(userAccount.getFreeze());
		accountDetail.setMemo(memo);
		accountDetail.setPayId(payId);
		accountDetail.setUserno(userAccount.getUserno());
		accountDetail.setOtherid(otherid);
		accountDetail.setSignStatus(SignStatus.in.value);
		accountDetail.setPayType(accountType.getValue());
		accountDetail.setLotteryType(lotteryType);
		accountDetail.setPhase(phase);
		accountDetail.setFinishTime(new Date());
		accountDetail.setNotDrawAmount(BigDecimal.ZERO);
		accountDetail.setDrawAmount(drawAmt);
		accountDetail.setGiveAmount(BigDecimal.ZERO);
		accountDetail.setUserName(userAccount.getUserName());
		accountDetail.setOrderId(payId);
		userAccountDetailDao.insert(accountDetail);
	}

	
	protected String getUserNotExitsMessage(String userno){
		return String.format("用户:%s不存在", userno);
	}
	/**
	 * 查询中奖排行榜
	 * 
	 * @param max
	 *            查询多少行
	 * **/
	public List<UserAccount> getWinTop(int max) {
		String whereString = "totalprizeamt>0 order by totalprizeamt desc";
		return dao.findByCondition(max, whereString, new HashMap<String, Object>());
	}

	/***
	 * 账户加款
	 * */
	/**
	 * 给用户加款
	 * 
	 * @param userno
	 *            用户编号
	 * @param payId
	 *            相关订单号
	 * @param amount
	 *            中奖金额
	 * @param memo
	 *            加钱说明
	 * @param flag
	 *            是否可提现 true 可提现,false 不可提现
	 * @param addFlag
	 *            是否增加
	 * 
	 * */
	@Transactional
	public void addMondy(String userno, String payId, BigDecimal amount, String memo, boolean flag, boolean addFlag) {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) == 0) {
			throw new LotteryException("账户+" + userno + ",充值金额为空或为0");
		}
		UserAccount account = dao.findWithLock(userno, true);
		if (account == null) {
			logger.error("账号{}不存在", userno);
			throw new LotteryException("账户+" + userno + ",不存在");
		}
		try {
			if (addFlag) {
				if (flag)
					account.setDrawbalance(account.getDrawbalance().add(amount));
				account.setBalance(account.getBalance().add(amount));
				account.setLastTradeTime(new Date());
				account.setLastTradeamt(amount);
				account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
				dao.update(account);
				// 生成一条交易记录
				String id = idDao.getUserAccountDetailId();
				UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(), payId, new Date(), amount, AccountType.charge, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), memo, SignStatus.in);
				userAccountDetailDao.insert(uad);
			} else {
				account.setDrawbalance(account.getDrawbalance().subtract(amount));
				account.setBalance(account.getBalance().subtract(amount));
				account.setLastTradeTime(new Date());
				account.setLastTradeamt(amount);
				account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
				dao.update(account);
				// 生成一条交易记录
				String id = idDao.getUserAccountDetailId();
				UserAccountDetail uad = new UserAccountDetail(id, userno,account.getUserName(), payId, new Date(), amount, AccountType.charge, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), memo, SignStatus.in);
				uad.setLotteryType(LotteryType.ALL.value);
				uad.setPhase("100");
				uad.setDrawAmount(amount);
				uad.setNotDrawAmount(BigDecimal.ZERO);
				uad.setGiveAmount(BigDecimal.ZERO);
				uad.setOrderId(payId);
				userAccountDetailDao.insert(uad);
			}

		} catch (Exception e) {
			logger.error("用户{}充值失败", userno, e);
			throw new LotteryException("用户" + account.getUserName() + "账户加钱失败");
		}
	}

	/**
	 * 中奖派奖
	 * @param userno 用户编号
	 * @param payId 订单号或票号
	 * @param orderId
	 *            订单号
	 * @param totalPrize
	 *            中奖金额
	 * */
	@Transactional
	public void drawPrize(String userno,String payId,String orderId,int lotteryType,String phase,BigDecimal totalPrize,String agencyNo,int orderPrizeType) {
		
		UserAccount account = dao.findWithLock(userno, true);
		if (account == null) {
			logger.error("账号{}不存在", userno);
			throw new LotteryException("账户+" + userno + ",不存在");
		}
		try{
			account.setDrawbalance(account.getDrawbalance().add(totalPrize));
			account.setBalance(account.getBalance().add(totalPrize));
			account.setLastTradeTime(new Date());
			account.setTotalprizeamt(account.getTotalprizeamt().add(totalPrize));
			account.setLastTradeamt(totalPrize);
			if (account.getBalance().compareTo(account.getDrawbalance()) < 0) {
				account.setDrawbalance(account.getBalance());
			}
			account.setTotalBalance(account.getBalance().add(account.getFreeze()));//重新计算一下总金额
			dao.update(account);
			// 生成一条交易记录
			String id = idDao.getUserAccountDetailId();
			UserAccountDetail userAccountDetail = new UserAccountDetail(id, userno,account.getUserName(), payId, new Date(), totalPrize, AccountType.drawprize, CommonStatus.success, account.getBalance(), AccountDetailType.add, account.getDrawbalance(), account.getFreeze(), "投注中奖", SignStatus.in);
			userAccountDetail.setLotteryType(lotteryType);
			userAccountDetail.setPhase(phase);
			userAccountDetail.setOtherid(payId);
			userAccountDetail.setDrawAmount(totalPrize);
			userAccountDetail.setNotDrawAmount(BigDecimal.ZERO);
			userAccountDetail.setGiveAmount(BigDecimal.ZERO);
			userAccountDetail.setAgencyNo(agencyNo);
			userAccountDetail.setOrderId(orderId);
			userAccountDetail.setOrderPrizeType(orderPrizeType);
			userAccountDetailDao.insert(userAccountDetail);
	       
		}catch(Exception e){
			logger.error("({})派奖失败",payId,e);
			throw new LotteryException("派奖失败");
		}

	}



	/**
	 * 退款操作
	 * 
	 * */
	@Transactional
	public void refund(String userno,String orderId,BigDecimal amount) {

		UserAccountDetail uad = userAccountDetailDao.getByPayIdAndType(orderId, AccountType.bet, AccountDetailType.refund);
		if (uad != null) {
			logger.error("订单{}退款记录处理", orderId);
			return;
		}

		UserAccountDetail uad1 = userAccountDetailDao.getByPayIdAndType(orderId, AccountType.bet, AccountDetailType.deduct);
		if (uad1 == null) {
			logger.error("订单{}无扣款记录,不做退款处理", orderId);
			return;
		}

		UserAccount userAccount = dao.findWithLock(userno, true);
		if (userAccount == null) {
			logger.error("账号{}不存在", userno);
			return;
		}
		try {
	

			BigDecimal drawAmt = uad1.getDrawAmount();
			if (drawAmt == null) {
				drawAmt = BigDecimal.ZERO;
			}

			userAccount.setBalance(userAccount.getBalance().add(amount));
			userAccount.setDrawbalance(userAccount.getDrawbalance().add(drawAmt));

			userAccount.setLastTradeTime(new Date());
			userAccount.setLastTradeamt(amount);
			if (userAccount.getBalance().compareTo(userAccount.getDrawbalance()) < 0) {
				userAccount.setDrawbalance(userAccount.getBalance());
			}
			userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
			dao.update(userAccount);
			String id = idDao.getUserAccountDetailId();
			UserAccountDetail uad3 = new UserAccountDetail(id, userno,userAccount.getUserName(), orderId, new Date(), amount, AccountType.bet, CommonStatus.success, userAccount.getBalance(), AccountDetailType.refund, userAccount.getDrawbalance(), userAccount.getFreeze(), "无法出票退款", SignStatus.in);
			uad3.setOtherid(uad1.getOtherid());
			uad3.setLotteryType(uad1.getLotteryType());
			uad3.setPhase(uad1.getPhase());
			uad3.setAgencyNo(uad1.getAgencyNo());
			userAccountDetailDao.insert(uad3);
		} catch (Exception e) {

			logger.error("订单({})退款处理失败",orderId,e);
			throw new LotteryException(orderId+"返款失败",e);
		}

	}
	//投注赔偿
    @Transactional
	public  void betCompensation(String userno,String orderId,BigDecimal amount,int accountType){
		UserAccountDetail accountDetail=userAccountDetailDao.getByPayIdAndType(orderId,accountType,AccountDetailType.unfreeze.value);
		if(accountDetail==null){
			throw  new LotteryException("订单没有解冻记录,orderid="+orderId);
		}

		UserAccountDetail accountDetail1=userAccountDetailDao.getByPayIdAndType(orderId,accountType,AccountDetailType.bet_compensation.value);
		if(accountDetail1!=null){
			throw  new LotteryException("赔付记录已存在,orderid="+orderId);
		}

		UserAccount userAccount = dao.findWithLock(userno, true);
		if (userAccount == null) {
			logger.error("账号{}不存在", userno);
			return;
		}
		try{
			userAccount.setBalance(userAccount.getBalance().add(amount));
			userAccount.setDrawbalance(userAccount.getDrawbalance().add(amount));

			userAccount.setLastTradeTime(new Date());
			userAccount.setLastTradeamt(amount);
			if (userAccount.getBalance().compareTo(userAccount.getDrawbalance()) < 0) {
				userAccount.setDrawbalance(userAccount.getBalance());
			}
			userAccount.setTotalBalance(userAccount.getBalance().add(userAccount.getFreeze()));//重新计算一下总金额
			dao.update(userAccount);
			String id = idDao.getUserAccountDetailId();
			UserAccountDetail uad3 = new UserAccountDetail(id, userno,userAccount.getUserName(), orderId, new Date(), amount, AccountType.getAccountType(accountType), CommonStatus.success, userAccount.getBalance(), AccountDetailType.bet_compensation, userAccount.getDrawbalance(), userAccount.getFreeze(), "出票失败赔付", SignStatus.in);
			uad3.setOtherid(accountDetail.getOtherid());
			uad3.setLotteryType(accountDetail.getLotteryType());
			uad3.setPhase(accountDetail.getPhase());
			uad3.setAgencyNo(accountDetail.getAgencyNo());
			userAccountDetailDao.insert(uad3);
		}catch (Exception e){
			logger.error("赔付失败",e);
            throw new LotteryException("订单赔付失败:"+orderId);
		}

	}



}
