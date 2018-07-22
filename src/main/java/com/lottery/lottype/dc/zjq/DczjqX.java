package com.lottery.lottype.dc.zjq;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotDcPlayType;

public abstract class DczjqX extends LotDcPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	
	private static String spfregex = "[(][0-7]{1}([,][0-7]){0,7}[)]";
	private static String baseregex = RegexUtil.matchdc+spfregex;
	
	private static String regex = baseregex + "([|]" + baseregex+")*"+"[\\^]";
	
	protected long getSingleBetAmountDF(String betcode, BigDecimal beishu,int oneAmount) {
		if(!isMatchBetcode(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(getTotalMatchNum(betcode)<getNeedMatchNumByType()) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isMatchDuplication(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return caculateZhushuN1(betcode)*beishu.longValue()*200;
	}
	
	
	protected boolean isMatchBetcode(String betcode) {
		return betcode.split("-")[1].matches(regex);
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
		
		return caculateZhushuDTN1(betcode)*beishu.longValue()*200;
	}
	
	
	protected boolean isMathchBetcodeDantuo(String betcode,int needMathes) {
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
	
	
	@Override
	protected int getLotterytype() {
		return LotteryType.DC_ZJQ.value;
	}
	
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingleBetAmount(betcode, beishu, oneAmount);
	}
}
