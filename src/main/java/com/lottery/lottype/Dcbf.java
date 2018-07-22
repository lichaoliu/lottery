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
import com.lottery.lottype.dc.bf.Dcbf10101;
import com.lottery.lottype.dc.bf.Dcbf10201;
import com.lottery.lottype.dc.bf.Dcbf10203;
import com.lottery.lottype.dc.bf.Dcbf10301;
import com.lottery.lottype.dc.bf.Dcbf10304;
import com.lottery.lottype.dc.bf.Dcbf10307;
import com.lottery.lottype.dc.bf.Dcbf20201;
import com.lottery.lottype.dc.bf.Dcbf20203;
import com.lottery.lottype.dc.bf.Dcbf20301;
import com.lottery.lottype.dc.bf.Dcbf20304;
import com.lottery.lottype.dc.bf.Dcbf20307;

@Component("5005")
public class Dcbf extends AbstractDanchangLot{
	
	private Logger logger = LoggerFactory.getLogger(Dcbf.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotDcPlayType> map = new HashMap<String, LotDcPlayType>();
	{
		map.put("500510101", new Dcbf10101());
		map.put("500510201", new Dcbf10201());
		map.put("500510203", new Dcbf10203());
		map.put("500510301", new Dcbf10301());
		map.put("500510304", new Dcbf10304());
		map.put("500510307", new Dcbf10307());
		map.put("500520201", new Dcbf20201());
		map.put("500520203", new Dcbf20203());
		map.put("500520301", new Dcbf20301());
		map.put("500520304", new Dcbf20304());
		map.put("500520307", new Dcbf20307());
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
		return LotteryType.DC_BF;
	}


}
