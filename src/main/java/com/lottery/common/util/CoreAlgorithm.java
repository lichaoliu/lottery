/**
 * 
 */
package com.lottery.common.util;

import java.util.Arrays;

/**
 * @author sunshow
 *
 */
public class CoreAlgorithm {
	/**
	 * 根据欧几里德算法（辗转相除法）求两个数的最大公约数
	 * @param a
	 * @param b
	 * @return a和b的最大公约数
	 */
	public static int gcd(int a, int b) {
		while (b != 0) {
			int r = b;
			b = a % b;
			a = r;
		}
		return a;
	}
	
	/**
	 * 求多个数的最大公约数
	 * @param nums
	 * @return
	 */
	public static int gcd(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		
		// 排除0
		int[] processNums = new int[nums.length];
		int validNumCount = 0;
		for (int num : nums) {
			if (num != 0) {
				processNums[validNumCount ++] = num;
			}
		}
		processNums = Arrays.copyOfRange(processNums, 0, validNumCount);
		
		// 先对数组排序，取到最小值
		Arrays.sort(processNums);
		int min = processNums[0];
		
		for (int i = min; i > 1; i --) {
			boolean found = true;
			for (int num : processNums) {
				if (num % i != 0) {
					found = false;
					break;
				}
			}
			if (found) {
				return i;
			}
		}
		return 1;
	}
}
