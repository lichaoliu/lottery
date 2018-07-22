package com.lottery.lottype.hubei11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Hubei11x5SL4 extends Hubei11x5X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] betcodes = betcode.split("\\-")[1].split("\\^");
		StringBuilder prize = new StringBuilder();
		
		for(String bet:betcodes) {
			if(4==totalHits(bet,wincode)) {
				prize.append(LotteryDrawPrizeAwarder.HB11X5_L41.value).append(",");
			}else if(3==totalHits(bet,wincode)) {
				prize.append(LotteryDrawPrizeAwarder.HB11X5_L42.value).append(",");
			}
		}
		
		if(prize.toString().endsWith(",")) {
			prize = prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(SL4)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		for(String bet:betcode.split("\\-")[1].split("\\^")) {
			if(isBetcodeDuplication(bet)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		return betcode.split("\\^").length*200*5*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transformone(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}

}
