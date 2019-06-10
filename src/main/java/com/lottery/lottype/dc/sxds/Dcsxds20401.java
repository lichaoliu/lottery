package com.lottery.lottype.dc.sxds;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcsxds20401 extends DcsxdsX{
	
	Dcsxds10401 sxds = new Dcsxds10401();

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
		return 401;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sxds.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
