package com.lottery.lottype.dc.bqc;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcbqc20642 extends DcbqcX{
	
	Dcbqc10642 bqc = new Dcbqc10642();

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
		return 642;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return bqc.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
