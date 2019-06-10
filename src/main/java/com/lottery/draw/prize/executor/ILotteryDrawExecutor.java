package com.lottery.draw.prize.executor;

import com.lottery.draw.ILotteryDrawTask;

/**
 * 开奖执行器
 * @author fengqinyun
 *
 */
public interface ILotteryDrawExecutor {
	/**
	 * 执行开奖任务
	 * @param lotteryDrawTask
	 * @return
	 */
	public void execute(ILotteryDrawTask lotteryDrawTask) throws Exception;
	
	
	
	/**
	 * 得到开奖扫描结果
	 * @return
	 * @throws LotteryDrawExecutorException
	 */
	//public LotteryDrawScanner getScanResult(ILotteryDrawTask lotteryDrawTask) throws LotteryDrawExecutorException;
	
	/**
	 * 销毁开奖子线程
	 * @throws
	 */
	public void destroyChildrenThreads() throws Exception;
	
	/**
	 * 记录开奖日志
	 * @param lotteryDrawTask
	 */
	public void updateDrawStatus(ILotteryDrawTask lotteryDrawTask) throws Exception;
	
}
