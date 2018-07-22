package com.lottery.ticket.vender.impl.huay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import com.lottery.ticket.sender.worker.XmlParse;


public class HuayService {
	  
	
	
	/**
	 * 头部格式
	 * @param agentNo  商户号
	 * @param code  服务编码
	 * @param messageId 加密串
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static XmlParse  addHuayHead(String agentCode,String code,String messageid,String time){
		XmlParse xmlParse=null;
		xmlParse = new XmlParse("message", "header", "body");
		
		xmlParse.addHeaderElement("messengerid", messageid);
		xmlParse.addHeaderElement("agenterid",agentCode);
		xmlParse.addHeaderElement("transactiontype", code);
		xmlParse.addHeaderElement("timestamp", time);
		xmlParse.addHeaderElement("username",agentCode);
		xmlParse.getDocument().getRootElement().setAttributeValue("version", "1.0");
		return xmlParse;
	}
	
	
	public static String getNotifyStr(HttpServletRequest request) throws IOException{
		BufferedReader reader = null;
		StringBuilder outStr = new StringBuilder();
		try {
			ServletInputStream input = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				outStr.append(line);
			}
		} catch (Exception e) {
                 
		} finally {
			if (reader != null)
				reader.close();
		}
		return outStr.toString();
	}
	
}