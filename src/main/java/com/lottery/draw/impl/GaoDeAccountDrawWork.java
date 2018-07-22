package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.lottery.ticket.vender.impl.gaode.GaodeConfig;
/**
 * 账户余额
 * @author wyliuxiaoyan
 *
 */
@Component("gaode_account")
public class GaoDeAccountDrawWork extends AbstractVenderBalanceWork{
	protected final Logger logger = LoggerFactory.getLogger(GaoDeAccountDrawWork.class);
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryAccount="P005";
	
	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if(config==null){
			return null;
		}
		String message=getElement(config);
		String returnStr="";
		try {
			 returnStr= HTTPUtil.post(config.getRequestUrl(), message);
		} catch (Exception e) {
			logger.error("查询账户接口返回异常" + e);
		}
		try {
			MemberAccount memberAccount =null;
			if(returnStr==null||returnStr.isEmpty()){
				return null;
			}
			//处理结果
			memberAccount = dealResult(returnStr,config);
			return memberAccount;
		} catch (Exception e) {
			logger.error("查询账户解析异常"+e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_GAODE;
	}

	
	/**
	 * 查询账户结果处理
	 * @param desContent
	 * @param config
	 * @return
	 * @throws DocumentException 
	 */
	private MemberAccount  dealResult(String desContent, IVenderConfig config) {
		MemberAccount memberAccount = new MemberAccount();
		try {
			HashMap<String, String> mapResult = XmlParse.getElementText("", "body", desContent);
			String leftMoney = mapResult.get("account");
			memberAccount.setBalance(new BigDecimal(leftMoney));
			memberAccount.setTerminalName(getTerminalType().getName());
			memberAccount.setId(config.getTerminalId());
			memberAccount.setSmsFlag(YesNoStatus.no.value);
			
		} catch (Exception e) {
			logger.error("查询账户结果异常");
		}
		return memberAccount;
	}
	/**
	 * 拼串
	 * @param fcbyConfig
	 * @return
	 */
	private String getElement(IVenderConfig gdConfig) {
		try {
			XmlParse xmlParse =  GaodeConfig.addGaodeHead(queryAccount,  gdConfig.getAgentCode(),DateUtil.format("yyyyMMddHHmmss", new Date()));
            String des = MD5Util.toMd5(gdConfig.getAgentCode()+"<body></body>"+"^"+gdConfig.getKey());
			xmlParse.addHeaderElement("digest",des);
			String requestUrl="<?xml version=\"1.0\" encoding=\"utf-8\"?><message>";
			requestUrl+=xmlParse.getHeaderElement().asXML()+"<body></body></message>";
			return requestUrl;
		} catch (Exception e) {
			logger.error("账户查询异常" + e.getMessage());
		}
		return null;
	}

	
}
