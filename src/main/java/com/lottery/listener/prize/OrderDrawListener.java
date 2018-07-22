package com.lottery.listener.prize;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.QueueName;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.jms.AbstractJmsStartListener;
import org.springframework.stereotype.Service;

@Service
public class OrderDrawListener{

	
    private final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
	private PrizeHandler prizeHandler;


	
	public void drawWork(@Header("orderId") String orderid, @Header("wincode") String wincode){
		try{

			prizeHandler.drawWork(orderid, wincode);

		}catch(Exception e){
			logger.error("订单:{},开奖号码:{},算奖出错",orderid,wincode);
			logger.error(e.getMessage(),e);
		}

		
	}



	public void prizeOpen(@Header("lotteryType") Integer lotteryType, @Header("phase") String phase,@Header("wincode") String wincode){

		logger.error("接到开奖消息:lotteryType={},phase={},wincode={}",lotteryType,phase,wincode);

		try {
			prizeHandler.prizeOpen(lotteryType,phase,wincode);
		} catch (Exception e) {
			logger.error("开奖失败",e);
		}
	}

}
