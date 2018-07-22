package com.lottery.lottype.jc.jclq.rfsf;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jclqrfsf26042 extends JclqrfsfX{
	
	Jclqrfsf16042 sf = new Jclqrfsf16042();

	

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
		return 6042;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
