/**
 * 
 */
package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fengqinyun
 * 事件执行线程处理的最小单位：调度任务
 */
public interface IScheduledTask extends Serializable {
	
	/**
	 * 该任务的彩种类型
	 * @return
	 */
	public LotteryType getLotteryType();
	
	/**
	 * @return 任务应该被调度的时间
	 */
	public Date getTaskTime();
	
	public PhaseEventType getEventType();
	
	/**
	 * 获取任务信息
	 * @return
	 */
	public String getTaskInfo();
	
	/**
	 * 任务是否重复
	 * @return
	 */
	public boolean isDuplicate();

	/**
	 * 偏移量
	 * */
	public long getOffsetTime();
	/**
	 * 获取期号
	 * */
	public String getCurrentPhaseNo();

}
