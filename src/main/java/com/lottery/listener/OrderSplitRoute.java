package com.lottery.listener;

import com.lottery.common.contains.QueueName;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by fengqinyun on 16/3/17.
 */
public class OrderSplitRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.betSplitQueue.value+"?concurrentConsumers=1&maxConcurrentConsumers=10&asyncConsumer=true").to("bean:orderSplitListener?method=process").routeId("拆票服务");
        from(QueueName.betGaopinSplitQueue.value+"?concurrentConsumers=1&maxConcurrentConsumers=10&asyncConsumer=true").to("bean:orderSplitListener?method=process").routeId("高频拆票服务");

    }
}
