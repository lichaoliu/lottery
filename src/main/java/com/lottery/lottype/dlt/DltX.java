package com.lottery.lottype.dlt;

import java.util.List;

import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class DltX implements LotPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.CJDLT.value;
	protected static String dlt_01 = "(200101)[-]((0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){4}[|](0[1-9]|1[0-2])[,](0[1-9]|1[0-2])[\\^])+";

	protected static String dlt_02 = "(200102)[-](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){4,17}[|](0[1-9]|1[0-2])([,](0[1-9]|1[0-2])){1,11}[\\^]";

	protected static String dlt_03 = "(200103)[-](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,3}[#](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,22}[|](0[1-9]|1[0-2])([,](0[1-9]|1[0-2])){1,11}[\\^]";
	protected static String dlt_04 = "(200103)[-](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,3}[#](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,22}[|](0[1-9]|1[0-2])[#](0[1-9]|1[0-2]){1}([,](0[1-9]|1[0-2])){0,10}[\\^]";

	protected static String dlt_qian = "(0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-3])){4,17}";
	
	protected static String dlt_hou = "(0[1-9]|1[0-2])([,](0[1-9]|1[0-2])){1,11}";
	
	protected static String dlt_dt_qian = "(0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,3}[#](0[1-9]|[12][0-9]|3[0-5]){1}([,](0[1-9]|[12][0-9]|3[0-5])){0,30}";
	
	protected static String dlt_dt_hou = "(0[1-9]|1[0-2])[#](0[1-9]|1[0-2]){1}([,](0[1-9]|1[0-2])){0,10}";
	
	
	public static boolean checkDuplicate(String code) {
		//将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			return false;
		}

		return true;
	}
	
	/**
	 * 按照逗号分割
	 * @param code
	 * @return
	 */
	public static int[] splitCodeToInt(String code) {
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
	public static boolean checkDuplicate(int[] zhuma) {
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i]==(zhuma[j]))
					return false;
			}
		}
		return true;
	}
	
	
	protected String getPrize(List<String> betcodeQ, List<String> betcodeH,
			String[] wincodeQ, String[] wincodeH,boolean addition) {
		//验证前驱号码
		int successQ=0;
		for (int i = 0; i < 5; i++) {
			if (betcodeQ.contains(wincodeQ[i])) {
				successQ++;
			}
		}
		
		int successH=0;
		for (int i = 0; i < 2; i++) {
			if (betcodeH.contains(wincodeH[i])) {
				successH++;
			}
		}
		
		if (successQ==5 && successH==2) { //5+2：一等奖
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_1A.value+","+LotteryDrawPrizeAwarder.DLT_1B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_1A.value;
		} else if (successQ==5 && successH==1) { //5+1：二等奖
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_2A.value+","+LotteryDrawPrizeAwarder.DLT_2B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_2A.value;
		} else if ((successQ==5 && successH==0)||(successQ==4 && successH==2)) { //5+0,4+2：三等奖
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_3A.value+","+LotteryDrawPrizeAwarder.DLT_3B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_3A.value;
		} else if ((successQ==4 && successH==1)||(successQ==3 && successH==2)) { //4+1,3+2：四等奖 600元
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_4A.value+","+LotteryDrawPrizeAwarder.DLT_4B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_4A.value;
		} else if ((successQ==4 && successH==0)||(successQ==3 && successH==1)||(successQ==2 && successH==2)) { //4+0,3+1,2+2五等奖 100元
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_5A.value+","+LotteryDrawPrizeAwarder.DLT_5B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_5A.value;
		} else if ((successQ==3 && successH==0)||(successQ==1 && successH==2) //3+0 || 1+2 || 2+1 || 0+2 六等奖 5元
				||(successQ==2 && successH==1)||(successQ==0 && successH==2)) { 
			if(addition) {
				return LotteryDrawPrizeAwarder.DLT_6B.value;
			}
			return LotteryDrawPrizeAwarder.DLT_6A.value;
		} else {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
	}
	
	
	protected void check2delete(StringBuilder builder) {
		if(!StringUtil.isEmpty(builder.toString())) {
			if(builder.toString().endsWith(",")) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
	}
	
}
