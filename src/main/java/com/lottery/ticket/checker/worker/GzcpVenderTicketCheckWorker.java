package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.gzcp.GzcpConfig;
import com.lottery.ticket.vender.impl.gzcp.GzcpDes;
import com.lottery.ticket.vender.notice.impl.Gzcp.GzcpUtil;

@Component
public class GzcpVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

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
			returnStr = HTTPUtil.post(venderConfig.getRequestUrl(), messageStr ,CharsetConstant.CHARSET_UTF8);
		} catch (Exception e) {
			logger.error("广州发送:{}查票接口返回异常", messageStr, e);
			return venderList;
		} 

		if (StringUtils.isNotEmpty(returnStr)) {
			// 查票处理结果
			try {
				dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,messageStr,returnStr,venderConverter);
			} catch (Exception e) {
				logger.error("查票异常",e);
			}
		} else {
			logger.error("广州查票返回空");
		}
		return venderList;
	}

	/**
	 * 查票结果处理
	 * 
	 * @param desContent
	 * @return
	 */
	private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig venderConfig,String sendMessage,String responseMessage,IVenderConverter venderConverter) {
		try {
			    if(desContent.length()>2){
			    	Document doc = DocumentHelper.parseText(desContent);
					Element rootElt = doc.getRootElement();
					Element el = rootElt.element("head");
					String result = el.elementText("result");
					if ("0".equals(result)) {
						String bodyencode = rootElt.elementText("body");
						String body = GzcpDes.des3DecodeCBC(venderConfig.getKey(), bodyencode);

			    	    List<HashMap<String, String>> mapLists = XmlParse.getElementTextList("records/", "record", body);
			    	    Document document = DocumentHelper.parseText(body);
			    		Element root = document.getRootElement();
			    		Element records = root.element("records");
			    		List<Element> list = records.elements("record");
					    Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
					    for (Ticket ticket : ticketBatchList) {
						    ticketMap.put(ticket.getId(), ticket);
				    	}
				    	for (int i = 0, len = mapLists.size(); i < len; i++) {
					   	  HashMap<String, String> map = mapLists.get(i);
						  String status = map.get("result");
						  String ticketId = map.get("id");
						  String externdId = map.get("ticketId");
						  TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_GZCP);
						  ticketVender.setId(ticketId);
						  ticketVender.setStatusCode(status);
						  ticketVender.setExternalId(externdId);
						  ticketVender.setSendMessage(sendMessage);
							ticketVender.setResponseMessage(responseMessage);
						  if ("200021".equals(status)) {//出票中
							 ticketVender.setStatus(TicketVenderStatus.printing);
						  } else if ("0".equals(status)) {// 成功
							 ticketVender.setStatus(TicketVenderStatus.success);
							 Ticket ticket = ticketMap.get(ticketId);
							 String info=null;
							  if(list.get(i).element("info")!=null){
								  info= list.get(i).element("info").asXML();
								  GzcpUtil gzcpUtil=new GzcpUtil();
								  String peilv=gzcpUtil.convertPeilu(info,LotteryType.get(ticket.getLotteryType()),ticket.getContent(),venderConverter);
								  ticketVender.setPeiLv(peilv);
							  }
							  ticketVender.setPrintTime(new Date());
						  } else if ("200020".equals(status)) {// 失败
							 ticketVender.setStatus(TicketVenderStatus.failed);
						  } else if ("200022".equals(status)) {// 票不存在
							 ticketVender.setStatus(TicketVenderStatus.not_found);
						  }else {
							ticketVender.setStatus(TicketVenderStatus.unkown);
						  }
						    venderList.add(ticketVender);
					}
				  }
			    }else{
			    	logger.error("广州查票处理结果异常{}",desContent);
			    }
				
		} catch (Exception e) {
			logger.error("广州查票处理结果异常", e);
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

	private String getElement(List<Ticket> tickets,IVenderConfig gzConfig) throws Exception {
		LotteryType lotteryType=LotteryType.get(tickets.get(0).getLotteryType());
		if (LotteryType.getJclq().contains(lotteryType)||LotteryType.getJczq().contains(lotteryType)){
			queryCode="1020";
		}
		String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
		XmlParse xmlParse = GzcpConfig.addGzcpHead(queryCode,gzConfig.getAgentCode(),messageId);
		XmlParse xml=new XmlParse("body");
		Element body=xml.getBodyElement().addElement("records");
		for(Ticket ticket:tickets){
			HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
			bodyAttr.put("id", ticket.getId());
			xml.addBodyElement("record", bodyAttr, body);
		}
		String des = GzcpDes.des3EncodeCBC(gzConfig.getKey(), "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml.getBodyElement().asXML());
		String md = MD5Util.toMd5(des);
		xmlParse.addHeaderElement("md", md);
		xmlParse.getBodyElement().setText(des);
		return xmlParse.asXML();
	}



	@Override
	protected TerminalType getTerminalType() {
		
		return TerminalType.T_GZCP;
	}

}
