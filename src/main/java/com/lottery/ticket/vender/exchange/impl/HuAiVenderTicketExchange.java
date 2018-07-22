package com.lottery.ticket.vender.exchange.impl;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.exchange.AbstractVenderTicketExchange;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author liuxiaoyan
 *
 */
@Service
public class HuAiVenderTicketExchange extends AbstractVenderTicketExchange{


    @Override
    protected void execute(List<Ticket> ticketList, IVenderConfig venderConfig) {
        for(Ticket ticket:ticketList){
        	StringBuffer stringBuffer=new StringBuffer();
			String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
			stringBuffer.append("exAgent=").append(venderConfig.getAgentCode())
			.append("&exAction=").append("111").append("&exMsgID="+messageId);
			StringBuilder  exParam=new StringBuilder();
			exParam.append("OrderID=").append(ticket.getId());	
			
            try {
            	String signMsg=MD5Util.toMd5(venderConfig.getAgentCode()+"111"+messageId+exParam.toString()+venderConfig.getKey());
    			stringBuffer.append("&exParam=").append(exParam.toString()).append("&exSign=").append(signMsg);
               String	returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), stringBuffer.toString());
              
               Document doc = DocumentHelper.parseText(returnStr);
    		   Element rootElt = doc.getRootElement();
    		   String returnCode = rootElt.elementText("reCode");	
    		   if (returnCode.equals("0")){
    			   Map<String, String> map = XmlParse.getElementAttribute("reValue/BonusQuery/Order/", "BonusItem", returnStr);
                   String isWin=map.get("BonusCls");
                   if ("1".equals(isWin)||"0".equals(isWin)){//1中小奖0中大奖
                       String taxawardbets=map.get("Money");
                       this.ticketCreate(ticket,new BigDecimal(taxawardbets).multiply(new BigDecimal(100)));
                   }
                }else{
                    logger.error("互爱兑奖返回return={}",returnStr);
                }
               
            } catch (Exception e) {
                logger.error("兑奖出错",e);
            }
        }
    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_HUAI;
    }
}
