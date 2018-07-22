package com.lottery.lottype.gd11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Gd11x5DZ2 extends Gd11x5X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		betcode = betcode.split("\\-")[1].replace("^", "");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		if(totalHits(dan, wincode.substring(0, 5))==dan.length()/2&&totalHits(tuo, wincode.substring(0, 5))==2-dan.length()/2) {
			return LotteryDrawPrizeAwarder.GD11X5_Z2.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(DZ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", "").replace("#", ","))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int dan = betcode.split("\\-")[1].split("\\#")[0].split(",").length;
		int tuo = betcode.split("\\-")[1].split("\\#")[1].split(",").length;
		
		long zhushu = MathUtils.combine(tuo, 2-dan);
		
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
