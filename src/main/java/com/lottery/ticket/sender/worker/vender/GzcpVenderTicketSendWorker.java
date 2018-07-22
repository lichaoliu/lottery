package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gzcp.GzcpConfig;
import com.lottery.ticket.vender.impl.gzcp.GzcpDes;
import com.lottery.ticket.vender.impl.gzcp.GzcpLotteryDef;
@Component
public class GzcpVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "1000";


	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		    List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		
			String messageStr = "";
			String returnStr = "";
			try {
				
				String lotno = venderConverter.convertLotteryType(lotteryType);
				String phase = venderConverter.convertPhase(lotteryType, ticketBatch.getPhase());
				messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, venderConverter);
				returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
				
				dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
				return ticketSendResultList;
			} catch (Exception e) {
				processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
				logger.error("广州送票处理错误", e);
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
			String body = GzcpDes.des3DecodeCBC(qhConfig.getKey(), bodyencode);

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
					} else if (Constants.gzcpSendErrorFailed.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
						ticketSendResult.setMessage(Constants.gzcpSendErrorFailed.get(returnCode));
					} else if (Constants.gzcpSendError.containsKey(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.unkown);
						ticketSendResult.setMessage(Constants.gzcpSendError.get(returnCode));
					} else if ("200003".equals(returnCode)) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
						ticketSendResult.setMessage("票已存在");
					}else if("200001".equals(returnCode)){
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
						ticketSendResult.setMessage("余额不足");
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
	 * @param ticket
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig gzcpConfig, LotteryType lotteryType, IVenderConverter gzcpConverter) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		XmlParse xmlParse =  GzcpConfig.addGzcpHead(betCode, gzcpConfig.getAgentCode(),messageId);
		String userNo = "123456";
		UserInfo userInfo = userInfoService.get(userNo);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		bodyzAttr.put("lotteryId", lotteryNo);
		if (!LotteryType.getJclq().contains(lotteryType)&&!LotteryType.getJczq().contains(lotteryType)) {
		  bodyzAttr.put("issue", phase);
		}
		XmlParse xml = new XmlParse("body");
		Element body = xml.addBodyElement(bodyzAttr, xml.getBodyElement()).addElement("records");

		for (Ticket ticket : tickets) {
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			Double amount = ticket.getAmount().doubleValue();
			bodyAttr.put("id", ticket.getId());
            bodyAttr.put("lotterySaleId", GzcpLotteryDef.playTypeMap.get(Integer.parseInt(ticket.getContent().split("\\-")[0])));
            bodyAttr.put("phone", userInfo.getPhoneno().trim());
            bodyAttr.put("idCard", userInfo.getIdcard());
            bodyAttr.put("code", gzcpConverter.convertContent(ticket));
            bodyAttr.put("money", amount.intValue() + "");
            bodyAttr.put("timesCount", ticket.getMultiple() + "");//倍数
            bodyAttr.put("issueCount", "1");//期数
           
            if (ticket.getAddition() == 1) {// 大乐透追加
				 bodyAttr.put("investCount", amount.intValue() / 100 / ticket.getMultiple() / 3 + "");//注数
				 bodyAttr.put("investType", "1");//投注方式
			} else {
				 bodyAttr.put("investCount", amount.intValue() / 100 / ticket.getMultiple() / 2 + "");//注数
				 bodyAttr.put("investType", "0");//投注方式
			}
            
           
			xml.addBodyElement("record", bodyAttr, body);
		}

		String des = GzcpDes.des3EncodeCBC(gzcpConfig.getKey(), "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml.getBodyElement().asXML());
		String md = MD5Util.toMd5(des);
		xmlParse.addHeaderElement("md", md);
		xmlParse.getBodyElement().setText(des);

		return xmlParse.asXML();
	}
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_GZCP;
	}

	

}
