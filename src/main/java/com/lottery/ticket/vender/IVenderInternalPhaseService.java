/**
 * 
 */
package com.lottery.ticket.vender;

import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.LotteryPhase;


public interface IVenderInternalPhaseService {
	
	/**
	 * 获取彩期列表
	 * @param lotteryType
	 * @return
	 */
	public List<LotteryPhase> getPhaseList(LotteryType lotteryType, Long terminalId);
	
	/**
	 * 获取不指定的彩期列表
	 * @param lotteryType
	 * @return
	 */
	public List<LotteryPhase> getOnsalePhaseList(LotteryType lotteryType, Long terminalId);

}
