package com.lottery.common.contains.lottery;
/**
 * 竞彩足球，竞彩篮球玩法销售状态
 * */
public enum RaceSaleStatus {
	SALE_UNOPEN(1,"不可销售"),
	SALE_OPEN(2,"可销售"),
    All(0,"全部");
	public int value;
	public String name;
	RaceSaleStatus(int value,String name) {
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
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
	}
