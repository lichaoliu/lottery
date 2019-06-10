package com.lottery.ticket.sender.worker.thread;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * 
 */


import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.terminal.ITerminalSelector;

/**
 * 多线程送票时的实际执行送票线程执行体
 * @author fengqinyun
 *
 */
public class MultiThreadInstanceTicketSenderRunnable extends AbstractTicketSenderRunnable {

	private TicketBatchService ticketBatchService;



	private ITerminalSelector terminalSelector;
	private Queue<TicketBatch> waitingProcessTicketBatchQueue=new LinkedTransferQueue<TicketBatch>();





	public void addQueue(TicketBatch ticketBatch){
		waitingProcessTicketBatchQueue.add(ticketBatch);
	}
	public void addQueue(List<TicketBatch> ticketBatchList){
		waitingProcessTicketBatchQueue.addAll(ticketBatchList);
	}
    @Override
    protected void executeRun() {
        if (this.ticketSendWorker == null) {
            logger.error("未绑定送票器");
            return;
        }

        if (this.waitingProcessTicketBatchQueue == null) {
            logger.error("未绑定待处理批次队列");
            return;
        }
        logger.info("准备开始执行送票线程");
        this.execute();
    }
    
	@Override
	public void execute() {
		while (running) {
			while (running) {

				TicketBatch ticketBatch = this.waitingProcessTicketBatchQueue.poll();
				if (ticketBatch == null) {
					break;
				}

				LotteryType lotteryType =LotteryType.getLotteryType( ticketBatch.getLotteryType());
				String batchId=null;
				try {
					batchId=ticketBatch.getId();
					TerminalType terminalType = terminalSelector.getTerminalType(ticketBatch.getTerminalId());
					if (terminalType == null) {
						logger.error("该批次({})指定的出票点({})未找到可用的出票商", batchId, ticketBatch.getTerminalId());
						continue;
					}
					ticketSendWorker.execute(ticketBatch, lotteryType);
				} catch (Exception e) {
					logger.error("多线程送票批次[{}]出错",batchId,e);
					logger.error(e.getMessage(),e);

				}
			}

            long interval = this.getInterval();

            if (interval <= 0) {
                interval = 1000l;
            }
			
			synchronized (this) {
				try {
					wait(interval);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}
	public TicketBatchService getTicketBatchService() {
		return ticketBatchService;
	}

	public void setTicketBatchService(TicketBatchService ticketBatchService) {
		this.ticketBatchService = ticketBatchService;
	}



	public void setTerminalSelector(ITerminalSelector terminalSelector) {
		this.terminalSelector = terminalSelector;
	}



	public void reload()throws Exception{

		logger.error("需要送票数量:{}",waitingProcessTicketBatchQueue.size());
	}




}
