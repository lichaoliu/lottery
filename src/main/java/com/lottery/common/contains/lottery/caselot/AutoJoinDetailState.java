package com.lottery.common.contains.lottery.caselot;

public enum AutoJoinDetailState {
	success(1, "跟单成功"), 
	biggerCanBet(2, "投注金额大于可参与金额"), 
	caselotFull(4, "合买满员"), 
	drawamtNotEnough(5, "账户余额不足"), 
	lessMinAmt(7, "投注金额小于最低认购金额");

	public int state;
	public String memo;
	AutoJoinDetailState(int state, String memo) {
		this.state = state;
		this.memo = memo;
	}
}
