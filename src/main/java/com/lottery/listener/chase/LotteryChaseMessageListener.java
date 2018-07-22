package com.lottery.listener.chase;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;

public class LotteryChaseMessageListener implements MessageListener{
	@Autowired
	private LotteryOrderService lotteryOrderService;
	
	private  final Logger logger=LoggerFactory.getLogger(getClass().getName());
	@Override
	public void onMessage(Message message) {
		 if(message instanceof MapMessage){
			 try {
				String orderid=((MapMessage) message).getString("orderId");
				logger.error("lisener订单:{}",orderid);
				
				
			} catch (JMSException e) {
			
				logger.error("接受错误",e);
			}
			 
		 }
		 else if(message instanceof ActiveMQMessage){
			 try {
				 ActiveMQMessage aMsg = (ActiveMQMessage) message;
				 String orderId = (String) aMsg.getProperty("orderId");
					
					logger.error("lisener订单:{}",orderId);
					LotteryOrder order = lotteryOrderService.get(orderId);
					if (order == null||order.getChaseId()==null) {
						return;
					}
					
					
				} catch (Exception e) {
				
					logger.error("接受错误Map",e);
				}
		 }else{
			 logger.error("类型不正确");
		 }
		
		
	}

}
