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
import com.lottery.lottype.dc.sfgg.Dcsfgg10301;
import com.lottery.lottype.dc.sfgg.Dcsfgg10401;
import com.lottery.lottype.dc.sfgg.Dcsfgg10405;
import com.lottery.lottype.dc.sfgg.Dcsfgg10501;
import com.lottery.lottype.dc.sfgg.Dcsfgg10506;
import com.lottery.lottype.dc.sfgg.Dcsfgg10516;
import com.lottery.lottype.dc.sfgg.Dcsfgg10601;
import com.lottery.lottype.dc.sfgg.Dcsfgg10607;
import com.lottery.lottype.dc.sfgg.Dcsfgg10622;
import com.lottery.lottype.dc.sfgg.Dcsfgg10642;
import com.lottery.lottype.dc.sfgg.Dcsfgg10701;
import com.lottery.lottype.dc.sfgg.Dcsfgg10801;
import com.lottery.lottype.dc.sfgg.Dcsfgg10901;
import com.lottery.lottype.dc.sfgg.Dcsfgg11001;
import com.lottery.lottype.dc.sfgg.Dcsfgg11101;
import com.lottery.lottype.dc.sfgg.Dcsfgg11201;
import com.lottery.lottype.dc.sfgg.Dcsfgg11301;
import com.lottery.lottype.dc.sfgg.Dcsfgg11401;
import com.lottery.lottype.dc.sfgg.Dcsfgg11501;
import com.lottery.lottype.dc.sfgg.Dcsfgg20301;
import com.lottery.lottype.dc.sfgg.Dcsfgg20401;
import com.lottery.lottype.dc.sfgg.Dcsfgg20405;
import com.lottery.lottype.dc.sfgg.Dcsfgg20501;
import com.lottery.lottype.dc.sfgg.Dcsfgg20506;
import com.lottery.lottype.dc.sfgg.Dcsfgg20516;
import com.lottery.lottype.dc.sfgg.Dcsfgg20601;
import com.lottery.lottype.dc.sfgg.Dcsfgg20607;
import com.lottery.lottype.dc.sfgg.Dcsfgg20622;
import com.lottery.lottype.dc.sfgg.Dcsfgg20642;
import com.lottery.lottype.dc.sfgg.Dcsfgg20701;
import com.lottery.lottype.dc.sfgg.Dcsfgg20801;
import com.lottery.lottype.dc.sfgg.Dcsfgg20901;
import com.lottery.lottype.dc.sfgg.Dcsfgg21001;
import com.lottery.lottype.dc.sfgg.Dcsfgg21101;
import com.lottery.lottype.dc.sfgg.Dcsfgg21201;
import com.lottery.lottype.dc.sfgg.Dcsfgg21301;
import com.lottery.lottype.dc.sfgg.Dcsfgg21401;
import com.lottery.lottype.dc.sfgg.Dcsfgg21501;

@Component("5006")
public class Dcsfgg extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dcsfgg.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500610301", new Dcsfgg10301());
		map.put("500610401", new Dcsfgg10401());
		map.put("500610405", new Dcsfgg10405());
		map.put("500610501", new Dcsfgg10501());
		map.put("500610506", new Dcsfgg10506());
		map.put("500610516", new Dcsfgg10516());
		map.put("500610601", new Dcsfgg10601());
		map.put("500610607", new Dcsfgg10607());
		map.put("500610622", new Dcsfgg10622());
		map.put("500610642", new Dcsfgg10642());
		map.put("500610701", new Dcsfgg10701());
		map.put("500610801", new Dcsfgg10801());
		map.put("500610901", new Dcsfgg10901());
		map.put("500611001", new Dcsfgg11001());
		map.put("500611101", new Dcsfgg11101());
		map.put("500611201", new Dcsfgg11201());
		map.put("500611301", new Dcsfgg11301());
		map.put("500611401", new Dcsfgg11401());
		map.put("500611501", new Dcsfgg11501());
		
		map.put("500620301", new Dcsfgg20301());
		map.put("500620401", new Dcsfgg20401());
		map.put("500620405", new Dcsfgg20405());
		map.put("500620501", new Dcsfgg20501());
		map.put("500620506", new Dcsfgg20506());
		map.put("500620516", new Dcsfgg20516());
		map.put("500620601", new Dcsfgg20601());
		map.put("500620607", new Dcsfgg20607());
		map.put("500620622", new Dcsfgg20622());
		map.put("500620642", new Dcsfgg20642());
		map.put("500620701", new Dcsfgg20701());
		map.put("500620801", new Dcsfgg20801());
		map.put("500620901", new Dcsfgg20901());
		map.put("500621001", new Dcsfgg21001());
		map.put("500621101", new Dcsfgg21101());
		map.put("500621201", new Dcsfgg21201());
		map.put("500621301", new Dcsfgg21301());
		map.put("500621401", new Dcsfgg21401());
		map.put("500621501", new Dcsfgg21501());
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
		return LotteryType.DC_SFGG;
	}



}
