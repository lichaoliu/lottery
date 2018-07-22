package com.lottery.lottype.jc.jczq.spf;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.LotJingCaiPlayType;

public abstract class JczqspfX extends LotJingCaiPlayType{
	
	private static String spfregex = "[(]([013]{1}([,][013]){0,2})[)]";
	private static String baseregex = RegexUtil.yyyymmdd+RegexUtil.matchzq+spfregex;
	
	private static String regex = baseregex + "([|]" + baseregex+")*"+"[\\^]";
	
	
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return null;
	}
	
	public static void main(String[] args) {
		String betcode = "20140101001(0,1,3)|20140101002(0,1,3)|20140101003(0,1,3)^";
		
		System.out.println(regex);
		
		System.out.println(betcode.matches(regex));
		
		System.out.println("20140101001(013)".split("\\|").length);
		
//		System.out.println(betcode.split("\\|").length);
	}

	
	protected boolean isMatchBetcode(String betcode) {
		return betcode.split("-")[1].matches(regex);
	}
	
	@Override
	protected int getLotterytype() {
		return LotteryType.JCZQ_RQ_SPF.value;
	}
	
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,int oneAmount) {
		return getSingleBetAmount(betcode, beishu, oneAmount);
	}
	
	protected long getSingleBetAmountDT(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!isMathchBetcodeDantuo(betcode,getNeedMatchNumByType())) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isMatchDuplication(betcode.replace("#", "|"))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.replace("#", "|"))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return caculateZhushuDT(betcode)*beishu.longValue()*200;
	}

	private boolean isMathchBetcodeDantuo(String betcode, int needMathes) {
		if(!betcode.contains("#")) {
			return false;
		}
		if(!betcode.replace("#", "|").split("-")[1].matches(regex)) {
			return false;
		}
		if("".equals(betcode.split("-")[1].split("#")[0])||"".equals(betcode.split("-")[1].split("#")[1])) {
			return false;
		}
		if(betcode.split("-")[1].replace("#", "|").split("\\|").length<=needMathes) {
			return false;
		}
		if(betcode.split("-")[1].split("\\#")[0].split("\\|").length>=needMathes) {
			return false;
		}
		return true;
	}

}
