package com.lottery.listener.split;

import org.apache.camel.spring.SpringRouteBuilder;

import com.lottery.common.contains.QueueName;

public class GaopinFreezeRouteBuilder extends SpringRouteBuilder{

	@Override
	public void configure() throws Exception {
		 from(QueueName.betgaopinFreeze.value+"?concurrentConsumers=2&asyncConsumer=true").to("bean:orderFreezeListener?method=process").routeId("高频冻结服务");
		
	}

}
