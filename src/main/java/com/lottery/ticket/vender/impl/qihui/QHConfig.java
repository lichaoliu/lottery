package com.lottery.ticket.vender.impl.qihui;

import java.util.Date;

import com.lottery.common.util.DateUtil;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class QHConfig extends BaseConfig {
	private String jcUrl;

	public String getJcUrl() {
		return jcUrl;
	}

	public void setJcUrl(String jcUrl) {
		this.jcUrl = jcUrl;
	}
	public static XmlParse addQHHead(String command,String agentCode) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("message", "head", "body");
		xmlParse.addHeaderElement("version", "V2");
		xmlParse.addHeaderElement("command", command);
		xmlParse.addHeaderElement("venderId",agentCode);
		xmlParse.addHeaderElement("messageId",timestamp);
		return xmlParse;
	}

}
