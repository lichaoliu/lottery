package com.lottery.common.contains.lottery.caselot;

public enum AutoJoinState {
	canncel(0, "取消"), 
	available(1, "有效"),
	canncelBystarter(2, "被发起人取消");

	public int value;
	public String memo;
	AutoJoinState(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}
}
