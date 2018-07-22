/**
 * 
 */
package com.lottery.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.util.PairValue;

/**
 * 存储多个时间范围
 * @author fengqinyun
 *
 */
public class TimeRegion {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeRegion.class.getName());

	private List<PairValue<Time, Time>> regionList = new ArrayList<PairValue<Time,Time>>();

	/**
	 * 从字符串反向解析回对象
	 * @param str 22:30|24:00,00:00|01:00,03:00|07:00
	 * @return
	 */
	public static TimeRegion parse(String str) {
		if (str == null) {
			return null;
		}
		
		List<PairValue<Time, Time>> regionList = new ArrayList<PairValue<Time,Time>>();

		String[] pairValueStrs = StringUtils.split(str, " ,");
		for (String pairValueStr : pairValueStrs) {
			String[] timeStrs = StringUtils.split(pairValueStr, "|");
			if (timeStrs.length != 2) {
				logger.error("错误的时间范围，直接忽略, {}", pairValueStr);
				continue;
			}
			Time timeLeft = Time.parse(timeStrs[0]);
			Time timeRight = Time.parse(timeStrs[1]);
			
			if (timeLeft == null || timeRight == null) {
				logger.error("时间解析出错，直接忽略, {}, {}", timeStrs[0], timeStrs[1]);
				continue;
			}
			
			regionList.add(new PairValue<Time, Time>(timeLeft, timeRight));
		}
		
		if (regionList.isEmpty()) {
			return null;
		}
		
		TimeRegion timeRegion = new TimeRegion();
		timeRegion.setRegionList(regionList);
		
		return timeRegion;
	}

	/**
	 * 转换成 range1_begin|range1_end,range2_begin|range2_end...
	 */
	@Override
	public String toString() {
		if (regionList == null) {
			return "";
		}
		List<String> strList = new ArrayList<String>();
		for (PairValue<Time, Time> pair : regionList) {
			if (pair == null) {
				continue;
			}
			
			if (pair.getLeft() == null || pair.getRight() == null) {
				// 一对都必须有值
				continue;
			}
			
			strList.add(pair.getLeft().toString() + "|" + pair.getRight().toString());
		}
		return StringUtils.join(strList, ",");
	}

	public List<PairValue<Time, Time>> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<PairValue<Time, Time>> regionList) {
		this.regionList = regionList;
	}
	
}
