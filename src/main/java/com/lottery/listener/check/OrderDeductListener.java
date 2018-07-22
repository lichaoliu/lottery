package com.lottery.listener.check;

import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.core.handler.UserAccountHandler;
import com.lottery.core.service.account.UserRebateInstantService;

@Service
public class OrderDeductListener  {

	private final Logger logger= LoggerFactory.getLogger(getClass());
	@Autowired
	protected UserAccountHandler accountHandler;
	@Autowired
	private UserRebateInstantService userRebateInstantService;

	
	public void deduct(@Header("orderId")String orderId){
		try{
			accountHandler.deduct(orderId);
		}catch(Exception e){
			logger.error("订单({})扣款出现异常",orderId,e);
		}
		rebate(orderId);
		
	}
	public void unfreeze(@Header("orderId")String orderId){
		try{
			accountHandler.unfreeze(orderId);
		}catch(Exception e){
			logger.error("订单({})解冻出现异常",orderId,e);
		}
		rebate(orderId);
	}
	
	public void refund(@Header("orderId") String orderId){
		try {
			accountHandler.refund(orderId);
		}catch (Exception e) {
			logger.error("订单({})退款出错",orderId, e);
		}
	}
	
	
	protected void rebate(String orderId){
		try{
			userRebateInstantService.betRebate(orderId);
		}catch(Exception e){
			logger.error("订单[{}]时时返现出错",orderId,e);
		}
		try{
			userRebateInstantService.hemaiRebateOrder(orderId);
		}catch(Exception e){
			logger.error("订单合买[{}]时时返现出错",orderId,e);
		}
	}

}
