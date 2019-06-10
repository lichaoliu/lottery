package com.lottery.scheduler.statistic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.PageBean;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.account.UserRebate;
import com.lottery.core.service.account.UserAccountDetailService;
import com.lottery.core.service.account.UserRebateService;
import com.lottery.core.service.account.UserRebateStatisticService;

public abstract class AbstractRebateStatisticsRunnable extends AbstractThreadRunnable {
	@Autowired
	protected UserRebateStatisticService rebateStatisticService;
	@Autowired
	protected UserRebateService rebateService;
	@Autowired
	protected UserAccountDetailService userAccountDetailService;

	protected long interval = 15000;

	protected int hour=6;

	@Override
	protected void executeRun() {
		boolean statisticFlag=false;
		while (running) {
	
			try {
				if(statisticFlag){
					logger.error("开始运行统计");
					statisticsRun();
				}
			} catch (Exception e) {
				logger.error("统计出错", e);
			}
			synchronized (this) {
				try {
					logger.error("统计等待时间{}", getWaitTime());
					this.wait(getWaitTime());
				} catch (InterruptedException e) {
					logger.error(e.getMessage(),e);
				}
				statisticFlag=true;
			}
		}

	}

	protected abstract void statisticsRun();

	protected long getWaitTime() {
		return this.interval;
	}

	protected List<UserRebate> getByRebateType(int rebateType) {
		int max = 15;
		List<UserRebate> allList = new ArrayList<UserRebate>();
		PageBean<UserRebate> pageBean = new PageBean<UserRebate>();
		pageBean.setMaxResult(max);
		int page = 1;
		while (true) {
			pageBean.setPageIndex(page);
			List<UserRebate> list = null;
			try {
				list = rebateService.getByType(rebateType, pageBean);
			} catch (Exception e) {
				logger.error("根据类型统计用户出错", e);
				break;
			}
			if (list != null && list.size() > 0) {
				allList.addAll(list);
				if (list.size() < max) {
					break;
				}
			} else {
				break;
			}
			page++;
		}
		return allList;
	}

	protected abstract Date getSartDate();

	protected abstract Date getEndDate();

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

}
