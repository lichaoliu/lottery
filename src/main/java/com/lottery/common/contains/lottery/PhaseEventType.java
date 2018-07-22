package com.lottery.common.contains.lottery;

import java.util.Arrays;
import java.util.List;

public enum PhaseEventType {
	swich(1,"彩期切换"),
	close(2,"销售截止");
	public int value;
	public String name;
	PhaseEventType(int value,String name){
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
	public static List<PhaseEventType> get(){
		return Arrays.asList(PhaseEventType.values());
	}
    public static PhaseEventType get(int value){
	 PhaseEventType[] orderStatus= PhaseEventType.values();
		for(PhaseEventType type:orderStatus){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
}
