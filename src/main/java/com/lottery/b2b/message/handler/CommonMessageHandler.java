package com.lottery.b2b.message.handler;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.lottery.b2b.message.IExternalMessage;
import com.lottery.b2b.message.impl.request.ExternalRequestMessage;
import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
@Service
public class CommonMessageHandler implements IExternalMessageHandler{

	@Override
	public IExternalMessage getMessage(String requestString) throws Exception {
		ExternalRequestMessage message=new ExternalRequestMessage();
		Document doc=DocumentHelper.parseText(requestString); 
		Element rootElt = doc.getRootElement();
		Element body=rootElt.element("body");
		message.setMessageBody(body.getText());
		Element sign=rootElt.element("signature");
		message.setSignature(sign.getText());
		Element head=rootElt.element("head");
		Element version=head.element("version");
		message.setVersion(version.getText());
		Element merchant=head.element("merchant");
		message.setMerchant(merchant.getText());
		Element commond=head.element("command");
		message.setCommand(commond.getText());
		Element messageid=head.element("messageid");
		message.setMessageId(messageid.getText());
		Element timestamp=head.element("timestamp");
		message.setTimestamp(timestamp.getText());
		return message;
	}

	@Override
	public String getResult(ExternalResponseMessage message) {
		
		return null;
	}
	
	

}
