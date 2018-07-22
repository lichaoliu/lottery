package com.lottery.listener.check;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/5/25.
 */
public class TicketCheckRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.ticketCheck.value).to("bean:ticketCheckListener?method=check").setId("手工检票");
        from(QueueName.ticketNotice.value+"?concurrentConsumers=5&asyncConsumer=true").to("bean:ticketCheckListener?method=notice").setId("普通彩种通知处理");
        from(QueueName.ticketNoticeJingcai.value+"?concurrentConsumers=5&asyncConsumer=true").to("bean:ticketCheckListener?method=notice").setId("竞彩通知处理");
    }
}
