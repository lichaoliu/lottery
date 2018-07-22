package com.lottery.common.contains.lottery;

public enum PhaseEncaseStatus {
	draw_not(1,"未派奖"),
	draw_start(2,"派奖中"),
	draw_end(3,"派奖结束"),
	
    all(0,"全部");
	public int value;
	public String name;
	
	PhaseEncaseStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
