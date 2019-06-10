package com.lottery.ticket.sender.processor;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;

/**
 * 送票成功
 * */
@Service
public class CommonSuccessfulTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor{

	@Override
	public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
		 
		return processSuccessful(ticketSendResult, ticket, ticketBatch);
	}

	@Override
	protected void init() {
		sendResultMap.put(TicketSendResultStatus.success, this);
	}

}
