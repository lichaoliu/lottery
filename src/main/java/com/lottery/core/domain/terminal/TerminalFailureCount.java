package com.lottery.core.domain.terminal;

import java.io.Serializable;

import com.lottery.common.contains.lottery.LotteryType;

/**
 * 每个彩期的终端失败计数，用于分票策略
 * 
 * @author fengqinyun
 */
public class TerminalFailureCount implements Serializable {

	private static final long serialVersionUID = 2639279585340733044L;
	private Long id;
	private Long terminalId;	//终端编号
	private String phase;		//彩期号
	private LotteryType lotteryType;
	private Long failuerCount;	//失败计数
	
	public Long getId() {
		
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public LotteryType getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Long getFailuerCount() {
		return failuerCount;
	}
	public void setFailuerCount(Long failuerCount) {
		this.failuerCount = failuerCount;
	}
	
}
