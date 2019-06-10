package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.RegexUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.ssq.Ssq01;
import com.lottery.lottype.ssq.Ssq02;
import com.lottery.lottype.ssq.Ssq03;

@Component("1001")
public class SSQ extends AbstractLot {


	@Override
	public Map<String, String> getPrizeLevelMapper() {
		Map<String, String> mapper = new HashMap<String, String>();
		mapper.put("1", "1");
		mapper.put("2", "2");
		mapper.put("3", "3");
		mapper.put("4", "4");
		mapper.put("5", "5");
		mapper.put("6", "6");
		
		return mapper;
	}

	private Logger logger = LoggerFactory.getLogger(SSQ.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("01", new Ssq01());
		map.put("02", new Ssq02());
		map.put("03", new Ssq03());
	}
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYYYXXX);
	}

	@Override
	public boolean validate(String betcode, BigDecimal amount,
			BigDecimal beishu, int oneAmount) {
		if(validateBasicBetcode(betcode)==false) {
			return false;
		}
		long totalAmt = 0;
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.substring(4, 6));
			if(null==type) {
				logger.error("注码金额校验错误betcode={} beishu={} amount={} oneAmout={},玩法不存在",new Object[]{betcode,beishu.intValue(),amount,oneAmount});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalAmt = totalAmt + type.getSingleBetAmount(code, beishu, oneAmount);
		}
		return amount.longValue()==totalAmt;
	}

	@Override
	public List<SplitedLot> split(String betcode, int lotmulti, long amt,
			int oneAmount) {
		List<SplitedLot> splitedLots = new ArrayList<SplitedLot>();
		
		long totalAmt = 0;
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.substring(4, 6));
			splitedLots.addAll(type.splitByType(code, lotmulti, oneAmount));
		}
				// 验证过程
		for (SplitedLot s : splitedLots) {
			// 验证拆分后的倍数和金额的限制
			if(s.getAmt()>2000000||s.getLotMulti()>50) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{s.getBetcode(),String.valueOf(s.getLotMulti()),String.valueOf(s.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
//			 验证注码格式和金额
//			if (!validate(s.getBetcode(), new BigDecimal(s.getAmt()),
//					new BigDecimal(s.getLotMulti()), oneAmount) ) {
//				logger.error("split validate err:betcode={} lotmulti={} amt={}",new String[]{s.getBetcode(),String.valueOf(s.getLotMulti()),String.valueOf(s.getAmt())});
//				throw new TiantiancaiException(ErrorCode.Betcode_Error, ErrorCode.Betcode_Error.memo);
//			}
			totalAmt = totalAmt + s.getAmt();
		}

		// 验证金额
		if (totalAmt != amt) {
			logger.error("split amt_equal totalAmt={} amt={}",new Object[]{String.valueOf(totalAmt),String.valueOf(amt)});
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}

		return splitedLots;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(4, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}

	@Override
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,BigDecimal afterprizeamt) {
		String[] prizeInfos = prizeinfo.split(",");
		for(String prize:prizeInfos) {
			if(prize.startsWith("1")||prize.startsWith("2")) {
				return true;
			}
		}
		
		if(preprizeamt.compareTo(new BigDecimal(1000000))>=0) {
			return true;
		}
		return false;
	}

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.SSQ;
	}
	
	@Override
	public BigDecimal computeAchievement(BigDecimal amt, BigDecimal preTaxAmount, BigDecimal afterTaxAmount) {
		BigDecimal result = BigDecimal.ZERO;
		if (amt == null || preTaxAmount == null || afterTaxAmount == null) {
			return result;
		}
		// 盈利=税后奖金-方案金额
		BigDecimal afterAmt = afterTaxAmount.add(amt.negate());
		// 盈利回报 = 税后奖金/方案金额
		BigDecimal afterrate = BigDecimal.ZERO;
		if (amt.compareTo(BigDecimal.ZERO) > 0) {
			afterrate = afterTaxAmount.divide(amt, 0, BigDecimal.ROUND_HALF_UP);
			if (afterAmt.compareTo(new BigDecimal(10000000)) >= 0 || afterrate.compareTo(new BigDecimal(500)) >= 0) {// 税后盈利达到10万或回报达到500倍，可获得两颗金星
				result = result.add(new BigDecimal(20));
			} else if (afterAmt.compareTo(new BigDecimal(90000)) >= 0 || afterrate.compareTo(new BigDecimal(10)) >= 0) {// 税后盈利达到900，或回报达到10倍以上，获得一颗金星
				result = result.add(new BigDecimal(10));
			}
			if (preTaxAmount.compareTo(new BigDecimal(100000000)) >= 0) {// 若用户中得一等奖，则再奖励一颗金星
				result = result.add(new BigDecimal(10));
			}
		}
		return result;
	}
	
	@Override
	public List<String> canUnionTypes() {
		return Arrays.asList(new String[]{"100101"});
	}
}
