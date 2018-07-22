package com.lottery.common.dto;


public class ChaseRequest{
	private int amt;//单倍金额
	private int amount;//总金额
	private String phase;//期号
	private int multiple;//倍数
	private int addition;//是否追加
	public int getAmt() {
		return amt;
	}

	public void setAmt(int amt) {
		this.amt = amt;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}
	

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getAddition() {
		return addition;
	}

	public void setAddition(int addition) {
		this.addition = addition;
	}

	
	
}

