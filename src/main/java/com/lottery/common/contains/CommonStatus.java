package com.lottery.common.contains;

public enum CommonStatus {
	failure(0,"失败"),
	success(1,"成功"),
	waiting(2,"处理中"),
	undo(3,"未处理"),
	enddo(4,"已处理"),
	cancelled(5,"已撤销"),
	;
	public int value;
	public String name;
	CommonStatus(int value,String name){
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
