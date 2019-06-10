package com.lottery.common;

import java.math.BigDecimal;

/**
 * 战绩积分类型
 */
public enum AchievementType {

	// 金星10分，钻石50分，奖杯250分，皇冠1250分
	goldStar(10), diamond(50), cup(250), crown(1250);

	BigDecimal state;

	public int intValue() {
		return state.intValue();
	}

	public BigDecimal value() {
		return state;
	}

	AchievementType(int val) {
		this.state = new BigDecimal(val);
	}
}
