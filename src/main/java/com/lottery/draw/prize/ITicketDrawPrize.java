package com.lottery.draw.prize;

import com.lottery.core.domain.ticket.Ticket;

public interface ITicketDrawPrize {

	/**
	 * 票算奖
	 * @param ticket 票信息
	 * @param prizeDetail 中奖信息
	 * @return boolean 是否需要更新
	 * */
	public boolean draw(Ticket ticket,IPrizeDetail prizeDetail);
}
