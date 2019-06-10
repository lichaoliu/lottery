package com.lottery.common.contains;

public enum DrawAmountStatus {
/**
 * 提现状态
 * */
	success(1,"成功"),
	failure(2,"失败"),
	haschecked(3,"审核成功"),
	handing(4,"处理中"),
	notdo(5,"未处理"),
	notexits(6,"提现不存在"),
	all(0,"全部");
    public int value;	
	public String name;
	private DrawAmountStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
