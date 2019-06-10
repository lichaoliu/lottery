package com.lottery.lottype.tjssc;

import java.util.Arrays;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class TjsscX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.TJSSC.value;
	
	
	protected static String DAN_1D = "(101601)[-]([0-9][\\^])+";
	protected static String DAN_2D = "(101602)[-]([0-9][,][0-9][\\^])+";
	protected static String DAN_3D = "(101603)[-]([0-9]([,][0-9]){2}[\\^])+";
	protected static String DAN_4D = "(101604)[-]([0-9]([,][0-9]){3}[\\^])+";
	protected static String DAN_5D = "(101605)[-]([0-9]([,][0-9]){4}[\\^])+";
	protected static String DAN_Z3 = "(101606)[-]([0-9]([,][0-9]){2}[\\^])+";
	protected static String DAN_Z6 = "(101607)[-]([0-9]([,][0-9]){2}[\\^])+";
	
	protected static String FU_1D = "(101611)[-][0-9]([,][0-9]){1,9}[\\^]";
	protected static String FU_2D = "(101612)[-][0-9]([,][0-9]){0,9}[;][0-9]([,][0-9]){0,9}[\\^]";
	protected static String FU_3D = "(101613)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	protected static String FU_4D = "(101614)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){3}[\\^]";
	protected static String FU_5D = "(101615)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){4}[\\^]";
	
	
	
	
	protected static String OTHER_2Z = "(101623)[-][0-9]([,][0-9]){1,9}[\\^]";
	protected static String OTHER_Z3 = "(101624)[-][0-9]([,][0-9]){1,9}[\\^]";
	protected static String OTHER_Z6 = "(101625)[-][0-9]([,][0-9]){3,9}[\\^]";
	protected static String OTHER_DD = "(101626)[-][1245]([,][1245]){0,3}[;][1245]([,][1245]){0,3}[\\^]";
	protected static String OTHER_5T = "(101627)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){4}[\\^]";
	protected static String OTHER_QW2 = "(101628)[-][12]([,][12]){0,1}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	protected static String OTHER_QJ2 = "(101629)[-][12345]([,][12345]){0,1}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	
	
	protected static String OTHER_R1 = "(101631)[-][0-9~]([,][0-9~]){0,9}([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	protected static String OTHER_R2 = "(101632)[-][0-9~]([,][0-9~]){0,9}([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	protected static String OTHER_R3= "(101633)[-][0-9~]([,][0-9~]){0,9}([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	
	
	/**
	 * 
	 * @param code  1,2,3
	 * @return
	 */
	protected boolean isZusan(String code) {
		String[] codes = code.split(",");
		Arrays.sort(codes);
		
		if(codes[0].equals(codes[1])&&(!codes[1].equals(codes[2]))) {
			return true;
		}else if(codes[1].equals(codes[2])&&(!codes[1].equals(codes[0]))) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param code  1,2,3
	 * @return
	 */
	protected boolean isZuliu(String code) {
		String[] codes = code.split(",");
		if(codes[0].equals(codes[1])||codes[1].equals(codes[2])||codes[0].equals(codes[2])) {
			return false;
		}
		return true;
	}
	
	
	protected boolean isBetcodeDuplication(String betcode) {
		return isArrayDuplication(betcode.split(","));
	}
	
	/**
	 * 比较array里是否有重复
	 * 
	 * @param array
	 * @return
	 */
	protected boolean isArrayDuplication(String[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean equalsIgnoreSort(String code1,String code2) {
		if(code1.length()!=code2.length()) {
			return false;
		}
		
		String[] code1s = code1.split(",");
		String[] code2s = code2.split(",");
		
		Arrays.sort(code1s);
		Arrays.sort(code2s);
		
		for(int i=0;i<code1s.length;i++) {
			if(!code1s[i].equals(code2s[i])) {
				return false;
			}
		}
		return true;
	}
	
	
	protected String getDDPrize(int dd) {
		StringBuilder prize = new StringBuilder();
		if(dd%2==0) {
			prize.append("4");
		}else {
			prize.append("5");
		}
		
		if(dd>=5) {
			prize.append("2");
		}else {
			prize.append("1");
		}
		
		return prize.toString();
	}
	
	
	protected static int[] OTHER_2H_ZHUSHU = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9, 8, 7,6, 5, 4, 3, 2, 1 };
	
	
	protected String sortBetcode(String betcode,String split) {
		String[] codes = betcode.split(split);
		Arrays.sort(codes);
		
		StringBuilder realcode = new StringBuilder();
		for(String code:codes) {
			realcode.append(code).append(split);
		}
		if(realcode.toString().endsWith(split)) {
			realcode = realcode.deleteCharAt(realcode.length()-1);
		}
		return realcode.toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println("~".matches("[0-9a]"));
		System.out.println(",".matches("[0-9,~,a-z]"));
	}
	
}
