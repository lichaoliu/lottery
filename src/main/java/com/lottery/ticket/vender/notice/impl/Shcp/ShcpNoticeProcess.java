package com.lottery.ticket.vender.notice.impl.Shcp;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ShcpNoticeProcess extends AbstractVenderNoticeProcess {



    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_SHCP;
    }
    
    protected Map<String, String> convertRequestParameter(String requestString) {
    	Map<String,String > requestMap= CoreHttpUtils.parseQueryString(requestString.replace("&gt;",">"));
    	requestString=requestMap.get("xml");
    	Map<String,String>map=new HashMap<String, String>();

		try {
			Document doc = DocumentHelper.parseText(requestString);
			Element rootElt = doc.getRootElement();
			String agent= rootElt.attributeValue("agent");
			map.put("body",requestString);
			map.put("agent",agent);
			return map;
		} catch (DocumentException e) {
			logger.error("解析xml异常",e);
		}
		
        return null;
    }


    @Override
    protected String getAgentId() {
        return "agent";
    }

	@Override
	protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
		return true;
	}


	@Override
    protected String execute(Map<String, String> requestMap,IVenderConfig venderConfig) {
        StringBuffer stringBuffer=new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringBuffer.append("<response agent=\""+venderConfig.getAgentCode()+"\" type=\"30000\">");
        //将具体信息解析
    	String exValue=requestMap.get("body");
    	try{
			writeNoticeLog("解析后的字符串:"+exValue);
    		 List<HashMap<String, String>> mapList = XmlParse.getElementAttributeList("/", "ticket", exValue);
 			if (mapList != null && mapList.size() > 0) {
 				for (Map<String, String> map : mapList) {
		    			TicketVender ticketVender = createInit();//参照检票操作
		    			String status = map.get("code");
						String extendId = map.get("tid");
						String ticketId = map.get("apply");
						String gid = map.get("gid");
						String pid = map.get("pid");
						String bid = map.get("bid");
						String odds = map.get("memo");
						ticketVender.setId(ticketId);
						ticketVender.setExternalId(extendId);
					    ticketVender.setOtherPeilv(odds);
						if("0".equals(status)) {// 成功
							 ticketVender.setStatus(TicketVenderStatus.success);
								Date date=new Date();
								try{
									String tdate=map.get("tdate");
									date= CoreDateUtils.parseDateTime(tdate);
								}catch (Exception e){

								}
							    ticketVender.setPrintTime(date);
								if(StringUtils.isNotEmpty(odds)){
									Ticket ticket = ticketService.getTicket(ticketId);
									if (ticket==null){
										logger.error("根据票号{}未查到相关票信息",ticketId);
										continue;
									}
									ticketVender.setLotteryType(ticket.getLotteryType());
									try {
										String peilv = getVenderConverter().convertSp(ticket,odds);
										ticketVender.setPeiLv(peilv);
									} catch (Exception e) {
										logger.error("解析赔率异常",e);
									}

								}
						 }else if ("1".equals(status)) {// 限号撤单
							    ticketVender.setStatus(TicketVenderStatus.failed);
								ticketVender.setPrintTime(new Date());
						 }else {
							ticketVender.setStatus(TicketVenderStatus.unkown);
						 }
						this.ticketVenderProcess(ticketVender);
						stringBuffer.append("<ticket gid=\""+gid+"\" pid=\""+pid+"\" bid=\""+bid+"\" apply=\""+ticketId+"\" code=\"0\" desc=\"成功\"/>");
				}
 				stringBuffer.append("</response>");
 			}
		} catch (Exception e) {
			logger.error("上海处理通知异常",e);
		}
		return stringBuffer.toString();
    }

    @Override
	protected String getRequestString(String requestString) throws Exception{
		return URLDecoder.decode(requestString, CharsetConstant.CHARSET_UTF8);
	}
	

		
}
