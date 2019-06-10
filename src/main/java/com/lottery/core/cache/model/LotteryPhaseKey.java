package com.lottery.core.cache.model;

import java.io.Serializable;

public class LotteryPhaseKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6044052379999807486L;
	private Integer lotteryType;
	private String phase;
	
	public LotteryPhaseKey(){}
	public LotteryPhaseKey(Integer lotteryType,String phase){
		this.lotteryType=lotteryType;
		this.phase=phase;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lotteryType == null) ? 0 : lotteryType.hashCode());
		result = prime * result + ((phase == null) ? 0 : phase.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LotteryPhaseKey other = (LotteryPhaseKey) obj;
		if (lotteryType == null) {
			if (other.lotteryType != null)
				return false;
		} else if (!lotteryType.equals(other.lotteryType))
			return false;
		if (phase == null) {
			if (other.phase != null)
				return false;
		} else if (!phase.equals(other.phase))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LotteryPhaseKey{" +
				"lotteryType=" + lotteryType +
				",phase='" + phase + '\'' +
				'}';
	}
}
