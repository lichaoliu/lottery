/**
 * 
 */
package com.lottery.ticket.sender.worker.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.thread.ThreadContainer;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.log.LotteryLog;

/**
 * 每个终端一个送票线程的多线程送票实现
 * 每个线程在该终端初次送票时动态创建
 * @author fengqinyun
 *
 */
public class MultiThreadTerminalTicketSenderRunnable extends CommonTicketSenderRunnable {
	private long instanceInterval = 1500l;//间隔时间(毫秒)
	private Map<Long, MultiThreadInstanceTicketSenderRunnable> terminalRunnableMap = new ConcurrentHashMap<Long, MultiThreadInstanceTicketSenderRunnable>();

	/**
	 * 初始化送票子线程并启动
	 * 如果指定终端号的子线程已存在，直接返回，否则创建
	 * @throws Exception
	 */
	protected MultiThreadInstanceTicketSenderRunnable initInstance(Long terminalId) {
		if (terminalRunnableMap.containsKey(terminalId)) {
			return terminalRunnableMap.get(terminalId);
		}

        synchronized (this) {
            if (terminalRunnableMap.containsKey(terminalId)) {
                return terminalRunnableMap.get(terminalId);
            }
            MultiThreadInstanceTicketSenderRunnable runnable =new MultiThreadInstanceTicketSenderRunnable();
            runnable.setLotteryList(this.getLotteryList());
            runnable.setTicketSendWorker(ticketSendWorker);
            runnable.setTerminalSelector(this.getTerminalSelector());
            runnable.setTicketBatchService(this.getTicketBatchService());
            runnable.setInterval(this.getInstanceInterval());
            ThreadContainer threadContainer = new ThreadContainer(runnable, "终端(" + terminalId.longValue() + ")送票子线程");
            threadContainer.setBeforeRunWaitTime(100);
            threadContainer.start();
            terminalRunnableMap.put(terminalId, runnable);
            return runnable;
        }
    }
	
	/**
	 * 销毁送票子线程
	 * @throws Exception
	 */
	protected void destroyInstance()  throws Exception {
		if (terminalRunnableMap != null) {
			Set<Long> terminalIdSet = terminalRunnableMap.keySet();
			for (Long terminalId : terminalIdSet) {
				MultiThreadInstanceTicketSenderRunnable runnable = terminalRunnableMap.get(terminalId);
				if (runnable!=null){
					runnable.executeStop();
				}else {
					logger.error("终端({})的送票线程为空",terminalId);
				}

			}
			terminalRunnableMap.clear();
		}
	}
	
	protected void dispatch(List<TicketBatch> ticketBatchList) {
		//this.dispatchMap(ticketBatchList);

			for (TicketBatch ticketBatch : ticketBatchList) {
				Long terminalId = ticketBatch.getTerminalId();
				MultiThreadInstanceTicketSenderRunnable runnable = this.initInstance(terminalId);
				runnable.addQueue(ticketBatch);
				runnable.executeNotify();
			}
	}


	protected void dispatchMap(List<TicketBatch> ticketBatchList){

		Map<Long,List<TicketBatch>> ticketBatchMap=new HashMap<Long, List<TicketBatch>>();
		for (TicketBatch ticketBatch:ticketBatchList){
			Long terminalId=ticketBatch.getTerminalId();
			if(ticketBatchMap.containsKey(terminalId)){
				ticketBatchMap.get(terminalId).add(ticketBatch);
			}else{
				List<TicketBatch> ticketBatchList1=new ArrayList<TicketBatch>();
				ticketBatchList1.add(ticketBatch);
				ticketBatchMap.put(terminalId,ticketBatchList1);
			}
		}

		for (Map.Entry<Long, List<TicketBatch>> entry : ticketBatchMap.entrySet()) {
			MultiThreadInstanceTicketSenderRunnable runnable = this.initInstance(entry.getKey());
			runnable.addQueue(entry.getValue());
			runnable.executeNotify();
		}

	}


	protected void beforeDispatch(List<TicketBatch> ticketBatchList) throws Exception {
		for (TicketBatch ticketBatch : ticketBatchList) {
			ticketBatch.setStatus(TicketBatchStatus.SEND_QUEUED.value);
			ticketBatch.setUpdateTime(new Date());
			ticketBatchService.updateTicketBatchStatus(ticketBatch);
		}
	}

	@Override
	public void execute() {
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

				continue;
			}

            ticketBatchList = this.filterTerminalSingletonDispatch(ticketBatchList);
			
			// 分派到子线程
			dispatch(ticketBatchList);
		}
		
		while (running) {
			for (LotteryType lotteryType : this.getLotteryList()) {


				if (this.isDuringGlobalSendForbidPeriod(lotteryType)) {
					LotteryLog.getLotterWarnLog().info("此彩种处于全局停售期,不做送票处理,{}", lotteryType);
					continue;
				}



				// 获取此彩种可以送票的终端ID列表
				List<Long> enableTerminalConfigList = this.getTerminalSelector().getEnabledTerminalIdList(lotteryType);

				if(enableTerminalConfigList==null||enableTerminalConfigList.isEmpty()){
					//logger.info("彩种:{},可用送票终端为空",lotteryType);
					continue;
				}

				for (Long terminalId:enableTerminalConfigList){
					// 获取当前期待送票的批次
					List<TicketBatch> ticketBatchList = null;
					try {
						ticketBatchList = this.getTicketBatchService().findForSend(lotteryType, terminalId, this.getMaxProcessBatchCount());
					} catch (Exception ex) {
						logger.error("查询({})要送票的批次出错!",lotteryType.getName(), ex);
					}
					if (ticketBatchList == null || ticketBatchList.isEmpty()) {
						logger.info("彩种({}),terminalId={},没有未送票的批次", lotteryType.getName(),terminalId);
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
						logger.error("彩种{},批次送票处理失败", lotteryType.getName());
						logger.error(e.getMessage(),e);
					}
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
		
		logger.info("线程退出,销毁送票子线程");
		try {
			destroyInstance();
		} catch (Exception e) {
			logger.error("送票子线程销毁失败", e);
		}
	}

	@Override
	public void executeStop() {
		super.executeStop();

		logger.info("线程退出,销毁送票子线程");
		try {
			destroyInstance();
		} catch (Exception e) {
			logger.error("送票子线程销毁失败", e);
		}
	}


	public long getInstanceInterval() {
		return instanceInterval;
	}

	public void setInstanceInterval(long instanceInterval) {
		this.instanceInterval = instanceInterval;
	}

	
}
