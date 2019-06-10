package com.lottery.ticket.vender.prizecheck.impl;

import com.lottery.common.contains.AgencyExchanged;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.AES;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lottery.ticket.vender.prizecheck.AbstractVenderTicketPrizeCheck;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 15/11/22.
 */
@Service
public class JYDPVenderTicketPrizeCheck extends AbstractVenderTicketPrizeCheck {
    @Override
    protected void execute(List<Ticket> ticketList, IVenderConfig venderConfig) {
        for(Ticket ticket:ticketList){
            String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
            String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
            JSONObject jsObject=new JSONObject();
            jsObject.put("command","1003");
            jsObject.put("messageid",messageId);
            jsObject.put("timestamp", timestamp);
            jsObject.put("userid", venderConfig.getAgentCode());
            JSONObject jsonBody=new JSONObject();
            jsonBody.put("fromserialno",ticket.getId());
            jsonBody.put("awardbets",ticket.getPretaxPrize().divide(new BigDecimal(100)).doubleValue());
            jsonBody.put("tempawardbets",ticket.getPosttaxPrize().divide(new BigDecimal(100)).doubleValue());
            jsObject.put("params",jsonBody.toString());
            try {
                String returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), "body="+ AES.encrypt(jsObject.toString(),venderConfig.getKey()));
                JSONObject returjJson=JSONObject.fromObject(AES.decrypt(returnStr,venderConfig.getKey()));
                String returnCode=returjJson.getString("errorCode");
                String returnMsg=returjJson.getString("message");
                if (returnCode.equals("0")){
                    ticket.setAgencyExchanged(AgencyExchanged.exchange_check.value);
                    ticketService.update(ticket);
                }else{
                    logger.error("返回不是0,msg={}",returjJson);
                }
            } catch (Exception e) {
               logger.error("奖金检查出错",e);
            }
        }


    	
    	
    	
    }

    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_JYDP;
    }
}
