package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
@Component("Zy_account")
public class ZYAccountDrawWork extends AbstractVenderBalanceWork{
	protected final Logger logger = LoggerFactory.getLogger(ZYAccountDrawWork.class);
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryAccount="13002";
	
	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if(config==null){
			return null;
		}
		String message=getElement(config);
		String returnStr="";

		try {
			returnStr = HTTPUtil.postJson(config.getRequestUrl(),message);
			//处理结果
			MemberAccount memberAccount = dealResult(returnStr,config);
			return memberAccount;
		} catch (Exception e) {
			logger.error("查询账户异常",e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_ZY;
	}

	
	/**
	 * 查询账户结果处理
	 * @param desContent
	 * @param config
	 * @return 元
	 */
	private MemberAccount  dealResult(String desContent, IVenderConfig config){
		MemberAccount memberAccount = new MemberAccount();
		try {
			JSONObject map =JSONObject.fromObject(desContent);
		     JSONObject returnMsg=JSONObject.fromObject(map.get("msg"));
		        String returnCode=String.valueOf(returnMsg.get("errorcode"));
				if("0".equals(returnCode)){
					 String leftMoney=returnMsg.getString("actmoney");
					 memberAccount.setBalance(new BigDecimal(leftMoney).divide(new BigDecimal(100)));
					 memberAccount.setTerminalName(getTerminalType().getName());
					 memberAccount.setId(config.getTerminalId());
					 memberAccount.setSmsFlag(YesNoStatus.no.value);	
				}
		} catch (Exception e) {
			logger.error("查询账户结果异常",e);
		}
		return memberAccount;
	}
	/**
	 * 拼串
	 * @param
	 * @return
	 */
	private String getElement(IVenderConfig zyConfig) {
		try {
			JSONObject message = new JSONObject();
			JSONObject header = new JSONObject();
			header.put("messengerid",igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS));
			header.put("timestamp",DateUtil.format("yyyyMMddHHmmss", new Date()));
			header.put("transactiontype",queryAccount);
			header.put("des","0");
			header.put("agenterid",zyConfig.getAgentCode());
			message.put("header", header);
			return message.toString();
		} catch (Exception e) {
			logger.error("账户查询异常" + e.getMessage());
		}
		return null;
	}

	
}
