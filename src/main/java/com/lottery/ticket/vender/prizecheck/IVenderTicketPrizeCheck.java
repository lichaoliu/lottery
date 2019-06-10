package com.lottery.ticket.vender.prizecheck;

import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IVenderConfig;

import java.util.List;

/**
 * Created by fengqinyun on 15/11/22.
 * 奖金检查
 */
public interface IVenderTicketPrizeCheck {
    /**
     *@param ticketList 票号
     *@param venderConfig 终端配置
     * */
    public void check(List<Ticket> ticketList,IVenderConfig venderConfig);

}
