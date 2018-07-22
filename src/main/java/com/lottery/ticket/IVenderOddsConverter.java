package com.lottery.ticket;

import com.lottery.core.domain.ticket.Ticket;

public interface IVenderOddsConverter {

	public String convertOdds(Ticket ticket,String odds);
}

