package com.lottery.b2b.message.handler.impl;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.b2b.message.impl.response.ExternalResponseMessage;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.util.XMLParseTool;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.ticket.sender.worker.XmlParse;

/**
 * 余额查询
 * */
@Component("806")
public class UserAccountHanlder extends AbstractResponseHandler {
	@Autowired
	private UserAccountService userAccountService;

	@Override
	public String getResponse(ExternalResponseMessage message) {

		try {
			String body = message.getMessageBody();
			Element eleme = XmlParse.getElemText("content", body);
			String merchant = eleme.elementText("merchant");
			if (StringUtils.isBlank(merchant)) {
				logger.error("参数不能为空,商户编号:{}", merchant);
				message.setErrorCode(ErrorCode.body_error);
				return null;
			}

			UserAccount userAccount = userAccountService.get(merchant);

			Element element = DocumentHelper.createElement("message");
			HashMap<String, String> mapstr = new HashMap<String, String>();
			mapstr.put("merchant", merchant);
			mapstr.put("balance", String.valueOf(userAccount.getBalance().longValue()));

			XMLParseTool.addBodyElement(mapstr, element);
			return element.asXML();
		} catch (Exception e) {
			logger.error("查询余额出错", e);
			return null;
		}

	}

}
