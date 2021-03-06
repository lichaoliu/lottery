package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.sjz.SJZConfig;
import com.lottery.ticket.vender.impl.sjz.SJZConverter;

@Component
public class SJZVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "1003";
	
	

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
			logger.error("石家庄发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		} 
        logger.error("石家庄请求内容为{},返回内容为{}",messageStr,returnStr);
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,messageStr,returnStr,venderConverter);
			} catch (Exception e) {
				logger.error("查票异常",e);
			}
		} else {
			logger.error("石家庄查票返回空");
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
					  ticketVender.setSendMessage(sendMessage);
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
						 if(StringUtils.isNotEmpty(sp)){
							 SJZConverter sjzConverter =(SJZConverter) venderConverter;
							 String peilv = sjzConverter.venderSpConvert(ticketMap.get(ticketId),sp);
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
					ticketVender.setSendMessage(sendMessage);
					ticketVender.setResponseMessage(desContent);
					venderList.add(ticketVender);
				}
			}else {
				logger.error("石家庄查票返回结果异常,发送:{},返回:{}", sendMessage, desContent);
			}
			 
		} catch (Exception e) {
			logger.error("石家庄查票处理结果异常", e);
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

	private String getElement(List<Ticket> tickets,IVenderConfig sjzConfig) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse =  SJZConfig.addHead(queryCode, sjzConfig.getAgentCode(),timestamp,messageId);
		Element bodyStr = xmlParse.getBodyElement().addElement("order");
		StringBuilder stringBuilder=new StringBuilder();
		int i=0;
		for(Ticket ticket:tickets){
			stringBuilder.append(ticket.getId());
			if(i!=tickets.size()-1){
				stringBuilder.append(",");
			}
			i++;
		}
		bodyStr.addAttribute("seqs",stringBuilder.toString());
		String des = MD5Util.toMd5(sjzConfig.getAgentCode()+sjzConfig.getKey()+timestamp+xmlParse.asBodyXML().split("<body>")[1].split("</body>")[0]);
		xmlParse.addHeaderElement("cipher", des);
		return "cmd=1003&msg="+xmlParse.asXML();
	}



	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_SJZ;
	}

}
