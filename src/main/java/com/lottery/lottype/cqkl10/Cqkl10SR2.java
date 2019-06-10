package com.lottery.lottype.cqkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Cqkl10SR2 extends Cqkl10X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7);
		StringBuilder sb = new StringBuilder("");
		for(String code:betcode.split("\\^")) {
			if(totalHits(code, wincode)==2) {
				sb.append(LotteryDrawPrizeAwarder.CQK10_R2.value).append(",");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7);
		for(String code:betcode.split("\\^")) {
			if(!validateBetcode(code, SR2)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		return betcode.split("\\^").length*beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transformSingle5(betcode,lotmulti,PlayType.cqkl10_sr2);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			s.setBetcode(getRSortCode(s.getBetcode()));
		}

		return list;
	}
}
