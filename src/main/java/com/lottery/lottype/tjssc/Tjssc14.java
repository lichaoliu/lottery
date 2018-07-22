package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Tjssc14 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		betcode = betcode.replace("^", "").split("-")[1];
		String[] wins = wincode.split(",");
		
		if(betcode.split(";")[0].contains(wins[1])
				&&betcode.split(";")[1].contains(wins[2])
				&&betcode.split(";")[2].contains(wins[3])
				&&betcode.split(";")[3].contains(wins[4])) {
			return LotteryDrawPrizeAwarder.TJSSC_4D.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(FU_4D)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		long zhushu = 1L;
		for(String code:betcode.split("\\-")[1].replace("^", "").split(";")) {
			zhushu = zhushu * code.split(",").length;
			if(isBetcodeDuplication(code)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		return zhushu*200*beishu.longValue();
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
			for(SplitedLot s:list) {
				s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
			}
		}
		return list;
	}

}
