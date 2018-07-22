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
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.dc.bqc.Dcbqc10101;
import com.lottery.lottype.dc.bqc.Dcbqc10201;
import com.lottery.lottype.dc.bqc.Dcbqc10203;
import com.lottery.lottype.dc.bqc.Dcbqc10301;
import com.lottery.lottype.dc.bqc.Dcbqc10304;
import com.lottery.lottype.dc.bqc.Dcbqc10307;
import com.lottery.lottype.dc.bqc.Dcbqc10401;
import com.lottery.lottype.dc.bqc.Dcbqc10405;
import com.lottery.lottype.dc.bqc.Dcbqc10411;
import com.lottery.lottype.dc.bqc.Dcbqc10415;
import com.lottery.lottype.dc.bqc.Dcbqc10501;
import com.lottery.lottype.dc.bqc.Dcbqc10506;
import com.lottery.lottype.dc.bqc.Dcbqc10516;
import com.lottery.lottype.dc.bqc.Dcbqc10526;
import com.lottery.lottype.dc.bqc.Dcbqc10531;
import com.lottery.lottype.dc.bqc.Dcbqc10601;
import com.lottery.lottype.dc.bqc.Dcbqc10607;
import com.lottery.lottype.dc.bqc.Dcbqc10622;
import com.lottery.lottype.dc.bqc.Dcbqc10642;
import com.lottery.lottype.dc.bqc.Dcbqc10657;
import com.lottery.lottype.dc.bqc.Dcbqc10663;
import com.lottery.lottype.dc.bqc.Dcbqc20201;
import com.lottery.lottype.dc.bqc.Dcbqc20203;
import com.lottery.lottype.dc.bqc.Dcbqc20301;
import com.lottery.lottype.dc.bqc.Dcbqc20304;
import com.lottery.lottype.dc.bqc.Dcbqc20307;
import com.lottery.lottype.dc.bqc.Dcbqc20401;
import com.lottery.lottype.dc.bqc.Dcbqc20405;
import com.lottery.lottype.dc.bqc.Dcbqc20411;
import com.lottery.lottype.dc.bqc.Dcbqc20415;
import com.lottery.lottype.dc.bqc.Dcbqc20501;
import com.lottery.lottype.dc.bqc.Dcbqc20506;
import com.lottery.lottype.dc.bqc.Dcbqc20516;
import com.lottery.lottype.dc.bqc.Dcbqc20526;
import com.lottery.lottype.dc.bqc.Dcbqc20531;
import com.lottery.lottype.dc.bqc.Dcbqc20601;
import com.lottery.lottype.dc.bqc.Dcbqc20607;
import com.lottery.lottype.dc.bqc.Dcbqc20622;
import com.lottery.lottype.dc.bqc.Dcbqc20642;
import com.lottery.lottype.dc.bqc.Dcbqc20657;
import com.lottery.lottype.dc.bqc.Dcbqc20663;

@Component("5003")
public class Dcbqc extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dcbqc.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500310101", new Dcbqc10101());
		map.put("500310201", new Dcbqc10201());
		map.put("500310203", new Dcbqc10203());
		map.put("500310301", new Dcbqc10301());
		map.put("500310304", new Dcbqc10304());
		map.put("500310307", new Dcbqc10307());
		map.put("500310401", new Dcbqc10401());
		map.put("500310405", new Dcbqc10405());
		map.put("500310411", new Dcbqc10411());
		map.put("500310415", new Dcbqc10415());
		map.put("500310501", new Dcbqc10501());
		map.put("500310506", new Dcbqc10506());
		map.put("500310516", new Dcbqc10516());
		map.put("500310526", new Dcbqc10526());
		map.put("500310531", new Dcbqc10531());
		map.put("500310601", new Dcbqc10601());
		map.put("500310607", new Dcbqc10607());
		map.put("500310622", new Dcbqc10622());
		map.put("500310642", new Dcbqc10642());
		map.put("500310657", new Dcbqc10657());
		map.put("500310663", new Dcbqc10663());
		
		map.put("500320201", new Dcbqc20201());
		map.put("500320203", new Dcbqc20203());
		map.put("500320301", new Dcbqc20301());
		map.put("500320304", new Dcbqc20304());
		map.put("500320307", new Dcbqc20307());
		map.put("500320401", new Dcbqc20401());
		map.put("500320405", new Dcbqc20405());
		map.put("500320411", new Dcbqc20411());
		map.put("500320415", new Dcbqc20415());
		map.put("500320501", new Dcbqc20501());
		map.put("500320506", new Dcbqc20506());
		map.put("500320516", new Dcbqc20516());
		map.put("500320526", new Dcbqc20526());
		map.put("500320531", new Dcbqc20531());
		map.put("500320601", new Dcbqc20601());
		map.put("500320607", new Dcbqc20607());
		map.put("500320622", new Dcbqc20622());
		map.put("500320642", new Dcbqc20642());
		map.put("500320657", new Dcbqc20657());
		map.put("500320663", new Dcbqc20663());
	}

	@Override
	public boolean validate(String betcode, BigDecimal amount,
			BigDecimal beishu, int oneAmount) {
		if(validateBasicBetcode(betcode)==false) {
			return false;
		}
		
		long totalAmt = 0;
		
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.split("-")[0]);
			if(null==type) {
				logger.error("注码金额校验错误betcode={} beishu={} amount={} oneAmout={},玩法不存在",new Object[]{betcode,beishu.intValue(),amount,oneAmount});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalAmt = totalAmt + type.getSingleBetAmount(code, beishu, oneAmount);
		}
		
		if(amount.longValue()!=totalAmt) {
			logger.error("注码金额校验错误betcode={} beishu={} amount={} realamount={},金额错误",new Object[]{betcode,beishu.intValue(),amount,totalAmt});
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//验证是否注码拆分后，会有一倍大于20000元的注码。
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.split("-")[0]);
			for(SplitedLot lot:type.splitByType(code, beishu.intValue(), 200)) {
				if(lot.getAmt()>2000000||lot.getLotMulti()>99) {
					logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{lot.getBetcode(),String.valueOf(lot.getLotMulti()),String.valueOf(lot.getAmt())});
					throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
				}
			}
		}
		
		return true;
	}

	@Override
	public List<SplitedLot> split(String betcode, int lotmulti, long amt,
			int oneAmount) {
		List<SplitedLot> splitedLots = new ArrayList<SplitedLot>();
		for(String code:betcode.split("!")) {
			splitedLots.addAll(map.get(code.split("-")[0]).splitByType(code, lotmulti, oneAmount));
		}
		
		long totalamt = 0L;
		for(SplitedLot slot:splitedLots) {
			if(slot.getAmt()>2000000||slot.getLotMulti()>99) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{slot.getBetcode(),String.valueOf(slot.getLotMulti()),String.valueOf(slot.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalamt = totalamt + slot.getAmt();
		}
		
		if(totalamt!=amt) {
			logger.error("split amt_equal totalAmt={} amt={}",new Object[]{String.valueOf(totalamt),String.valueOf(amt)});
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
	public LotteryType getLotteryType() {
		return LotteryType.DC_BQC;
	}



}
