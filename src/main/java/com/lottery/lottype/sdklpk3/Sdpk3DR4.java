package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Sdpk3DR4 extends Sdpk3X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		List<String> wins = Arrays.asList(new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)});
		StringBuilder prize = new StringBuilder();
		
		betcode = betcode.replace("^", "");
		
		String[] dans = betcode.split("\\-")[1].split("\\#")[0].split(",");
		String[] tuos = betcode.split("\\-")[1].split("\\#")[1].split(",");
		
		List<List<String>> codeCollection = MathUtils.getCodeCollection(Arrays.asList(tuos), 4-dans.length);
		
		for(List<String> codes:codeCollection) {
			codes.addAll(Arrays.asList(dans));
		}
		
		for(List<String> codes:codeCollection) {
			if(codes.contains(wins.get(0))&&codes.contains(wins.get(1))&&codes.contains(wins.get(2))) {
				prize.append(LotteryDrawPrizeAwarder.SDPK3_R4.value).append(",");
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
		if(!betcode.matches(DR4)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", "").replace("#", ","))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int dan = betcode.split("\\-")[1].split("\\#")[0].split(",").length;
		int tuo = betcode.split("\\-")[1].split("\\#")[1].split(",").length;
		
		if(dan+tuo<=4) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(tuo, 4-dan);
		
		return zhushu*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		long amt = getSingleBetAmount(betcode,new BigDecimal(lotmulti),oneAmount);
		if(!SplitedLot.isToBeSplit99(lotmulti,amt)) {
			list.add(new SplitedLot(betcode,lotmulti,amt,lotterytype));
		}else {
			int amtSingle = (int) (amt / lotmulti);
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti > 99) {
				permissionLotmulti = 99;
			}
			list.addAll(SplitedLot.splitToPermissionMulti(betcode, lotmulti, permissionLotmulti,lotterytype));
			for(SplitedLot s:list) {
				s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
			}
		}
		return list;
	}

}
