package com.lottery.common.contains;

public enum DrawType {
	bank_draw(1,"银行卡提现"),
	zfb_draw(2,"支付宝提现"),
	hand_raw(3,"手工提现"),
	all(0,"全部");
	public int value;
	public String name;
	private DrawType(int value,String name) {
		this.value=value;
		this.name=name;
	}
}
