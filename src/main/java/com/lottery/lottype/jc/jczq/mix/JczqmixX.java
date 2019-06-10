package com.lottery.lottype.jc.jczq.mix;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.LotJingCaiPlayType;

public abstract class JczqmixX extends LotJingCaiPlayType{
	
	private static String bfbetregex = "(10|20|21|30|31|32|40|41|42|50|51|52|01|02|12|03|13|23|04|14|24|05|15|25|00|11|22|33|99|09|90)";
	private static String spfregex = "[(]([013]{1}([,][013]){0,2})[)]";
	private static String bfregex = "[(]"+bfbetregex+"{1}"+"([,]"+bfbetregex+"){0,30}"+"[)]";
	private static String zjqregex = "[(][0-7]{1}([,][0-7]){0,7}[)]";
	private static String bqcregex = "[(](00|01|03|11|10|13|33|30|31){1}([,](00|01|03|11|10|13|33|30|31)){0,8}[)]";
	private static String rfspqregex = "[(]([013]{1}([,][013]){0,2})[)]";
	private static String commonregex = "[(][0123456789,]*[)]";
	private static String lotregex = "[*](3006|3007|3008|3009|3010)";
	private static String baseregex = RegexUtil.yyyymmdd+RegexUtil.matchzq+lotregex+commonregex;
	
	private static String regex = baseregex + "([|]" + baseregex+")*"+"[\\^]";
	
	
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return null;
	}


	
	protected boolean isMatchBetcode(String betcode) {
		betcode = betcode.split("-")[1];
		if(!betcode.matches(regex)) {
			return false;
		}
		
		String[] bets = betcode.replace("^", "").split("\\|");
		
		for(String bet:bets) {
			if(String.valueOf(LotteryType.JCZQ_BF.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(bfregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCZQ_BQC.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(bqcregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCZQ_JQS.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(zjqregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCZQ_RQ_SPF.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(rfspqregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCZQ_SPF_WRQ.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(spfregex)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	protected int getLotterytype() {
		return LotteryType.JCZQ_HHGG.value;
	}
	
	protected long getSingBetAmountMix(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!isMatchBetcode(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(getTotalMatchNum(betcode)<getNeedMatchNumByType()) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isMatchDuplicationMix(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplicationMix(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(getNeedMatchNumByType()>4) {
			if(betcode.contains("*"+String.valueOf(LotteryType.JCZQ_BF.value))
					||betcode.contains("*"+String.valueOf(LotteryType.JCZQ_BQC.value))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		if(getNeedMatchNumByType()>6) {
			if(betcode.contains("*"+String.valueOf(LotteryType.JCZQ_JQS.value))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		return caculateZhushuMix(betcode)*beishu.longValue()*200;
	}
	
	
	
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,int oneAmount) {
		return getSingleBetAmount(betcode, beishu, oneAmount);
	}
	
	protected long getSingleBetAmountDT(String betcode, BigDecimal beishu,
			int oneAmount) {
		//
		if(!isMathchBetcodeDantuo(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isMatchDuplicationMix(betcode.replace("#", "|"))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//胆码和托码场次不能重复
		if(isMatchDuplicationDT(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplicationMix(betcode.replace("#", "|"))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(getNeedMatchNumByType()>4) {
			if(betcode.contains("*"+String.valueOf(LotteryType.JCZQ_BF.value))
					||betcode.contains("*"+String.valueOf(LotteryType.JCZQ_BQC.value))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		if(getNeedMatchNumByType()>6) {
			if(betcode.contains("*"+String.valueOf(LotteryType.JCZQ_JQS.value))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		return caculateZhushuDTMix(betcode)*beishu.longValue()*200;
	}

	/**
	 * 胆拖注码要包含#
	 * 胆拖注码除去#要符合基本注码规则
	 * 胆码和托码不能为空
	 * 注码除去#要符合对应玩法注码规则
	 * 场次总数要大于要求总数
	 * 胆码总数要小于要求总数
	 * @param betcode
	 * @return
	 */
	private boolean isMathchBetcodeDantuo(String betcode) {
		if(!betcode.contains("#")) {
			return false;
		}
		if(!betcode.replace("#", "|").split("-")[1].matches(regex)) {
			return false;
		}
		if("".equals(betcode.split("-")[1].split("#")[0])||"".equals(betcode.split("-")[1].split("#")[1])) {
			return false;
		}
		if(isMatchBetcode(betcode.replace("#", "|"))==false) {
			return false;
		}
		if(getTotalMatchNum(betcode.replace("#", "|"))<=getNeedMatchNumByType()) {
			return false;
		}
		if(getTotalMatchNum(betcode.split("\\#")[0])>=getNeedMatchNumByType()) {
			return false;
		}
		return true;
	}

}
