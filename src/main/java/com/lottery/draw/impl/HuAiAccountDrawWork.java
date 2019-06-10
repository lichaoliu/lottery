package com.lottery.draw.impl;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
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

@Component("HuAi_account")
public class HuAiAccountDrawWork extends AbstractVenderBalanceWork {
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected static String accountMoney = "104";

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
			logger.error("处理查询账户余额结果异常" , e);
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
			desContent="<response>"+desContent+"</response>";
			HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", desContent);
			HashMap<String, String> reResult = XmlParse.getElementText("/ActionResult/", "reValue", desContent);
			String code = mapResult.get("reCode");
			String msg = mapResult.get("reMessage");
			if ("0".equals(code)) {
				String leftMoney = reResult.get("Balance");
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
		return TerminalType.T_HUAI;
	}

	private String getElement(IVenderConfig huaiConfig) {
		try {
			StringBuffer stringBuffer=new StringBuffer();
			String messageId=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			stringBuffer.append("exAgent=").append(huaiConfig.getAgentCode())
			.append("&exAction=").append(accountMoney).append("&exMsgID="+messageId);
			String signMsg=MD5Util.toMd5(huaiConfig.getAgentCode()+accountMoney+messageId+huaiConfig.getKey());
			stringBuffer.append("&exParam=").append("&exSign=").append(signMsg);
			return stringBuffer.toString();
		} catch (Exception e) {
			logger.error("查询账户余额拼串异常" ,e);
		}
		return null;
	}

}
