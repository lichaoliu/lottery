package com.lottery.lottype.dc.sfgg;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcsfgg20401 extends DcsfggX{
	
	Dcsfgg10401 spf = new Dcsfgg10401();
	
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
		return spf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
