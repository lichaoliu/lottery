package com.lottery.lottype.jc.jczq.spfwrq;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqspfwrq28070 extends JczqspfwrqX{
	
	Jczqspfwrq18070 spf = new Jczqspfwrq18070();
	
	@Override
	protected int getPlayType() {
		return 8070;
	}

	

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingleBetAmountDT(betcode, beishu, oneAmount);
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetTypeDT(betcode, lotmulti, oneAmount);
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return spf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
