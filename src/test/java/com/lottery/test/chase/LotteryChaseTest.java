package com.lottery.test.chase;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.TopicName;
import com.lottery.core.service.LotteryChaseService;
import com.lottery.core.service.PrizeService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.test.SpringBeanTest;

public class LotteryChaseTest extends SpringBeanTest{

	@Autowired
	private LotteryChaseService lotteryChaseService;
	@Autowired
    protected QueueMessageSendService queueMessageSendService;
	@Autowired 
	PrizeService prizeService;
    @Test
	public  void testPrize(){
    	// 发送新期jms
			try{
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("lotterytype", 1007);
				map.put("phase", "2014032");
				queueMessageSendService.sendMessage(TopicName.open_phase.value, map);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
 
}
