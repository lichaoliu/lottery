package com.lottery.ticket;

import com.lottery.core.domain.ticket.Ticket;
/**
 * 转化成出票商的内容
 * @author fengqinyun
 * */
public interface IVenderTicketConverter {
	
	public String convert(Ticket ticket);
	

}
