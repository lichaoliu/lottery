package com.lottery.ticket.vender.impl.jinnuo;

import java.util.Random;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class JinNuoConfig extends BaseConfig {

	public static XmlParse addHead(String command,String agentCode,String timestamp) {
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("msg", "head", "body");
		xmlParse.getRootElement().addAttribute("v", "1.0").addAttribute("id", timestamp+new Random().nextInt(10));
		xmlParse.addHeaderElement("agentid", agentCode);
		xmlParse.addHeaderElement("cmd", command);
		xmlParse.addHeaderElement("timestamp",timestamp);
		return xmlParse;
	}

}
