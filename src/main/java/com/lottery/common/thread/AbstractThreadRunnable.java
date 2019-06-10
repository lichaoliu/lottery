/**
 * 
 */
package com.lottery.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractThreadRunnable implements IThreadRunnable {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	protected volatile boolean running = false;
	
	private long beforeRunWaitTime = 0;	//启动前等待时间

	@Override
	public void executeNotify() {
		synchronized (this) {
			notify();
		}
	}

	@Override
	public void executeStop() {
		synchronized (this) {
			running = false;
		}
		executeNotify();
	}

	public void run() {
		if (beforeRunWaitTime > 0) {
			logger.info("启动前休眠等待({})毫秒", beforeRunWaitTime);
			synchronized (this) {
				try {
					wait(beforeRunWaitTime);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		 synchronized (this) {
	            if (running) {
	                logger.error("{}线程已经在运行中",Thread.currentThread().getName());
	                return;
	            }
	            running = true;
	        }
		try {
			executeRun();
		} catch (Exception e) {
			logger.error("线程执行时发生异常", e);
		}
		logger.info("线程执行结束");
		running = false;
	}
	
	abstract protected void executeRun();

	@Override
	public boolean isRunning() {
		return running;
	}

	public long getBeforeRunWaitTime() {
		return beforeRunWaitTime;
	}

	public void setBeforeRunWaitTime(long beforeRunWaitTime) {
		this.beforeRunWaitTime = beforeRunWaitTime;
	}

}
