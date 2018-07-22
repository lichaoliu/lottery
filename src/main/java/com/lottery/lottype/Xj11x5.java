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
import com.lottery.lottype.xj11x5.Xj11x5DR2;
import com.lottery.lottype.xj11x5.Xj11x5DR3;
import com.lottery.lottype.xj11x5.Xj11x5DR4;
import com.lottery.lottype.xj11x5.Xj11x5DR5;
import com.lottery.lottype.xj11x5.Xj11x5DR6;
import com.lottery.lottype.xj11x5.Xj11x5DR7;
import com.lottery.lottype.xj11x5.Xj11x5DR8;
import com.lottery.lottype.xj11x5.Xj11x5DZ2;
import com.lottery.lottype.xj11x5.Xj11x5DZ3;
import com.lottery.lottype.xj11x5.Xj11x5MQ1;
import com.lottery.lottype.xj11x5.Xj11x5MQ2;
import com.lottery.lottype.xj11x5.Xj11x5MQ3;
import com.lottery.lottype.xj11x5.Xj11x5MR2;
import com.lottery.lottype.xj11x5.Xj11x5MR3;
import com.lottery.lottype.xj11x5.Xj11x5MR4;
import com.lottery.lottype.xj11x5.Xj11x5MR5;
import com.lottery.lottype.xj11x5.Xj11x5MR6;
import com.lottery.lottype.xj11x5.Xj11x5MR7;
import com.lottery.lottype.xj11x5.Xj11x5MR8;
import com.lottery.lottype.xj11x5.Xj11x5MZ2;
import com.lottery.lottype.xj11x5.Xj11x5MZ3;
import com.lottery.lottype.xj11x5.Xj11x5SL3;
import com.lottery.lottype.xj11x5.Xj11x5SL4;
import com.lottery.lottype.xj11x5.Xj11x5SL5;
import com.lottery.lottype.xj11x5.Xj11x5SQ1;
import com.lottery.lottype.xj11x5.Xj11x5SQ2;
import com.lottery.lottype.xj11x5.Xj11x5SQ3;
import com.lottery.lottype.xj11x5.Xj11x5SR2;
import com.lottery.lottype.xj11x5.Xj11x5SR3;
import com.lottery.lottype.xj11x5.Xj11x5SR4;
import com.lottery.lottype.xj11x5.Xj11x5SR5;
import com.lottery.lottype.xj11x5.Xj11x5SR6;
import com.lottery.lottype.xj11x5.Xj11x5SR7;
import com.lottery.lottype.xj11x5.Xj11x5SR8;
import com.lottery.lottype.xj11x5.Xj11x5SZ2;
import com.lottery.lottype.xj11x5.Xj11x5SZ3;

@Component("2008")
public class Xj11x5 extends AbstractLot {
	
	private Logger logger = LoggerFactory.getLogger(Xj11x5.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("200802", new Xj11x5SR2());
		map.put("200803", new Xj11x5SR3());
		map.put("200804", new Xj11x5SR4());
		map.put("200805", new Xj11x5SR5());
		map.put("200806", new Xj11x5SR6());
		map.put("200807", new Xj11x5SR7());
		map.put("200808", new Xj11x5SR8());
		map.put("200831", new Xj11x5SQ1());
		map.put("200832", new Xj11x5SQ2());
		map.put("200833", new Xj11x5SQ3());
		map.put("200834", new Xj11x5SZ2());
		map.put("200835", new Xj11x5SZ3());
		
		map.put("200812", new Xj11x5MR2());
		map.put("200813", new Xj11x5MR3());
		map.put("200814", new Xj11x5MR4());
		map.put("200815", new Xj11x5MR5());
		map.put("200816", new Xj11x5MR6());
		map.put("200817", new Xj11x5MR7());
		map.put("200818", new Xj11x5MR8());
		map.put("200841", new Xj11x5MQ1());
		map.put("200842", new Xj11x5MQ2());
		map.put("200843", new Xj11x5MQ3());
		map.put("200844", new Xj11x5MZ2());
		map.put("200845", new Xj11x5MZ3());
		
		map.put("200822", new Xj11x5DR2());
		map.put("200823", new Xj11x5DR3());
		map.put("200824", new Xj11x5DR4());
		map.put("200825", new Xj11x5DR5());
		map.put("200826", new Xj11x5DR6());
		map.put("200827", new Xj11x5DR7());
		map.put("200828", new Xj11x5DR8());
		map.put("200854", new Xj11x5DZ2());
		map.put("200855", new Xj11x5DZ3());
		
		map.put("200863", new Xj11x5SL3());
		map.put("200864", new Xj11x5SL4());
		map.put("200865", new Xj11x5SL5());
		
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
		return LotteryType.XJ_11X5;
	}
}
