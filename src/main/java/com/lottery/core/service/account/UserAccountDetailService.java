package com.lottery.core.service.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.UserAccountDetailDAO;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;

@Service
public class UserAccountDetailService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IdGeneratorDao idDao;

	@Autowired
	private UserAccountDetailDAO userAccountDetailDao;



	@Transactional
	public List<UserAccountDetail> findEncashAccountDetailPage(String payId, String userno, String startTime, String endTime, PageBean<UserAccountDetail> page) {
		List<UserAccountDetail> list = new ArrayList<UserAccountDetail>();
		try {
			String whereString = "userno=:userno and payType=:payType  and create_time>=:startTime  and create_time<:endTime";
			String whereS = "userno=:userno  and create_time>=:startTime  and create_time<:endTime";
			String orderByString = "order by create_time desc,id desc";
			Map<String, Object> map = new HashMap<String, Object>();
			Date startDate = CoreDateUtils.parseDate(startTime, CoreDateUtils.DATETIME);
			Date endDate = CoreDateUtils.parseDate(endTime, CoreDateUtils.DATETIME);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.add(Calendar.DATE, 1);

			if (Integer.parseInt(payId) == -1) {
				map.put("userno", userno);
				map.put("startTime", startDate);
				map.put("endTime", calendar.getTime());
				list = userAccountDetailDao.findPageByCondition(whereS, map, page, orderByString);

			} else {
				map.put("userno", userno);
				map.put("startTime", startDate);
				map.put("endTime", calendar.getTime());
				map.put("payType", Integer.valueOf(payId));
				list = userAccountDetailDao.findPageByCondition(whereString, map, page, orderByString);
			}

		} catch (Exception e) {
			logger.error("查询账户记录错误", e);
			throw new LotteryException(ErrorCode.system_error, e.getMessage());
		}
		return list;

	}

	/***
	 * 根据用户名查询交易详情
	 * 
	 * @param userno
	 *            用户名
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * */
	@Transactional
	public List<UserAccountDetail> getByUserno(String userno, String startTime, String endTime, PageBean<UserAccountDetail> page) {
		BetweenDate date = SpecialDateUtils.getBetweenDate(3, startTime, endTime);
		return userAccountDetailDao.getByUserno(userno, date.getStartDate(), date.getEndDate(), page);
	}

	/**
	 * 根据交易号，支付类型，交易类型查询
	 * 
	 * @param payid
	 *            交易号
	 * @param payType
	 *            交易类型
	 * @param accountDetailType
	 *            交易类型
	 * */
	@Transactional
	public UserAccountDetail getByPayIdAndType(String payid, AccountType payType, AccountDetailType accountDetailType) {
		return userAccountDetailDao.getByPayIdAndType(payid, payType, accountDetailType);
	}

	/**
	 * 投注成功金额统计
	 * 
	 * @param userno
	 *            用户编号
	 * @param lotteryTypeList
	 *            彩种
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * */
	public Map<Integer, BigDecimal> getBetAmountStatic(String userno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		return userAccountDetailDao.getBetAmountStatic(userno, lotteryTypeList, startDate, endDate);
	}

	/**
	 * 用户投注成功金额统计
	 * 
	 * @param userno
	 *            用户编号
	 * @param lotteryTypeList
	 *            彩种
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * */
	public BigDecimal getUsernoBetAmountStatic(String userno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		return userAccountDetailDao.getUsernoBetAmountStatic(userno, lotteryTypeList, startDate, endDate);
	}

	/**
	 * 渠道投注成功金额统计
	 * 
	 * @param agencyno
	 *            渠道号
	 * @param lotteryTypeList
	 *            彩种
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * */
	@Transactional
	public BigDecimal getAgentBetAmountStatic(String agencyno, List<Integer> lotteryTypeList, Date startDate, Date endDate) {
		return userAccountDetailDao.getAgentBetAmountStatic(agencyno, lotteryTypeList, startDate, endDate);
	}
	/**
	 * 根据交易号，支付类型，交易类型查询
	 * @param payid 交易号
	 * @param payType 交易类型
	 * @param accountDetailType 交易类型
	 * */
	@Transactional
	public UserAccountDetail getByPayIdAndType(String payid,int payType,int accountDetailType){
		try{
			return userAccountDetailDao.getByPayIdAndType(payid, payType, accountDetailType);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * 根据交易号，交易类型查询
	 * @param payid 交易号
	 * @param accountDetailType 交易类型
	 * */
	@Transactional
	public List<UserAccountDetail> getByPayIdAndDetailType(String payid,int accountDetailType){
		return userAccountDetailDao.getByPayIdAndDetailType(payid, accountDetailType);
	}

	/**
	 * 修改中奖总金额
	 * @param orderId 订单号
	 * @param orderPrizeAmount 中奖金额
	 * */
	@Transactional
	public  int updateOrderPrizeAmount(String orderId,BigDecimal orderPrizeAmount){
	return 	userAccountDetailDao.updateOrderPrizeAmount(orderId,orderPrizeAmount);
	}
}
