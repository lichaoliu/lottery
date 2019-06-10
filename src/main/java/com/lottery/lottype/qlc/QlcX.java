package com.lottery.lottype.qlc;

import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class QlcX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}
	
	protected int lotterytype = LotteryType.QLC.value;

	protected static String qlc_01 = "(100301)[-]((0[1-9]|[12][0-9]|30){1}([,](0[1-9]|[12][0-9]|30)){6}[\\^])+";
	
	protected static String qlc_02 = "(100302)[-](0[1-9]|[12][0-9]|30){1}([,](0[1-9]|[12][0-9]|30)){7,14}[\\^]";
	
	protected static String qlc_03 = "(100303)[-](0[1-9]|[12][0-9]|30){1}([,](0[1-9]|[12][0-9]|30)){0,5}[#](0[1-9]|[12][0-9]|30){1}([,](0[1-9]|[12][0-9]|30)){1,20}[\\^]";
	
	
	/**
	 * 按照逗号分割
	 * @param code
	 * @return
	 */
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
	
	
	/**
	 * 同时检查重复和排序
	 * @param code
	 * @return
	 */
	public boolean checkDuplicate(String code) {
		//将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 查排序
	 * @param codes
	 * @return
	 */
	public boolean checkSort(int[] codes) {
		int[] codes2 = codes.clone();
		Arrays.sort(codes2);
		for(int i = 0,j=codes.length;i<j;i++) {
			if(codes[i]!=codes2[i]) {
				return false;
			}
		}
		return true;
	}
	
	
	protected String oneBetPrize(String wincode, List<String> betList) {
		String[] wins = wincode.split("\\|")[0].split(",");
		String special = wincode.split("\\|")[1].trim();
		int count = 0;
		for (String win:wins) {
			if (betList.contains(win)) {
				count++;
			}
		}
		String pirzeLevel = "";
		if (count < 4) {
			
		}else if (count == 4) {
			if (betList.contains(special)) {
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_6.value;
			}else {
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_7.value;
			}
		}else if (count == 5) {
			if (betList.contains(special)) {
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_4.value;
			}else {
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_5.value;
			}
		}else if (count == 6) {
			if (betList.contains(special)) {
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_2.value;
			}else{
				pirzeLevel = LotteryDrawPrizeAwarder.QLC_3.value;
			}
		}else if (count == 7) {
			pirzeLevel = LotteryDrawPrizeAwarder.QLC_1.value;
		}
		
		return pirzeLevel;
	}
	
	
	
	protected void check2delete(StringBuilder builder) {
		if(!StringUtil.isEmpty(builder.toString())) {
			if(builder.toString().endsWith(",")) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
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
	protected String getSortCode01(String betcode) {
		String type = betcode.split("\\-")[0];
		StringBuilder realcode = new StringBuilder(type+"-");
		for(String code:betcode.split("\\-")[1].split("\\^")) {
			realcode.append(sortCode(code)).append("^");
		}
		return realcode.toString();
	}
	
	
	//100302-01,02,03,04,05,06,07,08,09,10^
	protected String getSortCode02(String betcode) {
		StringBuilder realcode = new StringBuilder(betcode.split("\\-")[0]+"-");
		realcode.append(sortCode(betcode.split("\\-")[1].replace("^", ""))).append("^");
		return realcode.toString();
	}
	
	
	//100303-01,02,03#04,05,06,07,08,09,10^
	protected String getSortCode03(String betcode) {
		StringBuilder realcode = new StringBuilder(betcode.split("\\-")[0]+"-");
		String dan = betcode.split("\\-")[1].split("\\#")[0];
		String tuo = betcode.split("\\-")[1].split("\\#")[1].replace("^", "");
		realcode.append(sortCode(dan)).append("#").append(sortCode(tuo)).append("^");
		return realcode.toString();
		
	}
}
