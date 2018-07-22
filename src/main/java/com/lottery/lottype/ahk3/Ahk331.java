package com.lottery.lottype.ahk3;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.lottype.SplitedLot;

//三同号复式
//可以不做，直接用30
public class Ahk331 extends Ahk3X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		// TODO Auto-generated method stub
		return null;
	}

}
