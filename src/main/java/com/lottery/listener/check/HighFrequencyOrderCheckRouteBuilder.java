package com.lottery.listener.check;

import com.lottery.common.contains.QueueName;
import org.apache.camel.spring.SpringRouteBuilder;

/**
 * Created by fengqinyun on 16/6/15.
 */
public class HighFrequencyOrderCheckRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(QueueName.gaopinChercher.value+"?concurrentConsumers=5&asyncConsumer=true").to("bean:orderCheckListener?method=process").setId("高频检票");
        from(QueueName.cancelChercher.value+"?concurrentConsumers=2&asyncConsumer=true").to("bean:orderCheckListener?method=process").setId("回收检票");
    }
}
