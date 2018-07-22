package com.lottery.ticket.vender.lotterycenter.tianjin.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.ReCode;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.tianjincenter.TJFCConfig;
import com.lottery.ticket.vender.lotterycenter.tianjin.AbstractTJFCMessageHandler;

public abstract class AbstractTJFCWinDataHandler extends AbstractTJFCMessageHandler{

	protected LotteryPhase winDataProcess(String filename,TJFCConfig config) throws Exception{
		String localFileName=downloadFtp(localwindatadir,filename, config);
		String responseStr=readFile(localFileName,config.getNoticeKey(),1024 * 1024 * 10);

		if(StringUtils.isNotBlank(responseStr)){
			String[] issues = responseStr.split("\r\n");
			for(String winda : issues){
				String[] sArr = winda.split("\t");
				if(sArr.length<38){
					continue;
				}
				String pwd=sArr[0];
				String gamecode = sArr[1];
				String logic=sArr[2];
				String money=sArr[37];
				String phase=sArr[4];
				String selldate=sArr[6];
				logger.error("票密码:{},英文玩法:{},逻辑机:{},期号:{},销售日期:{},中奖金额:{}",pwd,gamecode,logic,phase,selldate,money);				
				String content=windataRequest(gamecode,logic,phase,money,pwd,selldate);
				logger.error("兑奖原始字符串:{}",content);
				encash(content, pwd, money, config);
			}
		}else{
			logger.error("文件:{}没有中奖名单",filename);
		}
		return null;
	}
	
 
	
	
	protected void encash(String content,String pwd,String winmoeny,IVenderConfig  venderConfig){
	
		try {

			if(StringUtils.isBlank(content)){
				return;
			}
			
			byte[] fcMsg = makeMessageHead(5,"", content,venderConfig.getAgentCode(),venderConfig.getKey());

			 byte[] result = sendMs(fcMsg,venderConfig.getRequestUrl(),venderConfig.getPort(),1024);
			 String requestmsg= splitMsg(result, venderConfig.getKey());
			 logger.error("福彩兑奖发送:{}返回:{},含义:{}",content,requestmsg,ReCode.bsCode.get(requestmsg));
			 if("0".equals(requestmsg)){
				 List<Ticket> ticketList=ticketService.getByTerminalIdAndExternalId(venderConfig.getTerminalId(), pwd);
				 if(ticketList==null||ticketList.size()==0||ticketList.size()>0){
					 logger.error("终端:{},外部 票号:{}的票不存在",venderConfig.getTerminalId(),pwd);
				 }else{
					 Ticket ticket=ticketList.get(0);
					 ticket.setAddPrize(new BigDecimal(winmoeny));
					 ticket.setAgencyExchanged(YesNoStatus.yes.value);
					 ticketService.update(ticket);
				 }
				 
			 }
		} catch (Exception e) {
			logger.error("兑奖失败", e);
			
		}		
	}
	
	
	protected String windataRequest(String gameName,String machineCode,String winPhase,String windmoney,String pwd,String selltime){
		IVenderConverter venderConverter=getVenderConverter();
		if(venderConverter==null){
			logger.error("终端转换为空");
			return null;
		}
		LotteryType lotteryType=venderConverter.findLotteryType(gameName);
		LotteryPhase lotteryPhase=lotteryPhaseService.getCurrent(lotteryType.value);
		if(lotteryPhase==null){
			return null;
		}
		String phase=venderConverter.convertPhase(lotteryType, lotteryPhase.getPhase());
		StringBuffer commodStr = new StringBuffer();
	    commodStr.append(paddingMessage(gameName));
		commodStr.append(paddingMessage(machineCode));
		commodStr.append(paddingMessage(phase));
		commodStr.append(paddingMessage(windmoney));
		commodStr.append(paddingMessage(pwd));
		commodStr.append(paddingMessage(selltime));
		String md5=null;
		try {
			md5 = MD5Util.toMd5(commodStr.toString());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		commodStr.append(paddingMessage(md5));
		return commodStr.toString();
	}
	
	protected String createMessages(String str, int size) {
		String returnStr = str;
		int temp = size - str.length();
		if (temp <= 0)
			return str;
		for (int loop = 0; loop < temp; loop++) {
			returnStr += "\0";
		}
		return returnStr;
	}
}
