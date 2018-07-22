package com.lottery.ticket.vender.impl.yinxi;

import com.lottery.ticket.sender.worker.XmlParse;


public class YinxiService {
	  
	
	
	/**
	 * 头部格式
	 * @param agentNo  商户号
	 * @param code  服务编码
	 * @param messageId 加密串
	 * @return
	 */
	public static XmlParse  addHead(String agentCode,String time,String command,String messsageId){
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("message", "head", "body");
		xmlParse.addHeaderElement("command", command);
		xmlParse.addHeaderElement("agentid",agentCode);
	
		xmlParse.addHeaderElement("messageid", messsageId);
		xmlParse.addHeaderElement("timestamp", time);
		xmlParse.getDocument().getRootElement();
		return xmlParse;
	}
	
	
	
	
}