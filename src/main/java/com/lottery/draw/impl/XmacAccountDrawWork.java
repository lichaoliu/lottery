package com.lottery.draw.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.terminal.MemberAccount;
import com.lottery.draw.AbstractVenderBalanceWork;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
/**
 * 账户余额
 * @author wyliuxiaoyan
 *
 */
@Component("xmac_account")
public class XmacAccountDrawWork extends AbstractVenderBalanceWork{
	protected final Logger logger = LoggerFactory.getLogger(XmacAccountDrawWork.class);
	@Autowired
	IdGeneratorService igenGeneratorService;
	protected String queryAccount="108";
	
	@Override
	protected MemberAccount getAccount(IVenderConfig config) {
		if(config==null){
			return null;
		}
		String message=getElement(config);
		String returnStr="";
		try {
			 returnStr= HTTPUtil.post(config.getRequestUrl(),message,CharsetConstant.CHARSET_GBK);
		} catch (Exception e) {
			logger.error("查询账户接口返回异常" + e);
		}
		logger.info("请求的数据为{},返回数据为{}",message,returnStr);
		try {
			//处理结果
			MemberAccount memberAccount = dealResult(returnStr,config);
			return memberAccount;
		} catch (Exception e) {
			logger.error("查询账户解析异常"+e);
		}
		return null;
	}

	protected TerminalType getTerminalType() {
		return TerminalType.T_XMAC;
	}

	
	/**
	 * 查询账户结果处理
	 * @param desContent
	 * @param count
	 * @return
	 * @throws DocumentException 
	 */
	private MemberAccount  dealResult(String desContent, IVenderConfig config) {
		 if(StringUtils.isNotBlank(desContent)){
			 desContent="<param>"+desContent.split("\\?>")[1]+"</param>";
         }
		MemberAccount memberAccount = new MemberAccount();
		try {
			    HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", desContent);
				String xCode=mapResult.get("xCode");	
				if("0".equals(xCode)){
					 String leftMoney=mapResult.get("xValue");
					 memberAccount.setBalance(new BigDecimal(leftMoney));
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
	 * @param fcbyConfig
	 * @return
	 */
	private String getElement(IVenderConfig xmacConfig) {
		try {
			 String wAgent=xmacConfig.getAgentCode();
		     String wMsgID=igenGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS);
			 String wParam="";
             String key=wAgent+queryAccount+wMsgID+wParam+xmacConfig.getKey();
             String wSign= MD5Util.getMD5((key).getBytes("GBK"));
             String param="wAgent="+wAgent+"&wAction="+queryAccount+"&wMsgID="+wMsgID+"&wSign="+wSign+"&wParam=";
			 return param;
		} catch (Exception e) {
			logger.error("账户查询异常" + e.getMessage());
		}
		return null;
	}

	
}
