package com.lottery.common.contains.lottery;

public enum BetType {

	//投注类型
	bet(1,"投注"),
	chase(2,"追号"),
	hemai(3,"合买"),
	upload(4,"单式上传"),
	bet_merchant(5,"出票商投注"),
	all(0,"全部")
	;
	public int value;
	public String name;
	
	BetType(int value,String name){
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

	public static BetType get(int value) {// 获取所有的值
		BetType[] betTypes = BetType.values();
		for (BetType type : betTypes) {
			if (type.value == value) {
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
