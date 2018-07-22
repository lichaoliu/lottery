/**
 * 
 */
package com.lottery.core.terminal.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.CoreAlgorithm;
import com.lottery.core.cache.TerminalFailureCache;

/**
 * Weighted Round-Robin选择终端算法
 * @author fengqinyun
 *
 */

public class WeightedRoundRobinTerminalSelector extends AbstractTerminalSelector {
	
	// 上次选择的终端
		private Map<LotteryType, Integer> lastSelectedTerminalIndexLotteryTypeMap = new ConcurrentHashMap<LotteryType, Integer>();
	    private Map<PlayType, Integer> lastSelectedTerminalIndexPlayTypeMap = new ConcurrentHashMap<PlayType, Integer>();
		
		// 当前调度的权重值
		private Map<LotteryType, Integer> currentWeightLotteryTypeMap = new ConcurrentHashMap<LotteryType, Integer>();
	    private Map<PlayType, Integer> currentWeightPlayTypeMap = new ConcurrentHashMap<PlayType, Integer>();

		/* (non-Javadoc)
		 * @see com.lehecai.engine.util.terminal.impl.AbstractTerminalSelector#chooseTopPriorityTerminalId(java.util.List)
		 */
		@Override
		protected Long chooseTopPriorityTerminalId(List<TerminalFailureCache> failureCacheList, Date deadline, LotteryType lotteryType, PlayType playType) {
			if (failureCacheList == null || failureCacheList.isEmpty()) {
				return null;
			}
			
			Long prepareSelectedTerminalId = prepareSelect(failureCacheList, deadline, lotteryType, playType);
			if (prepareSelectedTerminalId != null) {
				return prepareSelectedTerminalId;
			}
			
			int n = failureCacheList.size();	// 可选终端个数
			int maxWeight = 0;	// 可选终端的最大权重值
			int gcd = 0;	// 可选终端权重的最大公约数
			
			// 把所有权重小于1的终端权重都认为是1
			int[] weights = new int[n];
			for (int i = 0; i < failureCacheList.size(); i++) {
				TerminalFailureCache failureCache = failureCacheList.get(i);
				weights[i] = (failureCache.getWeight() < 1 ? 1 : failureCache.getWeight());
				if (weights[i] > maxWeight) {
					maxWeight = weights[i];
				}
			}
			
			// 求最大公约数
			gcd = CoreAlgorithm.gcd(weights);
			
			// Weighted Round-Robin 算法
			while (true) {
				int lastSelectedTerminalIndex = (this.getLastSelectedTerminalIndex(lotteryType, playType) + 1) % n;
				this.setLastSelectedTerminalIndex(lastSelectedTerminalIndex, lotteryType, playType);
				if (lastSelectedTerminalIndex == 0) {
					int currentWeight = this.getCurrentWeight(lotteryType, playType) - gcd;
					this.setCurrentWeight(currentWeight, lotteryType, playType);
					if (currentWeight <= 0) {
						currentWeight = maxWeight;
						this.setCurrentWeight(currentWeight, lotteryType, playType);
						if (currentWeight == 0) {
							return null;
						}
					}
				}
				if (weights[lastSelectedTerminalIndex] >= this.getCurrentWeight(lotteryType, playType)) {
					break;
				}
			}
			
			return failureCacheList.get(this.getLastSelectedTerminalIndex(lotteryType, playType)).getTerminalId();
		}
		
		/**
		 * 对终端的预选择
		 * 如果返回非NULL表示采用预选择的终端而不向下执行
		 * 如果只是需要对可选终端的列表进行预处理，请返回NULL
		 * @param failureCacheList 所有可选终端缓存列表
	     * @param deadline 截止期
	     * @param lotteryType 彩种
	     * @param playType 玩法
		 * @return 选择的终端号
		 */
		protected Long prepareSelect(List<TerminalFailureCache> failureCacheList, Date deadline, LotteryType lotteryType, PlayType playType) {
			return null;
		}

	    protected int getLastSelectedTerminalIndex(LotteryType lotteryType, PlayType playType) {
			Integer lastSelectedTerminalIndex;
	        if (this.usePlayType(playType)) {
	            lastSelectedTerminalIndex = lastSelectedTerminalIndexPlayTypeMap.get(playType);
	        } else {
	            lastSelectedTerminalIndex= lastSelectedTerminalIndexLotteryTypeMap.get(lotteryType);
	        }
			if (lastSelectedTerminalIndex == null) {
				lastSelectedTerminalIndex = -1;
	            this.setLastSelectedTerminalIndex(lastSelectedTerminalIndex, lotteryType, playType);
			}
			return lastSelectedTerminalIndex;
		}

	    protected void setLastSelectedTerminalIndex(int lastSelectedTerminalIndex, LotteryType lotteryType, PlayType playType) {
	        if (this.usePlayType(playType)) {
	            lastSelectedTerminalIndexPlayTypeMap.put(playType, lastSelectedTerminalIndex);
	        } else {
	            lastSelectedTerminalIndexLotteryTypeMap.put(lotteryType, lastSelectedTerminalIndex);
	        }
		}

	    protected int getCurrentWeight(LotteryType lotteryType, PlayType playType) {
			Integer currentWeight;
	        if (this.usePlayType(playType)) {
	            currentWeight = currentWeightPlayTypeMap.get(playType);
	        } else {
	            currentWeight = currentWeightLotteryTypeMap.get(lotteryType);
	        }
			if (currentWeight == null) {
				currentWeight = 0;
	            this.setCurrentWeight(0, lotteryType, playType);
			}
			return currentWeight;
		}

	    protected void setCurrentWeight(int currentWeight, LotteryType lotteryType, PlayType playType) {
	        if (this.usePlayType(playType)) {
	            currentWeightPlayTypeMap.put(playType, currentWeight);
	        } else {
	            currentWeightLotteryTypeMap.put(lotteryType, currentWeight);
	        }
		}

	    protected boolean usePlayType(PlayType playType) {
	        return playType != null  && playType.getValue() != PlayType.mix.getValue();
	    }

}
