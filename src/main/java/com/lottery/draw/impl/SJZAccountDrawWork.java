package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import org.dom4j.DocumentException;
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
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.sjz.SJZConfig;
/**
 * 账户余额
 * @author wyliuxiaoyan
 *
 */
@Component("sjz_account")
public class SJZAccountDrawWork extends AbstractVenderBalanceWork{
	protected final Logger logger = LoggerFactory.getLogger(SJZAccountDrawWork.class);
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryAccount="1006";
	
	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if(config==null){
			return null;
		}
		String message=getElement(config);
		String returnStr="";
		try {
			 returnStr= HTTPUtil.sendPostMsg(config.getRequestUrl(), message);
		} catch (Exception e) {
			logger.error("石家庄查询账户接口返回异常" + e);
		}
		logger.info("请求的数据为{},返回数据为{}",message,returnStr);
		try {
			MemberAccount memberAccount =null;
			if(returnStr==null||returnStr.isEmpty()){
				return null;
			}
			//处理结果
			memberAccount = dealResult(returnStr,config);
			return memberAccount;
		} catch (Exception e) {
			logger.error("石家庄查询账户解析异常"+e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_SJZ;
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
			    HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
				String xCode=mapResult.get("code");	
				if("0".equals(xCode)){
					 HashMap<String, String> getResult = XmlParse.getElementText("body/", "response", desContent);
					 String leftMoney=getResult.get("balance");
					 memberAccount.setBalance(new BigDecimal(leftMoney).divide(new BigDecimal(100)));
					 memberAccount.setTerminalName(getTerminalType().getName());
					 memberAccount.setId(config.getTerminalId());
					 memberAccount.setSmsFlag(YesNoStatus.no.value);	
				}
		} catch (Exception e) {
			logger.error("查询账户结果异常");
		}
		return memberAccount;
	}
	/**
	 * 拼串
	 * @param jinnuoConfig
	 * @return
	 */
	private String getElement(IVenderConfig sjzConfig) {
		try {
			String messageId=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
			XmlParse xmlParse = SJZConfig.addHead(queryAccount, sjzConfig.getAgentCode(),timestamp,messageId);
			String des = MD5Util.toMd5(sjzConfig.getAgentCode()+sjzConfig.getKey()+timestamp,CharsetConstant.CHARSET_UTF8);
	        xmlParse.addHeaderElement("cipher",des);
			return "cmd="+queryAccount+"&msg="+xmlParse.asXML();
		} catch (Exception e) {
			logger.error("账户查询异常" + e.getMessage());
		}
		return null;
	}

	
}
