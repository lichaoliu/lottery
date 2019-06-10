package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Sdpk3SSZ extends Sdpk3X{
	
	private Sdpk3MSZ msz = new Sdpk3MSZ();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		List<String> wins = Arrays.asList(new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)});
		StringBuilder prize = new StringBuilder();
		
		for(String bet:betcode.split("\\-")[1].split("\\^")) {
			if(isShunzi(wincode)) {
				int sumWin = Integer.parseInt(wins.get(0))+Integer.parseInt(wins.get(1))+Integer.parseInt(wins.get(2));
				int shouleBet = sumWin/3 - 1;
				Collections.sort(wins);
				if(wins.get(0).equals("01")&&wins.get(1).equals("12")&&wins.get(2).equals("13")) {
					shouleBet = 12;
				}
				String shouleBetStr = shouleBet>9?String.valueOf(shouleBet):"0"+shouleBet;
				
				if(bet.equals(shouleBetStr)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_SZ.value).append(",");
				}
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
		if(!betcode.matches(SSZ)) {
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
		List<SplitedLot> zhumaList = transformM(betcode,lotmulti,"200933","200943");
		
		for(SplitedLot splitedLot:zhumaList) {
			list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("200933")) {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(msz.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}

}
