package com.lottery.ticket.sender;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.terminal.ITerminalSelector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketSendProccess {

	protected final Logger logger= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ITerminalSelector terminalSelector;
	@Autowired
	protected TicketBatchService ticketBatchService;
	@Autowired
	protected ITicketSendWorker ticketSendWorker;
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	@Autowired
	protected QueueMessageSendService queueMessageSendService;


	public void send(String batchId ) {
		try {
			logger.error("批次:{}手工送票开始",batchId);
			TicketBatch ticketBatch = ticketBatchService.get(batchId);
			if (ticketBatch == null) {
				logger.error("批次{}不存在", batchId);
				return;
			}
			if (ticketBatch.getStatus() == TicketBatchStatus.SEND_SUCCESS.value || ticketBatch.getStatus() == TicketBatchStatus.SEND_FAILURE.value) {
				logger.error("批次{}的送票状态是:[{}],是完结态,不需要送票", batchId, ticketBatch.getStatus());
				return;
			}
			Long terminalId = ticketBatch.getTerminalId();
			TerminalType terminalType = terminalSelector.getTerminalType(terminalId);
			if (terminalType == null) {
				logger.error("批次{}类型终端id:[{}]未找到", batchId, terminalId);
				return;
			}

			if (ticketSendWorker == null) {
				logger.error("未找到({})的送票器", terminalType.getName());
				return;
			}
			LotteryType lotteryType = LotteryType.get(ticketBatch.getLotteryType());
			try {
				ticketSendWorker.execute(ticketBatch, lotteryType);
			} catch (Exception e) {
				logger.error("批次{}送票出错", batchId, e);
			}

			logger.info("批次{}送票结束", batchId);
		}catch (Exception e){
			logger.error("批次{}手工送票出错", batchId);
		}
	}

	public  void sendOrder(String userno){

		try{
			List<LotteryOrder> orderList=lotteryOrderService.getByUsernoAndStatus(userno, OrderStatus.PRINT_WAITING.value);
			if (orderList!=null&&orderList.size()>0){
				for (LotteryOrder lotteryOrder:orderList){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderId", lotteryOrder.getId());
					queueMessageSendService.sendMessage(QueueName.betFreeze, map);
				}
			}
		}catch (Exception e){

		}
	}
}
