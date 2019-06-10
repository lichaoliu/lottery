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
import com.lottery.lottype.dc.spf.Dcspf10101;
import com.lottery.lottype.dc.spf.Dcspf10201;
import com.lottery.lottype.dc.spf.Dcspf10203;
import com.lottery.lottype.dc.spf.Dcspf10301;
import com.lottery.lottype.dc.spf.Dcspf10304;
import com.lottery.lottype.dc.spf.Dcspf10307;
import com.lottery.lottype.dc.spf.Dcspf10401;
import com.lottery.lottype.dc.spf.Dcspf10405;
import com.lottery.lottype.dc.spf.Dcspf10411;
import com.lottery.lottype.dc.spf.Dcspf10415;
import com.lottery.lottype.dc.spf.Dcspf10501;
import com.lottery.lottype.dc.spf.Dcspf10506;
import com.lottery.lottype.dc.spf.Dcspf10516;
import com.lottery.lottype.dc.spf.Dcspf10526;
import com.lottery.lottype.dc.spf.Dcspf10531;
import com.lottery.lottype.dc.spf.Dcspf10601;
import com.lottery.lottype.dc.spf.Dcspf10607;
import com.lottery.lottype.dc.spf.Dcspf10622;
import com.lottery.lottype.dc.spf.Dcspf10642;
import com.lottery.lottype.dc.spf.Dcspf10657;
import com.lottery.lottype.dc.spf.Dcspf10663;
import com.lottery.lottype.dc.spf.Dcspf10701;
import com.lottery.lottype.dc.spf.Dcspf10801;
import com.lottery.lottype.dc.spf.Dcspf10901;
import com.lottery.lottype.dc.spf.Dcspf11001;
import com.lottery.lottype.dc.spf.Dcspf11101;
import com.lottery.lottype.dc.spf.Dcspf11201;
import com.lottery.lottype.dc.spf.Dcspf11301;
import com.lottery.lottype.dc.spf.Dcspf11401;
import com.lottery.lottype.dc.spf.Dcspf11501;
import com.lottery.lottype.dc.spf.Dcspf20201;
import com.lottery.lottype.dc.spf.Dcspf20203;
import com.lottery.lottype.dc.spf.Dcspf20301;
import com.lottery.lottype.dc.spf.Dcspf20304;
import com.lottery.lottype.dc.spf.Dcspf20307;
import com.lottery.lottype.dc.spf.Dcspf20401;
import com.lottery.lottype.dc.spf.Dcspf20405;
import com.lottery.lottype.dc.spf.Dcspf20411;
import com.lottery.lottype.dc.spf.Dcspf20415;
import com.lottery.lottype.dc.spf.Dcspf20501;
import com.lottery.lottype.dc.spf.Dcspf20506;
import com.lottery.lottype.dc.spf.Dcspf20516;
import com.lottery.lottype.dc.spf.Dcspf20526;
import com.lottery.lottype.dc.spf.Dcspf20531;
import com.lottery.lottype.dc.spf.Dcspf20601;
import com.lottery.lottype.dc.spf.Dcspf20607;
import com.lottery.lottype.dc.spf.Dcspf20622;
import com.lottery.lottype.dc.spf.Dcspf20642;
import com.lottery.lottype.dc.spf.Dcspf20657;
import com.lottery.lottype.dc.spf.Dcspf20663;
import com.lottery.lottype.dc.spf.Dcspf20701;
import com.lottery.lottype.dc.spf.Dcspf20801;
import com.lottery.lottype.dc.spf.Dcspf20901;
import com.lottery.lottype.dc.spf.Dcspf21001;
import com.lottery.lottype.dc.spf.Dcspf21101;
import com.lottery.lottype.dc.spf.Dcspf21201;
import com.lottery.lottype.dc.spf.Dcspf21301;
import com.lottery.lottype.dc.spf.Dcspf21401;
import com.lottery.lottype.dc.spf.Dcspf21501;

@Component("5001")
public class Dcspf extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dcspf.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500110101", new Dcspf10101());
		map.put("500110201", new Dcspf10201());
		map.put("500110203", new Dcspf10203());
		map.put("500110301", new Dcspf10301());
		map.put("500110304", new Dcspf10304());
		map.put("500110307", new Dcspf10307());
		map.put("500110401", new Dcspf10401());
		map.put("500110405", new Dcspf10405());
		map.put("500110411", new Dcspf10411());
		map.put("500110415", new Dcspf10415());
		map.put("500110501", new Dcspf10501());
		map.put("500110506", new Dcspf10506());
		map.put("500110516", new Dcspf10516());
		map.put("500110526", new Dcspf10526());
		map.put("500110531", new Dcspf10531());
		map.put("500110601", new Dcspf10601());
		map.put("500110607", new Dcspf10607());
		map.put("500110622", new Dcspf10622());
		map.put("500110642", new Dcspf10642());
		map.put("500110657", new Dcspf10657());
		map.put("500110663", new Dcspf10663());
		map.put("500110701", new Dcspf10701());
		map.put("500110801", new Dcspf10801());
		map.put("500110901", new Dcspf10901());
		map.put("500111001", new Dcspf11001());
		map.put("500111101", new Dcspf11101());
		map.put("500111201", new Dcspf11201());
		map.put("500111301", new Dcspf11301());
		map.put("500111401", new Dcspf11401());
		map.put("500111501", new Dcspf11501());
		
		map.put("500120201", new Dcspf20201());
		map.put("500120203", new Dcspf20203());
		map.put("500120301", new Dcspf20301());
		map.put("500120304", new Dcspf20304());
		map.put("500120307", new Dcspf20307());
		map.put("500120401", new Dcspf20401());
		map.put("500120405", new Dcspf20405());
		map.put("500120411", new Dcspf20411());
		map.put("500120415", new Dcspf20415());
		map.put("500120501", new Dcspf20501());
		map.put("500120506", new Dcspf20506());
		map.put("500120516", new Dcspf20516());
		map.put("500120526", new Dcspf20526());
		map.put("500120531", new Dcspf20531());
		map.put("500120601", new Dcspf20601());
		map.put("500120607", new Dcspf20607());
		map.put("500120622", new Dcspf20622());
		map.put("500120642", new Dcspf20642());
		map.put("500120657", new Dcspf20657());
		map.put("500120663", new Dcspf20663());
		map.put("500120701", new Dcspf20701());
		map.put("500120801", new Dcspf20801());
		map.put("500120901", new Dcspf20901());
		map.put("500121001", new Dcspf21001());
		map.put("500121101", new Dcspf21101());
		map.put("500121201", new Dcspf21201());
		map.put("500121301", new Dcspf21301());
		map.put("500121401", new Dcspf21401());
		map.put("500121501", new Dcspf21501());
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
		return LotteryType.DC_SPF;
	}



}
