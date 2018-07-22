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
import com.lottery.lottype.ahk3.Ahk310;
import com.lottery.lottype.ahk3.Ahk311;
import com.lottery.lottype.ahk3.Ahk312;
import com.lottery.lottype.ahk3.Ahk320;
import com.lottery.lottype.ahk3.Ahk321;
import com.lottery.lottype.ahk3.Ahk322;
import com.lottery.lottype.ahk3.Ahk330;
import com.lottery.lottype.ahk3.Ahk332;
import com.lottery.lottype.ahk3.Ahk340;
import com.lottery.lottype.ahk3.Ahk341;
import com.lottery.lottype.ahk3.Ahk342;
import com.lottery.lottype.ahk3.Ahk350;
import com.lottery.lottype.ahk3.Ahk360;

@Component("1008")
public class AnHuiKuai3 extends AbstractLot{
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYMMDDXXX);
	}



	private Logger logger = LoggerFactory.getLogger(AnHuiKuai3.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("100810", new Ahk310());
		map.put("100811", new Ahk311());
		map.put("100812", new Ahk312());
		map.put("100820", new Ahk320());
		map.put("100821", new Ahk321());
		map.put("100822", new Ahk322());
		map.put("100830", new Ahk330());
		map.put("100832", new Ahk332());
		map.put("100840", new Ahk340());
		map.put("100841", new Ahk341());
		map.put("100842", new Ahk342());
		map.put("100850", new Ahk350());
		map.put("100860", new Ahk360());
		
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
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,
			BigDecimal afterprizeamt) {
		return false;
	}

	
	
	@Override
	public LotteryType getLotteryType() {
		return LotteryType.AHK3;
	}

}
