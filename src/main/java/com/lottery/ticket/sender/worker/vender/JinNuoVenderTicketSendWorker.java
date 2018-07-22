package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
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
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConfig;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoLotteryDef;

@Component
public class JinNuoVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "2001";

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		

		// 分配送票
		String messageStr = "";
		String returnStr = "";
		try {
			IVenderConverter jnConverter = getVenderConverter();
			String lotno = jnConverter.convertLotteryType(lotteryType);
			String phase = jnConverter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, jnConverter);
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
		
			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("金诺送票{}返回", messageStr, e);
			processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
			return ticketSendResultList;
		}
		
	}

	/**
	 * 送票结果处理
	 * 
	 * @param desContent
	 * @param count
	 * @return
	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> ticket, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType, IVenderConfig qhConfig) throws Exception {
		HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
		String errorcode = mapResult.get("code");
		if ("0".equals(errorcode)) {
			List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/response/order/", "ticket", desContent);
			if (mapList != null && mapList.size() > 0) {
				for (Map<String, String> map : mapList) {
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
					String externalId ="";
					String returnCode = map.get("code");
					String ticketId = map.get("seq");
					String errormsg = map.get("msg");
					ticketSendResult.setId(ticketId);
					ticketSendResult.setLotteryType(lotteryType);
					ticketSendResult.setStatusCode(returnCode);
					ticketSendResult.setExternalId(externalId);
					ticketSendResult.setResponseMessage(desContent);
					ticketSendResult.setSendMessage(requestStr);
					ticketSendResult.setSendTime(new Date());
					ticketSendResult.setMessage(errormsg);
					ticketSendResult.setTerminalType(getTerminalType());
					if ("0".equals(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.success);
					} else if (returnCode.equals("8302")||returnCode.equals("8303")) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
					} else {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
					}
				}
			}
		} else {
			processError(ticketSendResultsList, ticketBatch, ticket, TicketSendResultStatus.unkown, errorcode, requestStr, desContent, "返回结果不为0,errorcode="+errorcode);
		}
	}

	/**
	 * 送票前拼串
	 * 
	 * @param ticketBatchList
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig jnConfig, LotteryType lotteryType, IVenderConverter jnConverter) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = JinNuoConfig.addHead(betCode, jnConfig.getAgentCode(),timestamp);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		Element body = xmlParse.addBodyElement(bodyzAttr, xmlParse.getBodyElement()).addElement("order");
		body.addAttribute("lotteryid", lotteryNo);
		if (!LotteryType.getJclq().contains(lotteryType)&&!LotteryType.getJczq().contains(lotteryType)) {
			body.addAttribute("issue", phase);
		}
		for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("seq", ticket.getId());
			StringBuffer stringBuffer=new StringBuffer();
			if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)){
				stringBuffer.append(JinNuoLotteryDef.toLotteryTypeMap.get(ticket.getPlayType()));
			}else{
				stringBuffer.append("01");
			}
			stringBuffer.append("_").append(JinNuoLotteryDef.playTypeMap.get(ticket.getPlayType()))
			.append("_").append(jnConverter.convertContent(ticket)).append("_").append(ticket.getMultiple())
			.append("_").append(ticket.getAmount().intValue());
			xmlParse.addBodyElementAndAttribute("ticket",stringBuffer.toString(), bodyAttr, body);

		}
		try{
			String des = MD5Util.toMd5(jnConfig.getAgentCode()+jnConfig.getKey()+timestamp+xmlParse.getBodyElement().asXML().split("<body>")[1].split("</body>")[0],CharsetConstant.CHARSET_UTF8);
	        xmlParse.addHeaderElement("cipher",des);
			return "cmd="+betCode+"&msg="+xmlParse.asXML();
		}catch(Exception e){
			logger.error("加密出错",e);
			return null;
		}
		
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_JINRUO;
	}

}
