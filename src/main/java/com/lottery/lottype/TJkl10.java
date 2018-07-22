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
import com.lottery.lottype.tjkl10.TJkl10DQ2;
import com.lottery.lottype.tjkl10.TJkl10DQ3;
import com.lottery.lottype.tjkl10.TJkl10DR2;
import com.lottery.lottype.tjkl10.TJkl10DR3;
import com.lottery.lottype.tjkl10.TJkl10DR4;
import com.lottery.lottype.tjkl10.TJkl10DR5;
import com.lottery.lottype.tjkl10.TJkl10DZ2;
import com.lottery.lottype.tjkl10.TJkl10DZ3;
import com.lottery.lottype.tjkl10.TJkl10MR2;
import com.lottery.lottype.tjkl10.TJkl10MR3;
import com.lottery.lottype.tjkl10.TJkl10MR4;
import com.lottery.lottype.tjkl10.TJkl10MR5;
import com.lottery.lottype.tjkl10.TJkl10MS1;
import com.lottery.lottype.tjkl10.TJkl10MZ2;
import com.lottery.lottype.tjkl10.TJkl10MZ3;
import com.lottery.lottype.tjkl10.TJkl10PQ2;
import com.lottery.lottype.tjkl10.TJkl10PQ3;
import com.lottery.lottype.tjkl10.TJkl10SH1;
import com.lottery.lottype.tjkl10.TJkl10SQ2;
import com.lottery.lottype.tjkl10.TJkl10SQ3;
import com.lottery.lottype.tjkl10.TJkl10SR2;
import com.lottery.lottype.tjkl10.TJkl10SR3;
import com.lottery.lottype.tjkl10.TJkl10SR4;
import com.lottery.lottype.tjkl10.TJkl10SR5;
import com.lottery.lottype.tjkl10.TJkl10SS1;
import com.lottery.lottype.tjkl10.TJkl10SZ2;
import com.lottery.lottype.tjkl10.TJkl10SZ3;

@Component("1004")
public class TJkl10 extends AbstractLot {


	private Logger logger = LoggerFactory.getLogger(TJkl10.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("100410", new TJkl10SH1());
		map.put("100411", new TJkl10SS1());
		map.put("100412", new TJkl10SR2());
		map.put("100413", new TJkl10SR3());
		map.put("100414", new TJkl10SR4());
		map.put("100415", new TJkl10SR5());
		map.put("100416", new TJkl10SQ2());
		map.put("100417", new TJkl10SQ3());
		map.put("100418", new TJkl10SZ2());
		map.put("100419", new TJkl10SZ3());
		
		map.put("100421", new TJkl10MS1());
		map.put("100422", new TJkl10MR2());
		map.put("100423", new TJkl10MR3());
		map.put("100424", new TJkl10MR4());
		map.put("100425", new TJkl10MR5());
		map.put("100428", new TJkl10MZ2());
		map.put("100429", new TJkl10MZ3());
		
		map.put("100432", new TJkl10DR2());
		map.put("100433", new TJkl10DR3());
		map.put("100434", new TJkl10DR4());
		map.put("100435", new TJkl10DR5());
		map.put("100436", new TJkl10DQ2());
		map.put("100437", new TJkl10DQ3());
		map.put("100438", new TJkl10DZ2());
		map.put("100439", new TJkl10DZ3());
		
		map.put("100446", new TJkl10PQ2());
		map.put("100447", new TJkl10PQ3());
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
			if(s.getAmt()>2000000||s.getLotMulti()>99) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{s.getBetcode(),String.valueOf(s.getLotMulti()),String.valueOf(s.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			// 验证注码格式和金额
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
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}

	@Override
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,BigDecimal afterprizeamt) {
		
		return preprizeamt.compareTo(new BigDecimal(1000000))>=0;
	}

	
	
	@Override
	public LotteryType getLotteryType() {
		return LotteryType.TJKL10;
	}
}
