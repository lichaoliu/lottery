package com.lottery.listener.check;

import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.JsonUtil;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fengqinyun on 16/6/16.
 */
@Service
public class TicketCheckListener {
    protected final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    protected TicketCheckerHandler ticketCheckerHandler;
    public  void check(@Header("ticketId") String ticketId){
        ticketCheckerHandler.process(ticketId);
    }
    public  void notice(@Body String ticketVenderBody){
        try{
            //logger.error("接到的通知是:{}",ticketVenderBody);
            TicketVender ticketVender= JsonUtil.fromJsonToObject(ticketVenderBody,TicketVender.class);

            ticketCheckerHandler.noticeProcess(ticketVender);
        }catch (Exception e){
            logger.error("jms出错",e);
        }

    }
}
