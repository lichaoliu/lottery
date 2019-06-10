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
import com.lottery.lottype.dc.sxds.Dcsxds10101;
import com.lottery.lottype.dc.sxds.Dcsxds10201;
import com.lottery.lottype.dc.sxds.Dcsxds10203;
import com.lottery.lottype.dc.sxds.Dcsxds10301;
import com.lottery.lottype.dc.sxds.Dcsxds10304;
import com.lottery.lottype.dc.sxds.Dcsxds10307;
import com.lottery.lottype.dc.sxds.Dcsxds10401;
import com.lottery.lottype.dc.sxds.Dcsxds10405;
import com.lottery.lottype.dc.sxds.Dcsxds10411;
import com.lottery.lottype.dc.sxds.Dcsxds10415;
import com.lottery.lottype.dc.sxds.Dcsxds10501;
import com.lottery.lottype.dc.sxds.Dcsxds10506;
import com.lottery.lottype.dc.sxds.Dcsxds10516;
import com.lottery.lottype.dc.sxds.Dcsxds10526;
import com.lottery.lottype.dc.sxds.Dcsxds10531;
import com.lottery.lottype.dc.sxds.Dcsxds10601;
import com.lottery.lottype.dc.sxds.Dcsxds10607;
import com.lottery.lottype.dc.sxds.Dcsxds10622;
import com.lottery.lottype.dc.sxds.Dcsxds10642;
import com.lottery.lottype.dc.sxds.Dcsxds10657;
import com.lottery.lottype.dc.sxds.Dcsxds10663;
import com.lottery.lottype.dc.sxds.Dcsxds20201;
import com.lottery.lottype.dc.sxds.Dcsxds20203;
import com.lottery.lottype.dc.sxds.Dcsxds20301;
import com.lottery.lottype.dc.sxds.Dcsxds20304;
import com.lottery.lottype.dc.sxds.Dcsxds20307;
import com.lottery.lottype.dc.sxds.Dcsxds20401;
import com.lottery.lottype.dc.sxds.Dcsxds20405;
import com.lottery.lottype.dc.sxds.Dcsxds20411;
import com.lottery.lottype.dc.sxds.Dcsxds20415;
import com.lottery.lottype.dc.sxds.Dcsxds20501;
import com.lottery.lottype.dc.sxds.Dcsxds20506;
import com.lottery.lottype.dc.sxds.Dcsxds20516;
import com.lottery.lottype.dc.sxds.Dcsxds20526;
import com.lottery.lottype.dc.sxds.Dcsxds20531;
import com.lottery.lottype.dc.sxds.Dcsxds20601;
import com.lottery.lottype.dc.sxds.Dcsxds20607;
import com.lottery.lottype.dc.sxds.Dcsxds20622;
import com.lottery.lottype.dc.sxds.Dcsxds20642;
import com.lottery.lottype.dc.sxds.Dcsxds20657;
import com.lottery.lottype.dc.sxds.Dcsxds20663;

@Component("5004")
public class Dcsxds extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dcsxds.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500410101", new Dcsxds10101());
		map.put("500410201", new Dcsxds10201());
		map.put("500410203", new Dcsxds10203());
		map.put("500410301", new Dcsxds10301());
		map.put("500410304", new Dcsxds10304());
		map.put("500410307", new Dcsxds10307());
		map.put("500410401", new Dcsxds10401());
		map.put("500410405", new Dcsxds10405());
		map.put("500410411", new Dcsxds10411());
		map.put("500410415", new Dcsxds10415());
		map.put("500410501", new Dcsxds10501());
		map.put("500410506", new Dcsxds10506());
		map.put("500410516", new Dcsxds10516());
		map.put("500410526", new Dcsxds10526());
		map.put("500410531", new Dcsxds10531());
		map.put("500410601", new Dcsxds10601());
		map.put("500410607", new Dcsxds10607());
		map.put("500410622", new Dcsxds10622());
		map.put("500410642", new Dcsxds10642());
		map.put("500410657", new Dcsxds10657());
		map.put("500410663", new Dcsxds10663());
		
		map.put("500420201", new Dcsxds20201());
		map.put("500420203", new Dcsxds20203());
		map.put("500420301", new Dcsxds20301());
		map.put("500420304", new Dcsxds20304());
		map.put("500420307", new Dcsxds20307());
		map.put("500420401", new Dcsxds20401());
		map.put("500420405", new Dcsxds20405());
		map.put("500420411", new Dcsxds20411());
		map.put("500420415", new Dcsxds20415());
		map.put("500420501", new Dcsxds20501());
		map.put("500420506", new Dcsxds20506());
		map.put("500420516", new Dcsxds20516());
		map.put("500420526", new Dcsxds20526());
		map.put("500420531", new Dcsxds20531());
		map.put("500420601", new Dcsxds20601());
		map.put("500420607", new Dcsxds20607());
		map.put("500420622", new Dcsxds20622());
		map.put("500420642", new Dcsxds20642());
		map.put("500420657", new Dcsxds20657());
		map.put("500420663", new Dcsxds20663());
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
		return LotteryType.DC_SXDS;
	}


}
