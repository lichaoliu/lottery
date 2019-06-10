package com.lottery.common.contains;


/**
 * 票批次状态
 * */
public enum TicketBatchStatus {

	SEND_WAITING(1,"待送票"),
	SEND_SUCCESS(2,"送票成功"),
	SEND_FAILURE(3,"送票失败"),
	SEND_QUEUED(4,"送票队列等待"),
	ALL(0,"全部");
	public int value;
	public String name;
	TicketBatchStatus(int value,String name){
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
