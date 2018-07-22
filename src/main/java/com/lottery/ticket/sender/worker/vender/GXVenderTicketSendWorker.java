package com.lottery.ticket.sender.worker.vender;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.lottery.ticket.sender.TicketSendUser;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.guoxin.GuoxinConfig;
import com.lottery.ticket.vender.impl.guoxin.GuoxinLotteryDef;

@Component
public class GXVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "001";


	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		    List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		    for (Ticket ticket : ticketList) {
				TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
				ticketSendResultList.add(ticketSendResult);
				String messageStr = "";
				String returnStr = "";
				try {
					IVenderConverter guoxinConverter = getVenderConverter();
					String lotno = guoxinConverter.convertLotteryType(lotteryType);
					String phase = guoxinConverter.convertPhase(lotteryType, ticketBatch.getPhase());
					messageStr = getElement(ticket, lotno, phase, venderConfig, lotteryType, guoxinConverter);
					ticketSendResult.setSendMessage(messageStr);
					ticketSendResult.setSendTime(new Date());
					returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
					dealResult(messageStr, returnStr, ticketBatch,ticketSendResult, lotteryType);
				} catch (Exception e) {
					List<Ticket> allnewList=new ArrayList<Ticket>();
					allnewList.add(ticket);
					processError(ticketSendResultList, ticketBatch, allnewList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
					logger.error("国信送票处理错误", e);

				}
			}
		    return ticketSendResultList; 
	}

	
	/**
	 * 送票结果处理
	 *
	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, TicketSendResult ticketSendResult, LotteryType lotteryType) throws Exception {

		HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
		String returnCode = mapResult.get("code");
		String returnMsg = mapResult.get("msg");
		ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
		ticketSendResult.setResponseMessage(desContent);
		ticketSendResult.setSendMessage(requestStr);
		ticketSendResult.setSendTime(new Date());
		ticketSendResult.setMessage(returnMsg);
		ticketSendResult.setTerminalType(getTerminalType());
		if ("1000".equals(returnCode)) {
			ticketSendResult.setStatus(TicketSendResultStatus.success);
			ticketSendResult.setSendTime(new Date());
		}else if ("0000".equals(returnCode)) {
			HashMap<String, String> map = XmlParse.getElementAttribute("body/order/", "ticket", desContent);
			String externalId = map.get("sysId");
			String ticketId = map.get("id");
			ticketSendResult.setStatus(TicketSendResultStatus.printed);
			ticketSendResult.setSendTime(new Date());
			ticketSendResult.setId(ticketId);
			ticketSendResult.setLotteryType(lotteryType);
			ticketSendResult.setStatusCode(returnCode);
			ticketSendResult.setExternalId(externalId);
		} else if ("0017".equals(returnCode)||"0018".equals(returnCode)) {// 票号重复
			ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
			ticketSendResult.setMessage("网站平台交易流水号重复");
		} else if (Constants.guoxinSendErrorFailed.containsKey(returnCode)) {
			ticketSendResult.setStatus(TicketSendResultStatus.failed);
			ticketSendResult.setMessage(Constants.guoxinSendErrorFailed.get(returnCode));
		} else if (Constants.guoxinSendError.containsKey(returnCode)) {
			ticketSendResult.setMessage(Constants.guoxinSendError.get(returnCode));
			ticketSendResult.setStatus(TicketSendResultStatus.failed);
		} else {
			ticketSendResult.setStatus(TicketSendResultStatus.unkown);
		}
	}

	/**
	 * 送票前拼串
	 * 
	 * @param ticket
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 * @throws Exception
	 */

	public String getElement(Ticket ticket, String lotteryNo, String phase, IVenderConfig gxConfig, LotteryType lotteryType, IVenderConverter gxConverter) throws UnsupportedEncodingException {
		XmlParse xmlParse =  GuoxinConfig.addGxHead(betCode, gxConfig.getAgentCode());


		TicketSendUser ticketSendUser=getDefaultUser();
		HashMap<String, Object> orderAttr = new HashMap<String, Object>();
		orderAttr.put("userInfo", ticketSendUser.getRealName() + "$" + ticketSendUser.getIdCard() + "$" + ticketSendUser.getPhone());
		orderAttr.put("lotteryId", lotteryNo);
		orderAttr.put("issue", phase);
		orderAttr.put("playId", GuoxinLotteryDef.toLotteryTypeMap.get(Integer.parseInt(ticket.getContent().split("\\-")[0])));
		orderAttr.put("orderId", ticket.getId());

		HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
		Double amount = ticket.getAmount().doubleValue()/ 100;
		bodyAttr.put("ticketId", ticket.getId());
		bodyAttr.put("item", amount.intValue() / 100 / ticket.getMultiple() / 2 + "");//注数
		bodyAttr.put("multiple ", ticket.getMultiple() + "");//倍数
		bodyAttr.put("amount", amount.intValue() + "");
        bodyAttr.put("pollId", GuoxinLotteryDef.playTypeMap.get(Integer.parseInt(ticket.getContent().split("\\-")[0])));	
            
		Element attributeMap2 = xmlParse.addBodyElementAndAttribute("order", "", orderAttr);
		xmlParse.addBodyElementAndAttribute("ticket", gxConverter.convertContent(ticket), bodyAttr, attributeMap2);
       
		String md = MD5Util.MD5(gxConfig.getAgentCode() + gxConfig.getKey() + xmlParse.getBodyElement().asXML());
		xmlParse.addHeaderElement("digest", md.toLowerCase());
		return "msg="+xmlParse.asXML();
	}
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_GX;
	}

	

}
