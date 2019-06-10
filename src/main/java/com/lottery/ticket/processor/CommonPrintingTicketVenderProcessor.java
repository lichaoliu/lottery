package com.lottery.ticket.processor;


import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;

import java.util.Date;

@Service
public class CommonPrintingTicketVenderProcessor extends AbstractTicketVenderProcessor {

    @Override
    protected void execute(Ticket ticket, TicketVender ticketVender) {
        // 对于出票中的票不做任何处理
        if(ticket.getDeadline()!=null&&(new Date()).after(ticket.getDeadline())){
            logger.error("票:{}已超过出票时间,但对方查询仍为出票中,发送:{},返回:{}",ticket.getId(),ticketVender.getSendMessage(),ticketVender.getResponseMessage());
        }
    }

	@Override
    protected void init() {
		  ticketVenderProcessorMap.put(TicketVenderStatus.printing, this);
    }

}
