package com.lottery.core.jms;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class AbstractJmsStartListener implements ApplicationListener<ContextRefreshedEvent>{
	/***
	 * spring的IOC容器和spring MVC的IOC容器的ContextRefreshedEvent
	 * Spring MVC，ContextRefreshedEvent会触发2次，
	 * 1次是ApplicationContext.xml初始化完毕时，一次是mvc的IOC初始化容器完毕时
	 * */
	
	protected volatile boolean running = false;
	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
	@Resource(name="camelContextMap")
	protected Map<String,CamelContext> camelContextMap;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(running){
			return;
		}
		running=true;
		logger.info("jms rotue is starting...");
		try{
			CamelContext camelContext=getCamelContext();
			camelContext.addRoutes(getRouteBuilder());
			if(!camelContext.isStartingRoutes())
			camelContext.start();
			
		}catch(Exception e){
			logger.error("jms启动失败",e);
		}
		
	}
	
	protected abstract RouteBuilder getRouteBuilder();
	
	protected  abstract CamelContext getCamelContext();
	
	
}
