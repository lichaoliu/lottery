package com.lottery.core.domain.print;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="print_balance_report")
public class PrintBalanceReport implements Serializable {

	@Id
	@Column(name="id")
	private String id;
	@Column(name = "machine_id")
	private String machineId;
	@Column(name="amount")
	private BigDecimal amount;
	@Column(name="pay_time")
	private Date payTime;
	@Column(name="create_time")
	private Date createTime;
	@Column(name="balance")
	private BigDecimal balance;
	//1充值 3佣金
	@Column(name="type")
	private Integer type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
