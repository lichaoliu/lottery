package com.lottery.lottype.jc.jclq.rfsf;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.LotJingCaiPlayType;

public abstract class JclqrfsfX extends LotJingCaiPlayType{

	private static String rfsfregex = "[(][03]{1}([,][03]){0,1}[)]";
	private static String baseregex = RegexUtil.yyyymmdd+RegexUtil.matchlq+rfsfregex;
	
	private static String regex = baseregex + "([|]" + baseregex+")*"+"[\\^]";
	
	
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return null;
	}
	
	protected boolean isMatchBetcode(String betcode) {
		return betcode.split("-")[1].matches(regex);
	}
	
	@Override
	protected int getLotterytype() {
		return LotteryType.JCLQ_RFSF.value;
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
