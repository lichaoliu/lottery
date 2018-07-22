package com.lottery.ticket.vender.exchange.impl;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.AES;
import com.lottery.common.util.DateUtil;
import com.lottery.common.util.HTTPUtil;
import com.lottery.core.SeqEnum;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.exchange.AbstractVenderTicketExchange;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by fengqinyun on 15/11/22.
 */
@Service
public class JYDPVenderTicketExchange extends AbstractVenderTicketExchange{


    @Override
    protected void execute(List<Ticket> ticketList, IVenderConfig venderConfig) {
        for(Ticket ticket:ticketList){
            String messageId=idGeneratorService.getMessageId(SeqEnum.vender_gzcp_messageid);
            String timestamp = DateUtil.format("yyyyMMddHHmmss", new Date());
            JSONObject jsObject=new JSONObject();
            jsObject.put("command","1004");
            jsObject.put("messageid",messageId);
            jsObject.put("timestamp", timestamp);
            jsObject.put("userid", venderConfig.getAgentCode());
            JSONObject jsonBody=new JSONObject();
            jsonBody.put("fromserialno",ticket.getId());

            jsObject.put("params",jsonBody.toString());
            try {
                String returnStr = HTTPUtil.sendPostMsg(venderConfig.getRequestUrl(), "body=" + AES.encrypt(jsObject.toString(), venderConfig.getKey()));
                JSONObject returjJson=JSONObject.fromObject(AES.decrypt(returnStr, venderConfig.getKey()));
                String returnCode=returjJson.getString("errorCode");
                if (returnCode.equals("0")){
                    JSONObject jsonValue=(JSONObject) returjJson.get("value");
                    String ticketaward=jsonValue.getString("ticketaward");
                    if ("1".equals(ticketaward)){
                        String taxawardbets=jsonValue.getString("taxawardbets");
                        this.ticketCreate(ticket,new BigDecimal(taxawardbets).multiply(new BigDecimal(100)));
                    }else {
                        //logger.error("id={},返回:{}",ticket.getId(),ticketaward);
                    }
                }else{
                    logger.error("返回不为0,return={}",returjJson);
                }
            } catch (Exception e) {
                logger.error("兑奖出错",e);
            }
        }
    }

    protected  void ticketProcess(Ticket ticket){

    }
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_JYDP;
    }
}
