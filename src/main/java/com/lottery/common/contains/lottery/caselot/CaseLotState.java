package com.lottery.common.contains.lottery.caselot;


/**
 * 合买状态
 */
public enum CaseLotState {
	processing(1, "新发起"),
	alreadyBet(2, "已投注"),
	finished(3, "完成"),
	canceled(4, "取消"),
	end(5, "截止"),
	all(0,"全部");
	public int value;
	public String memo;
	private CaseLotState(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}

}
