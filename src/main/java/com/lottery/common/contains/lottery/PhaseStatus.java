package com.lottery.common.contains.lottery;

public enum PhaseStatus {
	
	unopen(1,"未开售"),
	open(2,"已开售"),
	close(3,"已关闭"),
	prize_open(4,"已开奖"),
	prize_start(5,"算奖中"),
	prize_end(6,"算奖结束"),	
	prize_ing(7,"开奖中"),//足彩
	result_set(8,"开奖结果已公布"),
    all(0,"全部");
	public int value;
	public String name;
	
	PhaseStatus(int value,String name){
		this.name=name;
		this.value=value;
	}

	public int getValue() {
		return value;
	}

	public static PhaseStatus getPhaseStatus(int value){
		PhaseStatus[] playType=PhaseStatus.values();
		for(PhaseStatus type:playType){
			if(type.value==value){
				return type;
			}
		}
		return null;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
