package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;

@Component
public class HuAiVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "102";

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
			logger.error("互爱发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}
//	   logger.error("互爱查询返回{}",returnStr);
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),messageStr,venderConverter);
			} catch (Exception e) {
				logger.error("互爱查票异常" + e);
			}
		} else {
			logger.error("互爱查票返回空");
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
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,String sendmessage,IVenderConverter venderConverter) {
		try {
			Document doc  = DocumentHelper.parseText(desContent);
            Element root=doc.getRootElement();
            String reCode=root.element("reCode").getTextTrim();
			String msg = root.element("reMessage").getText();

			if ("0".equals(reCode)) {// 成功
				 Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
				 for (Ticket ticket : ticketBatchList) {
					 ticketMap.put(ticket.getId(), ticket);
			     }
				Element el = root.element("reValue");
				for ( Iterator i = el.elementIterator("Order"); i.hasNext();) {
					Element foo = (Element) i.next();
					String ticketId=foo.attributeValue("OrderID");
					String status = foo.attributeValue("Status"); //0待出票（接单成功）,1出票中 ,2出票成功 ,3部分流单 ,4出票失败,5订单不存在
					String msgStatus =foo.attributeValue("Errmsg");
					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_HUAI);
					ticketVender.setResponseMessage(desContent);
					ticketVender.setSendMessage(sendmessage);
					ticketVender.setStatusCode(status);
					ticketVender.setMessage(msgStatus);
					if("5".equals(status)){//不存在
						ticketVender.setStatus(TicketVenderStatus.not_found);
					} else if ("0".equals(status)||"1".equals(status)) {//
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("2".equals(status)) {// 成功
						Element ticketElement=foo.element("Ticket");
                        String ticketStatus=ticketElement.attributeValue("Status");//Y 成功  W出票中  N 失败
						if("Y".equals(ticketStatus)){
							String serialId = ticketElement.attributeValue("GroundID");//落地票号
							String extendId = ticketElement.attributeValue("TicketID");//外部票号
							ticketVender.setSerialId(serialId);
							ticketVender.setExternalId(extendId);
							ticketVender.setStatus(TicketVenderStatus.success);
							String odds = ticketElement.attributeValue("Odds");
							ticketVender.setOtherPeilv(odds);
							if (StringUtils.isNotBlank(odds)) {
								Ticket ticket = ticketMap.get(ticketId);
								odds=venderConverter.convertSp(ticket,odds);
								ticketVender.setPeiLv(odds);
							}
						}else if ("W".equals(ticketStatus)){
							ticketVender.setStatus(TicketVenderStatus.printing);
						}else if("N".equals(ticketStatus)){
							ticketVender.setStatus(TicketVenderStatus.failed);
						}else{
							ticketVender.setStatus(TicketVenderStatus.failed);
						}

					} else if ("3".equals(status)||"4".equals(status)) {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					}else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					}

					ticketVender.setId(ticketId);
					venderList.add(ticketVender);
				}

			} else {
				logger.error("互爱查票返回异常,code={},msg={}", reCode, msg);
			}
		} catch (Exception e) {
			logger.error("互爱查票处理结果异常,str={}",desContent, e);
		}
	}

	

	/**
	 * 查票前拼串
	 */

	private String getElement(List<Ticket> ticketList, IVenderConfig haConfig) {
		// 头部
		StringBuffer stringBuffer=new StringBuffer();
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		stringBuffer.append("exAgent=").append(haConfig.getAgentCode())
		.append("&exAction=").append(queryCode).append("&exMsgID="+messageId);
		
		StringBuilder  exParam=new StringBuilder();
		exParam.append("OrderID=");
		int i=0;
		for(Ticket ticket:ticketList){
			exParam.append(ticket.getId());
			if(i!=ticketList.size()-1){
				exParam.append("|");
			}
			i++;
		}
		try{
			String signMsg=MD5Util.toMd5(haConfig.getAgentCode()+queryCode+messageId+exParam.toString()+haConfig.getKey());
			stringBuffer.append("&exParam=").append(exParam.toString()).append("&exSign=").append(signMsg);
			return stringBuffer.toString();
		}catch(Exception e){
			logger.error("加密出错",e);
			return null;
		}
		
	}

	
	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_HUAI;
	}
	
	
}
