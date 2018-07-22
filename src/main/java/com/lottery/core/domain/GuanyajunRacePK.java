package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GuanyajunRacePK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5611762463836536097L;

	@Column(name = "lottery_type")
	private Integer lotteryType;// 彩种

	@Column(name = "phase",length=10)
	private String phase;// 期号

	@Column(name = "matchnum",length=5)
	private String matchNum;// 赛事编号

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

	public String getMatchNum() {
		return matchNum;
	}

	public void setMatchNum(String matchNum) {
		this.matchNum = matchNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lotteryType == null) ? 0 : lotteryType.hashCode());
		result = prime * result
				+ ((matchNum == null) ? 0 : matchNum.hashCode());
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
		GuanyajunRacePK other = (GuanyajunRacePK) obj;
		if (lotteryType == null) {
			if (other.lotteryType != null)
				return false;
		} else if (!lotteryType.equals(other.lotteryType))
			return false;
		if (matchNum == null) {
			if (other.matchNum != null)
				return false;
		} else if (!matchNum.equals(other.matchNum))
			return false;
		if (phase == null) {
			if (other.phase != null)
				return false;
		} else if (!phase.equals(other.phase))
			return false;
		return true;
	}
	
	
	
}
