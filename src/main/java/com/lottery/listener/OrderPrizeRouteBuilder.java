package com.lottery.listener;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/3/17.
 */
public class OrderPrizeRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.prizeExecute.getValue()+"?concurrentConsumers=10&asyncConsumer=true").to("bean:orderDrawListener?method=drawWork").setId("新算奖");
    }
}
