package com.lottery.listener.chase;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Header;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.ChaseType;
import com.lottery.core.camelconfig.ApplicationContextUtil;
import com.lottery.core.handler.LotteryChaseHandler;
import com.lottery.retrymodel.ApiRetryTaskExecutor;

@Service
public class LotteryChaseListener  {



	@Autowired
	protected LotteryChaseHandler chaseHandler;





	/**
	 * 收到新期消息,发送订单追号JMS消息
	 * 
	 * @param lottype
	 *            彩种编号
	 * @param phase
	 *            期号
	 */

	public void processChase(@Header("lotterytype") Integer lottype, @Header("phase") String phase) {
       chaseHandler.process(lottype, phase, ChaseType.normal_end.value);
	}

	public  void continueChase(@Header("lotteryType") Integer lottype, @Header("phase") String phase){

		chaseHandler.process(lottype, phase, ChaseType.normal_end.value);

		chaseHandler.process(lottype, phase, ChaseType.total_prize_end.value);
		chaseHandler.process(lottype, phase, ChaseType.prize_end.value);
	}

	/**
	 * 消费算奖成功消息，查询所有设置中奖停止的追号，判断是否需要停止。
	 * 
	 * @param lotteryType 彩种
	 * @param phase 期号
	 */
	public void drawPrizeCustomer(@Header("lotterytype") Integer lotteryType, @Header("phase") String phase) {
	  chaseHandler.processPrize(lotteryType, phase);

	}

	public  void chaseBet(@Header("chaseId") String chaseId,@Header("lotteryType") Integer lotteryType, @Header("phase") String phase){
        chaseHandler.toBet(chaseId,lotteryType,phase);
	}
}