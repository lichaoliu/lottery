package com.lottery.lottype.jc.jczq.spf;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqspf28255 extends JczqspfX{
	
	Jczqspf18255 spf = new Jczqspf18255();



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
	protected int getPlayType() {
		return 8255;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return spf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
