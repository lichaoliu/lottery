package com.lottery.test.service;

import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.test.SpringBeanTest;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IdTest extends SpringBeanTest{
    @Autowired
	PrizeErrorHandler service;
    @Test
	public void testId(){
//		int size=10;//生成10个订单
//		Long id=dao.getBatchTicketId(size);
//		for(int i=0;i<size;i++){
//			System.out.println(dao.getId(id));
//			id++;
//		}

		String[] s= StringUtils.split("TY150827000006330534,TY150827000006330533,TY150827000006330532",",");
		service.mergeTicket(s);
    	
	}
}
