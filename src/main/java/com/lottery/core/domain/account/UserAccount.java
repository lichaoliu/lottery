package com.lottery.core.domain.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="user_account")
public class UserAccount implements Serializable, Cloneable{
	/**
	 * 账号表
	 */
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name="userno")
	private String userno;//用户编号
    @Column(name="balance")
    private BigDecimal balance;//用户余额
    @Column(name="total_balance")
    private BigDecimal totalBalance;//总金额,总金额=用户余额+冻结金额
	@Column(name="draw_balance")
    private BigDecimal drawbalance;//可提现金额
	@Column(name="freeze")
	private BigDecimal freeze;//冻结金额
	@Column(name="last_freeze")
	private BigDecimal lastfreeze;//上次冻结金额
	@Column(name="lasttrade_time")
	private Date lastTradeTime;//上次交易时间
	@Column(name="last_trade_amt")
	private BigDecimal lastTradeamt;//上次交易金额
	@Column(name="total_bet_amt")
	private BigDecimal totalbetamt;//投注总金金额
	@Column(name="total_prize_amt")
	private BigDecimal totalprizeamt;//中奖总金额
	@Column(name="totalgiveamt")
	private BigDecimal totalgiveamt; //赠送总金额
	@Column(name="total_recharge")
	private BigDecimal totalRecharge;//充值总金额
	@Column(name="mac")
	private String mac;              //可变化金额 md5加密
	@Column(name="username")
	private String userName;
	@Column(name="phoneno")
	private String phoneno;//手机号码
	@Column(name="give_balance")
	private BigDecimal giveBalance; //赠送金额
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getDrawbalance() {
		return drawbalance;
	}
	public void setDrawbalance(BigDecimal drawbalance) {
		this.drawbalance = drawbalance;
	}
	public BigDecimal getFreeze() {
		return freeze;
	}
	public void setFreeze(BigDecimal freeze) {
		this.freeze = freeze;
	}
	public BigDecimal getLastfreeze() {
		return lastfreeze;
	}
	public void setLastfreeze(BigDecimal lastfreeze) {
		this.lastfreeze = lastfreeze;
	}
	public Date getLastTradeTime() {
		return lastTradeTime;
	}
	public void setLastTradeTime(Date lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}
	public BigDecimal getLastTradeamt() {
		return lastTradeamt;
	}
	public void setLastTradeamt(BigDecimal lastTradeamt) {
		this.lastTradeamt = lastTradeamt;
	}
	
	public BigDecimal getTotalprizeamt() {
		return totalprizeamt;
	}
	public void setTotalprizeamt(BigDecimal totalprizeamt) {
		this.totalprizeamt = totalprizeamt;
	}
	public BigDecimal getTotalgiveamt() {
		return totalgiveamt;
	}
	public void setTotalgiveamt(BigDecimal totalgiveamt) {
		this.totalgiveamt = totalgiveamt;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public BigDecimal getTotalbetamt() {
		return totalbetamt;
	}
	public void setTotalbetamt(BigDecimal totalbetamt) {
		this.totalbetamt = totalbetamt;
	}
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	
	public BigDecimal getTotalRecharge() {
		return totalRecharge;
	}
	public void setTotalRecharge(BigDecimal totalRecharge) {
		this.totalRecharge = totalRecharge;
	}
	
	public BigDecimal getGiveBalance() {
		return giveBalance;
	}
	public void setGiveBalance(BigDecimal giveBalance) {
		this.giveBalance = giveBalance;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[用户变号："+userno);
		sb.append(",用户名："+userName);
		sb.append(",用户金额："+balance);
		sb.append(",可提现金额:"+drawbalance);
		sb.append(",冻结金额："+freeze+"]");
		return sb.toString();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
}
