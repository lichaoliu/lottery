package com.lottery.scheduler.statistic;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.lottery.common.contains.account.RebateType;
import com.lottery.common.contains.account.StatisticType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.account.UserRebate;
import com.lottery.core.domain.account.UserRebateStatistic;
import com.lottery.core.domain.account.UserRebateStatisticPK;

public class UserRebateMonthStatisticsRunnable extends AbstractRebateStatisticsRunnable {

	protected int day=4;
	@Override
	protected void statisticsRun() {
		String monthStr = CoreDateUtils.formatDate(getSartDate(), CoreDateUtils.DATE_YYYYMM);
		Long monthNum = Long.valueOf(monthStr);
		List<UserRebate> rebateList = getByRebateType(RebateType.float_rebate.value);
		for (UserRebate userRebate : rebateList) {
			int lotteryType = userRebate.getLotteryType();
			String userno = userRebate.getUserno();
			try {

				String belongTo = userRebate.getAgencyno();
				BigDecimal betAmount = rebateStatisticService.getMonthTotal(belongTo, lotteryType, monthNum);
				if (betAmount == null || betAmount.compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}

				UserRebateStatisticPK pk = new UserRebateStatisticPK(monthNum, belongTo, lotteryType);
				UserRebateStatistic userRebateStatistic = rebateStatisticService.get(pk);
				if (userRebateStatistic != null) {
					logger.error("[{}]按月统计已存在", pk.toString());
					continue;
				}
				List<UserRebate> floatList = rebateService.getTopRebate(userno, lotteryType, betAmount);
				if (floatList == null || floatList.size() == 0) {
					continue;
				}
				UserRebate floatRebate = floatList.get(0);
				Double pointLocation = floatRebate.getPointLocation();

				List<UserRebateStatistic> statisticList = rebateStatisticService.getByUsernoAndMonth(belongTo, lotteryType, monthNum);
				BigDecimal rebateAmountTotal = BigDecimal.ZERO;
				for (UserRebateStatistic rebateStatistic : statisticList) {
					BigDecimal rebateAmount = rebateStatistic.getAmount().multiply(new BigDecimal(pointLocation));
					rebateAmountTotal=rebateAmountTotal.add(rebateAmount);
					rebateStatistic.setPointLocation(pointLocation);
					rebateStatistic.setRebateAmount(rebateAmount);
					rebateStatisticService.update(rebateStatistic);
				}
				userRebateStatistic = new UserRebateStatistic();
				userRebateStatistic.setAmount(betAmount);
				userRebateStatistic.setBelongTo(userno);
				userRebateStatistic.setCreateTime(new Date());
				userRebateStatistic.setId(pk);
				userRebateStatistic.setMonthNum(monthNum);
				userRebateStatistic.setStatisticLottery(lotteryType);
				userRebateStatistic.setIsAgent(userRebate.getIsAgent());
				userRebateStatistic.setStatisticType(StatisticType.month.value);
				userRebateStatistic.setPointLocation(pointLocation);
				userRebateStatistic.setRebateAmount(rebateAmountTotal);
				if (userRebate.getUserName() != null) {
					userRebateStatistic.setUserName(userRebate.getUserName());
				} else {
					userRebateStatistic.setUserName("按月统计");
				}
				rebateStatisticService.save(userRebateStatistic);
			} catch (Exception e) {
				logger.error("用户 {},彩种{}按月统计出错", userno, lotteryType, e);
			}

		}
	}

	@Override
	protected Date getSartDate() {
		Calendar cd = Calendar.getInstance();
		 cd.add(Calendar.MONTH, -1);
		return cd.getTime();
	}

	
	protected long getWaitTime() {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.HOUR_OF_DAY, hour);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.DATE, day);
        cd.set(Calendar.MILLISECOND, 0);
        if (Calendar.getInstance().get(Calendar.DATE) >day) {
            cd.add(Calendar.MONTH, 1);
        }
        //当前时间已经过了当天删除时间点，等待时间为下一天时间点到当前时间
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= hour) {
            cd.add(Calendar.DATE, 1);
        }
        return cd.getTimeInMillis() - System.currentTimeMillis();
	}
	
	
	@Override
	protected Date getEndDate() {
		// TODO Auto-generated method stub
		return null;
	}

}
