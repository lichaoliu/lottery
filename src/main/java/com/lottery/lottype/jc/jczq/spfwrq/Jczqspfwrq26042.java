package com.lottery.lottype.jc.jczq.spfwrq;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqspfwrq26042 extends JczqspfwrqX{
	
	Jczqspfwrq16042 spf = new Jczqspfwrq16042();
	
	@Override
	protected int getPlayType() {
		return 6042;
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
