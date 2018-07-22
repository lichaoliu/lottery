package com.lottery.common.contains.ticket;

public enum TicketBatchSendResultProcessType {
    success(1,"成功"),
    reallot(2,"重分终端"),
    retry(3,"重试"),
	all(0,"全部");
	public int value;
	public String name;
	TicketBatchSendResultProcessType(int value,String name){
		this.value=value;
		this.name=name;
	}
}
