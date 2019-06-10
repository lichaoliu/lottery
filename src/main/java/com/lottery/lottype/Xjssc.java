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
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.xjssc.Xjssc01;
import com.lottery.lottype.xjssc.Xjssc02;
import com.lottery.lottype.xjssc.Xjssc03;
import com.lottery.lottype.xjssc.Xjssc04;
import com.lottery.lottype.xjssc.Xjssc05;
import com.lottery.lottype.xjssc.Xjssc06;
import com.lottery.lottype.xjssc.Xjssc07;
import com.lottery.lottype.xjssc.Xjssc08;
import com.lottery.lottype.xjssc.Xjssc09;
import com.lottery.lottype.xjssc.Xjssc11;
import com.lottery.lottype.xjssc.Xjssc12;
import com.lottery.lottype.xjssc.Xjssc13;
import com.lottery.lottype.xjssc.Xjssc14;
import com.lottery.lottype.xjssc.Xjssc15;
import com.lottery.lottype.xjssc.Xjssc18;
import com.lottery.lottype.xjssc.Xjssc19;
import com.lottery.lottype.xjssc.Xjssc23;
import com.lottery.lottype.xjssc.Xjssc24;
import com.lottery.lottype.xjssc.Xjssc25;
import com.lottery.lottype.xjssc.Xjssc26;
import com.lottery.lottype.xjssc.Xjssc27;
import com.lottery.lottype.xjssc.Xjssc31;
import com.lottery.lottype.xjssc.Xjssc32;
import com.lottery.lottype.xjssc.Xjssc41;
import com.lottery.lottype.xjssc.Xjssc42;

@Component("1014")
public class Xjssc extends AbstractLot{

	private Logger logger = LoggerFactory.getLogger(Xjssc.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("101401", new Xjssc01());
		map.put("101402", new Xjssc02());
		map.put("101403", new Xjssc03());
		map.put("101404", new Xjssc04());
		map.put("101405", new Xjssc05());
		map.put("101406", new Xjssc06());
		map.put("101407", new Xjssc07());
		map.put("101408", new Xjssc08());
		map.put("101409", new Xjssc09());
		
		map.put("101411", new Xjssc11());
		map.put("101412", new Xjssc12());
		map.put("101413", new Xjssc13());
		map.put("101414", new Xjssc14());
		map.put("101415", new Xjssc15());
		map.put("101418", new Xjssc18());
		map.put("101419", new Xjssc19());
		
		map.put("101423", new Xjssc23());
		map.put("101424", new Xjssc24());
		map.put("101425", new Xjssc25());
		map.put("101426", new Xjssc26());
		map.put("101427", new Xjssc27());
		
		map.put("101431", new Xjssc31());
		map.put("101432", new Xjssc32());
		
		map.put("101441", new Xjssc41());
		map.put("101442", new Xjssc42());
		
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
		return LotteryType.XJSSC;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}
}
