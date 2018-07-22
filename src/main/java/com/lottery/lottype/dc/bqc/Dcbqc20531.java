package com.lottery.lottype.dc.bqc;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcbqc20531 extends DcbqcX{
	
	Dcbqc10531 bqc = new Dcbqc10531();

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
		return 531;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return bqc.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
