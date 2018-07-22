package com.lottery.draw.prize;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.service.PrizeService;
import com.lottery.lottype.LotManager;

/**
 * 通用彩种算奖
 * */
public abstract class AbstractCommonTicketDrawPrize implements ITicketDrawPrize {

	@Autowired
	protected LotManager lotManager;
	
	@Autowired
	protected PrizeService prizeService;

}
