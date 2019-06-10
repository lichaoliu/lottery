package com.lottery.common.contains.lottery;
/**
 * 彩期是否修正，用于快彩
 * */
public enum PhaseTimeStatus {
	CORRECTION(1,"已修正"),
	NO_CORRECTION(2,"未修正"),
	ALL(0,"全部");
	public int value;
	public String name;
	
	PhaseTimeStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
