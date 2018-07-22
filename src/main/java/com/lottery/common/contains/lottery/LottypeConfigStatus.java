package com.lottery.common.contains.lottery;

public enum LottypeConfigStatus {
	nomarl(1,"正常"),
	paused(2,"停售"),
	no_used(3,"不可用"),
	all(0,"全部");
	public int value;
	public String name;
	LottypeConfigStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	
	

}
