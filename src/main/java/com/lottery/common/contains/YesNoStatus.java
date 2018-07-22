package com.lottery.common.contains;

public enum YesNoStatus {
	yes(1,"是"),
	no(0,"否"),
	all(-1,"全部");
	public int value;
	public String name;
	YesNoStatus(int value,String name){
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
