package com.lottery.common.contains.pay;


/**
 * 充值赠送类型
 * */
public enum RechargeGiveType {
	register_recharge_give(1,"注册充值送彩金"),
	recharge_give(2,"充值送彩金"),
	register_give(3,"注册送彩金"),
	
	all(0,"默认");
	public int value;
	public String name;
	 RechargeGiveType(int value,String name){
		 this.value=value;
		 this.name=name;
	 }

	public static RechargeGiveType get(int value){
		RechargeGiveType[] allType=RechargeGiveType.values();
		for(RechargeGiveType type:allType){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	 
	 
}
