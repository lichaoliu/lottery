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
import com.lottery.lottype.cqkl10.Cqkl10DQ2;
import com.lottery.lottype.cqkl10.Cqkl10DQ3;
import com.lottery.lottype.cqkl10.Cqkl10DR2;
import com.lottery.lottype.cqkl10.Cqkl10DR3;
import com.lottery.lottype.cqkl10.Cqkl10DR4;
import com.lottery.lottype.cqkl10.Cqkl10DR5;
import com.lottery.lottype.cqkl10.Cqkl10DZ2;
import com.lottery.lottype.cqkl10.Cqkl10DZ3;
import com.lottery.lottype.cqkl10.Cqkl10MR2;
import com.lottery.lottype.cqkl10.Cqkl10MR3;
import com.lottery.lottype.cqkl10.Cqkl10MR4;
import com.lottery.lottype.cqkl10.Cqkl10MR5;
import com.lottery.lottype.cqkl10.Cqkl10MS1;
import com.lottery.lottype.cqkl10.Cqkl10MZ2;
import com.lottery.lottype.cqkl10.Cqkl10MZ3;
import com.lottery.lottype.cqkl10.Cqkl10PQ2;
import com.lottery.lottype.cqkl10.Cqkl10PQ3;
import com.lottery.lottype.cqkl10.Cqkl10SH1;
import com.lottery.lottype.cqkl10.Cqkl10SQ2;
import com.lottery.lottype.cqkl10.Cqkl10SQ3;
import com.lottery.lottype.cqkl10.Cqkl10SR2;
import com.lottery.lottype.cqkl10.Cqkl10SR3;
import com.lottery.lottype.cqkl10.Cqkl10SR4;
import com.lottery.lottype.cqkl10.Cqkl10SR5;
import com.lottery.lottype.cqkl10.Cqkl10SS1;
import com.lottery.lottype.cqkl10.Cqkl10SZ2;
import com.lottery.lottype.cqkl10.Cqkl10SZ3;

@Component("1012")
public class Cqkl10 extends AbstractLot {


	private Logger logger = LoggerFactory.getLogger(Cqkl10.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("101210", new Cqkl10SH1());
		map.put("101211", new Cqkl10SS1());
		map.put("101212", new Cqkl10SR2());
		map.put("101213", new Cqkl10SR3());
		map.put("101214", new Cqkl10SR4());
		map.put("101215", new Cqkl10SR5());
		map.put("101216", new Cqkl10SQ2());
		map.put("101217", new Cqkl10SQ3());
		map.put("101218", new Cqkl10SZ2());
		map.put("101219", new Cqkl10SZ3());
		
		map.put("101221", new Cqkl10MS1());
		map.put("101222", new Cqkl10MR2());
		map.put("101223", new Cqkl10MR3());
		map.put("101224", new Cqkl10MR4());
		map.put("101225", new Cqkl10MR5());
		map.put("101228", new Cqkl10MZ2());
		map.put("101229", new Cqkl10MZ3());
		
		map.put("101232", new Cqkl10DR2());
		map.put("101233", new Cqkl10DR3());
		map.put("101234", new Cqkl10DR4());
		map.put("101235", new Cqkl10DR5());
		map.put("101236", new Cqkl10DQ2());
		map.put("101237", new Cqkl10DQ3());
		map.put("101238", new Cqkl10DZ2());
		map.put("101239", new Cqkl10DZ3());
		
		map.put("101246", new Cqkl10PQ2());
		map.put("101247", new Cqkl10PQ3());
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
	public boolean isBigPrize(String prizeinfo, BigDecimal prizeamt,BigDecimal afterprizeamt) {
		
		return false;
	}

	
	
	@Override
	public LotteryType getLotteryType() {
		return LotteryType.CQKL10;
	}
}
