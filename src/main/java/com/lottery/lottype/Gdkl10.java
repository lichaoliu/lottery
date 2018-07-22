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
import com.lottery.lottype.gdkl10.Gdkl10DQ2;
import com.lottery.lottype.gdkl10.Gdkl10DQ3;
import com.lottery.lottype.gdkl10.Gdkl10DR2;
import com.lottery.lottype.gdkl10.Gdkl10DR3;
import com.lottery.lottype.gdkl10.Gdkl10DR4;
import com.lottery.lottype.gdkl10.Gdkl10DR5;
import com.lottery.lottype.gdkl10.Gdkl10DZ2;
import com.lottery.lottype.gdkl10.Gdkl10DZ3;
import com.lottery.lottype.gdkl10.Gdkl10MR2;
import com.lottery.lottype.gdkl10.Gdkl10MR3;
import com.lottery.lottype.gdkl10.Gdkl10MR4;
import com.lottery.lottype.gdkl10.Gdkl10MR5;
import com.lottery.lottype.gdkl10.Gdkl10MS1;
import com.lottery.lottype.gdkl10.Gdkl10MZ2;
import com.lottery.lottype.gdkl10.Gdkl10MZ3;
import com.lottery.lottype.gdkl10.Gdkl10PQ2;
import com.lottery.lottype.gdkl10.Gdkl10PQ3;
import com.lottery.lottype.gdkl10.Gdkl10SH1;
import com.lottery.lottype.gdkl10.Gdkl10SQ2;
import com.lottery.lottype.gdkl10.Gdkl10SQ3;
import com.lottery.lottype.gdkl10.Gdkl10SR2;
import com.lottery.lottype.gdkl10.Gdkl10SR3;
import com.lottery.lottype.gdkl10.Gdkl10SR4;
import com.lottery.lottype.gdkl10.Gdkl10SR5;
import com.lottery.lottype.gdkl10.Gdkl10SS1;
import com.lottery.lottype.gdkl10.Gdkl10SZ2;
import com.lottery.lottype.gdkl10.Gdkl10SZ3;

@Component("1013")
public class Gdkl10 extends AbstractLot {
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}


	private Logger logger = LoggerFactory.getLogger(Gdkl10.class);
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("101310", new Gdkl10SH1());
		map.put("101311", new Gdkl10SS1());
		map.put("101312", new Gdkl10SR2());
		map.put("101313", new Gdkl10SR3());
		map.put("101314", new Gdkl10SR4());
		map.put("101315", new Gdkl10SR5());
		map.put("101316", new Gdkl10SQ2());
		map.put("101317", new Gdkl10SQ3());
		map.put("101318", new Gdkl10SZ2());
		map.put("101319", new Gdkl10SZ3());
		
		map.put("101321", new Gdkl10MS1());
		map.put("101322", new Gdkl10MR2());
		map.put("101323", new Gdkl10MR3());
		map.put("101324", new Gdkl10MR4());
		map.put("101325", new Gdkl10MR5());
		map.put("101328", new Gdkl10MZ2());
		map.put("101329", new Gdkl10MZ3());
		
		map.put("101332", new Gdkl10DR2());
		map.put("101333", new Gdkl10DR3());
		map.put("101334", new Gdkl10DR4());
		map.put("101335", new Gdkl10DR5());
		map.put("101336", new Gdkl10DQ2());
		map.put("101337", new Gdkl10DQ3());	
		map.put("101338", new Gdkl10DZ2());
		map.put("101339", new Gdkl10DZ3());
		
		map.put("101346", new Gdkl10PQ2());
		map.put("101347", new Gdkl10PQ3());
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
		return LotteryType.GDKL10;
	}
}
