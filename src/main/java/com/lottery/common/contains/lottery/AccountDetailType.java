package com.lottery.common.contains.lottery;
/**
 * 账户明细类型
 * */
public enum AccountDetailType {
	deduct(1,"扣款"),
	freeze(2,"冻结"),
	unfreeze(3,"解冻"),
	chase_freeze_process(4,"追号总冻结"),
	add(5,"加款"),

	half_deduct(6,"部分扣款"),
	refund(7,"退款"),
	bet_compensation(8,"投注失败赔付"),
	hemai_deduct(9,"合买扣款"),
	hemai_canncelAdd(10,"合买失败返款"),
	hemai_yongjin(11,"合买佣金"),
	hemai_safeFreeze(12,"合买保底冻结"),
	hemai_retUnfreeze(13,"合买保底解冻"),
	hemai_safebetUnfreeze(14,"合买保底未购买解冻"),
	
	error_add(15,"奖金扣回"),
	hemai_yongjin_error(16,"佣金扣回"),

    red_package_give(17,"增送红包"),

	red_package_receive(18,"领取红包"),

	all(0,"全部");
	
	public int value;
	public String name;
	AccountDetailType(int value,String name){
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
	public static AccountDetailType getAccountDetailType(int value){
		AccountDetailType[] types = AccountDetailType.values();
		for(AccountDetailType type : types){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
}
