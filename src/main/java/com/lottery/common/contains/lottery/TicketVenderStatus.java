package com.lottery.common.contains.lottery;


public enum TicketVenderStatus {
	success(1,"出票成功"),
	failed(2,"出票失败"),
	printing(3,"出票中"),
	not_found(4,"票不存在"),
	system_busy(5,"系统繁忙"),
	duplicated(6,"交易已存在"),
	unkown(7,"未知错误"),
	ticket_limited(8,"限号"),//将限号单独作为一种类型
	all(0,"全部");
	public int value;
	public String name;
	TicketVenderStatus(int value,String name) {
	    this.value=value;
	    this.name=name;
    }
	
	public String toString(){
		return "name="+name+",value="+value;
	}
}
