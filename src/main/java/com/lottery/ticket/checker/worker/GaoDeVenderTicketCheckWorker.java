package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gaode.GaodeConfig;
@Component
public class GaoDeVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "P003";

	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr);
		} catch (Exception e) {
			logger.error("高德发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		} 

		if (StringUtils.isEmpty(returnStr)) {
			logger.error("高德查票返回空");
			return venderList;
		}
		// 查票处理结果
		try {
			 dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,venderConverter);
		} catch (Exception e) {
			logger.error("高德查票异常" , e);
		}	
			
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig venderConfig,IVenderConverter venderConverter) {
		try {
			HashMap<String, String> mapResult = XmlParse.getElementText("", "body", desContent);
			String errorcode = mapResult.get("responseCode");
			String responseMessage = mapResult.get("responseMessage");
			if ("0".equals(errorcode)) {
				List<HashMap<String, String>> resultList = XmlParse.getElementTextList("body/tickets/", "ticket", desContent);
				for (HashMap<String, String> map:resultList) {
					String status = map.get("status");
					String ticketId = map.get("ticketId");
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_GAODE);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setMessage(responseMessage);
				  if ("1".equals(status)) {//出票中
					 ticketVender.setStatus(TicketVenderStatus.printing);
				  } else if ("2".equals(status)) {// 成功
					 String odd = map.get("odd");
					 String time = map.get("time");
					 if(StringUtils.isNotEmpty(odd)){
						 Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
						 for (Ticket ticket : ticketBatchList) {
							ticketMap.put(ticket.getId(), ticket);
						 }
						 ticketVender.setOtherPeilv(odd);
						 odd=venderConverter.convertSp(ticketMap.get(ticketId),odd);
					 }
                     String extendId = map.get("TCid");
                     
					 ticketVender.setStatus(TicketVenderStatus.success);
					 ticketVender.setPrintTime(DateUtil.parse(time,"yyyy-MM-dd HH:mm:ss"));
					 ticketVender.setExternalId(extendId);
					 ticketVender.setPeiLv(odd);
				  } else if ("3".equals(status)) {// 失败
					 ticketVender.setStatus(TicketVenderStatus.failed);
				  } else if ("0".equals(status)) {// 票不存在
					 ticketVender.setStatus(TicketVenderStatus.not_found);
				  }else {
					ticketVender.setStatus(TicketVenderStatus.unkown);
				  }
				    venderList.add(ticketVender);
			}		
		 }else{
		    	logger.error("高德查票处理结果异常errorcode:{},responseMessage:{}",errorcode,responseMessage);
		 }	    
				
		} catch (Exception e) {
			logger.error("高德查票处理结果异常{}", e);
		}
	}

	
	/**
	 * 查票前拼串
	 */

	private String getElement(List<Ticket> tickets,IVenderConfig gaodeConfig) throws Exception {
	  try{
		  XmlParse xmlParse =  GaodeConfig.addGaodeHead(queryCode,  gaodeConfig.getAgentCode(),DateUtil.format("yyyyMMddHHmmss", new Date()));
		  Element bodyeElement = xmlParse.getBodyElement();
		  Element elements = bodyeElement.addElement("tickets");
          for (Ticket ticket : tickets) {
			HashMap<String, String> bodyAttr = new HashMap<String, String>();
            bodyAttr.put("ticketId", ticket.getId());
			xmlParse.addBodyElement(bodyAttr, elements);
		 }
        
		 String des = MD5Util.toMd5(gaodeConfig.getAgentCode()+xmlParse.asBodyXML()+"^"+gaodeConfig.getKey());
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
