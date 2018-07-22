package com.lottery.ticket.vender.impl.gzcp;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class GzcpConfig extends BaseConfig {
	
	public static XmlParse addGzcpHead(String command,String agentCode,String messageId) {

		XmlParse xmlParse = new XmlParse("message", "head", "body");
		xmlParse.addHeaderElement("version", "1500");
		xmlParse.addHeaderElement("command", command);
		xmlParse.addHeaderElement("venderId",agentCode);
		xmlParse.addHeaderElement("messageId",messageId);
		return xmlParse;
	}
	
	

}
