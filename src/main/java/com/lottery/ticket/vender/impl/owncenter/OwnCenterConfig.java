package com.lottery.ticket.vender.impl.owncenter;

import java.util.Date;

import com.lottery.common.util.DateUtil;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class OwnCenterConfig extends BaseConfig {
	
	public static XmlParse addOwnCenterHead(String command,String agentCode,String messageId) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = new XmlParse("content", "head", "body");
		xmlParse.addHeaderElement("version", "1.0");
		xmlParse.addHeaderElement("merchant",agentCode);
		xmlParse.addHeaderElement("command", command);
		xmlParse.addHeaderElement("messageid",messageId);
		xmlParse.addHeaderElement("timestamp",timestamp);
		return 	xmlParse;
	}
	
	

}
