/**
 * 
 */
package com.lottery.core.terminal.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.cache.TerminalFailureCache;

/**
 * 一个选择终端算法
 * @author fengqinyun
 *
 */
public class CommonTerminalSelector extends AbstractTerminalSelector {

	
	@Override
	protected Long chooseTopPriorityTerminalId(List<TerminalFailureCache> failureCacheList, Date deadline, LotteryType lotteryType, PlayType playType) {
		if (failureCacheList == null || failureCacheList.isEmpty()) {
			return null;
		}
		
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
		
		// 如果最近失败在十分钟之内，排除这个终端
		if (System.currentTimeMillis() - lastFailureTime < 600000) {
			TerminalFailureCache lastFailureCache = failureCacheList.remove(lastFailureIndex);
			
			// 如果没有其他终端，只能返回此终端
			if (failureCacheList.isEmpty()) {
				return lastFailureCache.getTerminalId();
			}
		}
		
		// 剩余终端，按照权重和失败次数进行排序
		Collections.sort(failureCacheList, getComparator());
		
		return failureCacheList.get(0).getTerminalId();
	}

	protected Comparator<TerminalFailureCache> getComparator() {
		
		Comparator<TerminalFailureCache> comparator = new Comparator<TerminalFailureCache>() {
			
			@Override
			public int compare(TerminalFailureCache o1, TerminalFailureCache o2) {
				
				// 优先按照权重降序排列
				if (o1.getWeight() != o2.getWeight()) {
					return -1 * Integer.valueOf(o1.getWeight()).compareTo(o2.getWeight());
				}
				
				// 否则按照失败次数排序
				return Long.valueOf(o1.getFailureCount()).compareTo(o2.getFailureCount());
			}
		};
		
		return comparator;
	}

	

	
}
