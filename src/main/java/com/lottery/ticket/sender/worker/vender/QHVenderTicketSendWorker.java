package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lottery.common.Constants;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
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
import com.lottery.ticket.vender.impl.qihui.QHConfig;
import com.lottery.ticket.vender.impl.qihui.QHDesUtil;
import com.lottery.ticket.vender.impl.qihui.QHLotteryDef;


public class QHVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "1002";

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		

		// 分配送票
		String messageStr = "";
		String returnStr = "";
		try {
			IVenderConverter qhConverter = getVenderConverter();
			String lotno = qhConverter.convertLotteryType(lotteryType);
			String phase = qhConverter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, qhConverter);
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("齐汇送票{}返回", messageStr, e);
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
		Document doc = DocumentHelper.parseText(desContent);
		Element rootElt = doc.getRootElement();
		Element el = rootElt.element("head");
		String result = el.elementText("result");
		if ("0".equals(result)) {
			String bodyencode = rootElt.elementText("body");
			String body = QHDesUtil.des3DecodeCBC(qhConfig.getKey(), bodyencode);

			List<HashMap<String, String>> mapList = XmlParse.getElementTextList("records/", "record", body);
			if (mapList != null && mapList.size() > 0) {
				for (Map<String, String> map : mapList) {
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
					String returnCode = map.get("result");
					String ticketId = map.get("id");
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
					} else if (Constants.qhExptSendError.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.success);
					} else if (Constants.qhSendFaildError.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
						ticketSendResult.setMessage(Constants.qhSendFaildError.get(returnCode));
					} else if (Constants.qhSendError.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.unkown);
						ticketSendResult.setMessage(Constants.qhSendError.get(returnCode));
					} else if ("200021".equals(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
						ticketSendResult.setMessage("票已存在");
					}else {
						ticketSendResult.setStatus(TicketSendResultStatus.unkown);
						ticketSendResult.setMessage(returnCode);
					}

				}
			}
		} else {
			processError(ticketSendResultsList, ticketBatch, ticket, TicketSendResultStatus.unkown, null, requestStr, desContent, "返回结果不为0");
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

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig qhConfig, LotteryType lotteryType, IVenderConverter QHConverter) {

		XmlParse xmlParse = QHConfig.addQHHead(betCode, qhConfig.getAgentCode());
		String userNo = "123456";
		UserInfo userInfo = userInfoService.get(userNo);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		if (LotteryType.getJclq().contains(lotteryType)) {
			bodyzAttr.put("lotteryId", "0");
		} else if (LotteryType.getJczq().contains(lotteryType)) {
			bodyzAttr.put("lotteryId", "1");
		} else {
			bodyzAttr.put("lotteryId", lotteryNo);
		}
		bodyzAttr.put("issue", phase);
		XmlParse xml = new XmlParse("body");
		Element body = xml.addBodyElement(bodyzAttr, xml.getBodyElement()).addElement("records");

		for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			Double amount = ticket.getAmount().doubleValue();
			bodyAttr.put("id", ticket.getId());

			if (LotteryType.getJclq().contains(lotteryType)) {
				bodyAttr.put("lotterySaleId", QHLotteryDef.playTypeMap.get(Integer.parseInt(ticket.getContent().split("\\-")[0])));
				bodyAttr.put("playType", lotteryNo);
			} else if (LotteryType.getJczq().contains(lotteryType)) {
				bodyAttr.put("lotterySaleId", QHLotteryDef.playTypeMap.get(Integer.parseInt(ticket.getContent().split("\\-")[0])));
				bodyAttr.put("playType", lotteryNo);
			} else {
				bodyAttr.put("lotterySaleId", QHLotteryDef.playTypeMap.get(ticket.getPlayType()));
				bodyAttr.put("playType", "");
			}

			bodyAttr.put("userName", userInfo.getRealName());
			bodyAttr.put("phone", userInfo.getPhoneno().trim());
			bodyAttr.put("idCard", userInfo.getIdcard());
			bodyAttr.put("code", QHConverter.convertContent(ticket));
			bodyAttr.put("money", amount.intValue() + "");
			bodyAttr.put("timesCount", ticket.getMultiple() + "");
			bodyAttr.put("issueCount", "1");
			bodyAttr.put("investCount", amount.intValue() / 100 / ticket.getMultiple() / 2 + "");
			bodyAttr.put("investType", "0");
			xml.addBodyElement("record", bodyAttr, body);

		}
		
		try{
			String des = QHDesUtil.des3EncodeCBC(qhConfig.getKey(), "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml.getBodyElement().asXML());
			String md = MD5Util.toMd5(des);
			xmlParse.addHeaderElement("md", md);
			xmlParse.getBodyElement().setText(des);
			return xmlParse.asXML();
		}catch(Exception e){
			logger.error("加密出错",e);
	        return null;
		}
		

	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_QH;
	}

}
