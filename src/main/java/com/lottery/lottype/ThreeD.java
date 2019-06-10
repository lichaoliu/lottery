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
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.d3.Threed01;
import com.lottery.lottype.d3.Threed02;
import com.lottery.lottype.d3.Threed03;
import com.lottery.lottype.d3.Threed04;
import com.lottery.lottype.d3.Threed05;
import com.lottery.lottype.d3.Threed06;
import com.lottery.lottype.d3.Threed07;
import com.lottery.lottype.d3.Threed08;
import com.lottery.lottype.d3.Threed09;
import com.lottery.lottype.d3.Threed11;
import com.lottery.lottype.d3.Threed12;
import com.lottery.lottype.d3.Threed13;
import com.lottery.lottype.d3.Threed14;
import com.lottery.lottype.d3.Threed15;
import com.lottery.lottype.d3.Threed16;
import com.lottery.lottype.d3.Threed17;
import com.lottery.lottype.d3.Threed18;
import com.lottery.lottype.d3.Threed19;
import com.lottery.lottype.d3.Threed21;
import com.lottery.lottype.d3.Threed22;
import com.lottery.lottype.d3.Threed23;
import com.lottery.lottype.d3.Threed24;
import com.lottery.lottype.d3.Threed25;
import com.lottery.lottype.d3.Threed33;
import com.lottery.lottype.d3.Threed34;
import com.lottery.lottype.d3.Threed35;
import com.lottery.lottype.d3.Threed36;

@Component("1002")
public class ThreeD extends AbstractLot {


	private Logger logger = LoggerFactory.getLogger(ThreeD.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		return false;
	}
	
	Map<String, LotPlayType> map = new HashMap<String, LotPlayType>();
	{
		map.put("100201", new Threed01());
		map.put("100202", new Threed02());
		map.put("100203", new Threed03());
		map.put("100204", new Threed04());
		map.put("100205", new Threed05());
		map.put("100206", new Threed06());
		map.put("100211", new Threed11());
		map.put("100212", new Threed12());
		map.put("100213", new Threed13());
		map.put("100214", new Threed14());
		map.put("100215", new Threed15());
		map.put("100216", new Threed16());
		map.put("100221", new Threed21());
		map.put("100222", new Threed22());
		map.put("100223", new Threed23());
		map.put("100224", new Threed24());
		map.put("100225", new Threed25());
		
		map.put("100233", new Threed33());
		map.put("100234", new Threed34());
		map.put("100235", new Threed35());
		map.put("100236", new Threed36());
		
		map.put("100207", new Threed07());
		map.put("100208", new Threed08());
		map.put("100209", new Threed09());
		
		map.put("100217", new Threed17());
		map.put("100218", new Threed18());
		map.put("100219", new Threed19());
		
	}
	
	@Override
	public boolean validatePhase(String phase) {
		return phase.matches(RegexUtil.PHASE_YYYYXXX);
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
		return LotteryType.F3D;
	}
	
	@Override
	public Map<String, String> getPrizeLevelMapper() {
		Map<String, String> mapper = new HashMap<String, String>();
		mapper.put("1", "1");
		mapper.put("2", "2");
		mapper.put("3", "3");
		return mapper;
	}
}
