package com.lottery.common.contains.lottery;


/**
 * 票据类型。
 */
public enum LotsType {

	/** 订单类型 0-单式上传，1-复式，2-胆拖，3-多方案 */
	Lots_danshishangchuan(0, "单式上传"), Lots_fushi(1, "复式"), Lots_dantuo(2, "胆拖"), Lots_duofangan(3, "多方案");

	Integer state;

	String memo;

	public Integer value() {
		return state;
	}

	public String memo() {
		return memo;
	}

	LotsType(int val, String memo) {
		this.state = val;
		this.memo = memo;
	}
	@Override
	public String toString(){
		return "[name="+this.memo+",value="+this.state+"]";
	}
}
