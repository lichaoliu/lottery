package com.lottery.common.contains.lottery;

public enum DcType {

	
	normal(1, "普通单场"),
	
	sfgg(2, "胜负过关");

	public Integer state;

	public String memo;

	public int intValue() {
		
		return state.intValue();
	}
	
	public Integer value() {
		return state;
	}

	public String memo() {
		return memo;
	}

	DcType(int val, String memo) {
		this.state = val;
		this.memo = memo;
	}
	@Override
	public String toString(){
		return "[name="+this.memo+",value="+this.state+"]";
	}
}
