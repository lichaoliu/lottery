package com.lottery.lottype.jc.jclq.sf;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jclqsf23004 extends JclqsfX{
	
	Jclqsf13004 sf = new Jclqsf13004();

	

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
		return 3004;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
