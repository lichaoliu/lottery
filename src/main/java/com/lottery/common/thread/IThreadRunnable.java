/**
 * 
 */
package com.lottery.common.thread;

/**
 * @author fengqinyun
 *
 */
public interface IThreadRunnable extends Runnable {
	
	/**
	 * 得到线程运行状态
	 * @return
	 */
	public boolean isRunning();
	
	/**
	 * 唤醒线程执行
	 */
	public void executeNotify();
	
	/**
	 * 通知线程执行退出
	 */
	public void executeStop();
	
	/**
	 * 设置启动延时，单位：毫秒
	 * @param beforeRunWaitTime
	 */
	public void setBeforeRunWaitTime(long beforeRunWaitTime);
}
