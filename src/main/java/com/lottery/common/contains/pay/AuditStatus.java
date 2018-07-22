package com.lottery.common.contains.pay;

import java.util.Arrays;
import java.util.List;

public enum AuditStatus {
	
	autid_not(1,"未审核"),
	autid_pass(2,"审核通过"),
	autid_fail(3,"审核未通过"),
	all(0,"全部")
	;
	
	public int value;
	public String name;
	
	AuditStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
	public static AuditStatus get(int value){
		AuditStatus[] lotteryType=AuditStatus.values();
		for(AuditStatus type:lotteryType){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}


	 @Override
	 public String toString(){  //自定义的public方法   
	 return "[value:"+value+",name:"+name+"]";   
	 }   

	
	public static List<AuditStatus> get(){//获取所有的值
		return Arrays.asList(AuditStatus.values());
	}
}
