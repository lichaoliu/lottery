package com.lottery.lottype.dc.zjq;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dczjq20304 extends DczjqX{
	
	Dczjq10304 zjq = new Dczjq10304();

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingleBetAmountDT(betcode, beishu, oneAmount);
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetTypeDTN1(betcode, lotmulti, oneAmount);
	}

	@Override
	public int getPlayType() {
		return 304;
	}

	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return zjq.getSingleBetAmount(betcode, beishu, oneAmount);
	}
}
