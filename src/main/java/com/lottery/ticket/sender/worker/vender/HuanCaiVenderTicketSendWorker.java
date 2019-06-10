package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.lottery.ticket.sender.TicketSendUser;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.sender.worker.vender.AbstractVenderTicketSendWorker;
import com.lottery.ticket.vender.impl.huancai.HuancaiConfig;
import com.lottery.ticket.vender.impl.huancai.HuancaiLotteryDef;

@Component
public class HuanCaiVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "1002";


	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		 List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
			
			String messageStr = "";
			String returnStr = "";
			try {
				IVenderConverter huancaiConverter = getVenderConverter();
				String lotno = huancaiConverter.convertLotteryType(lotteryType);
				String phase = huancaiConverter.convertPhase(lotteryType, ticketBatch.getPhase());
				messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, huancaiConverter);

				returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
				logger.error("送票内容为{}，返回为{}",messageStr,returnStr);
				dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
				return ticketSendResultList;

			} catch (Exception e) {
				if(StringUtils.isEmpty(returnStr)){
					returnStr=e.getMessage();
				}
				processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
				logger.error("环彩送票处理错误", e);
				return ticketSendResultList;
			}
	}

	
			protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> tickets, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType, IVenderConfig qhConfig) throws Exception {
				Map<String, String> map = XmlParse.getElementAttribute("/", "response", desContent);
				String code = map.get("code");
				String msg = map.get("msg");
				for(Ticket ticket:tickets){
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setStatusCode(code);
					ticketSendResult.setId(ticket.getId());
					ticketSendResult.setMessage(msg);
					ticketSendResult.setResponseMessage(desContent);
					ticketSendResult.setSendMessage(requestStr);
					ticketSendResult.setSendTime(new Date());
					ticketSendResult.setTerminalType(getTerminalType());
					if ("0000".equals(code)) {
						ticketSendResult.setStatus(TicketSendResultStatus.success);
						ticketSendResult.setSendTime(new Date());
					} else if ("0006".equals(code)) {// 票号重复
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
					} else if (Constants.huancaiErrorFailed.containsKey(code)) {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
					}else {
						ticketSendResult.setStatus(TicketSendResultStatus.unkown);
					}
				}
					
			}

	/**
	 * 送票前拼串
	 *
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig huancaiConfig, LotteryType lotteryType, IVenderConverter huancaiConverter) {


		try {
			String messageId=DateUtil.format("yyyyMMddHHmmss", new Date());;
			XmlParse xmlParse = HuancaiConfig.addGxHead(betCode,huancaiConfig.getAgentCode(),messageId);
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("lottid", lotteryNo);
			bodyAttr.put("issue", phase);
			Element attributeMap2 = xmlParse.addBodyElementAndAttribute("order", "", bodyAttr);
			for(Ticket ticket:tickets){
				HashMap<String, Object> bodyAttr2 = new HashMap<String, Object>();
				Double amount = ticket.getAmount().doubleValue();
				TicketSendUser ticketSendUser=getDefaultUser();
				bodyAttr2.put("ticketid", ticket.getId());
				String selectType =HuancaiLotteryDef.getPlayType(ticket);
				bodyAttr2.put("playcode", selectType);
				bodyAttr2.put("amount", amount.intValue());
				bodyAttr2.put("multiple", ticket.getMultiple() + "");
				bodyAttr2.put("name", ticketSendUser.getUserName());
				bodyAttr2.put("idcard", ticketSendUser.getIdCard());
				bodyAttr2.put("phone", ticketSendUser.getPhone());
				xmlParse.addBodyElementAndAttribute("ticket",huancaiConverter.convertContent(ticket), bodyAttr2, attributeMap2);

			}
			String md = MD5Util.toMd5(huancaiConfig.getAgentCode()+messageId + huancaiConfig.getKey() + xmlParse.getBodyElement().asXML().replace("&lt;","<").replace("&gt;",">"));
			xmlParse.addHeaderElement("digest", md);
			return  "cmd="+betCode+"&msg="+xmlParse.asXML().replace("&lt;","<").replace("&gt;",">");

		} catch (Exception e) {
			logger.error("送票拼串异常", e);
		}
		return null;
	}

	
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_HUANCAI;
	}
}
