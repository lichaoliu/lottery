package com.lottery.core.camelconfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.model.RouteDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class ApplicationContextUtil implements ApplicationContextAware {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass().getName());

	protected  Map<String, CamelContext> map;
	
	public Map<String, CamelContext> camelContextMap;
	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		map = applicationContext.getBeansOfType(CamelContext.class);
	}
	
	/**
	 * 获取camelcontext下所有route
	 * @param contextname
	 * @return
	 */
	public List<String> getRoutes(String contextname) {
		CamelContext camelContext = map.get(contextname);
		List<RouteDefinition> routes = ((ModelCamelContext)camelContext).getRouteDefinitions();
		List<String> routenames = new ArrayList<String>();
		for(RouteDefinition routeDefinition : routes) {
			routenames.add(routeDefinition.getId());
		}
		return routenames;
	}
	
	/**
	 * 开启指定camelcontext下的route
	 * @param contextname
	 * @param routeid
	 * @throws Exception
	 */
	public void startRoute(String contextname, String routeid) throws Exception {
		CamelContext camelContext = map.get(contextname);
		logger.error("start context:{},route:{}", new Object[] {camelContext.getName(), routeid});
		camelContext.start();
		RouteDefinition routeDefinition = ((ModelCamelContext)camelContext).getRouteDefinition(routeid);
		camelContext.startRoute(routeDefinition.getId());
		logger.error("end start context:{},route:{}", new Object[] {camelContext.getName(), routeDefinition.getId()});
	}
	
	
	/**
	 * 停止指定camelcontext下的route
	 * @param contextname
	 * @param routeid
	 * @throws Exception
	 */
	public void stopRoute(String contextname, String routeid) throws Exception {
		CamelContext camelContext = map.get(contextname);
		logger.error("stop context:{},route:{}", new Object[] {camelContext.getName(), routeid});
		RouteDefinition routeDefinition = ((ModelCamelContext)camelContext).getRouteDefinition(routeid);
		camelContext.stopRoute(routeDefinition.getId());
		logger.error("end stop context:{},route:{}", new Object[] {camelContext.getName(), routeDefinition.getId()});
	}
	
	/**
	 * 启动一个camelcontext
	 * @param name
	 * @throws Exception
	 */
	public void start(String name) throws Exception {
		if(map.containsKey(name)) {
			start(map.get(name));
		}
	}
	
	/**
	 * 启动所有camelcontext
	 * @throws Exception
	 */
	public void start() throws Exception {
		for(CamelContext camelContext : map.values()) {
			start(camelContext);
		}
	}
	
	
	private void start(CamelContext camelContext) throws Exception {
		logger.error("start context:{}", new Object[] {camelContext.getName()});
		camelContext.start();
		for(Route route:camelContext.getRoutes()){
			camelContext.startRoute(route.getId());
		}
		logger.error("end start context:{}", new Object[] {camelContext.getName()});
	}
	
	/**
	 * 停止所有camelcontext
	 * @throws Exception
	 */
	public void stop() throws Exception {
		for(CamelContext camelContext : map.values()) {
			stop(camelContext);
		}
	}
	
	
	public Map<String, CamelContext> getMap() {
		return map;
	}

	public void setMap(Map<String, CamelContext> map) {
		this.map = map;
	}

	/**
	 * 停止指定的camelcontext
	 * @param name
	 * @throws Exception
	 */
	public void stop(String name) throws Exception {
		if(map.containsKey(name)) {
			stop(map.get(name));
		}
	}
	
	private void stop(CamelContext camelContext) throws Exception {
		logger.error("stop context:{}", new Object[] {camelContext.getName()});
		for(Route route:camelContext.getRoutes()){
			camelContext.stopRoute(route.getId());
		}
		logger.error("end stop context:{}", new Object[] {camelContext.getName()});
	}
	
	
	public boolean canStartCamelContext(String contextName) {
		return map.containsKey(contextName);
	}
}
