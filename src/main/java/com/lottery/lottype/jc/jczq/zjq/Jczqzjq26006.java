package com.lottery.lottype.jc.jczq.zjq;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqzjq26006 extends JczqzjqX{
	
	Jczqzjq16006 zjq = new Jczqzjq16006();
	
	@Override
	protected int getPlayType() {
		return 6006;
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
		return zjq.getSingleBetAmount(betcode, beishu, oneAmount);
	}
}
