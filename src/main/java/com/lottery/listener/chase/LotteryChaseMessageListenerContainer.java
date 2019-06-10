package com.lottery.listener.chase;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

public class LotteryChaseMessageListenerContainer extends SimpleMessageListenerContainer {

	 @Override
		public void afterPropertiesSet(){ 	
			this.setDestination(new ActiveMQQueue("VirtualTopicConsumers.b2b.orderprize") );
			super.afterPropertiesSet();
		}
}
