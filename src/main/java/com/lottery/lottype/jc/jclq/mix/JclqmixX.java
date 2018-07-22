package com.lottery.lottype.jc.jclq.mix;

import java.math.BigDecimal;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.LotJingCaiPlayType;

public abstract class JclqmixX extends LotJingCaiPlayType{
	
	
	//300512001-20140501301*3001(3,1)|20140501302*3002(3,1)^
	
	private static String dxfregex = "[(][12]{1}([,][12]){0,1}[)]";
	private static String rfsfregex = "[(][03]{1}([,][03]){0,1}[)]";
	private static String sfcregex = "[(](01|02|03|04|05|06|11|12|13|14|15|16){1}([,](01|02|03|04|05|06|11|12|13|14|15|16)){0,11}[)]";
	private static String sfregex = "[(][03]{1}([,][03]){0,1}[)]";
	private static String commonregex = "[(][0123456789,]*[)]";
	private static String lotregex = "[*](3001|3002|3003|3004)";
	private static String baseregex = RegexUtil.yyyymmdd+RegexUtil.matchlq+lotregex+commonregex;
	
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
			if(String.valueOf(LotteryType.JCLQ_SF.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(sfregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCLQ_RFSF.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(rfsfregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCLQ_DXF.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(dxfregex)) {
					return false;
				}
			}else if(String.valueOf(LotteryType.JCLQ_SFC.getValue()).equals(bet.split("\\*")[1].substring(0, 4))) {
				if(!bet.split("\\*")[1].substring(4).matches(sfcregex)) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	protected int getLotterytype() {
		return LotteryType.JCLQ_HHGG.value;
	}
	
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,int oneAmount) {
		return getSingleBetAmount(betcode, beishu, oneAmount);
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
			if(betcode.contains("*"+String.valueOf(LotteryType.JCLQ_SFC.value))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		return caculateZhushuMix(betcode)*beishu.longValue()*200;
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
			if(betcode.contains("*"+String.valueOf(LotteryType.JCLQ_SFC.value))) {
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
