package com.lottery.ticket.checker.worker;

import com.lottery.common.Constants;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fengqinyun on 15/9/1
 */

public class XmacVenderTicketCheckWorker extends AbstractVenderTicketCheckWorker{
    private String wAction="203";
    @Override
    protected void init() {
        venderCheckerWorkerMap.put(TerminalType.T_XMAC, this);
    }

    @Override
    public List<TicketVender> process(List<Ticket> ticketList, IVenderConfig venderConfig, IVenderConverter venderConverter) throws Exception {
        String wAgent=venderConfig.getAgentCode();
        String wMsgID=idGeneratorService.getMessageId();
        List<TicketVender> venderList=new ArrayList<TicketVender>();
        try{
            for (Ticket ticket:ticketList){
                 String wParam="OrderID="+ticket.getId();
                 String key=wAgent+wAction+wMsgID+wParam+venderConfig.getKey();
                 String wSign= MD5Util.getMD5((key).getBytes("GBK"));
                 String param="wAgent="+wAgent+"&wAction="+wAction+"&wMsgID="+wMsgID+"&wSign="+wSign+"&wParam="+URLEncoder.encode(wParam,CharsetConstant.CHARSET_GBK);
                 String returnStr= HTTPUtil.post(venderConfig.getRequestUrl(),param,CharsetConstant.CHARSET_GBK);
              
                 if(StringUtils.isNotBlank(returnStr)){
                	returnStr="<param>"+returnStr.split("\\?>")[1]+"</param>";
                }
 				HashMap<String, String> mapResult = XmlParse.getElementText("/", "ActionResult", returnStr);
 				String xCode=mapResult.get("xCode");
                 TicketVender ticketVender=createTicketVender(venderConfig.getTerminalId(),TerminalType.T_XMAC);
                 ticketVender.setSendMessage(param);
                 ticketVender.setResponseMessage(returnStr);
                 ticketVender.setId(ticket.getId());
				 ticketVender.setStatusCode(xCode);
				 ticketVender.setExternalId("");
				 venderList.add(ticketVender);
                 if (xCode.equals("1")){
                    ticketVender.setStatus(TicketVenderStatus.success);
 					ticketVender.setMessage("出票成功");
 					ticketVender.setPrintTime(new Date());
                 }else if(xCode.equals("2002")){
                	 ticketVender.setStatus(TicketVenderStatus.printing);
                 }else if(xCode.equals("2005")||xCode.equals("2010")||xCode.equals("1014")){
                	 ticketVender.setStatus(TicketVenderStatus.not_found);
 					ticketVender.setMessage("票不存在"); 
                 }  else if (Constants.macSendFailedError.containsKey(returnStr)) {
                	 ticketVender.setStatus(TicketVenderStatus.failed);
                	 ticketVender.setMessage("出票失败");
 				} else if (Constants.macSendError.containsKey(returnStr)) {
 					ticketVender.setStatus(TicketVenderStatus.unkown);
 					ticketVender.setMessage(Constants.macSendError.get(xCode));
 				}
            }
        }catch (Exception e){
            logger.error("厦门爱彩检票失败",e);
        }
        return venderList;
    }

	@Override
	protected TerminalType getTerminalType() {
		// TODO Auto-generated method stub
		return null;
	}
}
