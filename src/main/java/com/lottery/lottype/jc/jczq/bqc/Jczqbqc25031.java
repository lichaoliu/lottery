package com.lottery.lottype.jc.jczq.bqc;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqbqc25031 extends JczqbqcX{
	
	Jczqbqc15031 bqc = new Jczqbqc15031();

	@Override
	protected int getPlayType() {
		return 5031;
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
		return bqc.getSingleBetAmount(betcode, beishu, oneAmount);
	}
}
