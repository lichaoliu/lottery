package com.lottery.lottype.jc.jczq.zjq;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqzjq23001 extends JczqzjqX{
	
	Jczqzjq13001 zjq = new Jczqzjq13001();

	@Override
	protected int getPlayType() {
		return 3001;
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
