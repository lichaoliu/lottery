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
import com.lottery.lottype.jx11x5.Jx11x5DR2;
import com.lottery.lottype.jx11x5.Jx11x5DR3;
import com.lottery.lottype.jx11x5.Jx11x5DR4;
import com.lottery.lottype.jx11x5.Jx11x5DR5;
import com.lottery.lottype.jx11x5.Jx11x5DR6;
import com.lottery.lottype.jx11x5.Jx11x5DR7;
import com.lottery.lottype.jx11x5.Jx11x5DR8;
import com.lottery.lottype.jx11x5.Jx11x5DZ2;
import com.lottery.lottype.jx11x5.Jx11x5DZ3;
import com.lottery.lottype.jx11x5.Jx11x5MQ1;
import com.lottery.lottype.jx11x5.Jx11x5MQ2;
import com.lottery.lottype.jx11x5.Jx11x5MQ3;
import com.lottery.lottype.jx11x5.Jx11x5MR2;
import com.lottery.lottype.jx11x5.Jx11x5MR3;
import com.lottery.lottype.jx11x5.Jx11x5MR4;
import com.lottery.lottype.jx11x5.Jx11x5MR5;
import com.lottery.lottype.jx11x5.Jx11x5MR6;
import com.lottery.lottype.jx11x5.Jx11x5MR7;
import com.lottery.lottype.jx11x5.Jx11x5MR8;
import com.lottery.lottype.jx11x5.Jx11x5MZ2;
import com.lottery.lottype.jx11x5.Jx11x5MZ3;
import com.lottery.lottype.jx11x5.Jx11x5SQ1;
import com.lottery.lottype.jx11x5.Jx11x5SQ2;
import com.lottery.lottype.jx11x5.Jx11x5SQ3;
import com.lottery.lottype.jx11x5.Jx11x5SR2;
import com.lottery.lottype.jx11x5.Jx11x5SR3;
import com.lottery.lottype.jx11x5.Jx11x5SR4;
import com.lottery.lottype.jx11x5.Jx11x5SR5;
import com.lottery.lottype.jx11x5.Jx11x5SR6;
import com.lottery.lottype.jx11x5.Jx11x5SR7;
import com.lottery.lottype.jx11x5.Jx11x5SR8;
import com.lottery.lottype.jx11x5.Jx11x5SZ2;
import com.lottery.lottype.jx11x5.Jx11x5SZ3;

@Component("2006")
public class JiangXi11c5 extends AbstractLot {
	
	private Logger logger = LoggerFactory.getLogger(JiangXi11c5.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("200602", new Jx11x5SR2());
		map.put("200603", new Jx11x5SR3());
		map.put("200604", new Jx11x5SR4());
		map.put("200605", new Jx11x5SR5());
		map.put("200606", new Jx11x5SR6());
		map.put("200607", new Jx11x5SR7());
		map.put("200608", new Jx11x5SR8());
		map.put("200631", new Jx11x5SQ1());
		map.put("200632", new Jx11x5SQ2());
		map.put("200633", new Jx11x5SQ3());
		map.put("200634", new Jx11x5SZ2());
		map.put("200635", new Jx11x5SZ3());
		
		map.put("200612", new Jx11x5MR2());
		map.put("200613", new Jx11x5MR3());
		map.put("200614", new Jx11x5MR4());
		map.put("200615", new Jx11x5MR5());
		map.put("200616", new Jx11x5MR6());
		map.put("200617", new Jx11x5MR7());
		map.put("200618", new Jx11x5MR8());
		map.put("200641", new Jx11x5MQ1());
		map.put("200642", new Jx11x5MQ2());
		map.put("200643", new Jx11x5MQ3());
		map.put("200644", new Jx11x5MZ2());
		map.put("200645", new Jx11x5MZ3());
		
		map.put("200622", new Jx11x5DR2());
		map.put("200623", new Jx11x5DR3());
		map.put("200624", new Jx11x5DR4());
		map.put("200625", new Jx11x5DR5());
		map.put("200626", new Jx11x5DR6());
		map.put("200627", new Jx11x5DR7());
		map.put("200628", new Jx11x5DR8());
		map.put("200654", new Jx11x5DZ2());
		map.put("200655", new Jx11x5DZ3());
		
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
		return LotteryType.JX_11X5;
	}

}
