package com.lottery.ticket.vender.notice.impl.HuAi;

import com.lottery.common.contains.CharsetConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class HuAiNoticeProcess extends AbstractVenderNoticeProcess {



    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_HUAI;
    }


    @Override
    protected String getAgentId() {
        return "exAgent";
    }

	@Override
	protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
		return true;
	}


	@Override
    protected String execute(Map<String, String> requestMap,IVenderConfig venderConfig) {

        //将具体信息解析
    	String exValue=requestMap.get("exValue");
    	 try {
			    String []tickets=exValue.split("\\;");
				for(String ticekt:tickets){
					 String []strs=ticekt.split("\\_");
		    			Map<String,String>map=new HashMap<String, String>();
		    			for(String con:strs){
		    				map.put(con.split("\\=")[0], con.substring(con.split("\\=")[0].length()+1));
		    			}
		    			if(StringUtils.isNotEmpty(map.get("Orders"))){
		    				String orders[]=map.get("Orders").split("\\^");
		    				for(String order:orders){
		    					map.put(order.split("\\=")[0],order.split("\\=").length>1?order.split("\\=")[1]:"");
		    				}
		    			}
		    			TicketVender ticketVender = createInit();//参照检票操作
						String status = map.get("Status");
					    String realId=map.get("GroundID");
						String externalId=map.get("TicketID");
						String ticketId=map.get("OrderID");
						String odds=map.get("Odds");
						ticketVender.setId(ticketId);
				     	ticketVender.setSerialId(realId);
						ticketVender.setExternalId(externalId);
						if ("W".equals(status)) {//出票中
							ticketVender.setStatus(TicketVenderStatus.printing);
						} else if ("Y".equals(status)) {// 成功
							ticketVender.setStatus(TicketVenderStatus.success);
							if(StringUtils.isNotEmpty(odds)){
								Ticket ticket = ticketService.getTicket(ticketId);
								if (ticket==null){
									logger.error("根据票号{}未查到相关票信息",ticketId);
									return "1";
								}
								String peilv="";
								try {
									peilv = getVenderConverter().convertSp(ticket,odds);
								} catch (Exception e) {
									logger.error("解析赔率异常",e);
								}
								ticketVender.setLotteryType(ticket.getLotteryType());
								ticketVender.setPeiLv(peilv);
							}
								
						} else if ("N".equals(status)) {// 失败
							ticketVender.setStatus(TicketVenderStatus.failed);
						}  else {
							ticketVender.setStatus(TicketVenderStatus.unkown);
						}
						ticketVender.setPrintTime(new Date());
						this.ticketVenderProcess(ticketVender);
				}
    		
			 return "1";
		} catch (Exception e) {
			logger.error("互爱处理通知异常",e);
			 return "failure";
		}
    }
	@Override
	protected String getRequestString(String requestString) throws Exception{
		return URLDecoder.decode(requestString, CharsetConstant.CHARSET_UTF8);
	}
		
}
