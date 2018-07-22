package com.lottery.listener.send;

import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.ticket.sender.TicketSendProccess;

@Service
public class TicketSendListener {
	protected final Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	protected TicketSendProccess ticketSendHandler;
	


	public  void process(@Header("batchId") String batchId){
      ticketSendHandler.send(batchId);
	}

	

	public void transactionbet(@Header("userno") String userno){
		ticketSendHandler.sendOrder(userno);
	}


}


