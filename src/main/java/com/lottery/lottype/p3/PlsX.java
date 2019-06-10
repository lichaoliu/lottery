package com.lottery.lottype.p3;

import java.util.Arrays;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class PlsX implements LotPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.PL3.value;
	
	protected static String p3_01 = "(200201)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";

	protected static String p3_02 = "(200202)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";

	protected static String p3_03 = "(200203)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";

	protected static String p3_11 = "(200211)[-](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[\\^]";

	protected static String p3_12 = "(200212)[-](0[0-9]){1}([,](0[0-9])){1,9}[\\^]";

	protected static String p3_13 = "(200213)[-](0[0-9]){1}([,](0[0-9])){3,9}[\\^]";

	protected static String p3_21 = "(200221)[-](0[0-9]|1[0-9]|2[0-7]){1}([,](0[0-9]|1[0-9]|2[0-7])){0,27}[\\^]";

	protected static String p3_22 = "(200222)[-](0[1-9]|1[0-9]|2[0-6]){1}([,](0[1-9]|1[0-9]|2[0-6])){0,25}[\\^]";

	protected static String p3_23 = "(200223)[-](0[3-9]|1[0-9]|2[0-4]){1}([,](0[3-9]|1[0-9]|2[0-4])){0,21}[\\^]";

	
	public static int[] dir_P3 = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
	public static int[] group3_P3 = {1,2,1,3,3,3,4,5,4,5,5,4,5,5,4,5,5,4,5,4,3,3,3,1,2,1};
	public static int[] group6_P3 = {1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1};
	
	
	/**
	 * 排三组三和值转单式
	 * @param num
	 * @return
	 */
	public static String getGroup3DanshiBetcode(int num) {
		String betcode = "";
		switch (num) {
		case 1:
			betcode = "200202-00,00,01^";
			break;
		case 2:
			betcode = "200202-00,01,01^00,00,02^";
			break;
		case 3:
			betcode = "200202-00,00,03^";
			break;
		case 4:
			betcode = "200202-00,00,04^00,02,02^01,01,02^";
			break;
		case 5:
			betcode = "200202-00,00,05^01,02,02^01,01,03^";
			break;
		case 6:
			betcode = "200202-00,00,06^01,01,04^00,03,03^";
			break;
		case 7:
			betcode = "200202-00,00,07^01,01,05^02,02,03^01,03,03^";
			break;
		case 8:
			betcode = "200202-00,00,08^01,01,06^02,02,04^02,03,03^00,04,04^";
			break;
		case 9:
			betcode = "200202-00,00,09^01,04,04^02,02,05^01,01,07^";
			break;
		case 10:
			betcode = "200202-01,01,08^02,02,06^03,03,04^02,04,04^00,05,05^";
			break;
		case 11:
			betcode = "200202-01,01,09^02,02,07^03,03,05^03,04,04^01,05,05^";
			break;
		case 12:
			betcode = "200202-02,02,08^03,03,06^02,05,05^00,06,06^";
			break;
		case 13:
			betcode = "200202-02,02,09^03,03,07^04,04,05^03,05,05^01,06,06^";
			break;
		case 14:
			betcode = "200202-03,03,08^04,04,06^04,05,05^02,06,06^00,07,07^";
			break;
		case 15:
			betcode = "200202-03,03,09^04,04,07^03,06,06^01,07,07^";
			break;
		case 16:
			betcode = "200202-04,04,08^05,05,06^04,06,06^02,07,07^00,08,08^";
			break;
		case 17:
			betcode = "200202-04,04,09^05,05,07^05,06,06^03,07,07^01,08,08^";
			break;
		case 18:
			betcode = "200202-05,05,08^04,07,07^02,08,08^00,09,09^";
			break;
		case 19:
			betcode = "200202-05,05,09^06,06,07^05,07,07^03,08,08^01,09,09^";
			break;
		case 20:
			betcode = "200202-06,06,08^06,07,07^04,08,08^02,09,09^";
			break;
		case 21:
			betcode = "200202-06,06,09^05,08,08^03,09,09^";
			break;
		case 22:
			betcode = "200202-07,07,08^06,08,08^04,09,09^";
			break;
		case 23:
			betcode = "200202-07,07,09^07,08,08^05,09,09^";
			break;
		case 24:
			betcode = "200202-06,09,09^";
			break;
		case 25:
			betcode = "200202-08,08,09^07,09,09^";
			break;
		case 26:
			betcode = "200202-08,09,09^";
			break;

		default:
			break;
		}
		
		return betcode;
	}
	
	
	/**
	 * 排三组六和值转单式
	 * @param num
	 * @return
	 */
	public static String getGroup6DanshiBetcode(int num) {
		String betcode = "";
		switch (num) {
		case 3:
			betcode = "200203-00,01,02^";
			break;
		case 4:
			betcode = "200203-00,01,03^";
			break;
		case 5:
			betcode = "200203-00,01,04^00,02,03^";
			break;
		case 6:
			betcode = "200203-00,01,05^00,02,04^01,02,03^";
			break;
		case 7:
			betcode = "200203-00,01,06^00,02,05^00,03,04^01,02,04^";
			break;
		case 8:
			betcode = "200203-00,01,07^00,02,06^00,03,05^01,02,05^01,03,04^";
			break;
		case 9:
			betcode = "200203-00,01,08^00,02,07^00,03,06^00,04,05^01,02,06^;200203-01,03,05^02,03,04^";
			break;
		case 10:
			betcode = "200203-00,01,09^00,02,08^00,03,07^00,04,06^01,02,07^;200203-01,03,06^01,04,05^02,03,05^";
			break;
			
		case 11:
			betcode = "200203-00,05,06^00,03,08^01,04,06^01,02,08^00,04,07^;200203-01,03,07^02,04,05^02,03,06^00,02,09^";
			break;
		case 12:
			betcode = "200203-00,05,07^00,04,08^00,03,09^01,04,07^01,03,08^;200203-03,04,05^01,02,09^01,05,06^02,04,06^02,03,07^";
			break;
		case 13:
			betcode = "200203-00,06,07^00,05,08^00,04,09^01,04,08^01,03,09^;200203-01,05,07^03,04,06^02,05,06^02,04,07^02,03,08^";
			break;
		case 14:
			betcode = "200203-02,03,09^00,06,08^03,05,06^01,04,09^00,05,09^;200203-02,05,07^01,05,08^02,04,08^03,04,07^01,06,07^";
			break;
		case 15:
			betcode = "200203-00,06,09^03,05,07^04,05,06^02,05,08^01,05,09^;200203-02,06,07^02,04,09^00,07,08^01,06,08^03,04,08^";
			break;
		case 16:
			betcode = "200203-03,04,09^04,05,07^03,06,07^03,05,08^01,07,08^;200203-01,06,09^02,06,08^02,05,09^00,07,09^";
			break;
		case 17:
			betcode = "200203-02,07,08^04,06,07^04,05,08^03,06,08^03,05,09^;200203-01,07,09^02,06,09^00,08,09^";
			break;
		case 18:
			betcode = "200203-03,07,08^02,07,09^03,06,09^04,06,08^05,06,07^;200203-04,05,09^01,08,09^";
			break;
		case 19:
			betcode = "200203-03,07,09^02,08,09^04,06,09^05,06,08^04,07,08^";
			break;
		case 20:
			betcode = "200203-03,08,09^05,06,09^05,07,08^04,07,09^";
			break;
		case 21:
			betcode = "200203-06,07,08^05,07,09^04,08,09^";
			break;
		case 22:
			betcode = "200203-05,08,09^06,07,09^";
			break;
		case 23:
			betcode = "200203-06,08,09^";
			break;
		case 24:
			betcode = "200203-07,08,09^";
			break;

		default:
			break;
		}
		return betcode;
	}
	
	public static void main(String[] args) {
		for(int num=3;num<=24;num++) {
			String code = getGroup6DanshiBetcode(num);
			for(String onecode:code.split(";")) {
				onecode = onecode.split("\\-")[1];
				for(String item:onecode.split("\\^")) {
					int total = Integer.parseInt(item.split(",")[0])+Integer.parseInt(item.split(",")[1])+Integer.parseInt(item.split(",")[2]);
					if(num!=total) {
						System.out.println(onecode);
					}else {
						System.out.println(num+"pass");
					}
				}
			}
		}
		
	}
	
	/**
	 * 检查3D组注码是不是两个注码相等
	 * 
	 * @param code
	 * @return
	 */
	protected boolean isZusan(String code) {
		int equal = totalEquals(code);
		if (equal == 1) {
			return true;
		}
		return false;
	}
	
	protected boolean isZuliu(String code) {
		int equal = totalEquals(code);
		if (equal == 0) {
			return true;
		}
		return false;
	}
	
	protected boolean isBaozi(String code) {
		int equal = totalEquals(code);
		if (equal == 3) {
			return true;
		}
		return false;
	}
	
	protected boolean isMatchIgnoreSequence(String betcode, String wincode) {
		int[] betcodeInt = new int[] {
				Integer.parseInt(betcode.substring(0, 2)),
				Integer.parseInt(betcode.substring(3, 5)),
				Integer.parseInt(betcode.substring(6, 8)) };
		int[] wincodeInt = new int[] {
				Integer.parseInt(wincode.substring(0, 2)),
				Integer.parseInt(wincode.substring(3, 5)),
				Integer.parseInt(wincode.substring(6, 8)) };
		Arrays.sort(betcodeInt);
		Arrays.sort(wincodeInt);
		if (betcodeInt[0] == wincodeInt[0] && betcodeInt[1] == wincodeInt[1]
				&& betcodeInt[2] == wincodeInt[2]) {
			return true;
		}
		return false;
	}
	
	private int totalEquals(String code) {
		int equal = 0;
		String[] codes = code.split(",");

		if (codes[0].equals(codes[1])) {
			equal = equal + 1;
		}
		if (codes[0].equals(codes[2])) {
			equal = equal + 1;
		}
		if (codes[1].equals(codes[2])) {
			equal = equal + 1;
		}
		return equal;
	}
	
	
	protected void check2delete(StringBuilder builder) {
		if(!StringUtil.isEmpty(builder.toString())) {
			if(builder.toString().endsWith(",")) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
	}
	
	
	public boolean checkDuplicate(String code) {
		//将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			return false;
		}
		return true;
	}
	
	
	private int[] splitCodeToInt(String code) {
		int[] codes = new int[code.split(",").length];
		String[] codeStrs = code.split(",");
		for(int i=0,j=codeStrs.length;i<j;i++) {
			codes[i] = Integer.parseInt(codeStrs[i]);
		}
		return codes;
	}
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	private boolean checkDuplicate(int[] zhuma) {
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i]==(zhuma[j]))
					return false;
			}
		}
		return true;
	}
	
	
	protected String getSumWincode(String wincode) {
		int sum = Integer.parseInt(wincode.substring(0, 2))
				+ Integer.parseInt(wincode.substring(3, 5))
				+ Integer.parseInt(wincode.substring(6, 8));
		return sum>=10?sum+"":"0"+sum;
	}
	
	
	/**
	 * 逗号分割的注码从小到大排序
	 * @param code
	 * @return
	 */
	private String sortCode(String code) {
		String[] codes = code.split(",");
		Arrays.sort(codes);
		StringBuilder realcode = new StringBuilder();
		
		for(String c:codes) {
			realcode.append(c).append(",");
		}
		check2delete(realcode);
		return realcode.toString();
	}
	
	//100301-01,07,10,11,12,13,18^04,05,08,11,19,21,28^
	protected String getSortCode02(String betcode) {
		String type = betcode.split("\\-")[0];
		StringBuilder realcode = new StringBuilder(type + "-");
		for (String code : betcode.split("\\-")[1].split("\\^")) {
			realcode.append(sortCode(code)).append("^");
		}
		return realcode.toString();
	}

	protected String getSortCode03(String betcode) {
		return getSortCode02(betcode);
	}
	
	
	protected String getSortCode11(String betcode) {
		String type = betcode.split("\\-")[0];
		StringBuilder realcode = new StringBuilder(type + "-");
		
		String bai = betcode.split("\\-")[1].split("\\|")[0];
		String shi = betcode.split("\\-")[1].split("\\|")[1];
		String ge = betcode.split("\\-")[1].split("\\|")[2].replace("^", "");
		realcode.append(sortCode(bai)).append("|").append(sortCode(shi)).append("|").append(ge).append("^");
		return realcode.toString();
	}
	
	//100212-00,01,01,03,04,05,06,07,08,09^
	protected String getSortCode12(String betcode) {
		StringBuilder realcode = new StringBuilder(betcode.split("\\-")[0]
				+ "-");
		realcode.append(sortCode(betcode.split("\\-")[1].replace("^", "")))
				.append("^");
		return realcode.toString();
	}
	
	
	protected String getSortCode13(String betcode) {
		return getSortCode12(betcode);
	}
	
	
	protected String transferWincode(String wincode) {
		String[] wins = wincode.split(",");
		wincode = "0"+wins[0]+","+"0"+wins[1]+","+"0"+wins[2];
		return wincode;
	}
}
