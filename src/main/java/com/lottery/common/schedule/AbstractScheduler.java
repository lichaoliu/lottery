package com.lottery.common.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractScheduler implements IScheduler{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		boolean success = executeRun();
		if (!success) {
			logger.error("任务({})执行失败", this.getName());
			return;
		}
		logger.info("执行({})任务成功", this.getName());
	}
	
	/**
	 * 任务主执行体
	 * @return
	 */
	abstract protected boolean executeRun();
}
