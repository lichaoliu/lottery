package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.Zcsfc;

public class ZcsfcTest {

	Zcsfc sfc = new Zcsfc();
	
	@Test
	public void testValidate() {
		Assert.assertTrue(sfc.validate("400101-0,1,3,0,1,3,1,3,1,3,1,3,1,3^", new BigDecimal(200), BigDecimal.ONE, 200));
		Assert.assertTrue(sfc.validate("400101-0,1,3,0,1,3,1,3,1,3,1,3,1,3^", new BigDecimal(20000), new BigDecimal(100), 200));
		
		Assert.assertTrue(sfc.validate("400102-013,103,3,0,1,3,1,3,1,3,1,3,1,3^", new BigDecimal(1800), new BigDecimal(1), 200));
		Assert.assertTrue(sfc.validate("400102-013,103,3,0,1,3,1,3,1,3,1,3,1,3^", new BigDecimal(180000), new BigDecimal(100), 200));
		
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse() {
		Assert.assertTrue(sfc.validate("400102-003,103,3,0,1,3,1,3,1,3,1,3,1,3^", new BigDecimal(180000), new BigDecimal(100), 200));
	}
	
	
	@Test
	public void testPrize() {
		
		Assert.assertEquals("1_2", sfc.getPrizeLevelInfo("400101-0,1,3,0,1,3,1,3,1,3,1,3,1,3^0,1,3,0,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		Assert.assertEquals("2_2", sfc.getPrizeLevelInfo("400101-0,1,3,1,1,3,1,3,1,3,1,3,1,3^0,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		Assert.assertEquals("", sfc.getPrizeLevelInfo("400101-1,1,3,1,1,3,1,3,1,3,1,3,1,3^1,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		Assert.assertEquals("1_4", sfc.getPrizeLevelInfo("400101-0,1,3,0,1,3,1,3,1,3,1,3,1,3^0,1,3,0,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		Assert.assertEquals("2_4", sfc.getPrizeLevelInfo("400101-0,1,3,1,1,3,1,3,1,3,1,3,1,3^0,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		Assert.assertEquals("", sfc.getPrizeLevelInfo("400101-1,1,3,1,1,3,1,3,1,3,1,3,1,3^1,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		Assert.assertEquals("1_4", sfc.getPrizeLevelInfo("400101-0,1,3,0,1,3,1,3,1,3,1,3,1,3^0,1,3,0,1,3,1,3,1,3,1,3,1,3^", "*,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		Assert.assertEquals("2_4", sfc.getPrizeLevelInfo("400101-0,1,3,1,1,3,1,3,1,3,1,3,1,3^0,1,3,1,1,3,1,3,1,3,1,3,1,3^", "*,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		Assert.assertEquals("", sfc.getPrizeLevelInfo("400101-1,3,3,1,1,3,1,3,1,3,1,3,1,3^1,3,3,1,1,3,1,3,1,3,1,3,1,3^", "*,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		
		
		Assert.assertEquals("2_4,1_2", sfc.getPrizeLevelInfo("400102-0,1,3,0,1,3,1,3,1,3,1,3,1,310^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
//		Assert.assertEquals("2_2", sfc.getPrizeLevelInfo("400101-0,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
//		Assert.assertEquals("", sfc.getPrizeLevelInfo("400101-1,1,3,1,1,3,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3,1,3", new BigDecimal(2), 200));
		
		
		System.out.println(sfc.getPrizeLevelInfo("400102-0,1,3,0,1,3,1,3,1,3,1,3,1,310^", "0,1,3,0,1,3,1,3,1,3,1,3,1,*", new BigDecimal(1), 200));
		System.out.println(sfc.getPrizeLevelInfo("400102-0,1,3,0,1,3,1,3,1,3,1,3,031,310^", "0,1,3,0,1,3,1,3,1,3,1,3,*,3", new BigDecimal(1), 200));
	}
	
	@Test
	public void testSplit() {
//		List<SplitedLot> split = sfc.split("400101-1,3,3,1,1,3,1,3,1,3,1,3,1,3^", 100, 20000, 200);
//		for(SplitedLot lot:split) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		
//		List<SplitedLot> split2 = sfc.split("400102-130,013,3,1,1,3,1,3,1,3,1,3,1,3^", 100, 180000, 200);
//		for(SplitedLot lot:split2) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
//		List<SplitedLot> split3 = sfc.split("400102-130,013,013,013,013,013,1,3,1,3,1,3,1,3^", 100, 14580000, 200);
//		for(SplitedLot lot:split3) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		
//		List<SplitedLot> split4 = sfc.split("400102-130,013,013,013,013,013,013,013,013,3,1,3,1,013^", 1, 11809800, 200);
//		for(SplitedLot lot:split4) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
//		List<SplitedLot> split5 = sfc.split("400102-130,013,013,01,013,013,013,013,013,3,1,3,1,013^", 1, 7873200, 200);
//		for(SplitedLot lot:split5) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		List<SplitedLot> split6 = sfc.split("400102-130,013,013,01,013,013,013,013,013,3,1,3,1,013^", 10, 78732000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testaa() {
		List<SplitedLot> split6 = sfc.split("400101-1,1,1,1,1,1,1,3,3,3,1,3,3,3^!400101-1,1,1,1,1,1,1,3,3,3,3,1,3,3^!400101-1,1,1,1,1,1,1,3,3,3,3,3,1,3^!400101-1,1,1,1,1,1,1,3,3,3,3,3,3,3^!400101-1,1,1,1,1,1,1,3,3,3,3,3,3,1^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testbb() {
		List<SplitedLot> split6 = sfc.split("400101-1,1,1,1,1,1,1,3,3,3,1,3,3,3^1,1,1,1,1,1,1,3,3,3,3,1,3,3^1,1,1,1,1,1,1,3,3,3,3,3,1,3^1,1,1,1,1,1,1,3,3,3,3,3,3,3^1,1,1,1,1,1,1,3,3,3,3,3,3,1^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testcc() {
		List<SplitedLot> split6 = sfc.split("400101-1,1,1,1,1,1,1,3,3,3,1,3,3,3^1,1,1,1,1,1,1,3,3,3,3,1,3,3^1,1,1,1,1,1,1,3,3,3,3,3,1,3^1,1,1,1,1,1,1,3,3,3,3,3,3,3^1,1,1,1,1,1,1,3,3,3,3,3,3,1^!400101-1,1,1,1,1,1,1,3,3,3,3,3,3,1^", 100, 120000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
}
