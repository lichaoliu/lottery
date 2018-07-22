package com.lottery.ticket.vender.process.fcby;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.util.DES3;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.impl.fcby.FcbyService;

public abstract class GetJczqDataFromFcby {
	
	public static final String FORMATTIME = "yyyyMMddHHmmss";
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


	public JSONArray perform(Map<String, String> reqMap, IVenderConfig config) {
		JSONArray jarray = null;

		Document returnDoc = DocumentHelper.createDocument();
		Map<String, String> requestMap = new HashMap<String, String>();
		
		try {
			generateRootElement(reqMap, returnDoc, config);
			StringWriter sw = new StringWriter(); 
			XMLWriter xo = new XMLWriter(sw);
			xo.write(returnDoc);
			xo.close();
			requestMap.put("msg", sw.toString());
			String response = FcbyService.request(requestMap, config);
			//对body中的信息进行解码
			String responseElementDec=DES3.des3DecodeCBC(config.getKey(), response);
			logger.info("消息体[响应] : {} ; ", responseElementDec);

			Document responseElement=DocumentHelper.parseText(responseElementDec);
			
			if (responseElement == null) {
				logger.error("{}瑞彩数据处理失败,请求内容={},响应内容为空", new Object[] { logger.getName(), xo.toString() });
				return jarray;
			}
			jarray = generateJsonData(responseElement.getRootElement(), reqMap);
			return jarray;
		} catch (Exception e) {
			logger.error("GetJczqDataFromRuiCai2013:获取数据 发生异常", e);
		}
		return jarray;
	}

	protected abstract void generateRootElement(Map<String, String> reqMap, Document returnDoc, IVenderConfig config) throws Exception;

	protected abstract JSONArray generateJsonData(Element responseElement, Map<String, String> reqMap) throws Exception;


}
