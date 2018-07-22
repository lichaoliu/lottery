package com.lottery.draw.impl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.guoxin.GuoxinConfig;

@Component("Gx_account")
public class GXAccountDrawWork extends AbstractVenderBalanceWork {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected static String accountMoney = "031";

	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if (config == null) {
			return null;
		}
		String message = getElement(config);
		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
		} catch (Exception e) {
			logger.error("查询账户余额返回异常", e);
		}
		// 处理结果
		try {
			if (StringUtils.isNotBlank(returnStr)) {
				MemberAccount memberAccount = dealResult(returnStr, config);
				return memberAccount;
			}
		} catch (Exception e) {
			logger.error("处理查询账户余额结果异常" + e);
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
			Map<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
			String code = mapResult.get("code");
			String msg = mapResult.get("msg");
			if ("0000".equals(code)) {
				HashMap<String, String> map= XmlParse.getElementAttribute("body/response/", "account", desContent);
                String leftMoney = map.get("amount");

				memberAccount.setBalance(new BigDecimal(leftMoney));
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
		return TerminalType.T_GX;
	}

	private String getElement(IVenderConfig gxConfig) {
		try {
			XmlParse xmlParse = GuoxinConfig.addGxHead(accountMoney,gxConfig.getAgentCode());
			String md = MD5Util.toMd5(gxConfig.getAgentCode() + gxConfig.getKey() + xmlParse.getBodyElement().asXML());
			xmlParse.addHeaderElement("digest", md);
			return "msg="+xmlParse.asXML();
			
		} catch (Exception e) {
			logger.error("查询账户余额拼串异常" + e.getMessage());
		}
		return null;
	}

}
