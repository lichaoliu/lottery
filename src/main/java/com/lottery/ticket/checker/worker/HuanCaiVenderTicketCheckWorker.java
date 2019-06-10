package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lottery.ticket.vender.impl.huancai.HuancaiConfig;
@Component
public class HuanCaiVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private final Logger caidaolog= LoggerFactory.getLogger("caidao-warn");
	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		return dealResult(ticketList, venderConfig);
	}

	
	/**
	 * 查票结果处理
	 *

	 */
	private List<TicketVender> dealResult(List<Ticket> ticketBatchList, IVenderConfig venderConfig) {
		List<TicketVender> ticketvenderList = new ArrayList<TicketVender>();
		String messageStr="";
		String returnStr="";
		try {

			Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
			for (Ticket ticket : ticketBatchList) {
				ticketMap.put(ticket.getId(), ticket);
			}
			messageStr = getElement(ticketBatchList, venderConfig);
			caidaolog.error("环彩查询:{}",messageStr);
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
          
			HashMap<String, String> mapCode = XmlParse.getElementAttribute("/", "response", returnStr);
			String code = mapCode.get("code");
			if (code.equals("0000")) {
				List<HashMap<String, String>> mapLists = XmlParse.getElementAttributeList("response/tickets/", "ticket", returnStr);
				if(mapLists==null||mapLists.isEmpty()){
					for (Ticket ticket : ticketBatchList) {
						TicketVender ticketVender = createTicketVender(venderConfig.getTerminalId(), TerminalType.T_HUANCAI);
						ticketVender.setStatus(TicketVenderStatus.not_found);
						ticketVender.setMessage("不存在此票");
						ticketVender.setId(ticket.getId());
						ticketvenderList.add(ticketVender);
						ticketVender.setResponseMessage(returnStr);
					}
					return ticketvenderList;
				}
				for (HashMap<String, String> map : mapLists) {
					String ticketId = map.get("ticketid");
					String status = map.get("status");
					TicketVender ticketVender = createTicketVender(venderConfig.getTerminalId(), TerminalType.T_HUANCAI);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setSendMessage(messageStr);
					ticketVender.setResponseMessage(returnStr);
					ticketvenderList.add(ticketVender);
					if ("1".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.printing);
						ticketVender.setMessage("出票中");
					} else if ("2".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.success);
						ticketVender.setMessage("出票成功");
						ticketVender.setPrintTime(new Date());
					} else if ("3".equals(status)) {
						ticketVender.setStatus(TicketVenderStatus.failed);
						ticketVender.setMessage("出票失败");
					} else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
						ticketVender.setMessage("未知异常");
					}

				}
			} else {
				logger.error("环彩查票返回结果异常,发送:{},返回:{}", messageStr, returnStr);
			}

		} catch (Exception e) {
			logger.error("环彩查票异常,发送:{},返回:{},异常为", messageStr, returnStr);
			logger.error(e.getMessage(),e);
		}
		return ticketvenderList;
	}

	
	/**
	 * 查票前拼串
	 *
	 * @param ticketBatchList
	 *            票集合
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */

	private String getElement(List<Ticket> ticketBatchList, IVenderConfig huancaiConfig) throws Exception {
		String queryCode = "1003";
		// 头部
		String md = "";
		XmlParse xmlParse = null;
		String messageId=DateUtil.format("yyyyMMddHHmmss", new Date());;
		xmlParse = HuancaiConfig.addGxHead(queryCode,huancaiConfig.getAgentCode(),messageId);
		Element bodyeElement = xmlParse.getBodyElement();
		Element elements = bodyeElement.addElement("tickets");
		HashMap<String, Object> bodyAttr = null;
		for (Ticket ticket : ticketBatchList) {
			bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("ticketid",  ticket.getId());
			Element element2 = elements.addElement("ticket");
			xmlParse.addElementAttribute(element2,bodyAttr);
		}
		try {
			md = MD5Util.toMd5(huancaiConfig.getAgentCode()+messageId + huancaiConfig.getKey() + xmlParse.getBodyElement().asXML());
		} catch (Exception e) {
			logger.error("加密异常" + e.getMessage());
		}
		xmlParse.addHeaderElement("digest", md);
		return  "cmd="+queryCode+"&msg="+xmlParse.asXML();

	}

	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_HUANCAI;
	}


}
