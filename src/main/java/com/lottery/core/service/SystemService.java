package com.lottery.core.service;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lottery.core.camelconfig.ApplicationContextUtil;
@Service
public class SystemService implements ServletContextAware{
	private static Logger logger=LoggerFactory.getLogger(SystemService.class);
	  @Autowired
	private ApplicationContextUtil applicationContextUtil;
	private ServletContext servletContext;
	private static ApplicationContext ctx = null;
	@Resource(name="camelContextMap")
	protected Map<String,CamelContext> camelContextMap;
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext=servletContext;
		
	}
	private static Lock lock = new ReentrantLock();
	public  ApplicationContext getCtx() {
		if(ctx!=null){
		  return ctx;
		}
		try{
			lock.lock();
	        if (ctx != null) {
	            return ctx;
	        }
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());//获取失败时抛出异常
		}catch(Exception e){
			
		}finally{
			lock.unlock();
		}
		return ctx;
	
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public   <T> Map<String,T> getMap(Class<T> t){
		return getCtx().getBeansOfType(t); 
	}


	public Object get(String name){
		return getCtx().getBean(name);
	}

    @PreDestroy
	public void stopCamelContexts(){
    	Map<String, CamelContext> contextMap =applicationContextUtil.getMap();
    	if(contextMap!=null){
    		for (Map.Entry<String, CamelContext> entry : contextMap.entrySet()) {
    			String context=entry.getKey();
    			CamelContext value=entry.getValue();
    			logger.error("开始停止context name={}",context);
    			try {
    				ServiceStatus status=value.getStatus();
    				if(status.isStoppable()||status.isStopped()||status.isStopping()){
    					logger.error("context已停止,name={}",context);
    					continue;
    				}
    				for(Route route:value.getRoutes()){
    					value.stopRoute(route.getId());
    				}
    				
    			} catch (Exception e) {
    				logger.error("停止context name={}失败",context,e);
    				continue;
    			}
    			logger.error("停止成功context name={}",context);
    			  
    			  }
    	}
    	//新的监控
		for (Map.Entry<String, CamelContext> entry : camelContextMap.entrySet()) {
			String context=entry.getKey();
			CamelContext value=entry.getValue();
			logger.error("开始停止context name={}",context);
			try {
				ServiceStatus status=value.getStatus();
				if(status.isStoppable()||status.isStopped()||status.isStopping()){
					logger.error("context已停止,name={}",context);
					continue;
				}
				for(Route route:value.getRoutes()){
					value.stopRoute(route.getId());
				}
				
			} catch (Exception e) {
				logger.error("停止context name={}失败",context,e);
				continue;
			}
			logger.error("停止成功context name={}",context);
			  
			  }
    	
	}
	
}
