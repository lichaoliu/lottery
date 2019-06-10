package com.lottery.lottype.dc.sxds;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcsxds20301 extends DcsxdsX{

	Dcsxds10301 sxds = new Dcsxds10301();
	
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
		return 301;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sxds.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
