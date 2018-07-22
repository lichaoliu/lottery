package com.lottery.ticket.sender.worker.vender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.lottery.ticket.sender.TicketSendUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketSendResult;
import com.lottery.common.contains.ticket.TicketSendResultStatus;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.zhangyi.EntryUtil;
import com.lottery.ticket.vender.impl.zhangyi.ZYConverter;
import com.lottery.ticket.vender.impl.zhangyi.ZYLotteryDef;

import net.sf.json.JSONObject;
@Component
public class ZYVenderTicketSendWorker extends AbstractVenderTicketSendWorker {
	private final Logger caidaolog= LoggerFactory.getLogger("caidao-warn");
	private String betCode = "13005";

	@Override
	protected List<TicketSendResult> executePerSend(TicketBatch ticketBatch, List<Ticket> ticketList, LotteryType lotteryType,IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketSendResult> ticketSendResultList = new ArrayList<TicketSendResult>();
		// 分配送票
		String messageStr = "";
		String returnStr = "";
		try {
		
			ZYConverter zyConverter = (ZYConverter) getVenderConverter();
			String lotno = zyConverter.convertLotteryType(lotteryType);
			String phase = zyConverter.convertPhase(lotteryType, ticketBatch.getPhase());
			messageStr = getElement(ticketList, lotno, phase, venderConfig, lotteryType, zyConverter);
			returnStr = HTTPUtil.postJson(venderConfig.getRequestUrl(), messageStr);
			logger.error("请求内容为{},返回内容为{}",messageStr,returnStr);
			dealResult(messageStr, returnStr, ticketBatch, ticketList, ticketSendResultList, lotteryType,venderConfig);
			return ticketSendResultList;
		} catch (Exception e) {
			logger.error("掌奕送票异常",e);
			processError(ticketSendResultList, ticketBatch, ticketList, convertResultStatusFromException(e), null, messageStr, returnStr, e.getMessage());
			return ticketSendResultList;
		} 
	}

	/**
	 * 送票结果处理
	 * 
	 * @param desContent

	 * @throws Exception
	 */
	protected void dealResult(String requestStr, String desContent, TicketBatch ticketBatch, List<Ticket> tickets, List<TicketSendResult> ticketSendResultsList, LotteryType lotteryType,IVenderConfig zyConfig) throws Exception {
       
        JSONObject map =JSONObject.fromObject(desContent);
        String entrystr=EntryUtil.decrypt(map.get("msg").toString(),zyConfig.getKey());
        JSONObject returnMsg=JSONObject.fromObject(entrystr);
        
        String returnCode=String.valueOf(returnMsg.get("errorcode"));
        for (Ticket ticket : tickets) {
			TicketSendResult ticketSendResult = new TicketSendResult();
			ticketSendResultsList.add(ticketSendResult);
			ticketSendResult.setTerminalId(ticketBatch.getTerminalId());
			ticketSendResult.setId(ticket.getId());
			ticketSendResult.setLotteryType(lotteryType);
			ticketSendResult.setStatusCode(returnCode);
			ticketSendResult.setExternalId("");
			ticketSendResult.setResponseMessage(desContent+","+returnMsg.toString());
			ticketSendResult.setSendMessage(requestStr);
			ticketSendResult.setSendTime(new Date());
			ticketSendResult.setMessage(returnMsg.get("errormsg").toString());
			ticketSendResult.setTerminalType(getTerminalType());
			if ("0".equals(returnCode)) {
				ticketSendResult.setStatus(TicketSendResultStatus.success);
			} else if (returnCode.equals("10032")) {
				ticketSendResult.setStatus(TicketSendResultStatus.duplicate);
			}else {
				ticketSendResult.setStatus(TicketSendResultStatus.unkown);
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
	 */

	public String getElement(List<Ticket> tickets, String lotteryNo, String phase, IVenderConfig zyConfig, LotteryType lotteryType, ZYConverter zyConverter) throws Exception{
		try {

			List<Object> dataList=new ArrayList<Object>();
			JSONObject message = new JSONObject();
			JSONObject header = new JSONObject();
			JSONObject msg = new JSONObject();
			header.put("messengerid",idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS));
			header.put("timestamp",DateUtil.format("yyyyMMddHHmmss", new Date()));
			header.put("transactiontype",betCode);
			header.put("des","1");
			header.put("agenterid",zyConfig.getAgentCode());
			message.put("header", header);
			TicketSendUser ticketSendUser=getDefaultUser();
			for(Ticket ticket:tickets){
				JSONObject map = new JSONObject();
				Double	amount = ticket.getAmount().doubleValue();
				map.put("id", ticket.getId());
				map.put("lotteryid",lotteryNo);
				map.put("issue",phase);
				if (ticket.getAddition() == 1) {// 大乐透追加
					map.put("childtype", "1");
				}else if(LotteryType.CJDLT.getValue()==lotteryType.getValue()&&ticket.getAddition() == 0){
					map.put("childtype","0");
				}else if(PlayType.SD_KLPK3_MBX.getValue()==ticket.getPlayType()){//山东扑克包选
					map.put("childtype", ZYLotteryDef.getChildType(ticket));
				}else{
					map.put("childtype", ZYLotteryDef.playCodeMap.get(ticket.getPlayType()));
				}
				if (LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)) {
					map.put("saletype","0");
				}else{
					map.put("saletype", zyConverter.getPlayTypeMap().get(ticket.getPlayType()));
				}
				if(PlayType.SD_KLPK3_MBX.getValue()==ticket.getPlayType()){
					map.put("lotterycode",ZYLotteryDef.getContent(ticket));
				}else{
					map.put("lotterycode",zyConverter.convertContent(ticket));
				}
				map.put("appnumbers",ticket.getMultiple());
				if (ticket.getAddition() == 1) {// 大乐透追加
					map.put("lotterynumber",amount.intValue() / ticket.getMultiple() / 300 + "");
				} else {
					map.put("lotterynumber",amount.intValue() / ticket.getMultiple() / 200 + "");
				}
				map.put("lotteryvalue",ticket.getAmount().intValue());
				map.put("mobile", ticketSendUser.getPhone());
				dataList.add(map);
			}
			
			msg.put("ticketlist", dataList);
			logger.error("请求内容为{}",msg.toString());
			String msgstr = EntryUtil.encrypt(msg.toString(),zyConfig.getKey());		
			message.put("msg",msgstr);
			return message.toString();
		} catch (Exception e) {
			logger.error("送票拼串异常", e);
		}
		return null;
	}


	protected  String getPhone(){
		List<String> list=new ArrayList<String>();
		list.add("18600758958");
		list.add("15901528135");
		list.add("13910774320");
		list.add("15810032394");
		list.add("18810659688");
		list.add("15810562342");
		list.add("13301177133");
		Collections.shuffle(list);
		return list.get(0);
	}

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_ZY;
	}
	
	
	
}
