package com.lottery.ticket.vender.impl.gaode;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class GaodeConfig extends BaseConfig {
	private String dcUrl;

	
	public String getDcUrl() {
		return dcUrl;
	}


	public void setDcUrl(String dcUrl) {
		this.dcUrl = dcUrl;
	}


	public static XmlParse addGaodeHead(String command,String username,String timestamp) {

		XmlParse xmlParse = new XmlParse("message", "header", "body");
		xmlParse.addHeaderElement("transactionType", command);
		xmlParse.addHeaderElement("userName",username);
		xmlParse.addHeaderElement("timestamp",timestamp);
		return xmlParse;
	}
	
	

}
