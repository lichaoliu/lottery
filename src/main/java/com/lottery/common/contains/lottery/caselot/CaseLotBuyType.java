package com.lottery.common.contains.lottery.caselot;

/**
 * 参与合买状态
 */
public enum CaseLotBuyType {
	starter(1,"发起人"), 
	follower(0,"参与人");
	public int value;
	public String memo;
	CaseLotBuyType(int val, String memo) {
		this.value = val;
		this.memo = memo;
	}

}
