package com.lottery.lottype.gxk3;

import java.util.Arrays;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class Gxk3X implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.GXK3.value;
	
	protected static String k310 = "(101010)[-](([1-6])([,]([1-6])){2}[\\^])+";
	
	protected static String k311 = "(101011)[-]([1-6])([,]([1-6])){0,5}[\\^]";
	
	protected static String k312 = "(101012)[-]([1-6])([,]([1-6])){0,4}[*]([1-6])([,]([1-6])){0,4}[\\^]";
	
	protected static String k320 = "(101020)[-](([1-6])[,]([1-6])[\\^])+";
	
	protected static String k321 = "(101021)[-]([1-6])([,]([1-6])){2,5}[\\^]";
	
	protected static String k322 = "(101022)[-]([1-6])[#]([1-6])([,]([1-6])){1,4}[\\^]";
	
	protected static String k330 = "(101030)[-](([1-6])([,]([1-6])){2}[\\^])+";
	
	protected static String k340 = "(101040)[-](([1-6])([,]([1-6])){2}[\\^])+";
	
	protected static String k341 = "(101041)[-]([1-6])([,]([1-6])){3,5}[\\^]";
	
	protected static String k342 = "(101042)[-]([1-6])([,]([1-6])){0,1}[#]([1-6])([,]([1-6])){1,4}[\\^]";
	
	protected static String k360 = "(101060)[-]([4-9]|1[0-7])([,]([4-9]|1[0-7])){0,13}[\\^]";
	
	
	protected void check2delete(StringBuilder builder) {
		if (builder.toString().endsWith(",")) {
			builder.deleteCharAt(builder.length() - 1);
		}
	}
	
	/**
	 * 两位分割一次
	 * 
	 * @param code
	 * @return
	 */
	protected String[] splitCodeString(String code) {
		String codes[] = new String[code.length()];
		for (int i = 0, j = codes.length; i < j; i++) {
			codes[i] = code.substring(i , i + 1);
		}
		return codes;
	}
	
	/**
	 * 判断号码是不是两个相同，一个不同
	 * @param wincode
	 * @return
	 */
	protected boolean isErTong(String wincode) {
		String[] wincodes = wincode.split(",");
		Arrays.sort(wincodes);
		if (wincodes[0].equals(wincodes[1]) && (!wincodes[0].equals(wincodes[2]))) {
			return true;
		}
		if (wincodes[1].equals(wincodes[2]) && (!wincodes[0].equals(wincodes[1]))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 检查注码是否为三同
	 * @param code
	 * @return
	 */
	protected boolean isSantong(String code) {
		String[] codes = code.split(",");
		if(codes[0].equals(codes[1])&&codes[1].equals(codes[2])) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否为二不同
	 * @param code
	 * @return
	 */
	protected boolean isErBuTong(String code) {
		String[] codes = code.split(",");
		if(codes[0].equals(codes[1])&&codes[1].equals(codes[2])) {
			return false;
		}
		return true;
	}
	
	/**
	 * 坚持号码是否为三不同
	 * @param code
	 * @return
	 */
	protected boolean isSanBuTong(String code) {
		String[] codes = code.split(",");
		if(codes[0].equals(codes[1])||codes[0].equals(codes[2])||codes[1].equals(codes[2])) {
			return false;
		}
		return true;
	}
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	protected boolean isDuplicate(String betcode) {
		String[] codes = betcode.split(",");
		for(int i = 0,i1=codes.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(codes[i].equals((codes[j])))
					return true;
			}
		}
		return false;
	}
	
	
	protected int[] convertToInt(String code) {
		String[] codes = code.split(",");
		int[] ints = new int[codes.length];
		for(int i=0;i<codes.length;i++) {
			ints[i] = Integer.parseInt(codes[i]);
		}
		return ints;
	}
}
