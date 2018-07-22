package com.lottery.listener.prize;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class OrderDrawRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.prizeExecute.getValue()+"?concurrentConsumers=10&asyncConsumer=true").to("bean:orderDrawListener?method=drawWork").setId("大盘算奖");
    }
}
