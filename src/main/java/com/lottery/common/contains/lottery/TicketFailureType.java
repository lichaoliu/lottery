package com.lottery.common.contains.lottery;


/**
 * 出票失败类型
 * */
public enum TicketFailureType {
	RETRY_WAITING(1,"等待重试"),
	PRINT_LIMITED(2,"出票限号"),
	PRINT_EXPIRED(3,"票已过期"),
	PRINT_FAILURE(4,"未出票撤单"),
	ALL(0,"全部");
	public int value;
	public String name;
	TicketFailureType(int value,String name){
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
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
	
}
