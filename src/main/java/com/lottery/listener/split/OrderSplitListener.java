package com.lottery.listener.split;

import com.lottery.common.contains.QueueName;
import com.lottery.core.handler.OrderSplitHandler;
import com.lottery.core.jms.AbstractJmsStartListener;
import com.lottery.core.service.queue.QueueMessageSendService;
import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderSplitListener{


	@Autowired
	protected OrderSplitHandler splitHandler;

	public void process(@Header("orderId") String orderid){
		splitHandler.handler(orderid);
	}



}
