package com.lottery.lottype.zc.jinqiu;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class ZucaijinqiuX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.ZC_JQC.value;

	protected static String regex_jqc01 = "(400301)[-]([0123]([,][0123]){7}[\\^])+";
	
	protected static String regex_jqc02 = "(400302)[-][0123]{1,4}([,][0123]{1,4}){7}[\\^]";
	
	
	protected boolean isRepeated(char[] chs) {
		if(chs.length<2) {
			return false;
		}
		
		for(int i=0;i<chs.length-1;i++) {
			for(int j=i+1;j<chs.length;j++) {
				if(chs[i]==chs[j]) {
					return true;
				}
			}
		}
		return false;
	}
}
