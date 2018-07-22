/**
 * 
 */
package com.lottery.ticket.sender.worker.thread;

import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.domain.ticket.TicketBatch;

/**多线程送票
 * @author fengqinyun
 *
 */
public class MultiThreadTicketSenderRunnable extends CommonTicketSenderRunnable {
	
	/**
	 * 实际生成送票子线程的数目
	 */
	private int threadCount = 10;
	private List<MultiThreadInstanceTicketSenderRunnable> runnableList = new ArrayList<MultiThreadInstanceTicketSenderRunnable>();
	private long instanceInterval = 1000l;

		
	/**
	 * 初始化送票子线程并启动
	 * @throws Exception
	 */
	protected void initInstance() throws Exception {
		for (int i = 0; i < threadCount; i ++) {
			MultiThreadInstanceTicketSenderRunnable runnable =new MultiThreadInstanceTicketSenderRunnable();
			runnable.setLotteryList(this.getLotteryList());
			runnable.setTicketSendWorker(ticketSendWorker);
			runnable.setTerminalSelector(this.getTerminalSelector());
			runnable.setTicketBatchService(this.getTicketBatchService());
			ThreadContainer threadContainer = new ThreadContainer(runnable,"送票子线程:" + i);
			runnable.setInterval(this.getInstanceInterval());
			threadContainer.setBeforeRunWaitTime(0);
			threadContainer.start();
			runnableList.add(runnable);
		}
	}
	
	/**
	 * 销毁送票子线程
	 * @throws Exception
	 */
	protected void destroyInstance()  throws Exception {
		if (runnableList != null) {
			for (MultiThreadInstanceTicketSenderRunnable runnable : runnableList) {
				runnable.executeStop();
			}
			runnableList.clear();
		}
	}
	
	protected void dispatch(List<TicketBatch> ticketBatchList) {

		while (true){

			if (ticketBatchList==null||ticketBatchList.isEmpty()){
				break;
			}
			for (MultiThreadInstanceTicketSenderRunnable runnable : runnableList) {
				if(ticketBatchList==null||ticketBatchList.isEmpty()){
					break;
				}
				runnable.addQueue(ticketBatchList.remove(0));
				runnable.executeNotify();
			}


		}

	}


	
	protected void beforeDispatch(List<TicketBatch> ticketBatchList) throws Exception {
	for (TicketBatch ticketBatch : ticketBatchList) {
			ticketBatch.setStatus(TicketBatchStatus.SEND_QUEUED.value);
			ticketBatchService.updateTicketBatchStatus(ticketBatch);
		}
	}

	@Override
	public void execute() {
		logger.info("初始化送票子线, count={}", threadCount);
		try {
			
			initInstance();
		} catch (Exception e) {
			logger.error("送票子线程初始化失败", e);
			return;
		}
		
		// 回收上次队列中未处理完的批次
		for (LotteryType lotteryType : this.getLotteryList()) {
			// 获取当前期待送票的批次
			List<TicketBatch> ticketBatchList = null;
			try {
				ticketBatchList = ticketBatchService.findForSendRecycle(lotteryType.value);
			} catch (Exception ex) {
				logger.error("查询({})要送票的批次出错!",lotteryType.getName(), ex);
			}
			if (ticketBatchList == null || ticketBatchList.isEmpty()) {
				logger.info("彩种({})没有未送票的批次", lotteryType.getName());
				continue;
			}

            ticketBatchList = this.filterTerminalSingletonDispatch(ticketBatchList);
			
			// 分派到子线程
			dispatch(ticketBatchList);
		}
		
		while (running) {
			for (LotteryType lotteryType : this.getLotteryList()) {
				
				logger.info("本次要执行送票的彩种为：{}, id={}", lotteryType.getName(), lotteryType.getValue());

				if (this.isDuringGlobalSendForbidPeriod(lotteryType)) {
					logger.info("此彩种处于全局停售期，不做送票处理, {}", lotteryType);
					continue;
				}

				List<Long> allExcludeTerminalIdList = new ArrayList<Long>();
				
				// 获取此彩种暂停送票的终端ID列表    
                List<Long> pausedTerminalConfigList = this.getTerminalSelector().getPausedTerminalIdList(lotteryType);

                if (pausedTerminalConfigList != null&&pausedTerminalConfigList.size()>0) {
                    allExcludeTerminalIdList.addAll(pausedTerminalConfigList);
                }

			

				// 获取当前期待送票的批次
				List<TicketBatch> ticketBatchList = null;
				try {
					ticketBatchList = this.getTicketBatchService().findForSend(lotteryType, allExcludeTerminalIdList, this.getMaxProcessBatchCount());
				} catch (Exception ex) {
					logger.error("查询({})要送票的批次出错!",lotteryType.getName(), ex);
				}
				if (ticketBatchList == null || ticketBatchList.isEmpty()) {
					logger.info("彩种({})没有未送票的批次", lotteryType.getName());
					continue;
				}
				
				try {
					ticketBatchList = this.filterSendPaused(ticketBatchList);

					ticketBatchList = this.filterTerminalSingletonDispatch(ticketBatchList);
					
					// 处理成中间态
					beforeDispatch(ticketBatchList);
					
					// 分派到子线程
					dispatch(ticketBatchList);
				} catch (Exception e) {
					logger.error("彩种{}, 批次送票处理失败", lotteryType.getName());
					continue;
				}
			}
			
			synchronized (this) {
				try {
					wait(this.getInterval());
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		
		logger.info("线程退出，销毁送票子线程");
		try {
			destroyInstance();
		} catch (Exception e) {
			logger.error("送票子线程销毁失败", e);
		}
	}

	protected int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	protected List<MultiThreadInstanceTicketSenderRunnable> getRunnableList() {
		return runnableList;
	}

	public long getInstanceInterval() {
		return instanceInterval;
	}

	public void setInstanceInterval(long instanceInterval) {
		this.instanceInterval = instanceInterval;
	}

	
}
