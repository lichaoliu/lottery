package com.lottery.ticket.sender.worker.vender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.jydp.JydpLotteryDef;
import com.lottery.ticket.vender.impl.jydp.ToolsAesCrypt;

import net.sf.json.JSONObject;
@Component
public class JYDPVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private String betCode = "1001";


	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList,LotteryType lotteryType, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		 List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		 String lotno = venderConverter.convertLotteryType(lotteryType);
		 String phase = venderConverter.convertPhase(lotteryType, ticketBatch.getPhase()); 
		 for (Ticket ticket : ticketList) {
				TicketSendResult ticketSendResult = createInitializedTicketSendResult(ticket);
				ticketSendResultList.add(ticketSendResult);
				String messageStr = "";
				String returnStr = "";
				try {
 					IVenderConverter jycpConverter = getVenderConverter();
					messageStr = getElement(ticket,lotno,phase, venderConfig, lotteryType, jycpConverter);
					ticketSendResult.setSendMessage(messageStr);
					ticketSendResult.setSendTime(new Date());
					ticketSendResult.setId(ticket.getId());
					returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
					dealResult(messageStr, returnStr, ticketBatch,ticketSendResult, lotteryType,venderConfig);
				} catch (Exception e) {
					List<Ticket> allnewList=new ArrayList<Ticket>();
					allnewList.add(ticket);
					processError(ticketSendResultList, ticketBatch, allnewList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
					logger.error("嘉优送票处理错误", e);

				}
			}
		
		return ticketSendResultList;
	}

	
	/**
	 * 送票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, TicketSendResult ticketSendResult, LotteryType lotteryType,IVenderConfig venderConfig) throws Exception {
		desContent=ToolsAesCrypt.Decrypt(desContent,venderConfig.getKey());
		JSONObject jsObject=JSONObject.fromObject(desContent);
		String returnCode=jsObject.getString("errorCode");
		String returnMsg=jsObject.getString("message");
		ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
		ticketSendResult.setResponseMessage(desContent);
		ticketSendResult.setSendMessage(requestStr);
		ticketSendResult.setSendTime(new Date());
		ticketSendResult.setMessage(returnMsg);
		ticketSendResult.setTerminalType(getTerminalType());
		if("0".equals(returnCode)) {
			JSONObject jsonValue=(JSONObject) jsObject.get("value");
			String extendId=jsonValue.getString("tempid");
			ticketSendResult.setExternalId(extendId);
			ticketSendResult.setStatus(TicketSendResultStatus.success);
			ticketSendResult.setSendTime(new Date());
			ticketSendResult.setLotteryType(lotteryType);
			ticketSendResult.setStatusCode(returnCode);
		} else if ("2001".equals(returnCode)) {// 票号重复
			ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
			ticketSendResult.setMessage("网站平台交易流水号重复");
		} else if("2002".equals(returnCode)) {
			ticketSendResult.setMessage("用户不存在");
			ticketSendResult.setStatus(TicketSendResultStatus.unkown);
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
	 * @throws Exception 
	 * @throws Exception
	 */

	public String getElement(Ticket ticket, String lotteryNo, String phase, IVenderConfig jydpConfig, LotteryType lotteryType, IVenderConverter jydpConverter) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		JSONObject jsObject=new JSONObject();
		jsObject.put("command",betCode);
		jsObject.put("messageid",messageId);
		jsObject.put("timestamp", timestamp);
		jsObject.put("userid", jydpConfig.getAgentCode());
		int amount = ticket.getAmount().divide(new BigDecimal(100)).intValue();
		JSONObject jsonBody=new JSONObject();
		if (ticket.getAddition() == 1) {// 大乐透追加//注数
			jsonBody.put("record", amount/ ticket.getMultiple()/3);
		} else {
			jsonBody.put("record", amount/ ticket.getMultiple()/2);
		}
		jsonBody.put("gamecode",Integer.parseInt(lotteryNo));//彩票类型
		jsonBody.put("bets",amount);//投注金额(元)
		jsonBody.put("issue", phase);
		jsonBody.put("lastscreeningtime", Long.parseLong(DateUtil.format("yyyyMMddHHmmss",ticket.getDeadline())));
		int playType=Integer.parseInt(ticket.getContent().split("\\-")[0]);
		jsonBody.put("manner", JydpLotteryDef.playTypeMap.get(playType));//玩法类型
		if (ticket.getAddition() == 1) {// 大乐透追加//注数
			if(LotteryType.CJDLT.getValue()==ticket.getLotteryType()){
				if("1".equals(JydpLotteryDef.playTypeMap.get(playType))){
					jsonBody.put("manner","5");
				}else if("2".equals(JydpLotteryDef.playTypeMap.get(playType))){
					jsonBody.put("manner","6");
				}else if("3".equals(JydpLotteryDef.playTypeMap.get(playType))){
					jsonBody.put("manner","7");
				}
			}
		}
		
		jsonBody.put("fromserialno",ticket.getId());//票号
		jsonBody.put("chipinnums",jydpConverter.convertContent(ticket));//注码
		jsonBody.put("multiple",ticket.getMultiple());//倍数
		
		jsObject.put("params", jsonBody);
		return "body="+ToolsAesCrypt.Encrypt(jsObject.toString(),jydpConfig.getKey());
	}
	
	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_JYDP;
	}



}
