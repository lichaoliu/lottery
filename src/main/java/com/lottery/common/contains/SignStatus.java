package com.lottery.common.contains;

public enum SignStatus {
/***
 * 账务标示
 * */
	in(1,"入账"),
	flat(0,"平账"),
	out(-1,"出账");
	public int value;
	public String name;
	SignStatus(int value,String name){
		this.value=value;
		this.name=name;		
	}
}
