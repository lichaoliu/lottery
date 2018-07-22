/**
 * 
 */
package com.lottery.core.terminal.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.HighFrequencyLottery;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.cache.TerminalFailureCache;

/**
 * 改进的Weighted Round-Robin选择终端算法
 * 在距离截止时间较近时进行优化处理
 * @author fengqinyun
 *
 */
@Component("terminalSelector")
public class AdvancedWeightedRoundRobinTerminalSelector extends WeightedRoundRobinTerminalSelector {
	
	/**
	 * 最近失败停用超时时间，默认一分钟
	 */
	private long lastFailureTimeout = 60000;
	
	/**
	 * 常规彩种紧急处理时间，默认为最后10分钟
	 */
	private long generalUrgentProcessTime = 600000;
	
	/**
	 * 高频彩种紧急处理时间，默认为最后1分钟
	 */
	private long highFrequencyUrgentProcessTime = 60000;

	@Override
	protected Long prepareSelect(List<TerminalFailureCache> failureCacheList, Date deadline, LotteryType lotteryType, PlayType playType) {
		
		// 先找出最近失败的一个终端
				int lastFailureIndex = -1;
				long lastFailureTime = 0;
				for (int i = 0; i < failureCacheList.size(); i++) {
					TerminalFailureCache failureCache = failureCacheList.get(i);
					if (failureCache.getLastFailedTime() > lastFailureTime) {
						lastFailureTime = failureCache.getLastFailedTime();
						lastFailureIndex = i;
					}
				}
				
				// 如果最近失败在超时时间之内，排除这个终端
				if (System.currentTimeMillis() - lastFailureTime < lastFailureTimeout) {
					TerminalFailureCache lastFailureCache = failureCacheList.remove(lastFailureIndex);
					
					// 如果没有其他终端，只能返回此终端
					if (failureCacheList.isEmpty()) {
						return lastFailureCache.getTerminalId();
					}
				}
				
				// 判断是否需要进行应急处理
				
				// 单场涉及每个场次不同的deadline，无法以彩种为单位进行紧急处理
				if (LotteryType.get().contains(lotteryType)) {
					return null;
				}
				
				boolean urgent = false;
				long urgentProcessTime = generalUrgentProcessTime;
				if (HighFrequencyLottery.contains(lotteryType)) {
					urgentProcessTime = highFrequencyUrgentProcessTime;
				}
				if (deadline.getTime() - urgentProcessTime <= System.currentTimeMillis()) {
					urgent = true;
				}
				
				// 进行紧急处理，采用Round-Robin算法
				if (urgent) {
					int n = failureCacheList.size();
					int index = this.getLastSelectedTerminalIndex(lotteryType, playType);

					do {
						index = (index + 1) % n;
						this.setLastSelectedTerminalIndex(index, lotteryType, playType);
						return failureCacheList.get(index).getTerminalId();
					} while (index != this.getLastSelectedTerminalIndex(lotteryType, playType));
				}
				
				return null;
	}

	public long getLastFailureTimeout() {
		return lastFailureTimeout;
	}

	public void setLastFailureTimeout(long lastFailureTimeout) {
		this.lastFailureTimeout = lastFailureTimeout;
	}

	public long getGeneralUrgentProcessTime() {
		return generalUrgentProcessTime;
	}

	public void setGeneralUrgentProcessTime(long generalUrgentProcessTime) {
		this.generalUrgentProcessTime = generalUrgentProcessTime;
	}

	public long getHighFrequencyUrgentProcessTime() {
		return highFrequencyUrgentProcessTime;
	}

	public void setHighFrequencyUrgentProcessTime(
			long highFrequencyUrgentProcessTime) {
		this.highFrequencyUrgentProcessTime = highFrequencyUrgentProcessTime;
	}
	
}
