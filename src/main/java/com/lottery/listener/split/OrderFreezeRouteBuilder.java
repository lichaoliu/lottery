package com.lottery.listener.split;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/5/12.
 */
public class OrderFreezeRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.betFreeze.value+"?concurrentConsumers=10&asyncConsumer=true") .to("bean:orderFreezeListener?method=process").setId("订单冻结");
    }
}
