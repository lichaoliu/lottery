package com.lottery.ticket.processor;

import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 16/7/5.
 */
@Service
public class CommonLimitedTicketVenderProcessor extends AbstractTicketVenderProcessor {
    @Override
    protected void execute(Ticket ticket, TicketVender ticketVender) {
        logger.error("票({})在终端:{},出票限号,直接置为失败,对方原始返回码为:{},是否通知:{},原始返回字符串:{}", ticket.getId(),ticket.getBatchId(), ticketVender.getStatusCode(), ticketVender.isTicketNotify(), ticketVender.getResponseMessage());

        //String message=String.format("票(%s)在终端:%s出票限号,直接置为失败,对方原始返回码为:%s,是否通知:%s,原始返回字符串:%s", ticket.getId(),ticket.getBatchId(), ticketVender.getStatusCode(), ticketVender.isTicketNotify(), ticketVender.getResponseMessage());
        //systemExceptionMessageHandler.saveMessage(message);
        ticket.setFailureType(TicketFailureType.PRINT_LIMITED.value);
        ticket.setFailureMessage(TicketFailureType.PRINT_LIMITED.name);
        ticket.setStatus(TicketStatus.CANCELLED.value);
        ticketService.updateTicketStatusAndFailureInfo(ticket);
        this.sendJms(ticket);
    }


    @Override
    protected void init() {
        ticketVenderProcessorMap.put(TicketVenderStatus.ticket_limited, this);
    }
}
