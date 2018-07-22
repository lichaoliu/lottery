package com.lottery.lottype.zc.sfc;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class ZucaisfcX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.ZC_SFC.value;

	protected static String regex_spf01 = "(400101)[-]([013]([,][013]){13}[\\^])+";
	
	protected static String regex_spf02 = "(400102)[-][013]{1,3}([,][013]{1,3}){13}[\\^]";
	
	
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
