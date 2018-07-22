package com.lottery.lottype.xjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Xjssc14 extends XjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		
		StringBuilder prize = new StringBuilder();
		betcode = betcode.replace("^", "").split("-")[1];
		String[] wins = wincode.split(",");
		
		String[] betcodes = betcode.split(";");
		
		for(String code0:betcodes[0].split(",")) {
			for(String code1:betcodes[1].split(",")) {
				for(String code2:betcodes[2].split(",")) {
					for(String code3:betcodes[3].split(",")) {
						if(code0.equals(wins[1])&&code1.equals(wins[2])
								&&code2.equals(wins[3])&&code3.equals(wins[4])) {
							prize.append(LotteryDrawPrizeAwarder.XJSSC_4DALL.value).append(",");
						}else if(code0.equals(wins[1])&&code1.equals(wins[2])
								&&code2.equals(wins[3])) {
							prize.append(LotteryDrawPrizeAwarder.XJSSC_4D2.value).append(",");
						}else if(code1.equals(wins[2])
								&&code2.equals(wins[3])&&code3.equals(wins[4])) {
							prize.append(LotteryDrawPrizeAwarder.XJSSC_4D2.value).append(",");
						}
					}
				}
			}
		}
		
		
		return prize.toString();
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
