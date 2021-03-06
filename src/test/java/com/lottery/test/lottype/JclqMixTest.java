package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.lottery.lottype.JclqMix;
import com.lottery.lottype.SplitedLot;

public class JclqMixTest {
	
	JclqMix mix = new JclqMix();

	@Test
	public void test1() {
//		boolean flag = mix.validate("300512001-20140501301*3001(3,0)|20140501302*3002(3,0)|20140501303*3001(3,0)^", new BigDecimal(2400), BigDecimal.ONE, 200, "");
//		System.out.println(flag);
		
//		boolean flag = mix.validate("300512001-20140501301*3001(3,0)|20140501301*3002(3,0)|20140501302*3002(3,0)|20140501303*3001(3,0)^", new BigDecimal(4000), BigDecimal.ONE, 200, "");
//		System.out.println(flag);
		
//		boolean flag = mix.validate("300522001-20140501301*3001(3,0)|20140501301*3002(3,0)#20140501302*3002(3,0)|20140501303*3001(3,0)|20140501304*3002(3,0)^", new BigDecimal(4800), BigDecimal.ONE, 200, "");
//		System.out.println(flag);
		
		boolean flag = mix.validate("300512001-20140529302*3001(3,0)|20140529301*3002(3,0)^", new BigDecimal(800), BigDecimal.ONE, 200);
		System.out.println(flag);
		
	}
	
	
	
	@Test
	public void test2() {
		
		List<SplitedLot> splits = mix.split("300512001-20140501301*3001(3,0)|20140501301*3002(3,0)|20140501302*3002(3,0)|20140501303*3001(3,0)^", 1, 4000, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotterytype()+" "+s.getAmt());
		}
		
	}
	
	
	
	@Test
	public void test3() {
		
		List<SplitedLot> splits = mix.split("300522001-20140501301*3001(3,0)|20140501301*3002(3,0)#20140501302*3002(3,0)|20140501303*3001(3,0)|20140501304*3002(3,0)^", 1, 4800, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotterytype()+" "+s.getAmt());
		}
		
	}
}
