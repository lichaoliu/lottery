package com.lottery.lottype.jc.jczq.bifen;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqbifen24010 extends JczqbifenfX{
	
	Jczqbifen14010 bf = new Jczqbifen14010();

	

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
		return 4010;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return bf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
