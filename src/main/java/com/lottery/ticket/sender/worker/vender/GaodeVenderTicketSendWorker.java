package com.lottery.ticket.sender.worker.vender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gaode.GaoDeConverter;
import com.lottery.ticket.vender.impl.gaode.GaoDeLotteryDef;
import com.lottery.ticket.vender.impl.gaode.GaodeConfig;

@Component
public class GaodeVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "P001";

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		// 分配送票
		String messageStr = "";
		String returnStr = "";
		try {
		
			GaoDeConverter gDeConverter = (GaoDeConverter) getVenderConverter();
			String lotno = gDeConverter.convertLotteryType(lotteryType);
			String phase = gDeConverter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, gDeConverter);
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr);

			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType,venderConfig);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("高德送票异常",e);
			processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
			return ticketSendResultList;
		} 
	}

	/**
	 * 送票结果处理
	 * 
	 * @param desContent

	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> tickets, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType,IVenderConfig zyConfig) throws Exception {
       
		HashMap<String, String> mapResult = XmlParse.getElementText("", "body", desContent);
		String errorcode = mapResult.get("responseCode");
		String responseMessage = mapResult.get("responseMessage");
		if ("0".equals(errorcode)) {
			List<HashMap<String, String>> resultList = XmlParse.getElementTextList("body/results/tickets/", "ticket", desContent);
             for (HashMap<String, String> map : resultList) {
     			TicketSendResult ticketSendResult = new TicketSendResult();
     			ticketSendResultsList.add(ticketSendResult);
     			ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
     			ticketSendResult.setId(map.get("ticketId"));
     			ticketSendResult.setLotteryType(lotteryType);
     			ticketSendResult.setStatusCode(map.get("ticketResultCode"));
     			ticketSendResult.setResponseMessage(map.get("ticketResultMessage"));
     			ticketSendResult.setSendMessage(requestStr);
     			ticketSendResult.setSendTime(new Date());
     			ticketSendResult.setMessage(requestStr);
     			ticketSendResult.setTerminalType(getTerminalType());
     			String returnCode=map.get("ticketResultCode");
     			if ("0".equals(returnCode)||"-2".equals(returnCode)) {
     				ticketSendResult.setExternalId(map.get("orderID"));
     				ticketSendResult.setStatus(TicketSendResultStatus.success);
     			}else {
     				ticketSendResult.setStatus(TicketSendResultStatus.unkown);
     			}
     		}	
		}else{
			logger.error("errorcode:"+errorcode+",responseMessage:"+responseMessage);
		}
	}

	/**
	 * 送票前拼串
	 * 
	 * @param tickets
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig gdConfig, LotteryType lotteryType, GaoDeConverter gDeConverter) throws Exception{
		try {
			XmlParse xmlParse =  GaodeConfig.addGaodeHead(betCode,  gdConfig.getAgentCode(),DateUtil.format("yyyyMMddHHmmss", new Date()));
			HashMap<String, String> bodyzAttr = new HashMap<String, String>();
			bodyzAttr.put("lotteryId", lotteryNo);
			bodyzAttr.put("issue", phase.substring(1));
			bodyzAttr.put("rule", GaoDeLotteryDef.playCodeMap.get(lotteryType));//玩法规则
			xmlParse.addBodyElement(bodyzAttr,xmlParse.getBodyElement());
			Element bodyeElement = xmlParse.getBodyElement();
			Element elements = bodyeElement.addElement("tickets");
	       for (Ticket ticket : tickets) {
				HashMap<String, String> bodyAttr = new HashMap<String, String>();
				Double amount = ticket.getAmount().divide(new BigDecimal("100")).doubleValue();
				bodyAttr.put("ticketId", ticket.getId());
				bodyAttr.put("passMode", GaoDeLotteryDef.playTypeMap.get(ticket.getPlayType()));
				bodyAttr.put("chips", (amount.intValue()/ ticket.getMultiple()/2)+"");//zhushu
	            bodyAttr.put("betContents", gDeConverter.convertContent(ticket));
	            bodyAttr.put("money", amount.intValue() + "");
	            bodyAttr.put("multiple", ticket.getMultiple() + "");//倍数
	            Element element2 = elements.addElement("ticket");
				xmlParse.addBodyElement(bodyAttr, element2);
			}
			String des = MD5Util.toMd5(gdConfig.getAgentCode()+xmlParse.asBodyXML()+"^"+gdConfig.getKey());
			xmlParse.addHeaderElement("digest",des);
			return xmlParse.asXML();
		} catch (Exception e) {
			logger.error("送票拼串异常", e);
		}
		return null;
	}


	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_GAODE;
	}
	
	
	
}
