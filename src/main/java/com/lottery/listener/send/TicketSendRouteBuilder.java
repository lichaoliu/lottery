package com.lottery.listener.send;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/5/25.
 */
public class TicketSendRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.ticketSend.value).to("bean:ticketSendListener?method=process").setId("手工送票");
        from(QueueName.tranasctionbet.value).to("bean:ticketSendListener?method=transactionbet").setId("充值处理待支付");
    }
}
