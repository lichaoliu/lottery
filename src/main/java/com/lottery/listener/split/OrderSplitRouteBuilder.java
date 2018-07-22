package com.lottery.listener.split;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/14.
 */
public class OrderSplitRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.betSplitQueue.value+"?concurrentConsumers=2&asyncConsumer=true").to("bean:orderSplitListener?method=process").routeId("拆票服务");
        from(QueueName.betGaopinSplitQueue.value+"?concurrentConsumers=2&asyncConsumer=true").to("bean:orderSplitListener?method=process").routeId("高频拆票服务");
    }
}

