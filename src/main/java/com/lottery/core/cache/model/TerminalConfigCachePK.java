package com.lottery.core.cache.model;

import java.io.Serializable;

public class TerminalConfigCachePK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2810768592401525345L;

	private Integer lotteryType;	//彩种编号
	
	private Long terminalId;			//终端编号

	private Integer playType;

	public TerminalConfigCachePK(Integer lotteryType,Long terminalId,Integer playType){
		this.lotteryType=lotteryType;
		this.terminalId=terminalId;
		this.playType=playType;
	}
	
	public TerminalConfigCachePK(){}
	
	
	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}



	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TerminalConfigCachePK that = (TerminalConfigCachePK) o;

		if (lotteryType != null ? !lotteryType.equals(that.lotteryType) : that.lotteryType != null) return false;
		if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) return false;
		return playType != null ? playType.equals(that.playType) : that.playType == null;

	}

	@Override
	public int hashCode() {
		int result = lotteryType != null ? lotteryType.hashCode() : 0;
		result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
		result = 31 * result + (playType != null ? playType.hashCode() : 0);
		return result;
	}

	public String toString(){
		return this.lotteryType+"_"+this.terminalId+"_"+this.playType;
	}
	
	
}
