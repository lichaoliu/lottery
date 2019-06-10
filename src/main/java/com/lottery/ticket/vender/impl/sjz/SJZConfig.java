package com.lottery.ticket.vender.impl.sjz;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class SJZConfig extends BaseConfig {
	
	public static XmlParse addHead(String command,String agentCode,String timeStamp,String messageId) {
		XmlParse xmlParse = new XmlParse("msg", "head", "body");
		xmlParse.getRootElement().setAttributeValue("v", "1.0");
		xmlParse.getRootElement().setAttributeValue("id", messageId);
		xmlParse.addHeaderElement("cmd", command);
		xmlParse.addHeaderElement("agentid", agentCode);
		xmlParse.addHeaderElement("timestamp", timeStamp);
		return xmlParse;
	}
	
	
	

}
