package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class LotteryAddPrizeStrategyPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="lottery_type",nullable=false)
	private Integer lotteryType;
	
	@Column(name="prize_level",nullable=false,length=10)
	private String prizeLevel;
	
	@Column(name="start_phase",length=20)
	private String startPhase;
	
	@Column(name="end_phase",length=20)
	private String endPhase;

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPrizeLevel() {
		return prizeLevel;
	}

	public void setPrizeLevel(String prizeLevel) {
		this.prizeLevel = prizeLevel;
	}

	public String getStartPhase() {
		return startPhase;
	}

	public void setStartPhase(String startPhase) {
		this.startPhase = startPhase;
	}

	public String getEndPhase() {
		return endPhase;
	}

	public void setEndPhase(String endPhase) {
		this.endPhase = endPhase;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((endPhase == null) ? 0 : endPhase.hashCode());
		result = prime * result
				+ ((lotteryType == null) ? 0 : lotteryType.hashCode());
		result = prime * result
				+ ((prizeLevel == null) ? 0 : prizeLevel.hashCode());
		result = prime * result
				+ ((startPhase == null) ? 0 : startPhase.hashCode());
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
		LotteryAddPrizeStrategyPK other = (LotteryAddPrizeStrategyPK) obj;
		if (endPhase == null) {
			if (other.endPhase != null)
				return false;
		} else if (!endPhase.equals(other.endPhase))
			return false;
		if (lotteryType == null) {
			if (other.lotteryType != null)
				return false;
		} else if (!lotteryType.equals(other.lotteryType))
			return false;
		if (prizeLevel == null) {
			if (other.prizeLevel != null)
				return false;
		} else if (!prizeLevel.equals(other.prizeLevel))
			return false;
		if (startPhase == null) {
			if (other.startPhase != null)
				return false;
		} else if (!startPhase.equals(other.startPhase))
			return false;
		return true;
	}
	
	
	
}
