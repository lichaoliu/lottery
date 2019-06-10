package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AwardLevelPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "lottery_type")
	private int lotterytype;
	
	@Column(name = "prize_level", nullable = false)
	private String prizelevel;

	public int getLotterytype() {
		return lotterytype;
	}

	public void setLotterytype(int lotterytype) {
		this.lotterytype = lotterytype;
	}

	public String getPrizelevel() {
		return prizelevel;
	}

	public void setPrizelevel(String prizelevel) {
		this.prizelevel = prizelevel;
	}

	public AwardLevelPK() {
		super();
	}

	public AwardLevelPK(int lotterytype, String prizelevel) {
		super();
		this.lotterytype = lotterytype;
		this.prizelevel = prizelevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lotterytype;
		result = prime * result + ((prizelevel == null) ? 0 : prizelevel.hashCode());
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
		AwardLevelPK other = (AwardLevelPK) obj;
		if (lotterytype != other.lotterytype)
			return false;
		if (prizelevel == null) {
			if (other.prizelevel != null)
				return false;
		} else if (!prizelevel.equals(other.prizelevel))
			return false;
		return true;
	}
	
	
}
