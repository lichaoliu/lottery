package com.lottery.core.domain.give;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 彩金账号
 * */
@Entity 
@Table(name="user_account_handsel")
public class UserAccountHandsel implements Serializable {
	/**
	 * 
	 */
    private static final long serialVersionUID = 1643177265986807149L;
	@Id
	@Column(name = "userno")
	private String userno;// 用户编号
	@Column(name = "username")
	private String userName;// 用户名
	@Column(name = "balance")
	private BigDecimal balance;// 金额
	@Column(name = "total_give")
	private BigDecimal totalGive;// 彩金总金额
	@Column(name = "last_transaction")
	private BigDecimal lastTransation;// 上次交易金额
	@Column(name = "payid")
	private String payId;// 交易编号
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getTotalGive() {
		return totalGive;
	}
	public void setTotalGive(BigDecimal totalGive) {
		this.totalGive = totalGive;
	}
	public BigDecimal getLastTransation() {
		return lastTransation;
	}
	public void setLastTransation(BigDecimal lastTransation) {
		this.lastTransation = lastTransation;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}

}
