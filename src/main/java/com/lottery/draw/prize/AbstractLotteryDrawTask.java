/**
 * 
 */
package com.lottery.draw.prize;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.draw.ILotteryDrawTask;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractLotteryDrawTask implements Serializable, ILotteryDrawTask {
	private static final long serialVersionUID = -6949573002864898848L;

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Integer lotteryType;
	private String phase;
	private Long lastMatchNum;
	private String winCode;
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
	public Long getLastMatchNum() {
		return lastMatchNum;
	}
	public void setLastMatchNum(Long lastMatchNum) {
		this.lastMatchNum = lastMatchNum;
	}
	public String getWinCode() {
		return winCode;
	}
	public void setWinCode(String winCode) {
		this.winCode = winCode;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[彩种(").append(lotteryType);
		sb.append("),期号(").append(phase);
		sb.append("),开奖号码(").append(winCode);
		sb.append("),最大场次:").append(lastMatchNum);
		sb.append("]");
		return sb.toString();
	}
	
}
