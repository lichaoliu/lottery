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
import com.lottery.common.util.RegexUtil;
import com.lottery.lottype.cqssc.Cqssc01;
import com.lottery.lottype.cqssc.Cqssc02;
import com.lottery.lottype.cqssc.Cqssc03;
import com.lottery.lottype.cqssc.Cqssc05;
import com.lottery.lottype.cqssc.Cqssc06;
import com.lottery.lottype.cqssc.Cqssc07;
import com.lottery.lottype.cqssc.Cqssc11;
import com.lottery.lottype.cqssc.Cqssc12;
import com.lottery.lottype.cqssc.Cqssc13;
import com.lottery.lottype.cqssc.Cqssc15;
import com.lottery.lottype.cqssc.Cqssc21;
import com.lottery.lottype.cqssc.Cqssc22;
import com.lottery.lottype.cqssc.Cqssc23;
import com.lottery.lottype.cqssc.Cqssc24;
import com.lottery.lottype.cqssc.Cqssc25;
import com.lottery.lottype.cqssc.Cqssc26;
import com.lottery.lottype.cqssc.Cqssc27;

@Component("1007")
public class Cqssc extends AbstractLot{

	private Logger logger = LoggerFactory.getLogger(Cqssc.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("100701", new Cqssc01());
		map.put("100702", new Cqssc02());
		map.put("100703", new Cqssc03());
		map.put("100705", new Cqssc05());
		map.put("100706", new Cqssc06());
		map.put("100707", new Cqssc07());
		
		map.put("100711", new Cqssc11());
		map.put("100712", new Cqssc12());
		map.put("100713", new Cqssc13());
		map.put("100715", new Cqssc15());
		
		map.put("100721", new Cqssc21());
		map.put("100722", new Cqssc22());
		map.put("100723", new Cqssc23());
		map.put("100724", new Cqssc24());
		map.put("100725", new Cqssc25());
		map.put("100726", new Cqssc26());
		map.put("100727", new Cqssc27());
		
	}
	
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYYYMMDDXXX);
	}

	@Override
	public boolean validate(String betcode, BigDecimal amount,
			BigDecimal beishu, int oneAmount) {
		if(validateBasicBetcode(betcode)==false) {
			return false;
		}
		
		long totalAmt = 0;
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.substring(0, 6));
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
			LotPlayType type = map.get(code.substring(0, 6));
			splitedLots.addAll(type.splitByType(code, lotmulti, oneAmount));
		}
				// 验证过程
		for (SplitedLot s : splitedLots) {
			// 验证拆分后的倍数和金额的限制
			if(s.getAmt()>2000000||s.getLotMulti()>50) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{s.getBetcode(),String.valueOf(s.getLotMulti()),String.valueOf(s.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			// 验证注码格式和金额
			if (!validate(s.getBetcode(), new BigDecimal(s.getAmt()),
					new BigDecimal(s.getLotMulti()), oneAmount) ) {
				logger.error("split validate err:betcode={} lotmulti={} amt={}",new Object[]{s.getBetcode(),String.valueOf(s.getLotMulti()),String.valueOf(s.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
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
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,
			BigDecimal afterprizeamt) {
		String[] prizeInfos = prizeinfo.split(",");
		for(String prize:prizeInfos) {
			if(prize.startsWith("5D")||prize.startsWith("5T5")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.CQSSC;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}
}
