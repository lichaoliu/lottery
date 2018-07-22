package com.lottery.lottype.xjssc;

import java.util.Arrays;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class XjsscX implements LotPlayType{

	protected int lotterytype = LotteryType.XJSSC.value;
	
	
	protected static String DAN_1D = "(101401)[-]([0-9~]([,][0-9~]){4}[\\^])+";
	protected static String DAN_2D = "(101402)[-]([0-9][,][0-9][\\^])+";
	protected static String DAN_3D = "(101403)[-]([0-9]([,][0-9]){2}[\\^])+";
	protected static String DAN_4D = "(101404)[-]([0-9]([,][0-9]){3}[\\^])+";
	protected static String DAN_5D = "(101405)[-]([0-9]([,][0-9]){4}[\\^])+";
	protected static String DAN_Z3 = "(101406)[-]([0-9]([,][0-9]){2}[\\^])+";
	protected static String DAN_Z6 = "(101407)[-]([0-9]([,][0-9]){2}[\\^])+";
	protected static String DAN_R2 = "(101408)[-]([0-9~]([,][0-9~]){4}[\\^])+";
	protected static String DAN_R3 = "(101409)[-]([0-9~]([,][0-9~]){4}[\\^])+";
	
	
	
	protected static String FU_1D = "(101411)[-]([0-9~]([,][0-9~]){0,9})([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	protected static String FU_2D = "(101412)[-][0-9]([,][0-9]){0,9}[;][0-9]([,][0-9]){0,9}[\\^]";
	protected static String FU_3D = "(101413)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	protected static String FU_4D = "(101414)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){3}[\\^]";
	protected static String FU_5D = "(101415)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){4}[\\^]";
	protected static String FU_R2 = "(101418)[-]([0-9~]([,][0-9~]){0,9})([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	protected static String FU_R3 = "(101419)[-]([0-9~]([,][0-9~]){0,9})([;][0-9~]([,][0-9~]){0,9}){4}[\\^]";
	
	
	
	protected static String OTHER_2Z = "(101423)[-][0-9]([,][0-9]){1,9}[\\^]";
	protected static String OTHER_Z3 = "(101424)[-][0-9]([,][0-9]){1,9}[\\^]";
	protected static String OTHER_Z6 = "(101425)[-][0-9]([,][0-9]){3,9}[\\^]";
	protected static String OTHER_DD = "(101426)[-][1245]([,][1245]){0,3}[;][1245]([,][1245]){0,3}[\\^]";
	protected static String OTHER_5T = "(101427)[-][0-9]([,][0-9]){0,9}([;][0-9]([,][0-9]){0,9}){4}[\\^]";
	
	
	protected static String DAN_QW2 = "(101431)[-]([12]([,][0-9]){2}[\\^])+";
	protected static String DAN_QJ2 = "(101432)[-]([1-5]([,][0-9]){2}[\\^])+";
	
	protected static String FU_QW2 = "(101441)[-][12]([,][12]){0,1}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	protected static String FU_QJ2 = "(101442)[-][1-5]([,][1-5]){0,4}([;][0-9]([,][0-9]){0,9}){2}[\\^]";
	
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
	protected static int[] OTHER_3H_ZHUSHU = { 1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1 };
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}
	
}
