package com.lottery.lottype.dc.sxds;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcsxds20203 extends DcsxdsX{
	
	Dcsxds10203 sxds = new Dcsxds10203();

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
		return 203;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sxds.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
