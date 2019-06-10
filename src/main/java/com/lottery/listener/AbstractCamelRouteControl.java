package com.lottery.listener;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public abstract class AbstractCamelRouteControl  implements ApplicationListener<ContextRefreshedEvent>{
	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
	/***
	 * spring的IOC容器和spring MVC的IOC容器的ContextRefreshedEvent
	 * Spring MVC，ContextRefreshedEvent会触发2次，
	 * 1次是ApplicationContext.xml初始化完毕时，一次是mvc的IOC初始化容器完毕时
	 * */

	protected CamelContext camelContext;
	
	protected RoutesBuilder routesBuilder;
	protected volatile boolean running = false;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(running){
			return;
		}
		running=true;
		
		try{
			//camelContext.addRoutes(routesBuilder);
			camelContext.start();
		}catch(Exception e){
			logger.error("jms启动失败",e);
		}
		
	}
	public CamelContext getCamelContext() {
		return camelContext;
	}
	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}

	public RoutesBuilder getRoutesBuilder() {
		return routesBuilder;
	}

	public void setRoutesBuilder(RoutesBuilder routesBuilder) {
		this.routesBuilder = routesBuilder;
	}
}
