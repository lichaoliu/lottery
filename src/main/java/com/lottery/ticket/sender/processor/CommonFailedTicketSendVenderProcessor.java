package com.lottery.ticket.sender.processor;

import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CommonFailedTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor{

	@Override
	public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
		Date sendtime=ticketSendResult.getSendTime();
		String dateStr=null;
		if(sendtime!=null){
			dateStr=CoreDateUtils.formatDate(sendtime, DATE_STR);
		}
		String message = String.format("票(%s)在终端(%s)送票失败,我方状态是(%s),对方返回状态是(%s),错误描述:(%s)",
                ticket.getId(), ticketBatch.getTerminalId(),ticketSendResult.getStatus(),ticketSendResult.getStatusCode(),ticketSendResult.getMessage());
        logger.error(message);

		String errormessage=String.format("票[%s]在终端%s,id=%s,errorcode=%s,送票时间:[%s],内容:%s,返回:%s",ticket.getId(),ticketSendResult.getTerminalType(),ticketBatch.getTerminalId(),ticketSendResult.getStatusCode(),dateStr,ticketSendResult.getSendMessage(),ticketSendResult.getResponseMessage());
        logger.error("票[{}]在终端{},id={},errorcode={},送票时间:[{}],内容:{},返回:{}",ticket.getId(),ticketSendResult.getTerminalType(),ticketBatch.getTerminalId(),ticketSendResult.getStatusCode(),dateStr,ticketSendResult.getSendMessage(),ticketSendResult.getResponseMessage());
		systemExceptionMessageHandler.saveMessage(errormessage);
		if (ticketSendResult.getFailureType() != null){

			if(ticketSendResult.getFailureType()== TicketFailureType.PRINT_LIMITED){
				ticket.setPrintTime(new Date());
				ticket.setStatus(TicketStatus.CANCELLED.value);
				ticket.setFailureType(TicketFailureType.PRINT_LIMITED.value);
				ticket.setFailureMessage(TicketFailureType.PRINT_LIMITED.name);
				ticketService.updateTicketStatusAndFailureInfo(ticket);
				this.sendJms(ticket);
			}
			return TicketBatchSendResultProcessType.retry;//直接当重试处理
			
		}
		return TicketBatchSendResultProcessType.reallot;
	}

	@Override
	protected void init() {
		sendResultMap.put(TicketSendResultStatus.failed, this);
		sendResultMap.put(TicketSendResultStatus.unkown, this);
	}

	
}
