package com.lottery.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.service.LotteryChaseService;
import com.lottery.test.SpringBeanTest;
import com.lottery.ticket.phase.thread.AsyncDcPhaseRunnable;

public class ChaseServiceTest extends SpringBeanTest {
	
	@Autowired
	LotteryChaseService chaseService;
	
	@Autowired
	AsyncDcPhaseRunnable ar;
	@Test
    public void testChase(){
		ar.match(LotteryType.DC_SPF, "40508");
		//System.out.println(chaseService);
		//OrderRequest orderRequest = new OrderRequest();
		//chaseService.chaseOrder(orderRequest);
	}
}
