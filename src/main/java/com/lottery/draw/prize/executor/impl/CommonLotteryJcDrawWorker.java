package com.lottery.draw.prize.executor.impl;

import org.springframework.stereotype.Service;

import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.draw.prize.executor.AbstractLotteryDrawWorker;
import com.lottery.lottype.OrderCalResult;

@Service
public class CommonLotteryJcDrawWorker extends AbstractLotteryDrawWorker {

	@Override
	public OrderCalResult draw(LotteryOrder order, String wincode) throws Exception {
		return jingcaiService.calcuteJingcaiOrder(order.getId());
	}

}
