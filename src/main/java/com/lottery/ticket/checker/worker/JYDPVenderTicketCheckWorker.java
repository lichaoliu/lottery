package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.LotteryType;
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
import com.lottery.ticket.vender.impl.jydp.JydpConverter;
import com.lottery.ticket.vender.impl.jydp.ToolsAesCrypt;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class JYDPVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "1005";
	
	

	@Resource(name = "venderConverterBinder")
	protected Map<TerminalType, IVenderConverter> venderConverterBinder;
	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		
		String messageStr = getElement(ticketList, venderConfig);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
		} catch (Exception e) {
			logger.error("嘉优发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		} 

		if (StringUtils.isEmpty(returnStr)) {
			logger.error("嘉优查票内容{}查票返回空",messageStr);
			return null;
		}
		// 查票处理结果
		try {
			dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,messageStr,returnStr,venderConverter);
		} catch (Exception e) {
			logger.error("嘉优查票异常",e);
			logger.error("returstr={}",returnStr);
		}	
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketList, List<TicketVender> venderList, Long terminalId,IVenderConfig venderConfig,String sendMessage,String responseMessage,IVenderConverter venderConverter) {
		try {
			desContent=ToolsAesCrypt.Decrypt(desContent,venderConfig.getKey());
			JSONObject jsonObject=JSONObject.fromObject(desContent);
			String msgCode=jsonObject.getString("errorCode");

			if ("0".equals(msgCode)) {
				 Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
				 for (Ticket ticket : ticketList) {
				     ticketMap.put(ticket.getId(), ticket);
			     } 
				 JSONArray jsArray=JSONArray.fromObject(jsonObject.get("value"));
				 if(jsArray!=null){
					 for(int i=0;i<jsArray.size();i++){
						 JSONObject jsObject=jsArray.getJSONObject(i);
						 String status=jsObject.getString("status");
						 String extendId=jsObject.getString("tempid");
						 String ticketId=jsObject.getString("fromserialno");
						 TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_JYDP);
						 ticketVender.setId(ticketId);
						 ticketVender.setStatusCode(status);
						 ticketVender.setExternalId(extendId);
						 ticketVender.setResponseMessage(desContent);
						 if ("0".equals(status)) {//出票中
							 ticketVender.setStatus(TicketVenderStatus.printing);
						 } else if ("2".equals(status)) {// 成功
							 ticketVender.setStatus(TicketVenderStatus.success);
							 if (LotteryType.getJclq().contains(LotteryType.get(ticketMap.get(ticketId).getLotteryType())) || LotteryType.getJczq().contains(LotteryType.get(ticketMap.get(ticketId).getLotteryType()))) {
								 String sp=jsObject.getString("spvalue");
								 ticketVender.setOtherPeilv(sp);
								 if(StringUtils.isNotBlank(sp)){
									 JydpConverter jydpConverter =(JydpConverter) venderConverter;
									 String peilv = jydpConverter.venderSpConvert(ticketMap.get(ticketId),sp);
									 ticketVender.setPeiLv(peilv);
								 }
							 }
							 String print=jsObject.getString("printtime");
							 if(StringUtils.isNotBlank(print)&&!print.equals("0")){
								 Date printtime= CoreDateUtils.parseDate(print,CoreDateUtils.DATE_YYYYMMDDHHMMSS);
								 ticketVender.setPrintTime(printtime);
							 }
						 } else if ("-2".equals(status)) {// 失败
							 ticketVender.setStatus(TicketVenderStatus.failed);
						 }else if ("404".equals(status)) {// 不存在
							 ticketVender.setStatus(TicketVenderStatus.not_found);
						 }else {
							 ticketVender.setStatus(TicketVenderStatus.unkown);
						 }
						 venderList.add(ticketVender);
					 }
				 }
				}else if("9999".equals(msgCode)){
					for (Ticket ticket : ticketList) {
						TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_JYDP);
						ticketVender.setId(ticket.getId());
						ticketVender.setStatus(TicketVenderStatus.not_found);
						ticketVender.setSendMessage(sendMessage);
						ticketVender.setResponseMessage(desContent);
						venderList.add(ticketVender);
					}
				}else {
					logger.error("嘉优查票返回结果异常,发送:{},返回:{}", sendMessage, desContent);
				}
				
		} catch (Exception e) {
			logger.error("嘉优查票处理结果异常", e);
		}
	}

	
	/**
	 * 查票前拼串
	 * 
	 * @param tickets
	 *            票集合
	 * @param gzConfig
	 *            配置
	 * @return
	 * @throws Exception 

	 */

	private String getElement(List<Ticket> tickets,IVenderConfig jydpConfig) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		JSONObject jsObject=new JSONObject();
		jsObject.put("command",queryCode);
		jsObject.put("messageid",messageId);
		jsObject.put("timestamp", timestamp);
		jsObject.put("userid", jydpConfig.getAgentCode());
		JSONObject bodyAttr = new JSONObject();
		StringBuffer tiBuffer=new StringBuffer();
		int i=0;
		for(Ticket ticket:tickets){
			tiBuffer.append(ticket.getId());
			if(i!=tickets.size()-1){
				tiBuffer.append(";");
			}
			i++;
		}
		bodyAttr.put("fromserialnos",tiBuffer.toString());
		jsObject.put("params", bodyAttr);
		return "body="+ToolsAesCrypt.Encrypt(jsObject.toString(),jydpConfig.getKey());
	}


	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_JYDP;
	}

}
