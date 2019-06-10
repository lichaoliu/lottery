package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.sjz.SJZConfig;
import com.lottery.ticket.vender.impl.sjz.SJZLotteryDef;

@Component
public class SJZVenderTicketSendWorker extends AbstractVenderTicketSendWorker {



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
				logger.error("石家庄请求内容为{}，返回为{}",messageStr,returnStr);
				dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType, venderConfig);
				return ticketSendResultList;
			} catch (Exception e) {
				processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
				logger.error("石家庄送票处理错误", e);

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
		
			List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/response/order/", "ticket", desContent);
			if (mapList != null && mapList.size() > 0) {
				for (Map<String, String> map : mapList) {
					TicketSendResult ticketSendResult = new TicketSendResult();
					ticketSendResultsList.add(ticketSendResult);
					ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
					String returnCode = map.get("code");
					String ticketId = map.get("seq");
					String desc = map.get("msg");
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
					}  else if (returnCode.equals("8302")||returnCode.equals("8303")) {
						ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
						ticketSendResult.setMessage("票已存在");
					}else {
						ticketSendResult.setStatus(TicketSendResultStatus.failed);
						ticketSendResult.setMessage(desc);
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

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig sjzConfig, LotteryType lotteryType, IVenderConverter shcpConverter) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		String betCode = "2001";
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse =  SJZConfig.addHead(betCode, sjzConfig.getAgentCode(),timestamp,messageId);
		
		Element bodyStr = null;
		if (!LotteryType.getJclq().contains(lotteryType)&&!LotteryType.getJczq().contains(lotteryType)) {
			bodyStr=xmlParse.getBodyElement().addElement("order").addAttribute("lotteryid", lotteryNo).addAttribute("issue", phase);
		}else{
			bodyStr=xmlParse.getBodyElement().addElement("order").addAttribute("lotteryid", lotteryNo);
		}
		
		String content=null;
		for (Ticket ticket : tickets) {
		   String playType=SJZLotteryDef.playMap.get(ticket.getPlayType());
		   String betType=SJZLotteryDef.playTypeMap.get(ticket.getPlayType());
    	   Double amount = ticket.getAmount().doubleValue();
    		if (LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)){
    			content=playType+"_05"+"_"+shcpConverter.convertContent(ticket)+"_"+ticket.getMultiple()+"_"+amount.intValue();
    		}else{
    			content=playType+"_"+betType+"_"+shcpConverter.convertContent(ticket)+"_"+ticket.getMultiple()+"_"+amount.intValue();
    		}
    	   
    	   bodyStr.addElement("ticket").addAttribute("seq", ticket.getId()).addText(content);
		}
		logger.error("内容{}",xmlParse.asBodyXML().split("<body>")[1].split("</body>")[0]);
		String des = MD5Util.toMd5(sjzConfig.getAgentCode()+sjzConfig.getKey()+timestamp+xmlParse.asBodyXML().split("<body>")[1].split("</body>")[0]);
		xmlParse.addHeaderElement("cipher", des);
		
		return "cmd=2001&msg="+xmlParse.asXML();
	}
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_SJZ;
	}

	

}
