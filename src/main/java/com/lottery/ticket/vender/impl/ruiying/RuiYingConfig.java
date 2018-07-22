package com.lottery.ticket.vender.impl.ruiying;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class RuiYingConfig extends BaseConfig{

	public static XmlParse addHead(String command,String agentCode,String timestamp) {
		XmlParse xmlParse = new XmlParse("request", "head", "body");
		xmlParse.addHeaderElement("msgId", command);
		xmlParse.addHeaderElement("timeStamp",timestamp);
		return xmlParse;
	}
}