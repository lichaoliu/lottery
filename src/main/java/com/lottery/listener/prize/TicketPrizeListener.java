package com.lottery.listener.prize;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.jms.AbstractJmsStartListener;

@Component
public class TicketPrizeListener {


	
	private final Logger logger= LoggerFactory.getLogger(getClass());
	
    @Autowired
	private PrizeHandler prizeHandler;


	
	public void encashDapanProcess(@Header("orderid") String orderid){
		prizeHandler.encashProcess(orderid, false);
	}
	public void encashGaopinProcess(@Header("orderid") String orderid){
		prizeHandler.encashProcess(orderid, false);
	}
	
}
