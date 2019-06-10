package com.lottery.draw.impl;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gzcp.GzcpConfig;
import com.lottery.ticket.vender.impl.gzcp.GzcpDes;

@Component("gzcp_account")
public class GzcpAccountDrawWork extends AbstractVenderBalanceWork {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	IdGeneratorService idGeneratorService;
	protected static String accountMoney = "1006";

	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if (config == null) {
			return null;
		}
		String messageStr = getElement(config);
		String returnStr = "";

		// 处理结果
		try {
			returnStr = HTTPUtil.post(config.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
			if (StringUtils.isNotBlank(returnStr)) {
				MemberAccount memberAccount = dealResult(returnStr, config);
				return memberAccount;
			}
		} catch (Exception e) {

			logger.error("处理查询账户余额结果异常",e);
			logger.error("send={},return={}",messageStr,returnStr);
		}
		return null;
	}

	/**
	 * 查询账户余额处理
	 * 
	 * @param desContent
	 * @param config
	 * @return
	 */
	private MemberAccount dealResult(String desContent, IVenderConfig config) {
		MemberAccount memberAccount = new MemberAccount();
		try {
			Document doc = DocumentHelper.parseText(desContent);
			Element rootElt = doc.getRootElement();
			Element el = rootElt.element("head");
			String result = el.elementText("result");
			if ("0".equals(result)) {
				String bodyencode = rootElt.elementText("body");
				String body = GzcpDes.des3DecodeCBC(config.getKey(), bodyencode);
				Document document = DocumentHelper.parseText(body);
		    	Element root = document.getRootElement();
		    	String leftMoney  = root.elementText("accountBalance");

				memberAccount.setBalance(new BigDecimal(leftMoney).divide(new BigDecimal(100)));
				memberAccount.setTerminalName(getTerminalType().getName());
				memberAccount.setId(config.getTerminalId());
				memberAccount.setSmsFlag(YesNoStatus.no.value);
				return memberAccount;
			}

		} catch (Exception e) {
			logger.error("处理查询账户余额异常", e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_GZCP;
	}

	private String getElement(IVenderConfig gzcpConfig) {
		try {
			String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			XmlParse xmlParse =  GzcpConfig.addGzcpHead(accountMoney, gzcpConfig.getAgentCode(),messageId);
			String des = GzcpDes.des3EncodeCBC(gzcpConfig.getKey(), "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xmlParse.getBodyElement().asXML());
			String md = MD5Util.toMd5(des);
			xmlParse.addHeaderElement("md", md);
			xmlParse.getBodyElement().setText(des);

			return xmlParse.asXML();
		} catch (Exception e) {
			logger.error("查询账户余额拼串异常" + e.getMessage());
		}
		return null;
	}

}
