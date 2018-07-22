package com.lottery.common.contains.lottery;


/**
 * 票状态
 * */
public enum TicketStatus {

	
	
	UNALLOTTED(1,"未分配"),
	UNSENT(2,"未送票"),
	PRINTING(3,"出票中"),
	PRINT_SUCCESS(4,"出票成功"),
	PRINT_FAILURE(5,"出票失败"),
	CANCELLED(6,"已撤单"),
	UNINIT(7,"不可用"),
	CHANGING(8,"转票中"),
	ALL(0,"全部");
	public int value;
	public String name;
	TicketStatus(int value,String name) {
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
	
	public static TicketStatus get(int value){
		TicketStatus[] ticketStatusList= TicketStatus.values();
		for(TicketStatus type:ticketStatusList){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	
}
