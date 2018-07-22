package com.lottery.listener;


/**
 * camel总控制
 * */
public class CommonCamelRouteControl extends AbstractCamelRouteControl implements ICamelRouteControl{

	@Override
	public void start() {
		try {
			this.camelContext.start();

		} catch (Exception e) {
			logger.error("camel启动失败",e);
		}
		
	}

	@Override
	public void stop() {
		try {
			this.camelContext.stop();
		} catch (Exception e) {
			logger.error("camel停止失败",e);
		}
		
		
	}

	
}
