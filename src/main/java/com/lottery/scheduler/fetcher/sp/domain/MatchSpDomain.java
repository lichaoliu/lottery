package com.lottery.scheduler.fetcher.sp.domain;

import java.io.Serializable;
import java.util.Map;

public class MatchSpDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String matchNum;
	private Map<String, Map<String, Object>> lotteryType;
	public String getMatchNum() {
		return matchNum;
	}
	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}
	public Map<String, Map<String, Object>> getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Map<String, Map<String, Object>> lotteryType) {
		this.lotteryType = lotteryType;
	}
	
	
}
