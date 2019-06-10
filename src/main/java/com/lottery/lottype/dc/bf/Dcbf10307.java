package com.lottery.lottype.dc.bf;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

public class Dcbf10307 extends DcbfX{

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return getSingleBetAmountDF(betcode, beishu, oneAmount);
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		return splitByBetTypeDFN1(betcode, lotmulti, oneAmount);
	}

	@Override
	public int getPlayType() {
		return 307;
	}

}
