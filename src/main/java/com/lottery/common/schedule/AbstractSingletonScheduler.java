package com.lottery.common.schedule;


/**
 * 同时只允许一个任务执行的调度器
 * @author fengqinyun
 *
 */
public abstract class AbstractSingletonScheduler extends AbstractScheduler {
	
	protected volatile boolean running = false;

	@Override
	public void run() {
		if (running) {
			logger.error("已经有一个相同的({})任务在调度中", this.getName());
			return;
		}
		
		running = true;
		
		super.run();
		
		running = false;
	}
	
}
