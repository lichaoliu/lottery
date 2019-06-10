package com.lottery.ticket.sender.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.sender.ITicketSendWorker;
@Service
public class TicketSendProcess {
	
	protected final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ITerminalSelector terminalSelector; 
	@Autowired
	protected TicketBatchService ticketBatchService;
	@Autowired
	protected ITicketSendWorker ticketSendWorker;
	public void send(String batchId ){
		TicketBatch ticketBatch=ticketBatchService.get(batchId);
		if(ticketBatch==null){
			logger.error("批次{}不存在",batchId);
			return;
		}
		if(ticketBatch.getStatus()==TicketBatchStatus.SEND_SUCCESS.value||ticketBatch.getStatus()==TicketBatchStatus.SEND_FAILURE.value){
			logger.error("批次{}的送票状态是:[{}],是完结态,不需要送票",batchId,ticketBatch.getStatus());
			return;
		}
		Long terminalId=ticketBatch.getTerminalId();
		TerminalType terminalType=terminalSelector.getTerminalType(terminalId);
		if(terminalType==null){
			logger.error("批次{}类型终端id:[{}]未找到",batchId,terminalId);
			return;
		}
		
		if (ticketSendWorker == null) {
			logger.error("未找到({})的送票器", terminalType.getName());
			return;
		}
		LotteryType lotteryType=LotteryType.get(ticketBatch.getLotteryType());
		try {
			ticketSendWorker.execute(ticketBatch,lotteryType);
		} catch (Exception e) {
			logger.error("批次{}送票出错",batchId,e);
		}
		logger.info("批次{}送票结束",batchId);
	}
}
