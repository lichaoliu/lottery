package com.lottery.draw.prize.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.PrizeService;
import com.lottery.draw.prize.AbstractCommonTicketDrawPrize;
import com.lottery.draw.prize.IPrizeDetail;
import com.lottery.lottype.Lot;
import com.lottery.lottype.LotManager;

/**
 * 普通彩种算奖
 * */
public class CommonTicketDrawPrize extends AbstractCommonTicketDrawPrize {

	

	@Override
	public boolean draw(Ticket ticket, IPrizeDetail prizeDetail) {
		Lot lot = lotManager.getLot(String.valueOf(ticket.getLotteryType()));

		int oneAmount = ticket.getAddition() == YesNoStatus.yes.value ? 300 : 200;
		
		String prizeLevelInfo = lot.getPrizeLevelInfo(ticket.getContent(), prizeDetail.getWinCode(), new BigDecimal(ticket.getMultiple()), oneAmount);
		return false;
	}

}
