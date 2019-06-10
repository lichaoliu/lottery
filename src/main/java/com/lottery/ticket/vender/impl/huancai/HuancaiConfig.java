package com.lottery.ticket.vender.impl.huancai;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class HuancaiConfig extends BaseConfig {
	
	public static XmlParse addGxHead(String command,String agentCode,String messageId) {

		XmlParse xmlParse = new XmlParse("message", "header", "body");
		xmlParse.addHeaderElement("sid", agentCode);
		xmlParse.addHeaderElement("cmd", command);
		xmlParse.addHeaderElement("timetag", messageId);
		return xmlParse;
	}
	
	

}
