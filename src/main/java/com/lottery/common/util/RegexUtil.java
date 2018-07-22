package com.lottery.common.util;

public class RegexUtil {

	public static String yyyymmdd = "([2-9][0-9])([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|30|31)";
	
	public static String matchzq = "([0-1][0-9][0-9])";
	
	public static String matchlq = "(3[0-9][0-9])";
	
	public static String matchdc = "([0-9][0-9][0-9])";
	
	
	public static String PHASE_YYYYMMDDXXX = "(20)([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|30|31)([0-9][0-9][0-9])";
	public static String PHASE_YYYYMMDDXX = "(20)([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|30|31)([0-9][0-9])";
	public static String PHASE_YYMMDDXX = "([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|30|31)([0-9][0-9])";
	public static String PHASE_YYMMDDXXX = "([0-9][0-9])(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|30|31)([0-9][0-9][0-9])";
	public static String PHASE_YYYYXXX = "(20)([0-9][0-9])([0-9][0-9][0-9])";
	public static String PHASE_YYXXX = "([0-9][0-9])([0-9][0-9][0-9])";
	
}
