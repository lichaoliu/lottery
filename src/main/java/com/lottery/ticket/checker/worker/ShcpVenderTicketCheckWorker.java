package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lottery.common.util.CoreDateUtils;
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
import com.lottery.ticket.vender.impl.shcp.SHCPConfig;

@Component
public class ShcpVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "20008";

	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		List<TicketVender> venderList = new ArrayList<TicketVender>();
		String lotno = venderConverter.convertLotteryType(ticketList.get(0).getLotteryType());
		String messageStr = getElement(ticketList, venderConfig,lotno);
		if (StringUtils.isEmpty(messageStr)) {
			return venderList;
		}
		String returnStr = "";
		try {
			returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), messageStr);
		} catch (Exception e) {
			logger.error("查票接口返回异常",e);
			logger.error("请求数据:{}",messageStr);
			return venderList;
		} 

		if (StringUtils.isEmpty(returnStr)) {
			logger.error("查票返回空");
			return venderList;
		}
		// 查票处理结果
		try {
		    dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,venderConverter);
		} catch (Exception e) {
			logger.error("处理查票结果异常",e);
			logger.error("returnstr={}",returnStr);
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig venderConfig,IVenderConverter venderConverter) {
		try {
			List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("body/rows/", "row", desContent);
			if (mapList != null && mapList.size() > 0) {
				 Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
				 for (Ticket ticket : ticketBatchList) {
					 ticketMap.put(ticket.getId(), ticket);
			     }
				for (Map<String, String> map : mapList) {
					String status = map.get("state");
					String extendId = map.get("tid");
					String msgStatus = map.get("tdesc");
					String ticketId = map.get("apply");
					String printTime = map.get("tdate");
					String tcode = map.get("tcode");
					String odds = map.get("memo");
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_SHCP);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setPrintTime(DateUtil.parse(printTime));
					ticketVender.setMessage(msgStatus);
					ticketVender.setResponseMessage(desContent);
					ticketVender.setExternalId(extendId);
					if ("1".equals(status)||("0".equals(status))) {//出票中
						 ticketVender.setStatus(TicketVenderStatus.printing);
					 } else if ("2".equals(status)&&"0".equals(tcode)) {// 成功
					    ticketVender.setStatus(TicketVenderStatus.success);
					    Date date=new Date();
					    try{
							String tdate=map.get("tdate");
							date= CoreDateUtils.parseDateTime(tdate);
						}catch (Exception e){

						}
						ticketVender.setPrintTime(date);
						ticketVender.setOtherPeilv(odds);
						if (StringUtils.isNotBlank(odds)) {
							Ticket ticket = ticketMap.get(ticketId);
							odds=venderConverter.convertSp(ticket,odds);
							ticketVender.setPeiLv(odds);
						}
					 }else if ("2".equals(status)&&"1".equals(tcode)) {// 限号撤单
						    ticketVender.setStatus(TicketVenderStatus.failed);
							ticketVender.setPrintTime(new Date());
					 }else if ("3".equals(status)) {// 票不存在
						 ticketVender.setStatus(TicketVenderStatus.not_found);
					 }else if ("4".equals(status)) {// 查询异常
						 ticketVender.setStatus(TicketVenderStatus.printing);
					 }else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					 }
				    venderList.add(ticketVender);
			}		
		 }
		} catch (Exception e) {
			logger.error("查票处理结果异常", e);
			logger.error("descontent={}",desContent);
		}
	}

	
	/**
	 * 查票前拼串
	 * 
	 * @param tickets
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */

	private String getElement(List<Ticket> tickets,IVenderConfig shcpConfig,String lotteryNo) throws Exception {
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		XmlParse xmlParse =  SHCPConfig.addShcpHead(queryCode, shcpConfig.getAgentCode(),messageId);
		Element element = xmlParse.addBodyElementAndAttribute("query", "", new HashMap<String, Object>());
		StringBuffer stringBuffer=new StringBuffer();
   		int i=0;
   		for (Ticket ticket : tickets) {
   			stringBuffer.append(ticket.getId());
   			if(i!=tickets.size()-1){
   				stringBuffer.append(",");
   			}
   			i++;
   		}
   		element.addAttribute("apply", stringBuffer.toString()).addAttribute("gid", lotteryNo);
		String des = MD5Util.toMd5(xmlParse.asXML()+shcpConfig.getKey());
		return "xml="+xmlParse.asXML()+"&sign="+des;
	}



	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_SHCP;
	}

	
	
	
}
