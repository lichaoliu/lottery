package com.lottery.ticket.vender.exchange;

import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;

import java.util.List;

/**
 * Created by fengqinyun on 15/11/18.
 * 兑奖
 */
public interface IVenderTicketExchange {

    /**
     *@param ticketList 票号
     *@param venderConfig 终端配置
     * */
    public void exchange(List<Ticket> ticketList,IVenderConfig venderConfig);
}
