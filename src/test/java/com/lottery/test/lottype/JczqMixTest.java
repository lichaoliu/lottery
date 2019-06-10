package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.lottery.lottype.JczqMix;
import com.lottery.lottype.SplitedLot;

public class JczqMixTest {
	
	JczqMix mix = new JczqMix();

	@Test
	public void test1() {
//		boolean flag = mix.validate("300512001-20140501301*3001(3,0)|20140501302*3002(3,0)|20140501303*3001(3,0)^", new BigDecimal(2400), BigDecimal.ONE, 200, "");
//		System.out.println(flag);
		
//		boolean flag = mix.validate("301112001-20140501001*3006(3,0)|20140501001*3010(3,0)|20140501002*3010(3,0)|20140501003*3006(3,0)^", new BigDecimal(4000), BigDecimal.ONE, 200);
//		System.out.println(flag);
		boolean flag = mix.validate("301115001-20140813004*3006(1,0)|20140813005*3006(3)|20140813007*3006(1,0)|20140813009*3010(1,0)|20140813019*3006(3)^", new BigDecimal(1600), BigDecimal.ONE, 200);
		System.out.println(flag);
		
	}
	
	
	
	@Test
	public void test2() {
		List<SplitedLot> splits = mix.split("301115001-20140813004*3006(1,0)|20140813005*3006(3)|20140813007*3006(1,0)|20140813009*3010(1,0)|20140813019*3006(3)^", 1, 1600, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotterytype()+" "+s.getAmt());
		}
		
	}
	
	
	@Test
	public void test3() {
		
		List<SplitedLot> splits = mix.split("301122001-20140501001*3006(3,0)|20140501001*3010(3,0)#20140501002*3010(3,0)|20140501003*3006(3,0)|20140501004*3010(3,0)^", 1, 4800, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotterytype()+" "+s.getAmt());
		}
		
	}
}
