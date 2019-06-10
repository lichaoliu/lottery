package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

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
import com.lottery.ticket.vender.impl.zch.ZchConfig;
import com.lottery.ticket.vender.impl.zch.ZchLotteryDef;


public class ZCHVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "001";

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();

		for (Ticket ticket : ticketList) {
			TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
			ticketSendResultList.add(ticketSendResult);
			String messageStr = "";
			String returnStr = "";
			try {

				IVenderConverter zchConverter = getVenderConverter();
				String lotno = zchConverter.convertLotteryType(lotteryType);
				String phase = "";
				if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
					phase = ticket.getPhase();
				} else {
					phase = zchConverter.convertPhase(lotteryType, ticketBatch.getPhase());
				}
				messageStr = getElement(ticket, lotno, phase, venderConfig, lotteryType, zchConverter);
				ticketSendResult.setSendMessage(messageStr);

				ticketSendResult.setSendTime(new Date());
				returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
				ticketSendResult.setResponseMessage(returnStr);
				Map<String, String> map = XmlParse.getElementAttribute("body/", "response", returnStr);
				String code = map.get("code");
				String msg = map.get("msg");

				ticketSendResult.setStatusCode(code);
				ticketSendResult.setMessage(msg);
				if ("0000".equals(code)) {
					ticketSendResult.setStatus(TicketSendResultStatus.success);
					ticketSendResult.setSendTime(new Date());
					dealResult(returnStr, ticketSendResult);
				} else if ("8104".equals(code) || "8121".equals(code)) {// 票号重复
					ticketSendResult.setStatus(TicketSendResultStatus.duplicate);

				} else if (Constants.zchSendErrorFailed.containsKey(code)) {
					ticketSendResult.setStatus(TicketSendResultStatus.failed);
				} else if (Constants.zchSendError.containsKey(code)) {
					ticketSendResult.setMessage(Constants.zchSendError.get(code));
					ticketSendResult.setStatus(TicketSendResultStatus.failed);
				} else {
					ticketSendResult.setStatus(TicketSendResultStatus.unkown);
				}

			} catch (Exception e) {
				ticketSendResult.setStatus(TicketSendResultStatus.unkown);
				ticketSendResult.setStatus(convertResultStatusFromException(e));
				ticketSendResult.setMessage(e.getMessage());
				ticketSendResult.setSendMessage(messageStr);
				ticketSendResult.setResponseMessage(returnStr);
				logger.error("中彩送票处理错误", e);
			}

		}
		return ticketSendResultList;
	}

	/**
	 * 送票结果处理
	 * 
	 * @param desContent
	 * @param count
	 * @return
	 */
	protected boolean dealResult(String returnStr, TicketSendResult ticketSendResult) {
		try {
			Map<String, String> mapResult = XmlParse.getElementAttribute("body/response/order/", "ticket", returnStr);
			if (mapResult != null) {

				String externalId = mapResult.get("sysId");
				String ticketId = mapResult.get("ticketId");
				if (!ticketId.equals(ticketSendResult.getId())) {
					logger.error("送票返回票号" + ticketId + "与原票号" + ticketSendResult.getId() + "不一致");
					return false;
				}
				ticketSendResult.setExternalId(externalId);
			}
		} catch (Exception e) {
			logger.error("送票处理结果异常", e);
			return false;
		}
		return true;
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

	public String getElement(Ticket ticket, String lotteryNo, String phase, IVenderConfig zchConfig, LotteryType lotteryType, IVenderConverter zchConverter) {
		String md = "";
		XmlParse xmlParse = null;
		try {
			Double amount = ticket.getAmount().doubleValue() / 100;
			xmlParse = ZchConfig.addZchHead(zchConfig.getAgentCode(), betCode);
			String userNo = "123456";
			UserInfo userInfo = userInfoService.get(userNo);
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("userInfo", userInfo.getRealName() + "$" + userInfo.getIdcard() + "$" + userInfo.getPhoneno().trim());
			bodyAttr.put("lotteryId", lotteryNo);
			bodyAttr.put("issue", phase);
			bodyAttr.put("orderId", ticket.getId() + "");
			HashMap<String, Object> bodyAttr2 = new HashMap<String, Object>();
			bodyAttr2.put("ticketId", ticket.getId());
			bodyAttr2.put("amount", amount.intValue());
			bodyAttr2.put("multiple", ticket.getMultiple() + "");
			if (ticket.getAddition() == 1) {// 大乐透追加
				bodyAttr2.put("item", amount.intValue() / ticket.getMultiple() / 3 + "");
				bodyAttr.put("playId", "02");
			} else {
				bodyAttr2.put("item", amount.intValue() / ticket.getMultiple() / 2 + "");
				bodyAttr.put("playId", ZchLotteryDef.playCodeMap.get(ticket.getPlayType()));
			}
			String selectType = "";
			if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
				selectType = "00";
			} else {
				selectType = zchConverter.convertPlayType(ticket.getPlayType() + "");
			}
			bodyAttr2.put("pollId", selectType);

			Element attributeMap2 = xmlParse.addBodyElementAndAttribute("order", "", bodyAttr);
			xmlParse.addBodyElementAndAttribute("ticket", zchConverter.convertContent(ticket), bodyAttr2, attributeMap2);
			try {
				md = MD5Util.toMd5(zchConfig.getAgentCode() + zchConfig.getKey() + xmlParse.getBodyElement().asXML());
			} catch (Exception e) {
				logger.error("加密异常" + e.getMessage());
			}
			xmlParse.addHeaderElement("digest", md);
			return "msg=" + xmlParse.asXML();
		} catch (Exception e) {
			logger.error("送票拼串异常", e);
		}
		return null;
	}

	@Override
	protected TerminalType getTerminalType() {

		return TerminalType.T_ZCH;
	}

}
