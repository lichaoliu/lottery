package com.lottery.ticket.vender.notice.impl.Huancai;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.common.util.HTTPUtil;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.sender.worker.XmlParse;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


@Component
public class HuancaiNoticeProcess extends AbstractVenderNoticeProcess {


    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_HUANCAI;
    }

    
    protected Map<String, String> convertRequestParameter(String requestString) {

    	Map<String,String>map=new HashMap<String, String>();
		try {
			Map<String,String > requestMap= CoreHttpUtils.parseQueryString(requestString);
			if(requestMap.get("cmd").equals("3003")){
				map.put("msg",requestMap.get("msg"));
				map.put("agentid","HC0008");
				return map;
			}
			return null;
		} catch (Exception e) {
			logger.error("解析xml异常",e);
			logger.error("request={}",requestString);
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

        //将具体信息解析
    	String bodyStr=requestMap.get("msg");

    	 try {
			List<HashMap<String, String>> maps = XmlParse.getElementAttributeList("body/tickets/", "ticket", bodyStr);
			for(HashMap<String, String> map:maps){
				String ticketId = map.get("ticketid");
				String status = map.get("status");
				TicketVender ticketVender = createInit();//参照检票操作
				ticketVender.setTerminalIdJudge(false);
				ticketVender.setId(ticketId);
				if ("1".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.printing);
					ticketVender.setMessage("出票中");
				} else if ("2".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.success);
					ticketVender.setMessage("出票成功");
					ticketVender.setPrintTime(new Date());
				} else if ("3".equals(status)) {
					ticketVender.setStatus(TicketVenderStatus.failed);
					ticketVender.setMessage("出票失败");
				} else {
					ticketVender.setStatus(TicketVenderStatus.unkown);
					ticketVender.setMessage("未知异常");
				}
				ticketVender.setPrintTime(new Date());
				this.ticketVenderProcess(ticketVender);
			}
			
			
			
					
				  
		} catch (Exception e) {

			logger.error("处理通知异常",e);
			 logger.error("bodyStr={}",bodyStr);
		}
		return "1";
    }

	@Override
	protected String getRequestString(String requestString) throws Exception{
		return URLDecoder.decode(requestString, CharsetConstant.CHARSET_UTF8);
	}
		protected IVenderConverter getVenderConverter(){
			return venderConverterBinder.get(getTerminalType());
		}
		
		
}
