package com.lottery.ticket.checker.worker;

import com.google.common.annotations.GwtCompatible;
import com.lottery.common.Constants;
import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.*;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.owncenter.OwnCenterConfig;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by fengqinyun on 15/5/24.
 */
@Component
public class OwnCenterVenderTicketCheckworker extends  AbstractVenderTicketCheckWorker{
   
    private String queryCode="802";
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
			dealResult(returnStr, ticketList, venderList, venderConfig.getTerminalId(),venderConfig,messageStr,venderConverter);
		} catch (Exception e) {
			logger.error("出票中心发送:{}查票接口返回异常,return={}", messageStr,returnStr);
			logger.error("异常信息",e);
			return venderList;
		}


		return venderList;
	}

/**
 * 查票结果处理
 *
 * @param desContent
 * @return
 */
private void dealResult(String desContent, List<Ticket> ticketBatchList, List<TicketVender> venderList, Long terminalId,IVenderConfig ownerConfig,String responseMessage,IVenderConverter venderConverter) {
	try {
		   Document doc = DocumentHelper.parseText(desContent);
			Element rootElt = doc.getRootElement();
			Element el = rootElt.element("head");
			String result = el.elementText("errorcode");
			if ("0".equals(result)) {
				String bodyencode = rootElt.elementText("body");
				String body = DESCoder.desDecrypt(bodyencode, ownerConfig.getKey());
                Document bodydoc=DocumentHelper.parseText(body);
				List<?> projects=bodydoc.selectNodes("message/orderlist/order");
				Iterator it=projects.iterator();
				while(it.hasNext()){
					Element elm=(Element)it.next();

					String status=elm.elementText("errorcode");
					String ticketId=elm.elementText("orderid");

					TicketVender ticketVender = createTicketVender(terminalId, TerminalType.T_OWN_CENTER);
					ticketVender.setId(ticketId);
					ticketVender.setStatusCode(status);
					ticketVender.setSendMessage(responseMessage);
					ticketVender.setResponseMessage(desContent);
					if ("1".equals(status)) {//出票中
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("0".equals(status)) {// 成功
						ticketVender.setStatus(TicketVenderStatus.success);
					    Element tikcetList=elm.element("ticketlist");
						String sp=null;
						if (tikcetList!=null){
							List<?> spList=tikcetList.elements();
							for(Iterator its =  spList.iterator();its.hasNext();){
								Element chileEle = (Element)its.next();
								sp=chileEle.attributeValue("sp");
							}
						}
						ticketVender.setPeiLv(sp);
						String sysid=elm.elementText("sysid");
						ticketVender.setExternalId(sysid);
					} else if ("2".equals(status)) {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					} else if ("5".equals(status)) {// 票不存在
						ticketVender.setStatus(TicketVenderStatus.not_found);
					}else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					}
					venderList.add(ticketVender);

				}

		    }else{
		    	logger.error("出票中心查票处理结果异常"+Constants.ownerSendError.get(desContent));
		    }

	} catch (Exception e) {
		logger.error("原始数据为:{}",desContent);
		logger.error("出票中心查票处理结果异常", e);
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

private String getElement(List<Ticket> tickets,IVenderConfig ownerConfig) throws Exception {
	String messageId=getMessageId(ownerConfig.getAgentCode(),queryCode);
	XmlParse xmlParse =OwnCenterConfig.addOwnCenterHead(queryCode, ownerConfig.getAgentCode(),messageId);
	XmlParse xml = new XmlParse("message");
	String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
	Element body=xml.getBodyElement().addElement("orderlist");
	for(Ticket ticket:tickets){
		HashMap<String, Object> bodyAttr = new HashMap<String, Object>();
		bodyAttr.put("orderid", ticket.getId());
		xml.addBodyElement("order", bodyAttr, body);
	}
	String md5Str=queryCode+timestamp+ownerConfig.getAgentCode()+ownerConfig.getKey();
	String md5=CoreStringUtils.md5(md5Str, CharsetConstant.CHARSET_UTF8);
	String desStr=DESCoder.desEncrypt(xml.asXML(), ownerConfig.getKey());
	xmlParse.getBodyElement().setText(desStr);
	xmlParse.addRootElement("signature", md5);
	return xmlParse.asXML();
}
public String getMessageId(String agentNo,String command){

    String dateStr= CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
    return agentNo+command+dateStr;
}

@Override
protected TerminalType getTerminalType() {
	
	return TerminalType.T_OWN_CENTER;
}
}
