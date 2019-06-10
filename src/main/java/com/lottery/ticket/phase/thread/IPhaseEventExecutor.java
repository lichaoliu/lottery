/**
 * 
 */
package com.lottery.ticket.phase.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.thread.IThreadRunnable;


public interface IPhaseEventExecutor extends IThreadRunnable {
	
	/**
	 * 添加执行任务
	 */
	public void addScheduledTask(IScheduledTask task);

	/**
	 * 清除执行任务
	 */
	public boolean removeScheduledTask(LotteryType lotteryType, PhaseEventType phaseEventType);

}
