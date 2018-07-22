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
import com.lottery.lottype.hljkl10.Hljkl10DQ2;
import com.lottery.lottype.hljkl10.Hljkl10DQ3;
import com.lottery.lottype.hljkl10.Hljkl10DR2;
import com.lottery.lottype.hljkl10.Hljkl10DR3;
import com.lottery.lottype.hljkl10.Hljkl10DR4;
import com.lottery.lottype.hljkl10.Hljkl10DR5;
import com.lottery.lottype.hljkl10.Hljkl10DZ2;
import com.lottery.lottype.hljkl10.Hljkl10DZ3;
import com.lottery.lottype.hljkl10.Hljkl10MR2;
import com.lottery.lottype.hljkl10.Hljkl10MR3;
import com.lottery.lottype.hljkl10.Hljkl10MR4;
import com.lottery.lottype.hljkl10.Hljkl10MR5;
import com.lottery.lottype.hljkl10.Hljkl10MS1;
import com.lottery.lottype.hljkl10.Hljkl10MZ2;
import com.lottery.lottype.hljkl10.Hljkl10MZ3;
import com.lottery.lottype.hljkl10.Hljkl10PQ2;
import com.lottery.lottype.hljkl10.Hljkl10PQ3;
import com.lottery.lottype.hljkl10.Hljkl10SH1;
import com.lottery.lottype.hljkl10.Hljkl10SQ2;
import com.lottery.lottype.hljkl10.Hljkl10SQ3;
import com.lottery.lottype.hljkl10.Hljkl10SR2;
import com.lottery.lottype.hljkl10.Hljkl10SR3;
import com.lottery.lottype.hljkl10.Hljkl10SR4;
import com.lottery.lottype.hljkl10.Hljkl10SR5;
import com.lottery.lottype.hljkl10.Hljkl10SS1;
import com.lottery.lottype.hljkl10.Hljkl10SZ2;
import com.lottery.lottype.hljkl10.Hljkl10SZ3;

@Component("1104")
public class HLJkl10 extends AbstractLot {


	private Logger logger = LoggerFactory.getLogger(HLJkl10.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYMMDDXX);
	}

	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("110410", new Hljkl10SH1());
		map.put("110411", new Hljkl10SS1());
		map.put("110412", new Hljkl10SR2());
		map.put("110413", new Hljkl10SR3());
		map.put("110414", new Hljkl10SR4());
		map.put("110415", new Hljkl10SR5());
		map.put("110416", new Hljkl10SQ2());
		map.put("110417", new Hljkl10SQ3());
		map.put("110418", new Hljkl10SZ2());
		map.put("110419", new Hljkl10SZ3());
		
		map.put("110421", new Hljkl10MS1());
		map.put("110422", new Hljkl10MR2());
		map.put("110423", new Hljkl10MR3());
		map.put("110424", new Hljkl10MR4());
		map.put("110425", new Hljkl10MR5());
		map.put("110428", new Hljkl10MZ2());
		map.put("110429", new Hljkl10MZ3());
		
		map.put("110432", new Hljkl10DR2());
		map.put("110433", new Hljkl10DR3());
		map.put("110434", new Hljkl10DR4());
		map.put("110435", new Hljkl10DR5());
		map.put("110436", new Hljkl10DQ2());
		map.put("110437", new Hljkl10DQ3());
		map.put("110438", new Hljkl10DZ2());
		map.put("110439", new Hljkl10DZ3());
		
		map.put("110446", new Hljkl10PQ2());
		map.put("110447", new Hljkl10PQ3());
		
		
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
		return LotteryType.HLJKL10;
	}
}
