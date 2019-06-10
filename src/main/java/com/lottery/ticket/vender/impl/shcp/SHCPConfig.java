package com.lottery.ticket.vender.impl.shcp;

import java.util.Date;

import com.lottery.common.util.DateUtil;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class SHCPConfig extends BaseConfig {
	
	public static XmlParse addShcpHead(String command,String agentCode,String messageId) {

		XmlParse xmlParse = new XmlParse("request", "head", "body");
		xmlParse.addHeaderAttribute("sid", command);
		xmlParse.addHeaderAttribute("agent", agentCode);
		xmlParse.addHeaderAttribute("timestamp",DateUtil.format("yyyy-MM-dd HH:mm:ss", new Date()));
		xmlParse.addHeaderAttribute("memo","票务");
		xmlParse.addHeaderAttribute("messageid",messageId);
		return xmlParse;
	}
	
	

}
