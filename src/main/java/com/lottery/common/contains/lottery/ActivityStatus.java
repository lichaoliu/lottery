package com.lottery.common.contains.lottery;

public enum ActivityStatus {

	open(0,"启动"),
	stop(1,"禁用");
	
	ActivityStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	private int value;
	private String name;
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
