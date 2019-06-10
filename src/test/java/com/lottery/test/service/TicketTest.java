package com.lottery.test.service;

import com.lottery.core.service.TicketService;
import com.lottery.test.SpringBeanTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class TicketTest extends SpringBeanTest{
    @Autowired
	TicketService ticketService;
    @Test
	public void test1(){
		BigDecimal b=ticketService.getSumAmountByOrderIdAndStatus("TY201406080000000973", 4);
		System.out.println(b);
		/*List<Ticket> ticketList=ticketService.getByOrderId("TY201406080000000973");
		for(Ticket ticket:ticketList){
			System.out.println(ticket.getId());
		}*/
	}
}
