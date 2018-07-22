package com.lottery.common.contains.lottery;


public enum AccountType {
	bet(1,"投注"),
	charge(2,"充值"),
	give(3,"赠送"),
	drawprize(4,"中奖"),
	withdraw(5,"提现"),
	Chase(6,"追号"),
	hemai(7,"合买"),
	expert(8,"专家订阅"),
	showbet(9,"晒单返提成"),
	followbet(10,"跟单扣提成"),
	deduct(11,"用户扣款"),
	rebate(12,"购彩返点"),
	agency(13,"代理返点"),
    all(0,"全部");
	public int value;
	public String name;
	
	private AccountType(int value,String name) {
		this.name=name;
		this.value=value;
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
	public static AccountType getAccountType(int value){
		AccountType[] playType=AccountType.values();
		for(AccountType type:playType){
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
