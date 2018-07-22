package com.lottery.ticket.sender.processor;
import com.lottery.common.contains.ticket.TicketBatchSendResultProcessType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/10/18.
 * 票重复
 */
@Service
public class CommonDuplicateTicketSendVenderProcessor extends AbstractTicketSendVenderProcessor{
    @Override
    protected void init() {
        sendResultMap.put(TicketSendResultStatus.duplicate, this);//订单已存在当送票成功处理
    }

    @Override
    public TicketBatchSendResultProcessType process(TicketSendResult ticketSendResult, Ticket ticket, TicketBatch ticketBatch) {
        logger.error("票({})在终端{},terminalId={},订单重复,发送详情:{},返回:{}",ticket.getId(),ticketSendResult.getTerminalType().getName(),ticketBatch.getTerminalId(),ticketSendResult.getSendMessage(),ticketSendResult.getResponseMessage());
        String message=String.format("票(%s)在终端(%s)送票提示重复送票,按送票成功处理",ticket.getId(),ticket.getTerminalId());
        systemExceptionMessageHandler.saveMessage(message);
        return processSuccessful(ticketSendResult, ticket, ticketBatch);
    }
}
