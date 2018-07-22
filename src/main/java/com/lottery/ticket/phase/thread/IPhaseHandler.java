/**
 * 
 */
package com.lottery.ticket.phase.thread;

import java.util.List;

import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.thread.IThreadRunnable;
import com.lottery.core.domain.LotteryPhase;


public interface IPhaseHandler extends IThreadRunnable {

	/**
	 * 执行重载请求
	 */
	public void executeReload();

	/**
	 * 如果当前期发生变化，加载新的当前期的调度事件
	 */
	public void executeTaskLoad();
	
	/**
	 * 查询当前期任务列表并且输出
	 */
	public String executeQueryTaskInfo();
	
	/**
	 * 暂停彩期守护
	 */
	public void executePause();
	
	/**
	 * 恢复暂停的彩期守护
	 */
	public void executeResume();
	
	/**
	 * 清空当前的守护事件
	 */
	public void executeTaskClear();

	/**
	 * 判断是否包含彩期守护绑定的彩期事件
	 */
	public boolean hasPhaseEvent(PhaseEventType phaseEventType);

	/**
	 * 执行指定的彩期守护事件
	 */
	public String executeSpecifyPhaseEvent(LotteryPhase phase, PhaseEventType phaseEventType, List<String> races);

	/**
	 * 清空指定彩种的彩期守护事件
	 */
	public String executeSpecifyPhaseEventClear(PhaseEventType phaseEventType);

    /**
     * 获取守护中的事件列表
     * @return 守护中的事件列表
     */
    public List<IScheduledTask> getScheduledTaskList();

}
