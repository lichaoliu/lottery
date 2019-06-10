package com.lottery.common.contains.lottery;

import java.util.Arrays;
import java.util.List;

public enum OrderResultStatus {
	
	refund(1,"已退票"),
	not_win(2,"未中奖"),
	win_big(3,"中大奖"),
	win_already(4,"已中奖"),
	not_open(5,"未开奖"),
	unable_to_draw(6,"无法开奖"),
	prizing(7,"开奖中"),
	all(0,"全部");
	
	public int value;
	public String name;
	private OrderResultStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public static OrderResultStatus getOrderResultStatus(int value){
		OrderResultStatus[] orderResultStatus= OrderResultStatus.values();
		for(OrderResultStatus type:orderResultStatus){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	public static List<OrderResultStatus> get(){//获取所有的值
		return Arrays.asList(OrderResultStatus.values());
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
