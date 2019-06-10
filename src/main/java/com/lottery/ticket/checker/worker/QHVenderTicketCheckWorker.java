package com.lottery.ticket.checker.worker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.HTTPUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.qihui.QHConfig;
import com.lottery.ticket.vender.impl.qihui.QHDesUtil;

public class QHVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker {

	private  String queryCode="1006";
	@Override
	public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig,IVenderConverter venderConverter) throws Exception {
		return dealResult(ticketList, venderConfig);
	}
	/**
	 * 查票结果处理
	 * @param desContent
	 * @param count
	 * @return
	 * @throws Exception 
	 */
	private List<TicketVender>  dealResult(List<Ticket> ticketList,IVenderConfig venderConfig){
		List<TicketVender> ticketvenderList = new ArrayList<TicketVender>();
		try {
		    String messageStr = getElement(ticketList,venderConfig);
		    String returnStr = HTTPUtil.post(venderConfig.getRequestUrl(),messageStr ,CharsetConstant.CHARSET_UTF8);
		    logger.error("查票内容{},查票返回{}",messageStr,returnStr);
		    Document doc = DocumentHelper.parseText(returnStr);
			Element rootElt = doc.getRootElement();
			Element el = rootElt.element("head");
			String result = el.elementText("result");
			if ("0".equals(result)) {
				String bodyencode = rootElt.elementText("body");
				String body = QHDesUtil.des3DecodeCBC(venderConfig.getKey(), bodyencode);
				List<HashMap<String, String>> mapList = XmlParse.getElementTextList("records/", "record", body);
	     
			for(HashMap<String, String> map:mapList){
				 String  msg=map.get("errormsg");
		         String status=map.get("result");
		         String externalId=map.get("ticketId");
		         String ticketId=map.get("id");
		         TicketVender ticketVender = createTicketVender(venderConfig.getTerminalId(), TerminalType.T_QH);
		         ticketVender.setId(ticketId);
				 ticketVender.setStatusCode(status);
				 ticketVender.setExternalId(externalId);
				 ticketvenderList.add(ticketVender);
	   		    if ("0".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.success);
					ticketVender.setMessage("出票成功");
					ticketVender.setPrintTime(new Date());
				} else if ("200022".equals(status)){
					ticketVender.setStatus(TicketVenderStatus.not_found);
					ticketVender.setMessage("票不存在"); 
				} else if ("200021".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.printing);
					ticketVender.setMessage("出票中");
				} else if ("200020".equals(status)||"200024".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.failed);
					ticketVender.setMessage("出票失败");
				} else {
					ticketVender.setStatus(TicketVenderStatus.unkown);
					ticketVender.setMessage(msg);
				}
			  }
			}else{
				 logger.error("齐汇查票返回异常,发送:{},返回:{}",messageStr,returnStr);
			 }
		} catch (Exception e) {
			logger.error("齐汇查票处理结果异常",e);
		}
		return ticketvenderList;
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

private String getElement(List<Ticket> tickets,IVenderConfig qhConfig) {
	XmlParse xmlParse = QHConfig.addQHHead(queryCode,qhConfig.getAgentCode());
	XmlParse xml=new XmlParse("body");
	Element body=xml.getBodyElement().addElement("records");
	for(Ticket ticket:tickets){
		xml.addBodyElement("id", ticket.getId(),body);
	}
	try{
		String des=QHDesUtil.des3EncodeCBC(qhConfig.getKey(),"<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml.getBodyElement().asXML());
		String md = MD5Util.toMd5(des);
		xmlParse.addHeaderElement("md", md);
		xmlParse.getBodyElement().setText(des);
		return xmlParse.asXML();
	}catch(Exception e){
		logger.error("加密出错",e);
	    return null;
	}
	
}

@Override
protected void init() {
	venderCheckerWorkerMap.put(TerminalType.T_QH, this);
    
}
@Override
protected TerminalType getTerminalType() {
	// TODO Auto-generated method stub
	return null;
}


}
