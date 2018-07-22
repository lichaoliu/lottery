/**
 * 
 */
package com.lottery.ticket.phase.thread.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.ticket.phase.thread.IScheduledTask;

import java.util.Date;

 
public abstract class AbstractScheduleTask implements IScheduledTask {
	
	private static final long serialVersionUID = 1L;
	private LotteryType lotteryType;			// 彩种类型
	private PhaseEventType eventType;			// 彩期事件类型
	private Date taskTime;						// 任务开始时间
	private boolean duplicate;					// 任务是否重复
	private long offsetTime;//偏移量
	private String currentPhaseNo;//当前期号

	@Override
	public PhaseEventType getEventType() {
		return this.eventType;
	}

	@Override
	public LotteryType getLotteryType() {
		return this.lotteryType;
	}

	@Override
	abstract public String getTaskInfo();

	@Override
	public Date getTaskTime() {
		return this.taskTime;
	}

	@Override
	public boolean isDuplicate() {
		return this.duplicate;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public void setEventType(PhaseEventType eventType) {
		this.eventType = eventType;
	}

	public void setTaskTime(Date taskTime) {
		this.taskTime = taskTime;
	}

	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}
	public String getCurrentPhaseNo() {
		return currentPhaseNo;
	}

	public void setCurrentPhaseNo(String currentPhaseNo) {
		this.currentPhaseNo = currentPhaseNo;
	}

	@Override
	public long getOffsetTime() {
		return offsetTime;
	}

	public void setOffsetTime(long offsetTime) {
		this.offsetTime = offsetTime;
	}

}
