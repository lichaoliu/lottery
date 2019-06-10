package com.lottery.scheduler.statistic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.account.RebateType;
import com.lottery.common.contains.account.StatisticType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.account.UserRebate;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.domain.account.UserRebateStatisticPK;

public class UserRebateDayStatisticsRunnable extends AbstractRebateStatisticsRunnable {

	@Override
	protected void statisticsRun() {
		Date startDate = getSartDate();
		Date endDate = getEndDate();
		String dateStr = CoreDateUtils.formatDate(startDate, CoreDateUtils.DATE_YYYYMMDD);
		String dateMonthStr = CoreDateUtils.formatDate(startDate, CoreDateUtils.DATE_YYYYMM);
		Long dateLong = Long.valueOf(dateStr);
		Long monthLong = Long.valueOf(dateMonthStr);

		List<UserRebate> rebateList = getByRebateType(RebateType.float_rebate.value);
		for (UserRebate userRebate : rebateList) {

			int lotteryType = userRebate.getLotteryType();
			String userno = userRebate.getUserno();
			try {
				if (userRebate.getIsPaused() == YesNoStatus.yes.value) {
					continue;
				}
				List<Integer> typeList = new ArrayList<Integer>();
				if (LotteryType.getJclqValue().contains(lotteryType)) {
					typeList.addAll(LotteryType.getJclqValue());
				} else if (LotteryType.getJczqValue().contains(lotteryType)) {
					typeList.addAll(LotteryType.getJczqValue());
				} else if (LotteryType.getDcValue().contains(lotteryType)) {
					typeList.addAll(LotteryType.getDcValue());
				} else if (LotteryType.getZcValue().contains(lotteryType)) {
					typeList.addAll(LotteryType.getZcValue());
				} else {
					typeList.add(lotteryType);
				}

				int isAgent = userRebate.getIsAgent();
				if (isAgent == YesNoStatus.no.value) {
					BigDecimal betAmount = userAccountDetailService.getUsernoBetAmountStatic(userno, typeList, startDate, endDate);
					if (betAmount == null || betAmount.compareTo(BigDecimal.ZERO) == 0) {
						continue;
					}
					UserRebateStatisticPK pk = new UserRebateStatisticPK(dateLong, userno, lotteryType);
					UserRebateStatistic userRebateStatistic = rebateStatisticService.get(pk);
					if (userRebateStatistic != null) {
						logger.error("[{}]按天统计已存在", pk.toString());
						continue;
					}
					userRebateStatistic = new UserRebateStatistic();
					userRebateStatistic.setAmount(betAmount);
					userRebateStatistic.setBelongTo(userno);
					userRebateStatistic.setCreateTime(new Date());
					userRebateStatistic.setId(pk);
					userRebateStatistic.setMonthNum(monthLong);
					userRebateStatistic.setStatisticLottery(lotteryType);
					userRebateStatistic.setIsAgent(isAgent);
					userRebateStatistic.setStatisticType(StatisticType.day.value);
					userRebateStatistic.setPointLocation(0.00);
					userRebateStatistic.setRebateAmount(BigDecimal.ZERO);
					if (userRebate.getUserName() != null) {
						userRebateStatistic.setUserName(userRebate.getUserName());
					} else {
						userRebateStatistic.setUserName("个人统计");
					}
					rebateStatisticService.save(userRebateStatistic);
				} else {
					String agencyno = userRebate.getAgencyno();
					BigDecimal betAmount = userAccountDetailService.getAgentBetAmountStatic(agencyno, typeList, startDate, endDate);
					if (betAmount == null || betAmount.compareTo(BigDecimal.ZERO) == 0) {
						continue;
					}
					UserRebateStatisticPK pk = new UserRebateStatisticPK(dateLong, agencyno, lotteryType);
					UserRebateStatistic userRebateStatistic = rebateStatisticService.get(pk);
					if (userRebateStatistic != null) {
						logger.error("[{}]按天统计已存在", pk.toString());
						continue;
					}
					userRebateStatistic = new UserRebateStatistic();
					userRebateStatistic.setAmount(betAmount);
					userRebateStatistic.setBelongTo(userno);
					userRebateStatistic.setCreateTime(new Date());
					userRebateStatistic.setId(pk);
					userRebateStatistic.setMonthNum(monthLong);
					userRebateStatistic.setStatisticLottery(lotteryType);
					userRebateStatistic.setIsAgent(isAgent);
					userRebateStatistic.setStatisticType(StatisticType.day.value);
					userRebateStatistic.setPointLocation(0.00);
					userRebateStatistic.setRebateAmount(BigDecimal.ZERO);
					if (userRebate.getUserName() != null) {
						userRebateStatistic.setUserName(userRebate.getUserName());
					} else {
						userRebateStatistic.setUserName("渠道统计");
					}
					rebateStatisticService.save(userRebateStatistic);
				}
			} catch (Exception e) {
				logger.error("用户{},彩种{}统计出错", userno, lotteryType, e);
			}

		}

	}

	protected Map<String, List<Integer>> getRebate() {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for (UserRebate userRebate : getByRebateType(RebateType.float_rebate.value)) {
			if (userRebate.getIsPaused() == YesNoStatus.yes.value) {
				continue;
			}
			String userno = userRebate.getUserno();
			int lotteryType = userRebate.getLotteryType();
			if (!map.containsKey(userno)) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(lotteryType);
				map.put(userno, list);
			} else {
				map.get(userno).add(lotteryType);
			}
		}
		return map;
	}

	@Override
	protected Date getSartDate() {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -1);
		cd.set(Calendar.HOUR_OF_DAY, 0);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		return cd.getTime();
	}

	@Override
	protected Date getEndDate() {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DATE, -1);
		cd.set(Calendar.HOUR_OF_DAY, 23);
		cd.set(Calendar.MINUTE, 59);
		cd.set(Calendar.SECOND, 59);
		return cd.getTime();
	}

	protected long getWaitTime() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.HOUR_OF_DAY, this.hour);
		cd.set(Calendar.MINUTE, 0);
		cd.set(Calendar.SECOND, 0);
		cd.set(Calendar.MILLISECOND, 0);
		if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= this.hour) {
			cd.add(Calendar.DATE, 1);
		}
		long waitmillis = cd.getTimeInMillis() - System.currentTimeMillis();
		return waitmillis;

	}

}
