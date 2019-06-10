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
import com.lottery.lottype.jxssc.Jxssc01;
import com.lottery.lottype.jxssc.Jxssc02;
import com.lottery.lottype.jxssc.Jxssc03;
import com.lottery.lottype.jxssc.Jxssc04;
import com.lottery.lottype.jxssc.Jxssc05;
import com.lottery.lottype.jxssc.Jxssc06;
import com.lottery.lottype.jxssc.Jxssc07;
import com.lottery.lottype.jxssc.Jxssc08;
import com.lottery.lottype.jxssc.Jxssc09;
import com.lottery.lottype.jxssc.Jxssc11;
import com.lottery.lottype.jxssc.Jxssc12;
import com.lottery.lottype.jxssc.Jxssc13;
import com.lottery.lottype.jxssc.Jxssc14;
import com.lottery.lottype.jxssc.Jxssc15;
import com.lottery.lottype.jxssc.Jxssc18;
import com.lottery.lottype.jxssc.Jxssc19;
import com.lottery.lottype.jxssc.Jxssc21;
import com.lottery.lottype.jxssc.Jxssc22;
import com.lottery.lottype.jxssc.Jxssc23;
import com.lottery.lottype.jxssc.Jxssc24;
import com.lottery.lottype.jxssc.Jxssc25;
import com.lottery.lottype.jxssc.Jxssc26;
import com.lottery.lottype.jxssc.Jxssc27;

@Component("1011")
public class Jxssc extends AbstractLot{

	private Logger logger = LoggerFactory.getLogger(Jxssc.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("101101", new Jxssc01());
		map.put("101102", new Jxssc02());
		map.put("101103", new Jxssc03());
		map.put("101104", new Jxssc04());
		map.put("101105", new Jxssc05());
		map.put("101106", new Jxssc06());
		map.put("101107", new Jxssc07());
		map.put("101108", new Jxssc08());
		map.put("101109", new Jxssc09());
		
		map.put("101111", new Jxssc11());
		map.put("101112", new Jxssc12());
		map.put("101113", new Jxssc13());
		map.put("101114", new Jxssc14());
		map.put("101115", new Jxssc15());
		map.put("101118", new Jxssc18());
		map.put("101119", new Jxssc19());
		
		map.put("101121", new Jxssc21());
		map.put("101122", new Jxssc22());
		map.put("101123", new Jxssc23());
		map.put("101124", new Jxssc24());
		map.put("101125", new Jxssc25());
		map.put("101126", new Jxssc26());
		map.put("101127", new Jxssc27());
		
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
		if(preprizeamt.compareTo(new BigDecimal(1000000))>=0) {
			return true;
		}
		return false;
	}

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.JXSSC;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}
}
