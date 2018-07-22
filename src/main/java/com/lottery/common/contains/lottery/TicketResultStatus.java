package com.lottery.common.contains.lottery;

public enum TicketResultStatus {
    
    not_win(1,"未中奖"),
    win_little(2,"已中奖"),
    win_big(3,"中大奖"),
    not_draw(4,"未开奖"),
    all(0,"全部");
	public int value;
	public String name;
	private TicketResultStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}
	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	@Override
	public String toString(){
		return "[name="+this.name+",value="+this.value+"]";
	}
}
