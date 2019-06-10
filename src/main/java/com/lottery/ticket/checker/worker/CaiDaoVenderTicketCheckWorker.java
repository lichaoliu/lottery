package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.HighFrequencyLottery;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;

import net.sf.json.JSONObject;

@Component
public class CaiDaoVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {


	private final Logger caidaolog= LoggerFactory.getLogger("caidao-warn");
	private String queryCode = "13009";

	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.postJson(venderConfig.getRequestUrl(), messageStr);
			caidaolog.error("彩岛查票发送:{},返回:{}", messageStr, returnStr);
		} catch (Exception e) {
			logger.error("彩岛发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,messageStr,venderConverter);
			} catch (Exception e) {
				logger.error("彩岛查票处理异常" , e);
			}
		} else {
			logger.error("彩岛查票返回空,请求数据为{}",messageStr);
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig zyConfig,String sendMessage,IVenderConverter venderConverter) {
		try {
			   JSONObject map =JSONObject.fromObject(desContent);
		       String requestStr=map.get("msg").toString();
		       JSONObject returnMsg=JSONObject.fromObject(requestStr);
		       String returnCode=String.valueOf(returnMsg.get("errorcode"));
		       
			if ("0".equals(returnCode)) {// 成功
					Iterator<JSONObject> ticketList=returnMsg.getJSONArray("ticketlist").iterator();
					if(ticketList==null||ticketList.hasNext()==false){
						for(Ticket ticket:ticketBatchList){
							TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_ZY);
							ticketVender.setId(ticket.getId());
							ticketVender.setStatusCode("");
							ticketVender.setMessage("");
							ticketVender.setExternalId("");
							ticketVender.setSendMessage(sendMessage);
							ticketVender.setResponseMessage(desContent);
							ticketVender.setStatus(TicketVenderStatus.failed);
							venderList.add(ticketVender);
						}
					}else{
						Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
						for (Ticket ticket : ticketBatchList) {
							ticketMap.put(ticket.getId(), ticket);
						}
						while(ticketList.hasNext()){
							JSONObject jObject=ticketList.next();
							String status = String.valueOf(jObject.get("status"));
							String odds= jObject.get("spinfo")!=null?String.valueOf(jObject.get("spinfo")):"";

							String msgStatus =String.valueOf(jObject.get("failreason"));
							String ticketId =String.valueOf(jObject.get("id"));
							String externalid =String.valueOf(jObject.get("ticketid"));

							TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_ZY);
							ticketVender.setOtherPeilv(odds);
							if(StringUtils.isNotEmpty(odds)){
								odds=venderConverter.convertSp(ticketMap.get(ticketId),odds);
							}
							ticketVender.setId(ticketId);
							ticketVender.setStatusCode(status);
							ticketVender.setMessage(msgStatus);
							ticketVender.setExternalId(externalid);
							ticketVender.setPeiLv(odds);
							ticketVender.setSendMessage(sendMessage);
							ticketVender.setResponseMessage(desContent);
							if ("0".equals(status)||"1".equals(status)||"3".equals(status)||"4".equals(status)||"5".equals(status)) {
								ticketVender.setStatus(TicketVenderStatus.printing);
							} else if ("2".equals(status)) {// 成功
								ticketVender.setStatus(TicketVenderStatus.success);
								try{//为了防止检票不成功
									if(jObject.has("tickettime")&&StringUtils.isNotBlank(jObject.getString("tickettime"))){
										Date dateTime=new java.util.Date(Long.parseLong(jObject.getString("tickettime"))*1000);
										ticketVender.setPrintTime(dateTime);
									}
								}catch (Exception e){
									logger.error("处理时间出错",e);
									logger.error(desContent);
								}

							} else if ("6".equals(status)) {// 失败
								if ("200022".equals(msgStatus)||"200101".equals(msgStatus)) {//不存在
									ticketVender.setStatus(TicketVenderStatus.not_found);
									ticketVender.setMessage("票不存在");
								}else if("200009".equals(msgStatus)||"200203".equals(msgStatus)){
									if(HighFrequencyLottery.contains(ticketMap.get(ticketId).getLotteryType())){
										ticketVender.setStatus(TicketVenderStatus.ticket_limited);
										ticketVender.setMessage("限号");
									}else{
										ticketVender.setStatus(TicketVenderStatus.failed);
									}
								}else{
									ticketVender.setStatus(TicketVenderStatus.failed);
								}

							}else {
								ticketVender.setStatus(TicketVenderStatus.unkown);
							}
							venderList.add(ticketVender);
					}
				}

			} else if (Constants.zhangyiSendError.containsKey(returnCode)) {// 失败
				for (Ticket ticket : ticketBatchList) {
					logger.error("掌奕订单{}失败,失败原因{}", ticket.getId(), Constants.zhangyiSendError.get(returnCode));
				}
			} else {
				logger.error("掌奕查票返回异常,code={},msg={}", returnCode, returnMsg.get("errormsg"));
			}
		} catch (Exception e) {

			logger.error("掌奕查票处理结果异常", e);
			logger.error("处理出错:{}",desContent);
		}
	}




	private String getElement(List<Ticket> ticketList, IVenderConfig zyConfig) throws Exception {
		// 头部
		List<Object> dataList=new ArrayList<Object>();
		JSONObject message = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject msg = new JSONObject();
		header.put("messengerid",idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS));
		header.put("timestamp",DateUtil.format("yyyyMMddHHmmss", new Date()));
		header.put("transactiontype",queryCode);
		header.put("des","0");
		header.put("agenterid",zyConfig.getAgentCode());
		message.put("header", header);		
		for(Ticket ticket:ticketList){
			JSONObject map = new JSONObject();
			map.put("id", ticket.getId());
			dataList.add(map);
		}
		msg.put("ticketlist", dataList);

		message.put("msg",msg);
		return message.toString();

	}


	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_ZY;
	}

	
	

}
