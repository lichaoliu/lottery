package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConfig;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConverter;

@Component
public class JinNuoVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "1003";

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
			//logger.error("发送:{},返回:{}",messageStr,returnStr);
		} catch (Exception e) {
			logger.error("金诺发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr,messageStr,ticketList, venderList, venderConfig.getTerminalId(),messageStr,venderConverter);
			} catch (Exception e) {
				logger.error("金诺查票异常" + e);
			}
		} else {
			logger.error("金诺查票返回空");
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @param sendmessage
	 * @return
	 */
	private void dealResult(String desContent,String sendMsg,List<Ticket> ticketList, List<TicketVender> venderList, Long terminalId,String sendmessage,IVenderConverter venderConverter) {
		try {
			HashMap<String, String> mapResult = XmlParse.getElementAttribute("body/", "response", desContent);
			String errorcode = mapResult.get("code");
			if ("0".equals(errorcode)) {
				List<HashMap<String, String>> mapLists = XmlParse.getElementAttributeList("body/response/", "ticket", desContent);
				 Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
				 for (Ticket ticket : ticketList) {
				     ticketMap.put(ticket.getId(), ticket);
			     } 
				 for (int i = 0, len = mapLists.size(); i < len; i++) {
				   	  HashMap<String, String> map = mapLists.get(i);
					  String status = map.get("state");
					  String ticketId = map.get("seq");
					  String externdId = map.get("ticketid");
					 
					  TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_JINRUO);
					  ticketVender.setId(ticketId);
					  ticketVender.setStatusCode(status);
					  ticketVender.setExternalId(externdId);
					  ticketVender.setSendMessage(sendMsg);
					  ticketVender.setResponseMessage(desContent);
					  if ("2".equals(status)) {//出票中
						 ticketVender.setStatus(TicketVenderStatus.printing);
					  } else if ("1".equals(status)) {// 成功
						 ticketVender.setStatus(TicketVenderStatus.success);
						 String printtime = map.get("printtime");
						 if(StringUtils.isNotBlank(printtime)){
							 ticketVender.setPrintTime(DateUtil.parse(printtime));
						 }
						 String sp = map.get("sp");
						  ticketVender.setOtherPeilv(sp);
						 if(StringUtils.isNotBlank(sp)){
							 JinNuoConverter jinnuoConverter =(JinNuoConverter) venderConverter;
							 String peilv = jinnuoConverter.venderSpConvert(ticketMap.get(ticketId),sp);
							 ticketVender.setPeiLv(peilv);
						 }
					  } else if ("0".equals(status)||"3".equals(status)) {// 失败
						 ticketVender.setStatus(TicketVenderStatus.failed);
					  }else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					  }
					    venderList.add(ticketVender); 
				 }
			}else if("3200".equals(errorcode)){
				for (Ticket ticket : ticketList) {
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_JINRUO);
					ticketVender.setId(ticket.getId());
					ticketVender.setStatus(TicketVenderStatus.not_found);
					ticketVender.setSendMessage(sendMsg);
					ticketVender.setResponseMessage(desContent);
					venderList.add(ticketVender);
				}
			}else {
				logger.error("金诺查票返回结果异常,发送:{},返回:{}", sendMsg, desContent);
			}
			 
			
	} catch (Exception e) {
			logger.error("原始数据信息:{}",desContent);
		logger.error("金诺查票处理结果异常", e);
	}
}

	

	/**
	 * 查票前拼串
	 */

	private String getElement(List<Ticket> ticketList, IVenderConfig jnConfig) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = JinNuoConfig.addHead(queryCode, jnConfig.getAgentCode(),timestamp);
		Element element = xmlParse.addBodyElementAndAttribute("order", "", new HashMap<String, Object>());
		StringBuffer stringBuffer=new StringBuffer();
		int i=0;
		for (Ticket ticket : ticketList) {
			stringBuffer.append(ticket.getId());
			if(i!=ticketList.size()-1){
				stringBuffer.append(",");
			}
			i++;
		}
		try{
			element.addAttribute("seqs", stringBuffer.toString());
			String des = MD5Util.toMd5(jnConfig.getAgentCode()+jnConfig.getKey()+timestamp+xmlParse.getBodyElement().asXML().split("<body>")[1].split("</body>")[0],CharsetConstant.CHARSET_UTF8);
			xmlParse.addHeaderElement("cipher",des);
			return "cmd="+queryCode+"&msg="+xmlParse.asXML();
		}catch(Exception e){
			logger.error("加密出错",e);
		    return null;
		}
		
	}

	

	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_JINRUO;
	}
	
	
}
