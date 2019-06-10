package com.lottery.lottype.jc.jczq.bifen;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.LotJingCaiPlayType;

public abstract class JczqbifenfX extends LotJingCaiPlayType{
	
	private static String betregex = "(10|20|21|30|31|32|40|41|42|50|51|52|01|02|12|03|13|23|04|14|24|05|15|25|00|11|22|33|99|09|90)";
	private static String bfregex = "[(]"+betregex+"{1}"+"([,]"+betregex+"){0,30}"+"[)]";
	private static String baseregex = RegexUtil.yyyymmdd+RegexUtil.matchzq+bfregex;
	
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
		return LotteryType.JCZQ_BF.value;
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
