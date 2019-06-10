package com.lottery.ticket.vender.exchange;

import com.lottery.common.PageBean;
import com.lottery.common.contains.AgencyExchanged;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.VenderConfigHandler;
import com.lottery.core.service.TicketService;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * Created by fengqinyun on 15/11/18.
 */
public abstract class AbstractVenderTicketExchange implements IVenderTicketExchange{
    protected final Logger logger= LoggerFactory.getLogger(getClass());
    @Autowired
    protected TicketService ticketService;
    @Autowired
    protected VenderConfigHandler venderConfigHandler;

    @Resource(name="venderExchangeMap")
    protected Map<TerminalType,IVenderTicketExchange> venderExchangeMap;
    @Resource(name = "venderConverterBinder")
    protected Map<TerminalType, IVenderConverter> venderConverterBinder;

    @Autowired
    protected IdGeneratorService idGeneratorService;
    public void exchange(List<Ticket> ticketList,IVenderConfig venderConfig){

        if(ticketList==null||ticketList.size()==0||venderConfig==null){
            return;
        }
        this.execute(ticketList, venderConfig);
        for (Ticket ticket:ticketList){
           this.ticketProcess(ticket);
        }
    }

    protected  void ticketProcess(Ticket ticket){
        if (ticket.getAgencyExchanged()!=null&&ticket.getAgencyExchanged()!=AgencyExchanged.exchange_no.value)
            ticketService.update(ticket);
    }

    /**
     *@param  venderAmount 出票方中奖金额,单位:分
     * */
    protected  void ticketCreate(Ticket ticket,BigDecimal venderAmount){
        if (venderAmount!=null&&ticket.getPosttaxPrize()!=null){

            ticket.setAgencyPrize(venderAmount);
            ticket.setExchangeTime(new Date());
            BigDecimal prizeAmount=ticket.getPosttaxPrize();

            if (prizeAmount.compareTo(venderAmount)==0){
                ticket.setAgencyExchanged(AgencyExchanged.exchange_yes.value);
            }else {
                logger.error("奖金有差异,票号:{},兑奖金额:{},算奖金额:{},单位(分)",ticket.getId(),venderAmount,prizeAmount);
                ticket.setAgencyExchanged(AgencyExchanged.exchange_error.value);
            }
            ticketService.update(ticket);
        }
    }

    protected abstract void execute(List<Ticket> ticketList,IVenderConfig venderConfig);

    protected abstract TerminalType getTerminalType();

    @PostConstruct
    protected void init(){
        venderExchangeMap.put(getTerminalType(),this);
    }


    protected IVenderConverter getVenderConverter() {
        return venderConverterBinder.get(getTerminalType());
    }

}
