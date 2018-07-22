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
import com.lottery.lottype.sdklpk3.Sdpk3DR2;
import com.lottery.lottype.sdklpk3.Sdpk3DR3;
import com.lottery.lottype.sdklpk3.Sdpk3DR4;
import com.lottery.lottype.sdklpk3.Sdpk3DR5;
import com.lottery.lottype.sdklpk3.Sdpk3DR6;
import com.lottery.lottype.sdklpk3.Sdpk3MBX;
import com.lottery.lottype.sdklpk3.Sdpk3MBZ;
import com.lottery.lottype.sdklpk3.Sdpk3MDZ;
import com.lottery.lottype.sdklpk3.Sdpk3MR1;
import com.lottery.lottype.sdklpk3.Sdpk3MR2;
import com.lottery.lottype.sdklpk3.Sdpk3MR3;
import com.lottery.lottype.sdklpk3.Sdpk3MR4;
import com.lottery.lottype.sdklpk3.Sdpk3MR5;
import com.lottery.lottype.sdklpk3.Sdpk3MR6;
import com.lottery.lottype.sdklpk3.Sdpk3MSZ;
import com.lottery.lottype.sdklpk3.Sdpk3MTH;
import com.lottery.lottype.sdklpk3.Sdpk3MTHS;
import com.lottery.lottype.sdklpk3.Sdpk3SBZ;
import com.lottery.lottype.sdklpk3.Sdpk3SDZ;
import com.lottery.lottype.sdklpk3.Sdpk3SR1;
import com.lottery.lottype.sdklpk3.Sdpk3SR2;
import com.lottery.lottype.sdklpk3.Sdpk3SR3;
import com.lottery.lottype.sdklpk3.Sdpk3SR4;
import com.lottery.lottype.sdklpk3.Sdpk3SR5;
import com.lottery.lottype.sdklpk3.Sdpk3SR6;
import com.lottery.lottype.sdklpk3.Sdpk3SSZ;
import com.lottery.lottype.sdklpk3.Sdpk3STH;
import com.lottery.lottype.sdklpk3.Sdpk3STHS;

@Component("2009")
public class SdKuailepk3 extends AbstractLot{
	
	private Logger logger = LoggerFactory.getLogger(SdKuailepk3.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("200901", new Sdpk3SR1());
		map.put("200902", new Sdpk3SR2());
		map.put("200903", new Sdpk3SR3());
		map.put("200904", new Sdpk3SR4());
		map.put("200905", new Sdpk3SR5());
		map.put("200906", new Sdpk3SR6());
		
		map.put("200911", new Sdpk3MR1());
		map.put("200912", new Sdpk3MR2());
		map.put("200913", new Sdpk3MR3());
		map.put("200914", new Sdpk3MR4());
		map.put("200915", new Sdpk3MR5());
		map.put("200916", new Sdpk3MR6());
		
		map.put("200922", new Sdpk3DR2());
		map.put("200923", new Sdpk3DR3());
		map.put("200924", new Sdpk3DR4());
		map.put("200925", new Sdpk3DR5());
		map.put("200926", new Sdpk3DR6());
		
		map.put("200931", new Sdpk3STH());
		map.put("200932", new Sdpk3STHS());
		map.put("200933", new Sdpk3SSZ());
		map.put("200934", new Sdpk3SBZ());
		map.put("200935", new Sdpk3SDZ());
		
		map.put("200941", new Sdpk3MTH());
		map.put("200942", new Sdpk3MTHS());
		map.put("200943", new Sdpk3MSZ());
		map.put("200944", new Sdpk3MBZ());
		map.put("200945", new Sdpk3MDZ());
		map.put("200946", new Sdpk3MBX());
		
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
		return LotteryType.SD_KLPK3;
	}

}
