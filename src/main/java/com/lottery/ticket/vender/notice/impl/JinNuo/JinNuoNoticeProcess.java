package com.lottery.ticket.vender.notice.impl.JinNuo;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.MD5Util;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConfig;
import com.lottery.ticket.vender.impl.jinnuo.JinNuoConverter;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;


@Component
public class JinNuoNoticeProcess extends AbstractVenderNoticeProcess {

   private String noticeCode="3002";

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_JINRUO;
    }

	protected String readRequestString(HttpServletRequest request) throws IOException{
		return request.getParameter("msg");
	}
    protected Map<String, String> convertRequestParameter(String requestString) {
    	Map<String,String>map=new HashMap<String, String>();

		try {

			Map<String,String> requestMap= CoreHttpUtils.parseQueryString(requestString);
			Document doc = DocumentHelper.parseText(requestMap.get("msg"));
			Element rootElt = doc.getRootElement();

            Element headElt=rootElt.element("head");
			Element agentidEl=headElt.element("agentid");

			Element bodyEl=rootElt.element("body");

			map.put("msg",bodyEl.asXML());
			map.put("agentid",agentidEl.getText());
			return map;
		} catch (Exception e) {
			logger.error("解析xml异常",e);
		}
		
        return null;
    }

    @Override
    protected String getAgentId() {
        return "agentid";
    }

	@Override
	protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
		return true;
	}


	@Override
    protected String execute(Map<String, String> requestMap,IVenderConfig venderConfig) {
		String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
		XmlParse xmlParse = JinNuoConfig.addHead(noticeCode, venderConfig.getAgentCode(),timestamp);
		HashMap<String, String> bodyzAttr = new HashMap<String, String>();
		Element body = xmlParse.addBodyElement(bodyzAttr, xmlParse.getBodyElement()).addElement("response");
        //将具体信息解析
    	String exValue=requestMap.get("msg");
    	 try {


			    List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("ticket/", "body", exValue);
			
				for(Map<String,String> map:mapList){
					TicketVender ticketVender = createInit();//参照检票操作
					String status = map.get("state");
					String externalId=map.get("ticketid");
					String ticketId=map.get("seq");
					
					String msg=map.get("msg");
					ticketVender.setId(ticketId);
					ticketVender.setExternalId(externalId);
					if ("2".equals(status)) {//出票中
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("1".equals(status)) {// 成功
					    String sp=map.get("sp");
						if(StringUtils.isNotBlank(sp)){
							 JinNuoConverter jinnuoConverter =(JinNuoConverter) venderConverterBinder.get(getTerminalType());;
							 Ticket ticket = ticketService.getTicket(ticketId);
							 String peilv = jinnuoConverter.venderSpConvert(ticket,sp);
							 ticketVender.setPeiLv(peilv);
							 ticketVender.setOtherPeilv(sp);
						 }
						ticketVender.setStatus(TicketVenderStatus.success);
					} else if ("0".equals(status)) {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					}  else {
						ticketVender.setStatus(TicketVenderStatus.unkown);
					}
					ticketVender.setMessage(msg);
					ticketVender.setPrintTime(new Date());
					this.ticketVenderProcess(ticketVender);
		    			
				}
				body.addAttribute("code", "1");
		} catch (Exception e) {
			logger.error("金诺处理通知异常",e);
			body.addAttribute("code", "0");
		}
		
		try {
			String des = MD5Util.toMd5(venderConfig.getAgentCode()+venderConfig.getKey()+timestamp+xmlParse.getBodyElement().asXML().split("<body>")[1].split("</body>")[0],CharsetConstant.CHARSET_UTF8);
			 xmlParse.addHeaderElement("cipher",des);
			 return "cmd="+noticeCode+"&msg="+xmlParse.asXML();
		} catch (Exception e) {
			logger.error("加密出错",e);
			return null;
		}
       
    }
	
		
}
