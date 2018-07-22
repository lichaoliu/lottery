package com.lottery.common.contains.lottery;

import java.util.Arrays;
import java.util.List;


public enum OrderStatus {
  
	PRINT_WAITING(1,"等待支付"),
	PRINTING(2,"出票中"),
	PRINTED(3,"已出票"),
	UNPRINTED_OBSOLETE(5,"未出票作废"),
	PRINTED_FAILED(7,"出票失败"),
	CANCELLED(9,"已撤单"),
	HALF_PRINTED(10,"部分出票"),
	REVOKED(11,"已流单"),//合买专用
	NOT_SPLIT(12,"未拆票"),
	UNPAY_OBSOLETE(13,"未支付作废"),
	ALL(0,"全部");
	public int value;
	public String name;
	OrderStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	public static OrderStatus getOrderStatus(int value){
		OrderStatus[] orderStatus= OrderStatus.values();
		for(OrderStatus type:orderStatus){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	public static List<OrderStatus> get(){//获取所有的值
		return Arrays.asList(OrderStatus.values());
	}
	public static OrderStatus get(int value){
		OrderStatus[] orderStatus= OrderStatus.values();
		for(OrderStatus type:orderStatus){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
