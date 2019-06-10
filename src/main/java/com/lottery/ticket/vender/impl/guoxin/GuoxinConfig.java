package com.lottery.ticket.vender.impl.guoxin;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class GuoxinConfig extends BaseConfig {
	
	public static XmlParse addGxHead(String command,String agentCode) {
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("message", "header", "body");
		xmlParse.addHeaderElement("sid", agentCode);
		xmlParse.addHeaderElement("cmd", command);
		xmlParse.addHeaderElement("digestType","md5");
		return xmlParse;
	}
	
	

}
