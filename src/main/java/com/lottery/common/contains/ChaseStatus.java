package com.lottery.common.contains;


public enum ChaseStatus {
	NORMAL(1,"正常"),
	CANCEL(2,"取消"),
	END(3,"结束"),
	ALL(0,"全部"),
	;
	public int value;
	public String name;
	ChaseStatus(int value, String name){
		this.value=value;
		this.name=name;
	}
	
	
	public static ChaseStatus getChaseStatus(int value) {
		ChaseStatus[] status = ChaseStatus.values();
		for(ChaseStatus type:status) {
			if(type.value==value) {
				return type;
			}
		}
		return null;
	}
}
