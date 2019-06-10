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
import com.lottery.lottype.gd11x5.Gd11x5DR2;
import com.lottery.lottype.gd11x5.Gd11x5DR3;
import com.lottery.lottype.gd11x5.Gd11x5DR4;
import com.lottery.lottype.gd11x5.Gd11x5DR5;
import com.lottery.lottype.gd11x5.Gd11x5DR6;
import com.lottery.lottype.gd11x5.Gd11x5DR7;
import com.lottery.lottype.gd11x5.Gd11x5DR8;
import com.lottery.lottype.gd11x5.Gd11x5DZ2;
import com.lottery.lottype.gd11x5.Gd11x5DZ3;
import com.lottery.lottype.gd11x5.Gd11x5MQ1;
import com.lottery.lottype.gd11x5.Gd11x5MQ2;
import com.lottery.lottype.gd11x5.Gd11x5MQ3;
import com.lottery.lottype.gd11x5.Gd11x5MR2;
import com.lottery.lottype.gd11x5.Gd11x5MR3;
import com.lottery.lottype.gd11x5.Gd11x5MR4;
import com.lottery.lottype.gd11x5.Gd11x5MR5;
import com.lottery.lottype.gd11x5.Gd11x5MR6;
import com.lottery.lottype.gd11x5.Gd11x5MR7;
import com.lottery.lottype.gd11x5.Gd11x5MR8;
import com.lottery.lottype.gd11x5.Gd11x5MZ2;
import com.lottery.lottype.gd11x5.Gd11x5MZ3;
import com.lottery.lottype.gd11x5.Gd11x5SL3;
import com.lottery.lottype.gd11x5.Gd11x5SL4;
import com.lottery.lottype.gd11x5.Gd11x5SL5;
import com.lottery.lottype.gd11x5.Gd11x5SQ1;
import com.lottery.lottype.gd11x5.Gd11x5SQ2;
import com.lottery.lottype.gd11x5.Gd11x5SQ3;
import com.lottery.lottype.gd11x5.Gd11x5SR2;
import com.lottery.lottype.gd11x5.Gd11x5SR3;
import com.lottery.lottype.gd11x5.Gd11x5SR4;
import com.lottery.lottype.gd11x5.Gd11x5SR5;
import com.lottery.lottype.gd11x5.Gd11x5SR6;
import com.lottery.lottype.gd11x5.Gd11x5SR7;
import com.lottery.lottype.gd11x5.Gd11x5SR8;
import com.lottery.lottype.gd11x5.Gd11x5SZ2;
import com.lottery.lottype.gd11x5.Gd11x5SZ3;

@Component("2005")
public class Gd11x5 extends AbstractLot {
	
	private Logger logger = LoggerFactory.getLogger(Gd11x5.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("200502", new Gd11x5SR2());
		map.put("200503", new Gd11x5SR3());
		map.put("200504", new Gd11x5SR4());
		map.put("200505", new Gd11x5SR5());
		map.put("200506", new Gd11x5SR6());
		map.put("200507", new Gd11x5SR7());
		map.put("200508", new Gd11x5SR8());
		map.put("200531", new Gd11x5SQ1());
		map.put("200532", new Gd11x5SQ2());
		map.put("200533", new Gd11x5SQ3());
		map.put("200534", new Gd11x5SZ2());
		map.put("200535", new Gd11x5SZ3());
		
		map.put("200512", new Gd11x5MR2());
		map.put("200513", new Gd11x5MR3());
		map.put("200514", new Gd11x5MR4());
		map.put("200515", new Gd11x5MR5());
		map.put("200516", new Gd11x5MR6());
		map.put("200517", new Gd11x5MR7());
		map.put("200518", new Gd11x5MR8());
		map.put("200541", new Gd11x5MQ1());
		map.put("200542", new Gd11x5MQ2());
		map.put("200543", new Gd11x5MQ3());
		map.put("200544", new Gd11x5MZ2());
		map.put("200545", new Gd11x5MZ3());
		
		map.put("200522", new Gd11x5DR2());
		map.put("200523", new Gd11x5DR3());
		map.put("200524", new Gd11x5DR4());
		map.put("200525", new Gd11x5DR5());
		map.put("200526", new Gd11x5DR6());
		map.put("200527", new Gd11x5DR7());
		map.put("200528", new Gd11x5DR8());
		map.put("200554", new Gd11x5DZ2());
		map.put("200555", new Gd11x5DZ3());
		
		map.put("200563", new Gd11x5SL3());
		map.put("200564", new Gd11x5SL4());
		map.put("200565", new Gd11x5SL5());
		
	}
	
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYMMDDXX);
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
			if(s.getAmt()>2000000||s.getLotMulti()>99) {
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
		return preprizeamt.compareTo(new BigDecimal(1000000))>=0;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}
	
	

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.GD_11X5;
	}
}
