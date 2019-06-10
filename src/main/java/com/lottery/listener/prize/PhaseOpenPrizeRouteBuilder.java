package com.lottery.listener.prize;

import com.lottery.common.TopicVirtaulQueueFactory;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TopicName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/3/29.
 */
public class PhaseOpenPrizeRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(TopicVirtaulQueueFactory.getQueueName(TopicName.phaseOpenPrize,"open")+"?concurrentConsumers=1").to("bean:orderDrawListener?method=prizeOpen").routeId("开奖");
    }
}
