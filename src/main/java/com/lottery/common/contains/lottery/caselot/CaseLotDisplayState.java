package com.lottery.common.contains.lottery.caselot;

/**
 * 合买显示状态，互联网合买方案的显示状态。用于前端显示。 用于处理合买订单流程的内部状态
 */
public enum CaseLotDisplayState {

	subscribing(1, "认购中"), 
	full(2, "满员"), 
	success(3, "成功"), 
	canceledByUser(4, "撤单"), 
	canceledBySystem(5, "流单"),
	win(6, "已中奖"), 
	fail(7, "出票失败"),
    all(0,"全部");
	int value;
	String memo;
	public int value() {
		return value;
	}

	public String memo() {
		return memo;
	}

	CaseLotDisplayState(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}
}
