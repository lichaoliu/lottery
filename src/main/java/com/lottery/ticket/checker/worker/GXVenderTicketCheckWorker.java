package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.guoxin.GuoxinConfig;

@Component
public class GXVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {
	private final Logger caidaolog= LoggerFactory.getLogger("caidao-warn");
	private String queryCode = "012";

	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
		} catch (Exception e) {
			logger.error("国信发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}

		caidaolog.error("国信发送:{},返回:{}",messageStr,returnStr);

		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig);
				} catch (Exception e) {
					logger.error("查票异常" + e);
				}
			} else {
				logger.error("国信查票返回空");
			}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @param count
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig venderConfig) {
		try {
			    if(desContent.length()>2){
			    	Map<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
					String code = mapResult.get("code");
					String msg = mapResult.get("msg");
					if ("0000".equals(code)) {// 成功
						List<HashMap<String, String>> mapLists = XmlParse.getElementAttributeList("body/response/", "ticket", desContent);
						
						for (int i = 0, len = mapLists.size(); i < len; i++) {
							HashMap<String, String> map = mapLists.get(i);
							String status = map.get("status");
							String extendId = map.get("sysId");
							String msgStatus = map.get("msg");
							String ticketId = map.get("ticketId");
							TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_GX);
							ticketVender.setId(ticketId);
							ticketVender.setStatusCode(status);
							ticketVender.setMessage(msgStatus);
							ticketVender.setExternalId(extendId);
						  if ("0".equals(status)||"1".equals(status)) {//出票中
							 ticketVender.setStatus(TicketVenderStatus.printing);
						  } else if ("2".equals(status)) {// 成功
							 ticketVender.setStatus(TicketVenderStatus.success);
							 ticketVender.setPrintTime(new Date());
						  } else if ("3".equals(status)) {// 失败
							 ticketVender.setStatus(TicketVenderStatus.failed);
						  } else if ("9".equals(status)) {// 票不存在
							 ticketVender.setStatus(TicketVenderStatus.not_found);
						  }else {
							ticketVender.setStatus(TicketVenderStatus.unkown);
						  }
						    venderList.add(ticketVender);
					}
				  }else{
					  logger.error("国信返回异常,code={},msg={}",code,msg);
				  }
			    }else{
			    	logger.error("国信查票处理结果异常"+Constants.guoxinSendError.get(desContent));
			    }
				
		} catch (Exception e) {
			logger.error("国信查票处理结果异常{}", e);
		}
	}

	
	/**
	 * 查票前拼串
	 * 
	 * @param ticketBatchList
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */

	private String getElement(List<Ticket> tickets,IVenderConfig gxConfig) throws Exception {
		XmlParse xmlParse = GuoxinConfig.addGxHead(queryCode,gxConfig.getAgentCode());
		Element element = xmlParse.addBodyElementAndAttribute("tickets", "", new HashMap<String, Object>());

		HashMap<String, Object> bodyAttr = null;
		for (Ticket ticket : tickets) {
			bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("ticketId", ticket.getId());
			xmlParse.addBodyElementAndAttribute("ticket", "", bodyAttr, element);
		}
		
		String md = MD5Util.toMd5(gxConfig.getAgentCode() + gxConfig.getKey() + xmlParse.getBodyElement().asXML());
		xmlParse.addHeaderElement("digest", md);
		return "msg="+xmlParse.asXML();
	}


	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_GX;
	}

	
	
	
}
