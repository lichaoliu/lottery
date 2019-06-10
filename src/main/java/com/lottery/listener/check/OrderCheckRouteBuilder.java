package com.lottery.listener.check;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class OrderCheckRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.betChercher.value+"?concurrentConsumers=10&asyncConsumer=true").to("bean:orderCheckListener?method=process").setId("普通检票");
    }
}
