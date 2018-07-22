package com.lottery.controller.merchant;

import com.lottery.b2b.message.IExternalMessage;
import com.lottery.b2b.message.IExternalResponseMessage;
import com.lottery.b2b.message.handler.IExternalMessageHandler;
import com.lottery.b2b.message.handler.IResponseHandler;
import com.lottery.b2b.message.impl.request.MessageValidate;
import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.common.util.CoreStringUtils;
import com.lottery.common.util.DESCoder;
import com.lottery.core.IdGeneratorService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/b2b")
public class B2bController {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	private final Logger betLogger= LoggerFactory.getLogger("b2b-bet");
	private final Logger checkLogger= LoggerFactory.getLogger("b2b-check");
	@Autowired
	protected IExternalMessageHandler messageHandler;
	@Autowired
	protected MessageValidate messageValidate;
	@Autowired
	private Map<String, IResponseHandler> map;
	@Autowired
	protected IdGeneratorService generatorService;
	@RequestMapping("/bet")
	public void bet(HttpServletRequest request, HttpServletResponse response) {
		String requestString = "";
		String responseString = "";
		String command="";
		String startTime=CoreDateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
		String ip = "";
		String messageId="";
		try {
			requestString = readRequestString(request);
			ip = CoreHttpUtils.getClientIP(request);
			if(StringUtils.isBlank(requestString)){
				responseString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><content><head><errorcode>70003</errorcode></head><body/><signature/></content>";
			}else{
				IExternalMessage message = messageHandler.getMessage(requestString);
				command=message.getCommand();
				messageId=message.getMessageId();
				IExternalResponseMessage resonseMessage = messageValidate.validate(message, ip);
				if (resonseMessage.getErrorCode() == null) {
					responseString = getResponseString(message.getMessageId(), message.getMerchant(), message.getCommand(), ErrorCode.unknown_error.value, null, resonseMessage.getCommand());
				} else if (!resonseMessage.getErrorCode().value.equals(ErrorCode.Success.value)) {
					responseString = getResponseString(message.getMessageId(), message.getMerchant(), message.getCommand(), resonseMessage.getErrorCode().value, null, resonseMessage.getCommand());
				} else {
					IResponseHandler handler = map.get(message.getCommand());
					if (handler != null) {
						String sing = handler.getResponse((ExternalResponseMessage) resonseMessage);
						responseString = getResponseString(message.getMessageId(), message.getMerchant(), message.getCommand(), resonseMessage.getErrorCode().value, sing, resonseMessage.getKey());
					} else {
						responseString = getResponseString(message.getMessageId(), message.getMerchant(), message.getCommand(), ErrorCode.command_error.value, null, resonseMessage.getCommand());
					}
				}
			}
	
	

		} catch (Exception e) {
			logger.error("请求{}出错", requestString, e);
			responseString = getResponseString(null, null, null, ErrorCode.system_error.value, null, null);
		}
		String endTime=CoreDateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
		String errorMessage=String.format("IP:[%s],messageid=%s,command=%s,请求的时间是:[%s],返回时间:[%s],请求字符串是:%s,返回字符串:%s",ip,messageId,command,startTime,endTime,requestString,responseString);
		writeLog(command,errorMessage);

		try {
			response.getWriter().println(responseString);
		} catch (IOException e) {
			logger.error("输出内容异常", e);
		}

	}

	private String getResponseString(String messageid, String merchant, String command, String errorcode, String bodyString, String key) {
		try {
			Document document = DocumentHelper.createDocument();
			Element rootElement = DocumentHelper.createElement("content");
			document.setRootElement(rootElement);

			Element headElement = rootElement.addElement("head");
			headElement.addElement("messageid").setText(messageid);
			headElement.addElement("merchant").setText(merchant);
			headElement.addElement("command").setText(command);
			String timestamp = CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
			headElement.addElement("timestamp").setText(timestamp);
			headElement.addElement("errorcode").setText(errorcode);

			Element bodyElement = rootElement.addElement("body");
			String messageInfo=String.format("请求messageid=%s,command=%s,返回的响应体为:%s",messageid,command,bodyString);
			writeLog(command,messageInfo);
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(bodyString)) {
				String desString = DESCoder.desEncrypt(bodyString, key);
				bodyElement.setText(desString);
			}else{
				bodyElement.setText("");
			}

			String md5Str = command + timestamp + merchant + key;
			String md5 = CoreStringUtils.md5(md5Str, CharsetConstant.CHARSET_UTF8);
			rootElement.addElement("signature").setText(md5);
			return document.asXML();

		} catch (Exception e) {
			logger.error("创建返回xml出错", e);
			return null;
		}

	}


	protected String readRequestString(HttpServletRequest request) {
		try {
			request.setCharacterEncoding(CharsetConstant.CHARSET_DEFAULT);
			BufferedReader reader = request.getReader();
			StringBuilder buffer = new StringBuilder();
			int n;
			while ((n = reader.read()) != -1) {
				buffer.append((char) n);
			}
			reader.close();
			return buffer.toString();
		} catch (Exception e) {
			logger.error("接收http请求出错", e);
			return null;
		}

	}


	private void writeLog(String command,String message){
		if(command.equals("801")){
			betLogger.info(message);
		}else if(command.equals("802")){
			checkLogger.info(message);
		}else {
			logger.info(message);
		}
	}


}
