package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.lottery.lottype.AnHuiKuai3;
import com.lottery.lottype.SplitedLot;

public class AnHuiK3Test {

	AnHuiKuai3 k3 = new AnHuiKuai3();
	
	@Test
	public void test() {
		System.out.println(k3.validate("100811-3,5^", new BigDecimal(400), new BigDecimal(1), 200));
		System.out.println(k3.validate("100812-1,4*2,5^", new BigDecimal(800), new BigDecimal(1), 200));
	}
	
	@Test
	public void testSplit() {
		List<SplitedLot> splits = k3.split("100811-3,5^", 100, 40000, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getLotterytype()+ " "+s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
}
