package com.lottery.lottype.d3;

import java.util.Arrays;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class ThreedX implements LotPlayType {
	
	protected int lotterytype = LotteryType.F3D.value;

	protected static String d3_01 = "(100201)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";

	protected static String d3_02 = "(100202)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";

	protected static String d3_03 = "(100203)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";
	
	protected static String d3_04 = "(100204)[-]((0[0-9]|~){1}([,](0[0-9]|~)){2}[\\^])+";
	
	protected static String d3_05 = "(100205)[-]((0[0-9]|~){1}([,](0[0-9]|~)){2}[\\^])+";
	
	protected static String d3_06 = "(100206)[-]((0[0-9]){1}([,](0[0-9])){2}[\\^])+";
	
	protected static String d3_07 = "(100207)[-]((0[0-9])[\\^])+";
	
	protected static String d3_08 = "(100208)[-]((0[0-9])[\\^])+";
	
	protected static String d3_09 = "(100209)[-]((0[0-9])([,](0[0-9]))[\\^])+";

	protected static String d3_11 = "(100211)[-](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[\\^]";

	protected static String d3_12 = "(100212)[-](0[0-9]){1}([,](0[0-9])){1,9}[\\^]";

	protected static String d3_13 = "(100213)[-](0[0-9]){1}([,](0[0-9])){3,9}[\\^]";
	
	protected static String d3_14 = "(100214)[-](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[|](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[|](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[\\^]";
	
	protected static String d3_15 = "(100215)[-](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[|](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[|](0[0-9]|~){1}([,](0[0-9]|~)){0,9}[\\^]";
	
	protected static String d3_16 = "(100216)[-](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[\\^]";
	
	protected static String d3_17 = "(100217)[-](0[0-9]){1}([,](0[0-9])){1,9}[\\^]";
	
	protected static String d3_18 = "(100218)[-](0[0-9]){1}([,](0[0-9])){1,9}[\\^]";
	
	protected static String d3_19 = "(100219)[-](0[0-9]){1}([,](0[0-9])){2,9}[\\^]";

	protected static String d3_21 = "(100221)[-](0[0-9]|1[0-9]|2[0-7]){1}([,](0[0-9]|1[0-9]|2[0-7])){0,27}[\\^]";

	protected static String d3_22 = "(100222)[-](0[1-9]|1[0-9]|2[0-6]){1}([,](0[1-9]|1[0-9]|2[0-6])){0,25}[\\^]";

	protected static String d3_23 = "(100223)[-](0[3-9]|1[0-9]|2[0-4]){1}([,](0[3-9]|1[0-9]|2[0-4])){0,21}[\\^]";
	
	protected static String d3_24 = "(100224)[-](0[0-9]|1[0-9]|2[0-7]){1}([,](0[0-9]|1[0-9]|2[0-7])){0,27}[\\^]";
	
	protected static String d3_25 = "(100225)[-](0[1-9]|1[0-9]|2[0-6]){1}([,](0[1-9]|1[0-9]|2[0-6])){0,25}[\\^]";
	
	protected static String d3_33 = "(100233)[-](0[1-2]){1}([,](0[1-2])){0,1}[\\^]";
	
	protected static String d3_34 = "(100234)[-](0[4-5]){1}([,](0[4-5])){0,1}[\\^]";

	
	public static void main(String[] args) {
		
		System.out.println("100214-01|~,01|01^".matches(d3_14));
		
	}
	
	
	public static int[] dir_3D = {1,3,6,10,15,21,28,36,45,55,63,69,73,75,75,73,69,63,55,45,36,28,21,15,10,6,3,1};
	public static int[] group3_3D = {1,2,1,3,3,3,4,5,4,5,5,4,5,5,4,5,5,4,5,4,3,3,3,1,2,1};
	public static int[] group6_3D = {1,1,2,3,4,5,7,8,9,10,10,10,10,9,8,7,5,4,3,2,1,1};
	public static int[] hzx_3D = {1,2,2,4,5,6,8,10,11,13,14,14,15,15,14,14,13,11,10,8,6,5,4,2,2,1};
	
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
	
	protected boolean isBaozi(String code) {
		int equal = totalEquals(code);
		if (equal == 3) {
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
		String[] codes = code.split(",");
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			return false;
		}
		return true;
	}
	
	
	
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	private boolean checkDuplicate(String[] zhuma) {
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i].equals(zhuma[j]))
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
	
	
	protected String getSortCod11(String betcode) {
		String type = betcode.split("\\-")[0];
		StringBuilder realcode = new StringBuilder(type + "-");
		
		String bai = betcode.split("\\-")[1].split("\\|")[0];
		String shi = betcode.split("\\-")[1].split("\\|")[1];
		String ge = betcode.split("\\-")[1].split("\\|")[2].replace("^", "");
		realcode.append(sortCode(bai)).append("|").append(sortCode(shi)).append("|").append(sortCode(ge)).append("^");
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
	
	
	protected boolean isErTong(String wincode) {
		return totalEquals(wincode)>=1;
	}
	
	protected boolean isErBuTong(String wincode) {
		return totalEquals(wincode)<=1;
	}
	
	protected String getErTong(String wincode) {
		String[] wins = wincode.split(",");
		Arrays.sort(wins);
		
		if(wins[0].equals(wins[1])) {
			return wins[0];
		}
		if(wins[2].equals(wins[1])) {
			return wins[2];
		}
		return "";
	}
	
	
	protected String getZuSanErBuTong(String wincode) {
		String[] wins = wincode.split(",");
		Arrays.sort(wins);
		
		if(wins[0].equals(wins[1])) {
			return wins[2];
		}
		if(wins[2].equals(wins[1])) {
			return wins[0];
		}
		return "";
	}
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}
	
}
