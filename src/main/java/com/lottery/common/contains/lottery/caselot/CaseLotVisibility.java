package com.lottery.common.contains.lottery.caselot;


/**
 * 合买状态
 */
public enum CaseLotVisibility {

	secrecy(1, "保密"), 
	endOpen(2, "对所有人截止后公开"), 
	open2Follower(3, "对跟单者立即公开"), 
	endOpern2Follower(4, "对跟单者截止后公开"),
	open(5, "对所有人立即公开"),
    all(0,"全部");
	public int value;
	public String memo;
	
	private CaseLotVisibility(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}
	

}
