package com.lottery.common.contains.lottery.caselot;

/**
 * 参与合买状态
 */
public enum CaseLotBuyState {
	success(1,"购买成功"),
	processing(2,"购买中"),
	systemRetract(3,"流单"),
	handRetract(4,"撤销"),
	orderfailRetract(5,"合买出票失败"),
	all(0,"全部");
	public int value;
	public String memo;
	public int value() {
		return value;
	}
	public String memo() {
		return memo;
	}
	CaseLotBuyState(int val, String memo) {
		this.value = val;
		this.memo = memo;
	}

}
