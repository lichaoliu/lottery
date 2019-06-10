package com.lottery.common.contains.account;
/**
 * 返点方式
 * */
public enum RebateType {
    fix(1,"固定返点"),
    float_rebate(2,"按月返点"),
	all(0,"全部");
	public int value;
	public String name;
	
	 RebateType(int value,String name) {
		this.value=value;
		this.name=name;
	}
}
