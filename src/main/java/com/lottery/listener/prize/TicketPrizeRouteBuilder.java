package com.lottery.listener.prize;

import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class TicketPrizeRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("jms:queue:encashorderdapan-queue?concurrentConsumers=1&maxConcurrentConsumers=10&asyncConsumer=true").to("bean:ticketPrizeListener?method=encashDapanProcess").setId("大盘派奖");
        from("jms:queue:encashordergaopin-queue?concurrentConsumers=1&maxConcurrentConsumers=10&asyncConsumer=true").to("bean:ticketPrizeListener?method=encashGaopinProcess").setId("高频派奖");
    }
}
