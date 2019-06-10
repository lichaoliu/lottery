package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Sdpk3MBX extends Sdpk3X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] winsH = new String[]{wincode.split(",")[0].substring(0,1),wincode.split(",")[1].substring(0,1),wincode.split(",")[2].substring(0,1)};
		
		List<String> bets = Arrays.asList(betcode.split("\\-")[1].replace("^", "").split(","));
		StringBuilder prize = new StringBuilder();
		
		for(String bet:bets) {
			if("07".equals(bet)) {
				if(isTonghua(winsH)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_BX_TH.value).append(",");
				}
			}else if("08".equals(bet)) {
				if(isTonghua(winsH)&&isShunzi(wincode)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_BX_THS.value).append(",");
				}
			}else if("09".equals(bet)) {
				if(isShunzi(wincode)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_BX_SZ.value).append(",");
				}
			}else if("10".equals(bet)) {
				if(isBaozi(wincode)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_BX_BZ.value).append(",");
				}
			}else if("11".equals(bet)) {
				if(isDuizi(wincode)) {
					prize.append(LotteryDrawPrizeAwarder.SDPK3_BX_DZ.value).append(",");
				}
			}
		}
		if(prize.toString().endsWith(",")) {
			prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(MBX)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		
		if(isBetcodeDuplication(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = betcode.split("-")[1].split(",").length;
		
		return zhushu*beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}

}
