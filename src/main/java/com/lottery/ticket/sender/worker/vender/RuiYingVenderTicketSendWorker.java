package com.lottery.ticket.sender.worker.vender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.YesNoStatus;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
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
import com.lottery.ticket.vender.impl.ruiying.RuiYingConfig;


@Component
public class RuiYingVenderTicketSendWorker extends AbstractVenderTicketSendWorker {

	private static final String TRADE_CODE = "101"; // 交易代码:101-投注

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,
			IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		String messageStr = "";
		String returnStr = "";
		try {
			IVenderConverter converter = getVenderConverter();
			String lotno = converter.convertLotteryType(lotteryType);
			String phase = converter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, converter);
			
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);

			
			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("瑞盈送票发送:{},返回:{}", messageStr, returnStr);
			logger.error(e.getMessage(),e);
			processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), 
					null, messageStr, returnStr, e.getMessage());

			return ticketSendResultList;
		}
	}

	/**
	 * 送票前拼串
	 * 
	 * @param tickets
	 * 			票集合
	 * @param lotteryNo
	 * 			彩种
	 * @param phase
	 * 			彩期
	 * @param venderConfig
	 * 			出票终端配置
	 * @param lotteryType
	 * 			彩种类型
	 * @param venderConverter
	 * 			出票终端转换器
	 * @return
	 */
	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig venderConfig, 
			LotteryType lotteryType, IVenderConverter venderConverter) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = RuiYingConfig.addHead(lotteryNo + phase, venderConfig.getAgentCode(), timestamp);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		Element body = xmlParse.addBodyElement(bodyzAttr, xmlParse.getBodyElement()).addElement("order");
		body.addAttribute("lotId", lotteryNo);
		if (!LotteryType.getJclq().contains(lotteryType)&&!LotteryType.getJczq().contains(lotteryType)) {
			body.addAttribute("issue", phase);
		}
		for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("tid", ticket.getId());
			bodyAttr.put("multiple", ticket.getMultiple());
			bodyAttr.put("money", ticket.getAmount().divide(new BigDecimal(100), 0, BigDecimal.ROUND_UP));
			bodyAttr.put("ball", venderConverter.convertContent(ticket));
			if(ticket.getAddition()== YesNoStatus.yes.value){
				bodyAttr.put("zjFlag",1);
			}else{
				bodyAttr.put("zjFlag",0);
			}

			xmlParse.addBodyElementAndAttribute("ticket", null, bodyAttr, body);
		}
		String wMsg = xmlParse.asXML().replace("\n", "");
		StringBuilder signSb = new StringBuilder();
		signSb.append(venderConfig.getAgentCode()).append(TRADE_CODE).append(wMsg).append(venderConfig.getKey());
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("wAgent=").append(venderConfig.getAgentCode())
			.append("&wCmd=").append(TRADE_CODE)
			.append("&wMsg=").append(wMsg)
			.append("&wSign=").append(MD5Util.toMd5(signSb.toString()));
			return sb.toString();
		} catch (Exception e) {
			logger.error("加密出错",e);
			return null;
		}	

	}

	/**
	 * 送票结果处理
	 * 
	 * @param desContent
	 * @return
	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> ticket, 
			List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType, IVenderConfig qhConfig) throws Exception {
		desContent = desContent.substring(0, desContent.indexOf("</response>")) + "</response>";
		HashMap<String, String> mapResult = XmlParse.getElementText("/", "head", desContent);;
		String errorcode = mapResult.get("errCode");
		if ("0".equals(errorcode)) {
			List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/order/", "ticket", desContent);
			for (Map<String, String> map : mapList) {
				TicketSendResult ticketSendResult = new TicketSendResult();
				ticketSendResultsList.add(ticketSendResult);
				ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
				String ticketId = map.get("tid");
				String status = map.get("status");
				String errormsg = Constants.ruiyingErrorFailed.get(status);
				ticketSendResult.setId(ticketId);
				ticketSendResult.setLotteryType(lotteryType);
				ticketSendResult.setStatusCode(status);
				ticketSendResult.setExternalId("");
				ticketSendResult.setResponseMessage(desContent);
				ticketSendResult.setSendMessage(requestStr);
				ticketSendResult.setSendTime(new Date());
				ticketSendResult.setMessage(errormsg);
				ticketSendResult.setTerminalType(getTerminalType());

				if ("0".equals(status)) {
					ticketSendResult.setStatus(TicketSendResultStatus.success);
				} else if ("1008".equals(status)) {//
					ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
				} else if(Constants.ruiyingErrorFailed.containsKey(status)){
					ticketSendResult.setStatus(TicketSendResultStatus.unkown);
					ticketSendResult.setMessage(Constants.ruiyingErrorFailed.get(status));
				}
			}
		} else {
			processError(ticketSendResultsList, ticketBatch, ticket, TicketSendResultStatus.unkown, errorcode, 
					requestStr, desContent, "返回结果不为0,errorcode="+errorcode);
		}
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_RUIYING;
	}

}
