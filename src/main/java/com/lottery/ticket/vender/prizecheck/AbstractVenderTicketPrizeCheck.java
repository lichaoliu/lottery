package com.lottery.ticket.vender.prizecheck;

import com.lottery.common.contains.AgencyExchanged;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.exchange.IVenderTicketExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by fengqinyun on 15/11/22.
 */
public abstract class AbstractVenderTicketPrizeCheck implements IVenderTicketPrizeCheck {
    protected final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected VenderConfigHandler venderConfigHandler;
    @Autowired
    protected IdGeneratorService idGeneratorService;
    @Resource(name="venderTicketPrizeCheckMap")
    protected Map<TerminalType,IVenderTicketPrizeCheck> venderTicketPrizeCheckMap;
    public void check(List<Ticket> ticketList,IVenderConfig venderConfig){

        if(ticketList==null||ticketList.size()==0||venderConfig==null){
            return;
        }
        this.execute(ticketList, venderConfig);
        for (Ticket ticket:ticketList){
            this.ticketProcess(ticket);
        }
    }

    protected  void ticketProcess(Ticket ticket){
        if (ticket.getAgencyExchanged()!=null&&ticket.getAgencyExchanged()!= AgencyExchanged.exchange_no.value)
            ticketService.update(ticket);
    }




    protected abstract void execute(List<Ticket> ticketList,IVenderConfig venderConfig);

    protected abstract TerminalType getTerminalType();

    @PostConstruct
    protected void init(){
        venderTicketPrizeCheckMap.put(getTerminalType(),this);
    }
}
