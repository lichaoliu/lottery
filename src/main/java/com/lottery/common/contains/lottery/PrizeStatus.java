package com.lottery.common.contains.lottery;

public enum PrizeStatus {
    not_open(1,"未开奖"),
	draw(2,"已开奖"),
	rewarded(3,"已派奖"),
	not_win(4,"未中奖"),
	unable_to_draw(5,"无法开奖"),
	result_null(6,"结果未公布"),
	result_set(7,"结果已公布"),
	all(0,"全部");
	public int value;
	public String name;
	PrizeStatus(int value,String name){
		this.value=value;
		this.name=name;
	}
}
