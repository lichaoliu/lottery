package com.lottery.lottype.gd11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Gd11x5DR3 extends Gd11x5X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return caculatePrizeDS(betcode, wincode, 3, LotteryDrawPrizeAwarder.GD11X5_R3.value, 3);
//		String dan = betcode.split("\\-")[1].split("#")[0];
//		String tuo = betcode.split("\\-")[1].split("#")[1];
//		
//		if(dan.split(",").length==2&&tuo.split(",").length==9&&StringUtil.isNotEmpt(prize)) {
//			StringBuilder prizeadd = new StringBuilder();
//			for(int i=0,j=prize.split(",").length;i<j;i++) {
//				prizeadd.append(LotteryDrawPrizeAwarder.GD11X5_R3.value).append(",")
//					.append(LotteryDrawPrizeAwarder.GD11X5_R3_ADD.value).append(",");
//			}
//			prizeadd = prizeadd.deleteCharAt(prizeadd.length()-1);
//			return prizeadd.toString();
//		}
//		
//		return prize;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(DR3)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", "").replace("#", ","))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int dan = betcode.split("\\-")[1].split("\\#")[0].split(",").length;
		int tuo = betcode.split("\\-")[1].split("\\#")[1].split(",").length;
		
		long zhushu = MathUtils.combine(tuo, 3-dan);
		
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
