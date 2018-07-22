package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Tjssc23 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] wins = wincode.split(",");
		if(wins[3].equals(wins[4])) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		
		betcode = betcode.replace("^", "").split("-")[1];
		if(betcode.contains(wins[3])&&betcode.contains(wins[4])) {
			return LotteryDrawPrizeAwarder.TJSSC_2Z.value;
		}
		
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(OTHER_2Z)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", ""))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int total = betcode.split("\\-")[1].replace("^", "").split(",").length;
		return MathUtils.combine(total, 2)*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		long amt = getSingleBetAmount(betcode,new BigDecimal(lotmulti),oneAmount);
		if(!SplitedLot.isToBeSplitFC(lotmulti,amt)) {
			list.add(new SplitedLot(betcode,lotmulti,amt,lotterytype));
		}else {
			int amtSingle = (int) (amt / lotmulti);
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti > 50) {
				permissionLotmulti = 50;
			}
			list.addAll(SplitedLot.splitToPermissionMulti(betcode, lotmulti, permissionLotmulti,lotterytype));
		}
		for(SplitedLot s:list) {
			s.setBetcode("101623-"+sortBetcode(s.getBetcode().split("\\-")[1].replace("^", ""), ",")+"^");
			s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
		}
		
		return list;
	}

}
