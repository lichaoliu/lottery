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
import com.lottery.lottype.dc.zjq.Dczjq10101;
import com.lottery.lottype.dc.zjq.Dczjq10201;
import com.lottery.lottype.dc.zjq.Dczjq10203;
import com.lottery.lottype.dc.zjq.Dczjq10301;
import com.lottery.lottype.dc.zjq.Dczjq10304;
import com.lottery.lottype.dc.zjq.Dczjq10307;
import com.lottery.lottype.dc.zjq.Dczjq10401;
import com.lottery.lottype.dc.zjq.Dczjq10405;
import com.lottery.lottype.dc.zjq.Dczjq10411;
import com.lottery.lottype.dc.zjq.Dczjq10415;
import com.lottery.lottype.dc.zjq.Dczjq10501;
import com.lottery.lottype.dc.zjq.Dczjq10506;
import com.lottery.lottype.dc.zjq.Dczjq10516;
import com.lottery.lottype.dc.zjq.Dczjq10526;
import com.lottery.lottype.dc.zjq.Dczjq10531;
import com.lottery.lottype.dc.zjq.Dczjq10601;
import com.lottery.lottype.dc.zjq.Dczjq10607;
import com.lottery.lottype.dc.zjq.Dczjq10622;
import com.lottery.lottype.dc.zjq.Dczjq10642;
import com.lottery.lottype.dc.zjq.Dczjq10657;
import com.lottery.lottype.dc.zjq.Dczjq10663;
import com.lottery.lottype.dc.zjq.Dczjq20201;
import com.lottery.lottype.dc.zjq.Dczjq20203;
import com.lottery.lottype.dc.zjq.Dczjq20301;
import com.lottery.lottype.dc.zjq.Dczjq20304;
import com.lottery.lottype.dc.zjq.Dczjq20307;
import com.lottery.lottype.dc.zjq.Dczjq20401;
import com.lottery.lottype.dc.zjq.Dczjq20405;
import com.lottery.lottype.dc.zjq.Dczjq20411;
import com.lottery.lottype.dc.zjq.Dczjq20415;
import com.lottery.lottype.dc.zjq.Dczjq20501;
import com.lottery.lottype.dc.zjq.Dczjq20506;
import com.lottery.lottype.dc.zjq.Dczjq20516;
import com.lottery.lottype.dc.zjq.Dczjq20526;
import com.lottery.lottype.dc.zjq.Dczjq20531;
import com.lottery.lottype.dc.zjq.Dczjq20601;
import com.lottery.lottype.dc.zjq.Dczjq20607;
import com.lottery.lottype.dc.zjq.Dczjq20622;
import com.lottery.lottype.dc.zjq.Dczjq20642;
import com.lottery.lottype.dc.zjq.Dczjq20657;
import com.lottery.lottype.dc.zjq.Dczjq20663;

@Component("5002")
public class Dczjq  extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dczjq.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500210101", new Dczjq10101());
		map.put("500210201", new Dczjq10201());
		map.put("500210203", new Dczjq10203());
		map.put("500210301", new Dczjq10301());
		map.put("500210304", new Dczjq10304());
		map.put("500210307", new Dczjq10307());
		map.put("500210401", new Dczjq10401());
		map.put("500210405", new Dczjq10405());
		map.put("500210411", new Dczjq10411());
		map.put("500210415", new Dczjq10415());
		map.put("500210501", new Dczjq10501());
		map.put("500210506", new Dczjq10506());
		map.put("500210516", new Dczjq10516());
		map.put("500210526", new Dczjq10526());
		map.put("500210531", new Dczjq10531());
		map.put("500210601", new Dczjq10601());
		map.put("500210607", new Dczjq10607());
		map.put("500210622", new Dczjq10622());
		map.put("500210642", new Dczjq10642());
		map.put("500210657", new Dczjq10657());
		map.put("500210663", new Dczjq10663());
		
		map.put("500220201", new Dczjq20201());
		map.put("500220203", new Dczjq20203());
		map.put("500220301", new Dczjq20301());
		map.put("500220304", new Dczjq20304());
		map.put("500220307", new Dczjq20307());
		map.put("500220401", new Dczjq20401());
		map.put("500220405", new Dczjq20405());
		map.put("500220411", new Dczjq20411());
		map.put("500220415", new Dczjq20415());
		map.put("500220501", new Dczjq20501());
		map.put("500220506", new Dczjq20506());
		map.put("500220516", new Dczjq20516());
		map.put("500220526", new Dczjq20526());
		map.put("500220531", new Dczjq20531());
		map.put("500220601", new Dczjq20601());
		map.put("500220607", new Dczjq20607());
		map.put("500220622", new Dczjq20622());
		map.put("500220642", new Dczjq20642());
		map.put("500220657", new Dczjq20657());
		map.put("500220663", new Dczjq20663());
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
		return LotteryType.DC_ZJQ;
	}


}
