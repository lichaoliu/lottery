package com.lottery.lottype.zc.renjiu;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.MathUtils;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class ZucairenjiuX implements LotPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	protected int lotterytype = LotteryType.ZC_RJC.value;

	protected static String regex_rjc01 = "(400201)[-]([013~]([,][013~]){13}[\\^])+";
	
	protected static String regex_rjc02 = "(400202)[-][013~]{1,3}([,][013~]{1,3}){13}[\\^]";
	
	protected static String regex_rjc03 = "(400203)[-][013~]{1,3}([,][013~]{1,3}){13}[#][013~]{1,3}([,][013~]{1,3}){13}[\\^]";
	
	
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
	
	
	
	protected long dantuoZhushu(String bet) {
		String[] bets = bet.split("-")[1].replace("^", "").split("\\#");
		int dtotal=0,d2=0,d3=0,t1=0,t2=0,t3=0,choose=0;
		for(String s1:bets[0].split(",")) {
			if(!s1.equals("~")) {
				dtotal = dtotal + 1;
				if(s1.length()==2) {
					d2 = d2 + 1;
				}else if(s1.length()==3) {
					d3 = d3 + 1;
				}
			}
		}
		
		for(String s2:bets[1].split(",")) {
			if(!s2.equals("~")) {
				if(s2.length()==1) {
					t1 = t1 + 1;
				}else if(s2.length()==2) {
					t2 = t2 + 1;
				}else if(s2.length()==3) {
					t3 = t3 + 1;
				}
			}
		}
		choose = 9 - dtotal;
		long m = MathUtils.exp(2, d2);
		long n = MathUtils.exp(3, d3);
		long result = 0;
		for (int i = 0; i <= t1; i++) {
			for (int j = 0; j <= t2; j++) {
				int k = choose - i - j;
				if(k <= t3 && k >= 0) {
					long nk1, nk2, nk3, exp1, exp2;
					nk1 = (t1 == 0 ? 1 : MathUtils.combine(t1, i));//
					nk2 = (t2 == 0 ? 1 : MathUtils.combine(t2, j));
					nk3 = (t3 == 0 ? 1 : MathUtils.combine(t3, k));
					exp1 = MathUtils.exp(2, j);
					exp2 = MathUtils.exp(3, k);
					result = result + nk1 * nk2 * nk3 * exp1 * exp2;
					//System.out.println("i=" + i + ",j=" + j + ",k=" + k);
				}
				
			}
		}
		if (m > 0)
			result = result * m;
		if (n > 0)
			result = result * n;
		bets = null;
		return result;
	}

}
