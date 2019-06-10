package com.lottery.core.cache;

import com.lottery.common.cache.IMemcachedObject;


/**
 * 终端送票失败计数的缓存对象
 * @author fengqinyun
 *
 */
public class TerminalFailureCache implements IMemcachedObject {

	private static final long serialVersionUID = -3012987983343787242L;

	/**
	 * 终端编号
	 */
	private Long terminalId;

	/**
	 * 终端权重
	 */
	private int weight;
	
	/**
	 * 本终端打印指定彩种票的截止时间（时间戳）
	 */
	private long terminatedTime;
	
	/**
	 * 指定彩种指定彩期的送票失败次数
	 */
	private long failureCount;
	
	/**
	 * 上次送票失败的时间（时间戳）
	 */
	private long lastFailedTime;
	/**
	 * 票号
	 * */
	private String ticketId;

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}

	public long getLastFailedTime() {
		return lastFailedTime;
	}

	public void setLastFailedTime(long lastFailedTime) {
		this.lastFailedTime = lastFailedTime;
	}

	public long getTerminatedTime() {
		return terminatedTime;
	}

	public void setTerminatedTime(long terminatedTime) {
		this.terminatedTime = terminatedTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TerminalFailureCache that = (TerminalFailureCache) o;

		if (weight != that.weight) return false;
		if (terminatedTime != that.terminatedTime) return false;
		if (failureCount != that.failureCount) return false;
		if (lastFailedTime != that.lastFailedTime) return false;
		return terminalId != null ? terminalId.equals(that.terminalId) : that.terminalId == null;

	}

	@Override
	public int hashCode() {
		int result = terminalId != null ? terminalId.hashCode() : 0;
		result = 31 * result + weight;
		result = 31 * result + (int) (terminatedTime ^ (terminatedTime >>> 32));
		result = 31 * result + (int) (failureCount ^ (failureCount >>> 32));
		result = 31 * result + (int) (lastFailedTime ^ (lastFailedTime >>> 32));
		return result;

	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public String toString() {
		return "TerminalFailureCache{" +
				"terminalId=" + terminalId +
				", weight=" + weight +
				", terminatedTime=" + terminatedTime +
				", failureCount=" + failureCount +
				", lastFailedTime=" + lastFailedTime +
				'}';
	}
}
