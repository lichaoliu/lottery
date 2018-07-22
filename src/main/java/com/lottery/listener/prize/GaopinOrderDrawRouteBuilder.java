package com.lottery.listener.prize;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/7/3.
 */
public class GaopinOrderDrawRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.prizeorderGaopin.getValue()+"?concurrentConsumers=10&asyncConsumer=true").to("bean:orderDrawListener?method=drawWork").setId("高频算奖");
    }
}
