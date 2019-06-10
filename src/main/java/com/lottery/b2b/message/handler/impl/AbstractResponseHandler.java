package com.lottery.b2b.message.handler.impl;

import com.lottery.b2b.message.handler.IResponseHandler;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.merchant.MerchantOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.ticket.sender.worker.XmlParse;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractResponseHandler implements IResponseHandler {

	protected static String TIME_STAMP="yyyyMMddHHmmss";
	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
    @Autowired
    protected MerchantOrderService merchantOrderService;
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected LotteryOrderService lotteryOrderService;
    @Autowired
    protected QueueMessageSendService queueMessageSendService;
	@Autowired
	protected LotteryPhaseHandler phaseHandler;

	protected List<HashMap<String,String>>  parseXml(String xml){
		List<HashMap<String,String>>lists=new ArrayList<HashMap<String,String>>();
		try {
			lists=XmlParse.getElementTextList("orderlist/", "order", xml);
		} catch (DocumentException e) {
			logger.error("解析xml{}出错",xml,e);
		}
		return lists;
	  }
	
	protected HashMap<String,String>  parseXmlText(String nodeName,String rootElement,String xml){
		HashMap<String,String> map=new HashMap<String,String>();
		try {
			map=XmlParse.getElementText(nodeName, rootElement, xml);
		} catch (DocumentException e) {
			logger.error("解析xml{}出错",xml,e);
		}
		return map;
	  }
	
	
	
}
