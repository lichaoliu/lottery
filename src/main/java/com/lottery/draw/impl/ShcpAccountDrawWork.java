package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import com.lottery.ticket.vender.impl.shcp.SHCPConfig;
/**
 * 账户余额
 * @author wyliuxiaoyan
 *
 */
@Component("shcp_account")
public class ShcpAccountDrawWork extends AbstractVenderBalanceWork{
	protected final Logger logger = LoggerFactory.getLogger(ShcpAccountDrawWork.class);
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryAccount="20010";
	
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
			logger.error("上海查询账户接口返回异常" + e);
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
			logger.error("上海查询账户解析异常"+e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_SHCP;
	}

	
	/**
	 * 查询账户结果处理
	 * @param desContent
	 * @param count
	 * @return
	 * @throws DocumentException 
	 */
	private MemberAccount  dealResult(String desContent, IVenderConfig config) {
		MemberAccount memberAccount = new MemberAccount();
		try {
			    HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/rows/", "row", desContent);
			    String leftMoney=mapResult.get("balance");
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
	private String getElement(IVenderConfig shcpConfig) {
		try {
			String messageId=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			XmlParse xmlParse =  SHCPConfig.addShcpHead(queryAccount, shcpConfig.getAgentCode(),messageId);
			xmlParse.addBodyElement("query", "");
			logger.info(xmlParse.asXML());
			String des = MD5Util.toMd5(xmlParse.asXML()+shcpConfig.getKey());
			return "xml="+xmlParse.asXML()+"&sign="+des;
		} catch (Exception e) {
			logger.error("账户查询异常" + e.getMessage());
		}
		return null;
	}

	
}
