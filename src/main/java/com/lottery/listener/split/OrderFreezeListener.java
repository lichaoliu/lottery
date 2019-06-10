package com.lottery.listener.split;

import com.lottery.common.contains.QueueName;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.handler.OrderSplitHandler;
import com.lottery.core.handler.UserAccountHandler;
import com.lottery.core.jms.AbstractJmsStartListener;
import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class OrderFreezeListener {

	private Logger logger= LoggerFactory.getLogger(getClass());
	@Autowired
	protected UserAccountHandler accountHandler;
	@Autowired
	protected OrderSplitHandler orderSplitHandler;



	public void process(@Header("orderId")String orderId){
		try{
			accountHandler.freeze(orderId);
			orderSplitHandler.handler(orderId);

		}catch (LotteryException e){
			logger.error("订单({})冻结出现异常",orderId,e);
		}catch(Exception e){
			logger.error("订单({})冻结出现异常",orderId,e);
		}
	}


}
