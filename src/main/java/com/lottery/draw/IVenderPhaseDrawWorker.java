package com.lottery.draw;

import com.lottery.core.domain.LotteryPhase;

import java.util.List;


/**
 * 从出票商抓取开奖号码及同步余额
 * */
public interface IVenderPhaseDrawWorker {
    /**
     * 通过彩种,期号获取开奖号码
     * @param lotteryType 彩种
     * @param phase 期号
     * */
	LotteryDraw getPhaseDraw(Integer lotteryType,String phase);

	/**
	 * 获取在售彩期列表
	 * @param lotteryType 彩种
	 * */
	public List<LotteryPhase> getPhaseList(int lotteryType,String phase);



	
}
