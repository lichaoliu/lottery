/**
 * 
 */
package com.lottery.common.thread;


/**
 * @author fengqinyun
 *
 */
public class ThreadContainer {
	
	private static int counter = 0;
	
	private IThreadRunnable runnable;
	private Thread thread;
	private String name;
	
	public ThreadContainer(IThreadRunnable runnable) {
		this(runnable, null);
	}
	
	public ThreadContainer(IThreadRunnable runnable, String name) {
		this.runnable = runnable;
		this.name = name;
		if (this.name == null) {
			this.name = "Custom Thread " + counter;
			counter ++;
		}
	}
	
	public boolean isRunning() {
		return this.runnable.isRunning();
	}
	
	public void start() {
		if (this.isRunning()) {
			// 防止重复启动
			return;
		}
		this.thread = new Thread(this.runnable, this.name);
		this.thread.setDaemon(true);
		this.thread.start();
	}
	
	public void stop() {
		this.runnable.executeStop();
	}
	
	public void executeNotify() {
		this.runnable.executeNotify();
	}

	public void setBeforeRunWaitTime(long beforeRunWaitTime) {
		this.runnable.setBeforeRunWaitTime(beforeRunWaitTime);
	}
	public IThreadRunnable getRunnable(){
		return this.runnable;
	}

	public static int getCounter() {
		return counter;
	}

	public Thread getThread() {
		return thread;
	}

	public String getName() {
		return name;
	}

	
}
