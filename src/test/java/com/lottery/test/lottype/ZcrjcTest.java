package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.Zcrjc;

public class ZcrjcTest {

	Zcrjc rj = new Zcrjc();
	
	
	@Test
	public void testValidate() {
//		Assert.assertTrue(rj.validate("400201-0,1,3,1,3,1,3,1,3,~,~,~,~,~^", new BigDecimal(200), new BigDecimal(1), 200, ""));
//		Assert.assertTrue(rj.validate("400202-0,1,3,1,3,1,3,1,3,3,1,0,0,1^", new BigDecimal(400400), new BigDecimal(1), 200, ""));
//		Assert.assertTrue(rj.validate("400202-0,1,3,1,3,1,3,1,3,3,1,0,0,~^", new BigDecimal(143000), new BigDecimal(1), 200, ""));
//		Assert.assertTrue(rj.validate("400202-0,1,3,1,3,1,3,1,3,3,1,0,0,103^", new BigDecimal(915200), new BigDecimal(1), 200, ""));
//		Assert.assertTrue(rj.validate("400202-0,1,3,1,3,1,3,1,3,3,1,0,~,103^", new BigDecimal(341000), new BigDecimal(1), 200, ""));
		
		Assert.assertTrue(rj.validate("400203-0,1,3,1,3,1,3,~,~,~,~,~,~,~#~,~,~,~,~,~,~,1,3,0,3,1,1,1^", new BigDecimal(4200), new BigDecimal(1), 200));
		Assert.assertTrue(rj.validate("400203-013,103,301,1,3,1,3,~,~,~,~,~,~,~#~,~,~,~,~,~,~,1,3,0,3,13,13,13^", new BigDecimal(226800), new BigDecimal(1), 200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse1() {
		Assert.assertTrue(rj.validate("400201-0,1,3,1,3,1,3,1,~,~,~,~,~,~^", new BigDecimal(200), new BigDecimal(1), 200));
	}
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse2() {
		Assert.assertTrue(rj.validate("400202-0,1,3,1,3,1,3,1,3,3,1,0,0,113^", new BigDecimal(915200), new BigDecimal(1), 200));
	}
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse3() {
		Assert.assertTrue(rj.validate("400203-0,1,3,1,3,1,3,~,~,~,~,~,~,~#~,~,~,~,~,~,1,~,3,0,3,1,1,1^", new BigDecimal(4200), new BigDecimal(1), 200));
	}
	
	
	@Test
	public void testPrize() {
		//1_1
		System.out.println(rj.getPrizeLevelInfo("400201-0,1,3,0,1,3,1,3,1,~,~,~,~,~^0,1,3,0,1,3,1,3,1,~,~,~,~,~^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//""
		System.out.println(rj.getPrizeLevelInfo("400201-0,1,3,0,1,3,1,3,3,~,~,~,~,~^0,1,3,0,1,3,1,3,3,~,~,~,~,~^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//"1_3"
		System.out.println(rj.getPrizeLevelInfo("400202-0,103,3,0,1,3,1,3,1,~,~,~,~,~^", "0,*,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//"1_3"
		System.out.println(rj.getPrizeLevelInfo("400202-0,103,013,0,1,3,1,3,1,~,~,~,~,~^", "0,*,3,*,1,3,1,3,1,3,1,3,1,*", BigDecimal.ONE, 200));
		
	}
	
	
	@Test
	public void testSplit() {
//		List<SplitedLot> split = rj.split("400201-0,1,3,0,1,3,1,3,1,~,~,~,~,~^", 100, 20000, 200);
//		for(SplitedLot lot:split) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
//		List<SplitedLot> split2 = rj.split("400202-0,1,3,0,1,3,1,3,1,3,~,~,~,~^", 1, 2000, 200);
//		for(SplitedLot lot:split2) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
//		List<SplitedLot> split3 = rj.split("400202-013,013,013,013,013,013,013,013,013,013,~,~,~,~^", 1, 39366000, 200);
//		for(SplitedLot lot:split3) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		List<SplitedLot> split4 = rj.split("400203-013,013,013,013,013,~,~,~,~,~,~,~,~,~#~,~,~,~,~,013,013,013,013,013,~,~,~,~^", 1, 19683000, 200);
		for(SplitedLot lot:split4) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testaa() {
		List<SplitedLot> split6 = rj.split("400201-~,~,~,~,~,1,1,3,3,3,1,3,3,3^!400201-1,~,~,~,~,~,1,3,3,3,3,1,3,3^!400201-1,1,~,~,~,~,~,3,3,3,3,3,1,3^!400201-1,1,1,~,~,~,~,~,3,3,3,3,3,3^!400201-1,1,1,1,~,~,~,~,~,3,3,3,3,1^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testbb() {
		List<SplitedLot> split6 = rj.split("400201-~,~,~,~,~,1,1,3,3,3,1,3,3,3^1,~,~,~,~,~,1,3,3,3,3,1,3,3^1,1,~,~,~,~,~,3,3,3,3,3,1,3^1,1,1,~,~,~,~,~,3,3,3,3,3,3^1,1,1,1,1,~,~,~,~,~,3,3,3,1^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testcc() {
		List<SplitedLot> split6 = rj.split("400201-~,~,~,~,~,1,1,3,3,3,1,3,3,3^1,~,~,~,~,~,1,3,3,3,3,1,3,3^1,1,~,~,~,~,~,3,3,3,3,3,1,3^1,1,1,~,~,~,~,~,3,3,3,3,3,3^1,1,1,1,~,~,~,~,~,3,3,3,3,1^!400201-1,1,1,1,1,~,~,~,~,~,3,3,3,1^", 100, 120000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
}
