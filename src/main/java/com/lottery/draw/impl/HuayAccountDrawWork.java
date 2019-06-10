package com.lottery.draw.impl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huay.HuayService;

@Component("HuaYang_account")
public class HuayAccountDrawWork extends AbstractVenderBalanceWork {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	IdGeneratorService igenGeneratorService;


	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if (config == null) {
			return null;
		}
		String message = getElement(config);
		String returnStr = "";

		
		// 处理结果
		try {
			returnStr = HTTPUtil.post(config.getRequestUrl(), message ,CharsetConstant.CHARSET_UTF8);
			if (StringUtils.isNotBlank(returnStr)) {
				MemberAccount memberAccount = dealResult(returnStr, config);
				return memberAccount;
			}
		} catch (Exception e) {
			logger.error("发送:{},返回:{}",message,returnStr);
			logger.error("处理查询账户余额结果异常" ,e);
		}
		return null;
	}

	/**
	 * 查询账户余额处理
	 * 
	 * @param desContent
	 * @return
	 */
	private MemberAccount dealResult(String desContent, IVenderConfig config) {
		MemberAccount memberAccount = new MemberAccount();
		try {
			Map<String, String> map = XmlParse.getElementText("body/", "oelement", desContent);
			if ("0".equals(map.get("errorcode"))) {
				String betMoney = map.get("actmoney");
				String prizeMoney = map.get("bonusmoney");
				memberAccount.setBalance(new BigDecimal(betMoney).divide(new BigDecimal("100")));
				memberAccount.setTotalPrize(new BigDecimal(prizeMoney).divide(new BigDecimal("100")));
				memberAccount.setTerminalName(getTerminalType().getName());
				memberAccount.setId(config.getTerminalId());
				memberAccount.setSmsFlag(YesNoStatus.no.value);
				return memberAccount;
			}

		} catch (Exception e) {
			logger.error("处理查询账户余额异常", e);
			logger.error("descontent={}",desContent);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_HY;
	}

	private String getElement(IVenderConfig huayConfig) {
		String accountMoney = "13002";
		if (huayConfig.getPort()!=null){
			accountMoney="21002";
		}
		String md = "";
		XmlParse xmlParse = null;
		try {
			String messageid = igenGeneratorService.getMessageId();
			String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
			xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), accountMoney, messageid, timestamp);
			Element bodyeElement=xmlParse.getBodyElement();
			bodyeElement.addText("");
			md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
			xmlParse.addHeaderElement("digest", md);
			return xmlParse.asXML();
		} catch (Exception e) {
			logger.error("查询账户余额拼串异常" + e.getMessage());
		}
		return null;
	}

}
