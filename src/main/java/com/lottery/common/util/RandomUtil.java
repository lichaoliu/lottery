package com.lottery.common.util;

import org.apache.commons.lang.RandomStringUtils;

public class RandomUtil {

private RandomUtil(){}
	
	/**
	 * 生成随机数
	 * <li>生成后的内容包含数字和大小写字母</li>
	 * @param count 需要生成的长度
	 * @return 随机数
	 */
	public static String randomAlphanumeric(int count){
		return RandomStringUtils.randomAlphanumeric(count);
	}
	
	/**
	 * 生成随机数
	 * <li>生成后的内容只包含数字</li>
	 * @param count 需要生成的长度
	 * @return 随机数
	 */
	public static String randomNumeric(int count){
		return RandomStringUtils.randomNumeric(count);
	}


	/**
	 *返回随机数
	 * @param max 最大值
	 * @param min 最小值
	 *
	 *
	 方法1
	 (数据类型)(最小值+Math.random()*(最大值-最小值+1))
	 例:
	 (int)(1+Math.random()*(10-1+1))
	 * */

	public static int getRandom(int max,int min){
		return (int)(min+Math.random()*(max-min+1));
	}
}
