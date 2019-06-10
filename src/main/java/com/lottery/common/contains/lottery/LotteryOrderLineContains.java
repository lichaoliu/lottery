package com.lottery.common.contains.lottery;

public class LotteryOrderLineContains {
	/**
	 * 单式几注之间的分割,如单式5注
	 * */
	public static String SINGLE_LINE="^";
	/**
	 * 注码之间的分隔符，如果 双色球 红球|篮球
	 * */
	public static String ELEMENT_LINE="|";
	/**
	 * 不 同玩法之间的分隔符
	 * */
	public static String CODE_LINE="!";
	/**
	 * 胆码，托码分割
	 * */
	public static String DAN_LINE="#";
	
	public static String NULL_LINE = "~";
	
	//奖金优化分割注码倍数金额
	public static String PRIZE_OPTIMIZE_SPLITLINE = ":";

}
