package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Sdpk3STHS extends Sdpk3X{
	
	private Sdpk3MTHS mths = new Sdpk3MTHS();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] wins = new String[]{wincode.split(",")[0].substring(0,1),wincode.split(",")[1].substring(0,1),wincode.split(",")[2].substring(0,1)};
		StringBuilder prize = new StringBuilder();
		for(String bet:betcode.split("\\-")[1].split("\\^")) {
			if(isShunzi(wincode)&&isTonghua(wins)&&bet.equals("0"+wins[0])) {
				prize.append(LotteryDrawPrizeAwarder.SDPK3_THS.value).append(",");
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
		if(!betcode.matches(SHS)) {
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
		List<SplitedLot> zhumaList = transformM(betcode,lotmulti,"200932","200942");
		
		for(SplitedLot splitedLot:zhumaList) {
			list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("200932")) {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(mths.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}

}
