package com.lottery.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	
	public static String nullValue = "";

	public static boolean isEmpty(String str) {
		if (StringUtils.isEmpty(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		return false;
	}
	
	public static boolean isNotEmpt(String str) {
		return !isEmpty(str);
	}


	
	public static String join(String split, String... values) {
		StringBuilder builder = new StringBuilder();
		for(String s : values) {
			builder.append(s).append(split);
		}
		if(!isEmpty(split)) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString().trim();
	}
	
	public static String join(List<String> list, String split) {
		StringBuilder builder = new StringBuilder();
		for(String s : list) {
			builder.append(s).append(split);
		}
		if(!isEmpty(split)) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString().trim();
	}
}
