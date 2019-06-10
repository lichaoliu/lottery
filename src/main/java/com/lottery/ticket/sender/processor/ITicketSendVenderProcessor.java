package com.lottery.ticket.sender.processor;

import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;

public interface ITicketSendVenderProcessor {

	/**
     * 处理送票后的单张票
     * @param ticketSendResult
     * @param ticket
     * @return 此张票的处理结果下对所属批次的处理方式
     */
    public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch);

}
