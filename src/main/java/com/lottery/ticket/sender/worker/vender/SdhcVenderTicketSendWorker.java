package com.lottery.ticket.sender.worker.vender;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.RandomUtil;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by fengqinyun on 15/8/15.
 */
@Component
public class SdhcVenderTicketSendWorker extends VirtualVenderTicketSendWorker{
    @Override
    protected TerminalType getTerminalType() {

        return TerminalType.T_SDHC;
    }


    protected  String getExtermianlId(){
        return "HC"+ CoreDateUtils.formatDate(new Date(), "yyyyMMddHHmmss")+ RandomUtil.randomAlphanumeric(5);
    }
    @Override
    public long getTicketSendInterval() {
        return 1000l;
    }
}
