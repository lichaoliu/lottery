package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Sdpk3SBZ extends Sdpk3X{
	
	private Sdpk3MBZ mbz = new Sdpk3MBZ();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		List<String> wins = Arrays.asList(new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)});
		StringBuilder prize = new StringBuilder();
		
		for(String bet:betcode.split("\\-")[1].split("\\^")) {
			if(isBaozi(wincode)&&wins.contains(bet)) {
				prize.append(LotteryDrawPrizeAwarder.SDPK3_BZ.value).append(",");
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
		if(!betcode.matches(SBZ)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isArrayDuplication(betcode.split("-")[1].split("\\^"))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return betcode.split("\\^").length*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transformM(betcode,lotmulti,"200934","200944");
		
		for(SplitedLot splitedLot:zhumaList) {
			list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("200934")) {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(mbz.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}

}
