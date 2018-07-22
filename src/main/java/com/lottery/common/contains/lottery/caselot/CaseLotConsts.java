package com.lottery.common.contains.lottery.caselot;

import java.math.BigDecimal;

public class CaseLotConsts {

	// 网站兜底百分率
	public static BigDecimal lotteryFillRate = new BigDecimal(100);
	// 禁止撤销合买进度百分率
	public static BigDecimal cancelCaseLotRate = new BigDecimal(50);
	// 禁止撤资合买进度百分率
	public static BigDecimal cancelCaseLotBuyRate = new BigDecimal(20);
	// 置顶合买数量
	public static Integer topCaseLotCount = 10;
}
