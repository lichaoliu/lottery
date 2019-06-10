package com.lottery.ticket.sender;

import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;

/**
 * 出票商送票接口
 */
public interface IVenderTicketSendWorker {

    public List<TicketSendResult> executeSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType) throws Exception;

}
