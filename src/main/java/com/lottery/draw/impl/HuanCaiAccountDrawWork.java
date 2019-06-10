package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huancai.HuancaiConfig;
@Component("HuanCai_account")
public class HuanCaiAccountDrawWork extends AbstractVenderBalanceWork{
	private final Logger logger=LoggerFactory.getLogger(getClass().getName());
	private static String accountMoney="1005";
    protected TerminalType getTerminalType() {
		return TerminalType.T_HUANCAI;
	}
    protected MemberAccount getAccount(IVenderConfig config) {
		if(config==null){
			return null;
		}
		String message=getElement(config);
		String returnStr="";
		try {
			returnStr = HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
		} catch (Exception e) {
			logger.error("环彩查询账户余额接口返回异常" , e);
		}
		 //处理结果
		if(StringUtils.isNotBlank(returnStr)){
			return dealResult(returnStr,config);
        }
		return null;
	}


	/**
	 * 账户余额结果处理
	 * @param desContent
	 * @return
	 */
	private MemberAccount dealResult(String desContent,IVenderConfig config){
		try {
			Map<String,String> map=XmlParse.getElementAttribute("/", "response", desContent);
			if("0000".equals(map.get("code"))){
				Map<String,String> mapParam=XmlParse.getElementAttribute("response/", "account", desContent);
				if(mapParam!=null) {
					MemberAccount memberAccount=new MemberAccount();
					memberAccount.setId(config.getTerminalId());
				    memberAccount.setSmsFlag(YesNoStatus.yes.value);
				    String account=new BigDecimal(String.valueOf(mapParam.get("balance"))).divide(new BigDecimal("100")).toString();
				    memberAccount.setBalance(new BigDecimal(account.replace(",", "")));
				    memberAccount.setTerminalName(TerminalType.T_HUANCAI.getName());
				    return memberAccount;
			   }
			}
		} catch (Exception e) {
			logger.error("处理账户余额异常",e);
		}
		return null;
	}
	private String getElement(IVenderConfig huancaiConfig) {
		XmlParse xmlParse=null;
		String messageId=DateUtil.format("yyyyMMddHHmmss", new Date());
		xmlParse = HuancaiConfig.addGxHead(accountMoney,huancaiConfig.getAgentCode(),messageId);
		String md="";
		try {
			md = MD5Util.toMd5(huancaiConfig.getAgentCode()+messageId + huancaiConfig.getKey() + "<body></body>");
		} catch (Exception e) {
			logger.error("加密异常",e);
		}
		xmlParse.addHeaderElement("digest", md);
		return  "cmd="+accountMoney+"&msg="+xmlParse.asXML().replace("<body/>","<body></body>");
	}

}
