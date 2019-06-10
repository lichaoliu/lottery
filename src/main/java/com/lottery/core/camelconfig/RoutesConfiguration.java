package com.lottery.core.camelconfig;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class RoutesConfiguration implements ApplicationListener<ContextRefreshedEvent> {
	
	//private Logger logger = LoggerFactory.getLogger(RoutesConfiguration.class);

//	@Resource(name="lotteryCamelContext")
//	private CamelContext camelContext;
	
	


	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		logger.info("init lotteryCamelContext routes start");
//		try {
//			camelContext.addRoutes(new RouteBuilder() {
//				@Override
//				public void configure() throws Exception {
//					deadLetterChannel("jms:queue:dead").maximumRedeliveries(-1).redeliveryDelay(3000);
//				}
//			});
//			
//		} catch (Exception e) {
//			logger.error("lotteryCamelContext context start failed",e.getMessage());
//			e.printStackTrace();
//		}
//		
	}
}
