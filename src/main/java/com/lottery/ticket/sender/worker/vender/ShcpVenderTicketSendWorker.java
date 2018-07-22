package com.lottery.ticket.sender.worker.vender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.util.CoreDateUtils;
import com.lottery.ticket.sender.TicketSendUser;
import org.apache.http.conn.HttpHostConnectException;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.shcp.SHCPConfig;

@Component
public class ShcpVenderTicketSendWorker extends AbstractVenderTicketSendWorker {



	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		    List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		
			String messageStr = "";
			String returnStr = "";
			try {
				String lotno = venderConverter.convertLotteryType(lotteryType);
				String phase = venderConverter.convertPhase(lotteryType, ticketBatch.getPhase());
				messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, venderConverter);
				returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
				dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
				return ticketSendResultList;
			} catch (Exception e) {
				processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
				logger.error("送票处理错误", e);

				return ticketSendResultList;
			}
	}

	
	/**
	 * 送票结果处理
	 *
	 * @param desContent
	 * @return
	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> ticket, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType, IVenderConfig qhConfig) throws Exception {
		
			List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/tickets/", "ticket", desContent);
			if (mapList != null && mapList.size() > 0) {
				for (Map<String, String> map : mapList) {
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
					String returnCode = map.get("code");
					String ticketId = map.get("apply");
					String desc = map.get("desc");
					ticketSendResult.setId(ticketId);
					ticketSendResult.setLotteryType(lotteryType);
					ticketSendResult.setStatusCode(returnCode);
					ticketSendResult.setExternalId("");
					ticketSendResult.setResponseMessage(desContent);
					ticketSendResult.setSendMessage(requestStr);
					ticketSendResult.setSendTime(new Date());
					ticketSendResult.setTerminalType(getTerminalType());
					if (ErrorCode.Success.getValue().equals(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.success);
					} else if (Constants.shcpSendError.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
						ticketSendResult.setMessage(desc);
					} else if ("1".equals(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
						ticketSendResult.setMessage("票已存在");
					}else {
						ticketSendResult.setStatus(TicketSendResultStatus.unkown);
						ticketSendResult.setMessage(returnCode);
					}
				}
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
	 * @throws Exception
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig shcpConfig, LotteryType lotteryType, IVenderConverter shcpConverter) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		 String betCode = "10001";
		if (LotteryType.getJclq().contains(lotteryType)){
			betCode="10003";
		}else if(LotteryType.getJczq().contains(lotteryType)){
			betCode="10002";
		}else if(LotteryType.getDC().contains(lotteryType)){
			betCode="10005";
		}else{
			betCode="10001";
		}
		XmlParse xmlParse =  SHCPConfig.addShcpHead(betCode, shcpConfig.getAgentCode(),messageId);

		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		TicketSendUser ticketSendUser=getDefaultUser();
		Element body = xmlParse.addBodyElement(bodyzAttr, xmlParse.getBodyElement());
		body.addElement("user").addAttribute("idcard", ticketSendUser.getIdCard()).addAttribute("name", ticketSendUser.getRealName()).addAttribute("mobile",ticketSendUser.getPhone());
		
		Element bodyStr =body.addElement("tickets").addAttribute("gid", lotteryNo).addAttribute("pid", phase);
       for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			Double amount = ticket.getAmount().divide(new BigDecimal("100")).doubleValue();
			bodyAttr.put("apply", ticket.getId());
            bodyAttr.put("codes", shcpConverter.convertContent(ticket));
            
            bodyAttr.put("money", amount.intValue() + "");
            bodyAttr.put("mulity", ticket.getMultiple() + "");//倍数
		    bodyAttr.put("etime", CoreDateUtils.DateToStr(ticket.getDeadline()));
            xmlParse.addBodyElementAndAttribute("ticket", "", bodyAttr,bodyStr);
		}
       
		String des = MD5Util.toMd5(xmlParse.asXML().replace("&gt;",">")+shcpConfig.getKey());
		return "xml="+xmlParse.asXML().replace("&gt;",">")+"&sign="+des;
	}
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_SHCP;
	}

	

}
