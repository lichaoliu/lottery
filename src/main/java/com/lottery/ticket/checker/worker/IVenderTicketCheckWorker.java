package com.lottery.ticket.checker.worker;

import java.util.List;

import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;

public interface IVenderTicketCheckWorker {

	public List<TicketVender> process(List<Ticket> ticketList,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception;
}
