package com.lottery.common.contains.ticket;
/**
 * 逻辑机状态
 * */
public enum LogicMachineStatus {
	not_use(1,"未启用"),
	useing(2,"启用"),
	used(3,"结束"),
	all(0,"全部");
	;
	public int value;
	public String name;
	LogicMachineStatus(int value,String name){
		this.name=name;
		this.value=value;
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
