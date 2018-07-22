package com.lottery.ticket.vender.notice.impl.letou;

import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.ticket.TicketVender;
import com.lottery.common.util.CoreHttpUtils;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.notice.AbstractVenderNoticeProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fengqinyun on 16/11/14.
 */
@Component
public class LetouVenderNoticeProcess extends AbstractVenderNoticeProcess {
    @Override
    protected TerminalType getTerminalType() {
        return TerminalType.T_LETOU;
    }

    @Override
    protected String getAgentId() {
        return null;
    }

    @Override
    protected boolean validate(Map<String, String> requestMap, IVenderConfig venderConfig) {
        return false;
    }

    @Override
    protected String execute(Map<String, String> requestMap, IVenderConfig venderConfig) {
        return null;
    }

    @Override
    public  String process(String requestString,String requestIp,Long termialId) throws Exception{

        //logger.error("接到乐投的通知:{}",requestString);
        Map<String,String> crossMap= CoreHttpUtils.parseQueryString(requestString);
        String ticketId=crossMap.get("ticketId");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ticketId", ticketId);
        queueMessageSendService.sendMessage(QueueName.ticketCheck, map);
        return "success";
    }
}
