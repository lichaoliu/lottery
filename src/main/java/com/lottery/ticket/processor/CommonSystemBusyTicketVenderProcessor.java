package com.lottery.ticket.processor;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;

@Service
public class CommonSystemBusyTicketVenderProcessor extends AbstractTicketVenderProcessor {

	@Override
	protected void execute(Ticket ticket, TicketVender ticketVender) {
		// 对于系统繁忙的票不做任何处理
		String message=String.format("票:%s,在终端:%s,检票异常,请注意",ticket.getId(),ticket.getBatchId());
		systemExceptionMessageHandler.saveMessage(message);
	}

	@Override
	protected void init() {
		ticketVenderProcessorMap.put(TicketVenderStatus.system_busy, this);
	}
}
