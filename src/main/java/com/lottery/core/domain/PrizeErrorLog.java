package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="prize_error_log")
public class PrizeErrorLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7623035556497480848L;
	/**
	 * 算奖错误记录表
	 * */
	@Id
	@Column(name="tranaction_id")
	private String transactionId; //交易id
	@Column(name="userno")
	private String userno;//用户名
	@Column(name="lottery_type")
	private Integer lotteryType;//彩种
	@Column(name="order_id")
	private String orderId;
	@Column(name="phase")
	private String phase;//期号
	@Column(name="amt")
	private BigDecimal amt;//错误金额
	@Column(name="balance")
	private BigDecimal balance;//当前账户余额
	@Column(name="draw_balance")
	private BigDecimal drawBalance;//当前账户可用余额
	@Column(name="after_balance")
	private BigDecimal afterBalance;//校验后台的账户余额
	@Column(name="after_draw_balance")
	private BigDecimal afterDrawBalance;//校验后的账户可用余额
	@Column(name="create_time")
	private Date createTime;
	@Column(name="bet_type")
	private Integer betType;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
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
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getAfterBalance() {
		return afterBalance;
	}
	public void setAfterBalance(BigDecimal afterBalance) {
		this.afterBalance = afterBalance;
	}
	public BigDecimal getAfterDrawBalance() {
		return afterDrawBalance;
	}
	public void setAfterDrawBalance(BigDecimal afterDrawBalance) {
		this.afterDrawBalance = afterDrawBalance;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getDrawBalance() {
		return drawBalance;
	}
	public void setDrawBalance(BigDecimal drawBalance) {
		this.drawBalance = drawBalance;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getBetType() {
		return betType;
	}
	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	
	
}
