package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.jc.guanjun.JcGuanJun01;

@Component("3012")
public class JcGuanjun extends AbstractGuanyajunLot{
	
	private Logger logger = LoggerFactory.getLogger(JcGuanjun.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	
	{
		map.put("301201", new JcGuanJun01());
	}

	@Override
	public boolean validate(String betcode, BigDecimal amount,
			BigDecimal beishu, int oneAmount) {
		if(validateBasicBetcode(betcode)==false) {
			return false;
		}
		long totalAmt = 0;
		
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.split("-")[0]);
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
		for(String code:betcode.split("!")) {
			splitedLots.addAll(map.get(code.split("-")[0]).splitByType(code, lotmulti, oneAmount));
		}
		
		long totalamt = 0L;
		for(SplitedLot slot:splitedLots) {
			if(slot.getAmt()>2000000||slot.getLotMulti()>99) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{slot.getBetcode(),String.valueOf(slot.getLotMulti()),String.valueOf(slot.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalamt = totalamt + slot.getAmt();
		}
		
		if(totalamt!=amt) {
			logger.error("split amt_equal totalAmt={} amt={}",new Object[]{String.valueOf(totalamt),String.valueOf(amt)});
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return splitedLots;
	}

	@Override
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,
			BigDecimal afterprizeamt) {
		return false;
	}

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.JC_GUANJUN;
	}

}
