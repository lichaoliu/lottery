package com.lottery.common.contains.lottery;


public enum LottypeState {
  
	normal(1, "正常"),
	
	pause(2, "暂停销售"),
	
	stop(0, "停止销售");

	Integer state;

	String memo;

	public int intValue() {
		
		return state.intValue();
	}
	
	public Integer value() {
		return state;
	}

	public String memo() {
		return memo;
	}

	LottypeState(int val, String memo) {
		this.state = val;
		this.memo = memo;
	}
	@Override
	public String toString(){
		return "[name="+this.memo+",value="+this.state+"]";
	}
}
