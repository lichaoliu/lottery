package com.lottery.lottype.plw;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class PlwX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.PL5.value;
	
	protected static String p5_01 = "(200301)[-]((0[0-9]){1}([,](0[0-9])){4}[\\^])+";
	
	protected static String p5_02 = "(200302)[-](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[\\^]";
	
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
	
	protected String transferWincode(String wincode) {
		String[] wins = wincode.split(",");
		wincode = "0"+wins[0]+","+"0"+wins[1]+","+"0"+wins[2]+","+"0"+wins[3]+","+"0"+wins[4];
		return wincode;
	}
}
