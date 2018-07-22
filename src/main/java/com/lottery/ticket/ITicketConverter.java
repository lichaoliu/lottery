package com.lottery.ticket;

import com.lottery.core.domain.ticket.Ticket;

/**
 * 出票转换器
 * @author fengqinyun
 * */

public interface ITicketConverter {
	public String convert(Ticket ticket);
}
