package com.lottery.lottype.jc.jclq.mix;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jclqmix16006 extends JclqmixX{



	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingBetAmountMix(betcode, beishu, oneAmount);
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetTypeMix(betcode, lotmulti, oneAmount);
	}

	@Override
	protected int getPlayType() {
		return 6006;
	}

}
