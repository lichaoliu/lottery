package com.lottery.common.contains.lottery.coupon;

import java.util.Arrays;
import java.util.List;

public enum UserCouponStatus {
	unuse(1, "未使用"),
	used(2, "已使用"),
	invalid(3, "已过期");
	
	
	public static List<Integer> getValues() {
		return Arrays.asList(unuse.value,used.value,invalid.value);
	}
	
	private int value;
	private String memo;
	UserCouponStatus(int value, String memo) {
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
