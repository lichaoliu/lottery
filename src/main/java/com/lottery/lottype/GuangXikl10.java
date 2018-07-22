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
import com.lottery.lottype.gxkl10.Gxkl10DHY2;
import com.lottery.lottype.gxkl10.Gxkl10DHY3;
import com.lottery.lottype.gxkl10.Gxkl10DHY4;
import com.lottery.lottype.gxkl10.Gxkl10DHY5;
import com.lottery.lottype.gxkl10.Gxkl10MHY1;
import com.lottery.lottype.gxkl10.Gxkl10MHY2;
import com.lottery.lottype.gxkl10.Gxkl10MHY3;
import com.lottery.lottype.gxkl10.Gxkl10MHY4;
import com.lottery.lottype.gxkl10.Gxkl10MHY5;
import com.lottery.lottype.gxkl10.Gxkl10MHYT;
import com.lottery.lottype.gxkl10.Gxkl10MHYTX3;
import com.lottery.lottype.gxkl10.Gxkl10MHYTX4;
import com.lottery.lottype.gxkl10.Gxkl10MHYTX5;
import com.lottery.lottype.gxkl10.Gxkl10SHY1;
import com.lottery.lottype.gxkl10.Gxkl10SHY2;
import com.lottery.lottype.gxkl10.Gxkl10SHY3;
import com.lottery.lottype.gxkl10.Gxkl10SHY4;
import com.lottery.lottype.gxkl10.Gxkl10SHY5;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX3b4;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX3b5;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX3b6;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b10;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b5;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b6;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b7;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b8;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX4b9;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX5b10;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX5b6;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX5b7;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX5b8;
import com.lottery.lottype.gxkl10.Gxkl10SHYBX5b9;
import com.lottery.lottype.gxkl10.Gxkl10SHYT;
import com.lottery.lottype.gxkl10.Gxkl10SHYTX3;
import com.lottery.lottype.gxkl10.Gxkl10SHYTX4;
import com.lottery.lottype.gxkl10.Gxkl10SHYTX5;

@Component("1105")
public class GuangXikl10 extends AbstractLot {


	private Logger logger = LoggerFactory.getLogger(GuangXikl10.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("110501", new Gxkl10SHY1());
		map.put("110502", new Gxkl10SHY2());
		map.put("110503", new Gxkl10SHY3());
		map.put("110504", new Gxkl10SHY4());
		map.put("110505", new Gxkl10SHY5());
		map.put("110506", new Gxkl10SHYT());
		map.put("110507", new Gxkl10SHYTX3());
		map.put("110508", new Gxkl10SHYTX4());
		map.put("110509", new Gxkl10SHYTX5());
		
		map.put("110511", new Gxkl10MHY1());
		map.put("110512", new Gxkl10MHY2());
		map.put("110513", new Gxkl10MHY3());
		map.put("110514", new Gxkl10MHY4());
		map.put("110515", new Gxkl10MHY5());
		map.put("110516", new Gxkl10MHYT());
		map.put("110517", new Gxkl10MHYTX3());
		map.put("110518", new Gxkl10MHYTX4());
		map.put("110519", new Gxkl10MHYTX5());
		
		map.put("110522", new Gxkl10DHY2());
		map.put("110523", new Gxkl10DHY3());
		map.put("110524", new Gxkl10DHY4());
		map.put("110525", new Gxkl10DHY5());
		
		map.put("110531", new Gxkl10SHYBX3b4());
		map.put("110532", new Gxkl10SHYBX3b5());
		map.put("110533", new Gxkl10SHYBX3b6());
		
		map.put("110541", new Gxkl10SHYBX4b5());
		map.put("110542", new Gxkl10SHYBX4b6());
		map.put("110543", new Gxkl10SHYBX4b7());
		map.put("110544", new Gxkl10SHYBX4b8());
		map.put("110545", new Gxkl10SHYBX4b9());
		map.put("110546", new Gxkl10SHYBX4b10());
		
		map.put("110551", new Gxkl10SHYBX5b6());
		map.put("110552", new Gxkl10SHYBX5b7());
		map.put("110553", new Gxkl10SHYBX5b8());
		map.put("110554", new Gxkl10SHYBX5b9());
		map.put("110555", new Gxkl10SHYBX5b10());
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
		return LotteryType.GXKL10;
	}
}
