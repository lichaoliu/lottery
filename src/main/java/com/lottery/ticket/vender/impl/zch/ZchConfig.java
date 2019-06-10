package com.lottery.ticket.vender.impl.zch;

import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.BaseConfig;

public class ZchConfig extends BaseConfig {
	private String jcUrl;

	public String getJcUrl() {
		return jcUrl;
	}

	public void setJcUrl(String jcUrl) {
		this.jcUrl = jcUrl;
	}
	
	/**
	 * 中彩汇头部内容
	 * @param agentNo  商户号
	 * @param code  服务编码
	 * @param messageId 加密串
	 * @return
	 */
	public static XmlParse  addZchHead(String agentNo,String code){
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("message", "header", "body");
		xmlParse.addHeaderElement("sid", agentNo);
		xmlParse.addHeaderElement("cmd", code);
		xmlParse.addHeaderElement("digestType","md5");
		return xmlParse;
	}

}
