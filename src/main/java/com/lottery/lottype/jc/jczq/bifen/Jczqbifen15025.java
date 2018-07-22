package com.lottery.lottype.jc.jczq.bifen;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Jczqbifen15025 extends JczqbifenfX{

	@Override
	protected int getPlayType() {
		return 5025;
	}
	

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!isMatchBetcode(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(getTotalMatchNum(betcode)<getNeedMatchNumByType()) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isMatchDuplication(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return caculateZhushu(betcode)*beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetType(betcode, lotmulti, oneAmount);
	}

}
