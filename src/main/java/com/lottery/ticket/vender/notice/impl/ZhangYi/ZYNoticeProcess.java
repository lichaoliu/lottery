package com.lottery.ticket.vender.notice.impl.ZhangYi;

import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketVenderStatus;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.DateUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import com.lottery.ticket.vender.impl.zhangyi.ZYLotteryDef;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Component
public class ZYNoticeProcess extends AbstractVenderNoticeProcess {

	
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_ZY;
    }


    @Override
    protected String getAgentId() {
        return "agenterid";
    }

    protected Map<String, String> convertRequestParameter(String requestString) {
    	Map<String,String>map=new HashMap<String, String>();
		try {
			 JSONObject mapObject =JSONObject.fromObject(requestString);
	    	 JSONObject headMap=(JSONObject) mapObject.get("header");
			 map.put("msg",mapObject.get("msg").toString());
			 map.put("agenterid",(String) headMap.get("agenterid"));
			return map;
		} catch (Exception e) {
			logger.error("解析xml异常",e);
		}
		
        return null;
    }

	@Override
	protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
		return true;
	}

	@Override
    protected String execute(Map<String, String> requestMap,IVenderConfig venderConfig) {
		
        //将具体信息解析
    	String exValue=requestMap.get("msg");
    	 try {
    		 JSONObject returnMsg=JSONObject.fromObject(exValue);
    		 Iterator<JSONObject> ticketList=returnMsg.getJSONArray("ticketlist").iterator();
				while(ticketList.hasNext()){
					JSONObject jObject=ticketList.next();
					String status = String.valueOf(jObject.get("status"));
					String externalId =String.valueOf(jObject.get("ticketid"));
					String ticketId =String.valueOf(jObject.get("id"));
					String failreason=jObject.getString("failreason");
					TicketVender ticketVender = createInit();//参照检票操作
					ticketVender.setId(ticketId);
			     	ticketVender.setSerialId("");
					ticketVender.setStatusCode(status);
					ticketVender.setExternalId(externalId);
				    ticketVender.setMessage(failreason);
					ticketVender.setResponseMessage(exValue);
					ticketVender.setTerminalIdJudge(false);
					if ("1".equals(status)||"4".equals(status)||"5".equals(status)) {//出票中
						ticketVender.setStatus(TicketVenderStatus.printing);
					} else if ("2".equals(status)) {// 成功
						String odds= jObject.get("spinfo")!=null?String.valueOf(jObject.get("spinfo")):"";
						ticketVender.setStatus(TicketVenderStatus.success);
						ticketVender.setOtherPeilv(odds);

						if(StringUtils.isNotEmpty(odds)){
							Ticket ticket = ticketService.getTicket(ticketId);
							if (ticket==null){
								logger.error("根据票号{}未查到相关票信息",ticketId);
								continue;
							}
							IVenderConverter venderConverter=this.getVenderConverter();
							if(StringUtils.isNotEmpty(odds)){
								odds=venderConverter.convertSp(ticket,odds);
								ticketVender.setPeiLv(odds);
							}
							ticketVender.setLotteryType(ticket.getLotteryType());
						}

						try{
							if(jObject.has("tickettime")&&StringUtils.isNotBlank(jObject.getString("tickettime"))){
								Date dateTime=new java.util.Date(Long.parseLong(jObject.getString("tickettime"))*1000);
								ticketVender.setPrintTime(dateTime);
							}
						}catch (Exception e){
							logger.error("通知出票时间处理出错",e);
						}

					}else if("6".equals(status)){
						if(failreason.equals("200009")){
							Ticket ticket = ticketService.getTicket(ticketId);
							if(HighFrequencyLottery.contains(ticket.getLotteryType())){
								ticketVender.setStatus(TicketVenderStatus.ticket_limited);
							}else {
								ticketVender.setStatus(TicketVenderStatus.failed);
							}
						}else{
							ticketVender.setStatus(TicketVenderStatus.failed);
						}
					} else {// 失败
						ticketVender.setStatus(TicketVenderStatus.failed);
					}  
					ticketVender.setPrintTime(new Date());
					this.ticketVenderProcess(ticketVender);	
				}
 		
				return getReturn(venderConfig);  
		} catch (Exception e) {
			logger.error("掌奕处理通知异常",e);
			 return "failure";
		}
    }
    /**
     * 返回
     * @param zyConfig
     * @return
     */
	private String getReturn(IVenderConfig zyConfig){
		JSONObject message = new JSONObject();
		JSONObject header = new JSONObject();
		JSONObject msg = new JSONObject();
		header.put("messengerid",idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid,CoreDateUtils.DATE_YYYYMMDDHHMMSS));
		header.put("timestamp",DateUtil.format("yyyyMMddHHmmss", new Date()));
		header.put("transactiontype","13006");
		header.put("des","0");
		header.put("agenterid",zyConfig.getAgentCode());
		message.put("header", header);	
		msg.put("errorcode", "0");
		msg.put("errormsg", "ok");		
		message.put("msg",msg);
		return message.toString();
	}
		

	
	
}
