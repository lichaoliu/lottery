package com.lottery.ticket.sender.processor;

import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by fengqinyun on 16/9/3.
 * 网络错误
 */
@Service
public class CommonHttpErrorTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor{
    @Override
    protected void init() {
        sendResultMap.put(TicketSendResultStatus.sys_busy, this);
        sendResultMap.put(TicketSendResultStatus.http_502, this);
        sendResultMap.put(TicketSendResultStatus.http_504, this);
        sendResultMap.put(TicketSendResultStatus.http_404, this);
        sendResultMap.put(TicketSendResultStatus.http_connection_refused, this);
    }

    @Override
    public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
        Date sendtime=ticketSendResult.getSendTime();
        String dateStr=null;
        if(sendtime!=null){
            dateStr= CoreDateUtils.formatDate(sendtime, DATE_STR);
        }

        String errormsg=String.format("票(%s),在终端(%s),发生http错误,code=%s,送票时间:%s,送票内容:%s,terminalType=%s",ticket.getId(),ticketBatch.getTerminalId(),ticketSendResult.getStatus(),dateStr,ticketSendResult.getSendMessage(),ticketSendResult.getTerminalType());
        logger.error(errormsg);
        systemExceptionMessageHandler.saveMessage(errormsg);
        return TicketBatchSendResultProcessType.reallot;
    }
}
