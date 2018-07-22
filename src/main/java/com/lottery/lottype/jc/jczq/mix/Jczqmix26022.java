package com.lottery.lottype.jc.jczq.mix;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jczqmix26022 extends JczqmixX{
	
	Jczqmix16022 spf = new Jczqmix16022();
	
	@Override
	protected int getPlayType() {
		return 6022;
	}

	

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingleBetAmountDT(betcode, beishu, oneAmount);
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetTypeDTMix(betcode, lotmulti, oneAmount);
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return spf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
