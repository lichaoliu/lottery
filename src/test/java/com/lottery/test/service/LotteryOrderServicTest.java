package com.lottery.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.PageBean;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.test.SpringBeanTest;

public class LotteryOrderServicTest extends SpringBeanTest  {
	
	@Autowired
	private LotteryOrderService lots;

	@Test
	public void getToday(){
		PageBean<LotteryOrder> page=new PageBean<LotteryOrder>();
		page.setPageIndex(0);
		page.setMaxResult(10000);
		
//		for(LotteryOrder loOrder:page){
//			
//		}
	}

}
