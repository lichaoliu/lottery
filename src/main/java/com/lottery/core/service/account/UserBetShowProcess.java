package com.lottery.core.service.account;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.dto.DocumentaryOrder;
import com.lottery.common.dto.ShareOrder;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户晒单,跟单金额操作
 * */
@Service
public class UserBetShowProcess {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private UserAccountDAO dao;
	@Autowired
	private LotteryOrderDAO lotteryOrderDao;
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDao;
	/**
	 * 晒单跟单提成
	 * */
	@Transactional
	public BigDecimal shareOrderDeduct(ShareOrder shareOrder) {
		try {
			String shareUserno = shareOrder.getUserno();
			String shareLotteryOrderId = shareOrder.getOrderId();
			BigDecimal commission = new BigDecimal(shareOrder.getCommission());
			if (commission.compareTo(BigDecimal.ZERO)<=0){
				logger.error("订单:{}提成为0,返回",shareOrder.getOrderId());
				return  BigDecimal.ZERO;
			}
			UserAccountDetail uad2 = userAccountDetailDao.getByPayIdAndType(shareLotteryOrderId, AccountType.showbet, AccountDetailType.add);
			if (uad2 != null) {
				logger.error("交易:[{}}提成记录已存在", shareLotteryOrderId);
				return BigDecimal.ZERO;
			}

			LotteryOrder shareLotteryOrder = lotteryOrderDao.find(shareLotteryOrderId);
			if (shareLotteryOrder == null) {
				logger.error("{}晒单订单不存在", shareLotteryOrderId);
				return BigDecimal.ZERO;
			}

			if (shareLotteryOrder.getSmallPosttaxPrize() != null && (shareLotteryOrder.getOrderResultStatus() == OrderResultStatus.win_already.getValue() || shareLotteryOrder.getOrderResultStatus() == OrderResultStatus.win_big.getValue())) {
				
				Map<String, Set<String>> map = new HashMap<String, Set<String>>();
				for (DocumentaryOrder documentaryOrder : shareOrder.getDocumentaryList()) {
					String userno = documentaryOrder.getUserno();
					String orderId = documentaryOrder.getOrderId();
					if (userno.equals(shareUserno)) {// 判断晒单人与发单人是否一个人
						continue;
					}
					if (!map.containsKey(userno)) {
						Set<String> set = new HashSet<String>();
						set.add(orderId);
						map.put(userno, set);
					} else {
						map.get(userno).add(orderId);
					}
				}

				if (map.size() == 0) {
					return BigDecimal.ZERO;
				}
				int size = map.values().size();
				Long id = idDao.getUserAccountDetailId(size + 1);
				BigDecimal totalAmount = new BigDecimal(0);
				for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
					String userno = entry.getKey();

					Set<String> set = entry.getValue();
					UserAccount userAccount = dao.findWithLock(userno, true);
					for (String orderId : set) {
						LotteryOrder lotteryOrder = lotteryOrderDao.find(orderId);
						if (lotteryOrder == null || lotteryOrder.getSmallPosttaxPrize() == null) {
							logger.error("订单{}晒单跟单提成异常", orderId);
							continue;
						}

						BigDecimal subAmount = lotteryOrder.getSmallPosttaxPrize().multiply(commission).setScale(2, BigDecimal.ROUND_HALF_UP);
						totalAmount = totalAmount.add(subAmount);
						userAccount.setBalance(userAccount.getBalance().subtract(subAmount));
						userAccount.setTotalBalance(userAccount.getTotalBalance().subtract(subAmount));
						if (userAccount.getBalance().compareTo(userAccount.getDrawbalance()) < 0) {
							userAccount.setDrawbalance(userAccount.getBalance());
						}
						String tid = idDao.getId(id);
						UserAccountDetail uad = new UserAccountDetail(tid, userno,userAccount.getUserName(), orderId, new Date(), subAmount, AccountType.followbet, CommonStatus.success, userAccount.getBalance(), AccountDetailType.deduct, userAccount.getDrawbalance(), userAccount.getFreeze(), "跟单提成扣款", SignStatus.out);
						uad.setOtherid(shareOrder.getOrderId());
						dao.merge(userAccount);
						userAccountDetailDao.insert(uad);
						id++;
					}
				}
				UserAccount shareAccount = dao.findWithLock(shareUserno, true);
				shareAccount.setBalance(shareAccount.getBalance().add(totalAmount));
				shareAccount.setTotalBalance(shareAccount.getTotalBalance().add(totalAmount));
				shareAccount.setDrawbalance(shareAccount.getDrawbalance().add(totalAmount));
				String tid = idDao.getId(id);
				UserAccountDetail uad = new UserAccountDetail(tid, shareUserno,shareAccount.getUserName(), shareLotteryOrderId, new Date(), totalAmount, AccountType.showbet, CommonStatus.success, shareAccount.getBalance(), AccountDetailType.add, shareAccount.getDrawbalance(), shareAccount.getFreeze(), "晒单提成", SignStatus.in);
				uad.setOtherid(shareOrder.getOrderId());
				dao.merge(shareAccount);
				userAccountDetailDao.insert(uad);
				return totalAmount;
			} else {
				logger.error("晒单订单[{}]未中奖不能拿提成", shareLotteryOrderId);
				return BigDecimal.ZERO;
			}

		} catch (Exception e) {
			logger.error("订单{}提成加钱异常", shareOrder.getOrderId(), e);
			throw new LotteryException("订单" + shareOrder.getOrderId() + "成加钱异常");
		}
	}

}
