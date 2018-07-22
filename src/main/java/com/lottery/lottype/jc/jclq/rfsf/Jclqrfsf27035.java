package com.lottery.lottype.jc.jclq.rfsf;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Jclqrfsf27035 extends JclqrfsfX{
	
	Jclqrfsf17035 sf = new Jclqrfsf17035();



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
		return 7035;
	}
	
	@Override
	protected long getNormalBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return sf.getSingleBetAmount(betcode, beishu, oneAmount);
	}

}
