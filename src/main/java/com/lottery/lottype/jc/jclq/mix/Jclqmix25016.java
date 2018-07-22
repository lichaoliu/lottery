package com.lottery.lottype.jc.jclq.mix;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jclqmix25016 extends JclqmixX{
	
	Jclqmix15016 sf = new Jclqmix15016();



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
	protected int getPlayType() {
		return 5016;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
