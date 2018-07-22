package com.lottery.ticket.processor;

import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;

/**
 * 针对一种特定错误码的票处理器
 */
public interface ITicketVenderProcessor {

    public void process(Ticket ticket, TicketVender ticketVender);
}
