package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.ticket.sender.TicketSendUser;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.huay.HuayConverter;
import com.lottery.ticket.vender.impl.huay.HuayLotteryDef;
import com.lottery.ticket.vender.impl.huay.HuayService;

@Component
public class HYVenderTicketSendWorker extends AbstractVenderTicketSendWorker {


	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		// 分配送票
		String messageStr = "";
		String returnStr = "";
		// 拼串

		try {
		
			HuayConverter huayConverter = (HuayConverter) getVenderConverter();

			String lotno = huayConverter.convertLotteryType(lotteryType);
			String phase = huayConverter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, huayConverter);
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("华阳送票异常",e);
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
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> ticket, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType) throws Exception {

		HashMap<String, String> mapResult = XmlParse.getElementText("body/", "oelement", desContent);
		String errorcode = mapResult.get("errorcode");
		if ("0".equals(errorcode)) {
			List<HashMap<String, String>> mapList = XmlParse.getElementTextList("body/elements/", "element", desContent);
			if (mapList != null && mapList.size() > 0) {
				for (Map<String, String> map : mapList) {
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
					String externalId = map.get("ltappid");
					String returnCode = map.get("errorcode");
					String ticketId = map.get("id");
					String errormsg = map.get("errormsg");
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
					} else if (returnCode.equals("10032")) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
					} else {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
					}
				}
			}
		} else {
			processError(ticketSendResultsList, ticketBatch, ticket, TicketSendResultStatus.unkown, errorcode, requestStr, desContent, "返回结果不为0");
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

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig huayConfig, LotteryType lotteryType, HuayConverter huayConverter) throws Exception{

		String betCode = "13005";

			String messageid = idGeneratorService.getMessageId();
			if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType) || LotteryType.getDc().contains(lotteryType)) {
				betCode = "13010";
				if (huayConfig.getPort()!=null){
					betCode="21010";
				}
			} else {

				if (huayConfig.getPort()!=null){
					betCode="21005";
				}
			}
			String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
			XmlParse xmlParse = HuayService.addHuayHead(huayConfig.getAgentCode(), betCode, messageid, timestamp);
			Element bodyeElement = xmlParse.getBodyElement();
			Element elements = bodyeElement.addElement("elements");
		TicketSendUser ticketSendUser=getDefaultUser();
			for (Ticket ticket : tickets) {
				Double amount = ticket.getAmount().doubleValue() / 100;

				HashMap<String, String> bodyAttr = new HashMap<String, String>();
				bodyAttr.put("ticketuser", ticketSendUser.getRealName());
				bodyAttr.put("identify", ticketSendUser.getIdCard());
				bodyAttr.put("phone", ticketSendUser.getPhone());
				bodyAttr.put("email", ticketSendUser.getEmail());
				bodyAttr.put("id", ticket.getId());
				bodyAttr.put("lotteryid", lotteryNo);
				if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {
					bodyAttr.put("issue", "20000");
				} else if (LotteryType.getDc().contains(lotteryType)) {
					bodyAttr.put("issue", phase.substring(1));
				} else {
					bodyAttr.put("issue", phase);
				}

				if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType) || LotteryType.getDc().contains(lotteryType)) {
					bodyAttr.put("saletype", "0");
					bodyAttr.put("childtype", HuayLotteryDef.playTypeMapJc.get(ticket.getPlayType()));
				} else if (LotteryType.getGaoPin().contains(lotteryType)) {
					bodyAttr.put("childtype", HuayLotteryDef.playTypeMapJc.get(ticket.getPlayType()));
					bodyAttr.put("saletype", huayConverter.getPlayTypeMap().get(ticket.getPlayType()));
				} else {
					if (ticket.getAddition() == 1) {// 大乐透追加
						bodyAttr.put("childtype", "1");
					} else {
						bodyAttr.put("childtype", "0");
					}
					if((ticket.getPlayType()==PlayType.d3_z3_hz.getValue()||ticket.getPlayType()==PlayType.d3_z6_hz.getValue()||ticket.getPlayType()==PlayType.d3_zhi_hz.getValue())&&getPlayType(ticket)!=null){
						bodyAttr.put("saletype", getPlayType(ticket));
					}else{
						bodyAttr.put("saletype", huayConverter.getPlayTypeMap().get(ticket.getPlayType()));
					}
				}
                if(ticket.getPlayType()==PlayType.d3_z3_hz.getValue()||ticket.getPlayType()==PlayType.d3_z6_hz.getValue()||ticket.getPlayType()==PlayType.d3_zhi_hz.getValue()){
                	String content=getContent(ticket);
                	bodyAttr.put("lotterycode",content);
                }else{
                	bodyAttr.put("lotterycode", huayConverter.convertContent(ticket));
                }
				bodyAttr.put("appnumbers", ticket.getMultiple() + "");
				if (ticket.getAddition() == 1) {// 大乐透追加
					bodyAttr.put("lotterynumber", amount.intValue() / ticket.getMultiple() / 3 + "");
				} else {
					bodyAttr.put("lotterynumber", amount.intValue() / ticket.getMultiple() / 2 + "");
				}

				bodyAttr.put("lotteryvalue", ticket.getAmount().intValue() + "");
				if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType) || LotteryType.getDc().contains(lotteryType)) {
					bodyAttr.put("betlotterymode", getChangci(ticket.getContent(), lotteryType));
				}
				Element element2 = elements.addElement("element");
				xmlParse.addBodyElement(bodyAttr, element2);
			}

			String md = MD5Util.MD5(timestamp + huayConfig.getKey() + xmlParse.getBodyElement().asXML());
			xmlParse.addHeaderElement("digest", md);
			return xmlParse.asXML();
		

	}

	private static String getContent(Ticket ticket){
		String content=ticket.getContent().split("\\-")[1].replace("^","");
		int con=Integer.parseInt(content);
		if(ticket.getPlayType()==PlayType.d3_z3_hz.getValue()){
			if(con==1){
				content="001";
			}else if(con==3){
				content="003";
			}else if(con==26){
				content="899";
			}else if(con==24){
				content="699";
			}else{
				content=con+"";
			}
		}else if(ticket.getPlayType()==PlayType.d3_z6_hz.getValue()){
			if(con==24){
				content="789";
			}else if(con==23){
				content="689";
			}else if(con==4){
				content="013";
			}else if(con==3){
				content="012";
			}else{
				content=con+"";
			}
		}else if(ticket.getPlayType()==PlayType.d3_zhi_hz.getValue()){
			if(con==0){
				content="0*0*0";
			}else if(con==27){
				content="9*9*9";
			}else {
				content=String.valueOf(con);
			}
		}
		return content;
	}
	
	private static String getPlayType(Ticket ticket){
		String content=ticket.getContent().split("\\-")[1].replace("^","");
		int con=Integer.parseInt(content);
		if(ticket.getPlayType()==PlayType.d3_z3_hz.getValue()){
			if(con==1||con==3||con==24||con==26){
				return "3";
			}	
		}else if(ticket.getPlayType()==PlayType.d3_z6_hz.getValue()){
			if(con==3||con==4||con==23||con==24){
				return "3";
			}	
		}else if(ticket.getPlayType()==PlayType.d3_zhi_hz.getValue()){
			if(con==0||con==27){
				return "0";
			}
		}
		return null;
	}
	
	
	
	/**
	 * 竞彩场次 最小^最大
	 * 
	 * @param ticketContent
	 * @return
	 */
	public static String getChangci(String ticketContent, LotteryType lotteryType) {
		List<Long> lists = new ArrayList<Long>();
		String strs[] = StringUtils.split(ticketContent.split("-")[1], "|");
		for (String content : strs) {
			String changci = StringUtils.split(content, "(")[0];
			lists.add(Long.valueOf(StringUtils.split(changci, "*")[0]));
		}
		StringBuffer strBuffer = new StringBuffer();
		Collections.sort(lists);
		for (int i = 0, len = lists.size(); i < len; i++) {
			if (i == 0 || i == lists.size() - 1) {
				if (LotteryType.getDc().contains(lotteryType)) {
					strBuffer.append(lists.get(i));
				} else if (LotteryType.getJclq().contains(lotteryType)) {
					strBuffer.append(lists.get(i).toString().substring(0, 8) + "-" + lists.get(i).toString().substring(8, 11));
				} else {
					strBuffer.append(lists.get(i).toString().substring(2, 8) + "-" + lists.get(i).toString().substring(8, 11));
				}
			}
			if (i == 0) {
				strBuffer.append("^");
			}
			if (lists.size() == 1) {
				if (LotteryType.getDc().contains(lotteryType)) {
					strBuffer.append(lists.get(i));
				} else if (LotteryType.getJclq().contains(lotteryType)) {
					strBuffer.append(lists.get(i).toString().substring(0, 8) + "-" + lists.get(i).toString().substring(8, 11));
				} else {
					strBuffer.append(lists.get(i).toString().substring(2, 8) + "-" + lists.get(i).toString().substring(8, 11));
				}

			}

		}
		return strBuffer.toString();
	}

	@Override
	protected TerminalType getTerminalType() {

		return TerminalType.T_HY;
	}

}
