package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.jc.jczq.spf.Jczqspf13003;

public class JingcaiZQSPFtest {

	Jczqspf13003 spf = new Jczqspf13003();
	
	@Test
	public void test() {
		long amt = spf.getSingleBetAmount("30063003-20140219001(01)|20140219002(13)|20140219003(0)^", new BigDecimal(10), 200);
		System.out.println(amt);
	}
	
	@Test
	public void testSplit() {
		List<SplitedLot> list = spf.splitByType("30063003-20140219001(01)|20140219002(13)|20140219003(0)|20140219004(10)^", 2, 200);
		
		for(SplitedLot s:list) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti()+" "+s.getLotterytype());
		}
	}
}
