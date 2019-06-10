package com.lottery.common.contains.lottery.coupon;

public enum CouponTypeStatus {
	normal(1, "正常"),
	close(2, "关闭");
	
	private int value;
	private String memo;
	private CouponTypeStatus(int value, String memo) {
		this.value = value;
		this.memo = memo;
	}
	public int getValue() {
		return value;
	}
	public String getMemo() {
		return memo;
	}
}
