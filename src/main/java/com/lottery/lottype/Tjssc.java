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
import com.lottery.lottype.tjssc.Tjssc01;
import com.lottery.lottype.tjssc.Tjssc02;
import com.lottery.lottype.tjssc.Tjssc03;
import com.lottery.lottype.tjssc.Tjssc04;
import com.lottery.lottype.tjssc.Tjssc05;
import com.lottery.lottype.tjssc.Tjssc06;
import com.lottery.lottype.tjssc.Tjssc07;
import com.lottery.lottype.tjssc.Tjssc11;
import com.lottery.lottype.tjssc.Tjssc12;
import com.lottery.lottype.tjssc.Tjssc13;
import com.lottery.lottype.tjssc.Tjssc14;
import com.lottery.lottype.tjssc.Tjssc15;
import com.lottery.lottype.tjssc.Tjssc23;
import com.lottery.lottype.tjssc.Tjssc24;
import com.lottery.lottype.tjssc.Tjssc25;
import com.lottery.lottype.tjssc.Tjssc26;
import com.lottery.lottype.tjssc.Tjssc27;
import com.lottery.lottype.tjssc.Tjssc28;
import com.lottery.lottype.tjssc.Tjssc29;
import com.lottery.lottype.tjssc.Tjssc31;
import com.lottery.lottype.tjssc.Tjssc32;
import com.lottery.lottype.tjssc.Tjssc33;

@Component("1016")
public class Tjssc extends AbstractLot{

	private Logger logger = LoggerFactory.getLogger(Tjssc.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("101601", new Tjssc01());
		map.put("101602", new Tjssc02());
		map.put("101603", new Tjssc03());
		map.put("101604", new Tjssc04());
		map.put("101605", new Tjssc05());
		map.put("101606", new Tjssc06());
		map.put("101607", new Tjssc07());
		
		map.put("101611", new Tjssc11());
		map.put("101612", new Tjssc12());
		map.put("101613", new Tjssc13());
		map.put("101614", new Tjssc14());
		map.put("101615", new Tjssc15());
		
		map.put("101623", new Tjssc23());
		map.put("101624", new Tjssc24());
		map.put("101625", new Tjssc25());
		map.put("101626", new Tjssc26());
		map.put("101627", new Tjssc27());
		map.put("101628", new Tjssc28());
		map.put("101629", new Tjssc29());
		
		map.put("101631", new Tjssc31());
		map.put("101632", new Tjssc32());
		map.put("101633", new Tjssc33());
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
			if(s.getAmt()>2000000||s.getLotMulti()>50) {
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
		String[] prizeInfos = prizeinfo.split(",");
		for(String prize:prizeInfos) {
			if(prize.startsWith("5D")||prize.startsWith("5T5")) {
				return true;
			}
		}
		
		if(preprizeamt.compareTo(new BigDecimal(1000000))>=0) {
			return true;
		}
		return false;
	}

	@Override
	public LotteryType getLotteryType() {
		return LotteryType.TJSSC;
	}
	
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		String playtype = betcode.substring(0, 6);
		LotPlayType type = map.get(playtype);
		return combinePrizeInfo(type.caculatePrizeLevel(betcode, wincode,oneAmount), lotmulti);
	}
}
