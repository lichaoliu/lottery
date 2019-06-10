package com.lottery.common.contains.account;

public enum StatisticType {
    day(1,"按天统计"),
    month(2,"按月统计"),
	all(0,"全部");
	public int value;
	public String name;
	
	StatisticType(int value,String name) {
		this.value=value;
		this.name=name;
	}
}
