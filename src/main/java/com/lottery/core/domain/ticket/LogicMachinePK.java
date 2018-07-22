package com.lottery.core.domain.ticket;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class LogicMachinePK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="id")
	private  Long id;//逻辑机号
	@Column(name="terminal_type")
	private  Integer terminalType;//终端类型
	@Column(name="lottery_type")
    private  Integer lotteryType;//彩种
	
	public LogicMachinePK(Long id,Integer terminalType,Integer lotteryType){
		this.id=id;
		this.lotteryType=lotteryType;
		this.terminalType=terminalType;
	}
	public LogicMachinePK(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lotteryType == null) ? 0 : lotteryType.hashCode());
		result = prime * result + ((terminalType == null) ? 0 : terminalType.hashCode());
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
		LogicMachinePK other = (LogicMachinePK) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lotteryType == null) {
			if (other.lotteryType != null)
				return false;
		} else if (!lotteryType.equals(other.lotteryType))
			return false;
		if (terminalType == null) {
			if (other.terminalType != null)
				return false;
		} else if (!terminalType.equals(other.terminalType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogicMachinePK{" +
				"id=" + id +
				",terminalType=" + terminalType +
				",lotteryType=" + lotteryType +
				'}';
	}
}
