package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.anrui.AnruiConverter;

@Component
public class AnRuiVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private String queryCode = "205";

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
			logger.error("安瑞发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		}
		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConverter);
			} catch (Exception e) {
				logger.error("安瑞查票异常" + e);
			}
		} else {
			logger.error("安瑞查票返回空");
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @param count
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConverter venderConverter) {
		try {
			    if(StringUtils.isNotBlank(desContent)){
			    	List<HashMap<String, String>> mapLists = XmlParse.getElementAttributeList("/", "PrintedTicket", desContent);
					Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
					for (Ticket ticket : ticketBatchList) {
						ticketMap.put(ticket.getId(), ticket);
					}
					for (int i = 0, len = mapLists.size(); i < len; i++) {
						HashMap<String, String> map = mapLists.get(i);
						String status = map.get("TicketStatus");
						String ticketId = "TY"+map.get("ChannelTicketID");
						String externdId = map.get("TicketNo");
						String odds= map.get("ODDS");
						logger.error("odds==="+odds);
						TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_ARUI);
						
						ticketVender.setId(ticketId);
						ticketVender.setStatusCode(status);
						ticketVender.setExternalId(externdId);
						if ("0".equals(status)) {//未处理
							ticketVender.setStatus(TicketVenderStatus.printing);
						} else if ("1".equals(status)) {//出票中
							ticketVender.setStatus(TicketVenderStatus.printing);
						} else if ("2".equals(status)) {// 成功
							ticketVender.setStatus(TicketVenderStatus.success);
							Ticket ticket = ticketMap.get(ticketId);
							AnruiConverter qhConverter =(AnruiConverter) venderConverter;
							String peilv = qhConverter.convertodd(ticket,odds);
							ticketVender.setPeiLv(peilv);
							ticketVender.setPrintTime(new Date());
						} else if ("-1".equals(status)) {// 失败
							ticketVender.setStatus(TicketVenderStatus.failed);
						} else {
							ticketVender.setStatus(TicketVenderStatus.unkown);
						}
						venderList.add(ticketVender);
					}
			    }else{
			    	logger.error("安瑞查票处理结果异常{}",desContent);
			    }
				
		} catch (Exception e) {
			logger.error("安瑞查票处理结果异常{}", e);
		}
	}

	
	/**
	 * 查票前拼串
	 * 
	 * @param ticketBatchList
	 *            票集合
	 * @param lotteryNo
	 *            彩种
	 * @return
	 * @throws Exception
	 */

	private String getElement(List<Ticket> ticketBatchList, IVenderConfig anruiConfig,String lotteryNo) {
		StringBuffer stringBuffer=new StringBuffer();
		try {
			StringBuffer ticketBuffer=new StringBuffer();
			int i=0;
			for(Ticket ticket:ticketBatchList){
				ticketBuffer.append(ticket.getId().substring(2));
				if(i<ticketBatchList.size()-1){
					ticketBuffer.append(",");
				}
					i++;
			}
			String md = "";
			try {
				md = MD5Util.toMd5(anruiConfig.getAgentCode() +lotteryNo +ticketBuffer.toString()+anruiConfig.getKey());
			} catch (Exception e) {
				logger.error("加密异常" + e.getMessage());
			}
			stringBuffer.append("transcode=").append(queryCode)
			.append("&ChannelID=").append(anruiConfig.getAgentCode())
			.append("&LotteryID=").append(lotteryNo)
			.append("&ChannelTicketID=").append(ticketBuffer.toString())
			.append("&Sign=").append(md.toUpperCase());
			logger.error("查票内容："+stringBuffer.toString());
			
		} catch (Exception e) {
			logger.error("查票拼串异常", e);
		}
		return stringBuffer.toString();
	}

	

	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_ARUI;
	}
	
	

	

}
