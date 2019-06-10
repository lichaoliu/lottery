package com.lottery.ticket.vender.lotterycenter;

import com.lottery.core.domain.LotteryPhase;

/**
 * 福彩中心信息同步
 * */
public interface IVenderMessageFromCenter {

	public LotteryPhase syncFromCenter(int lotteryType,String phase);
}
