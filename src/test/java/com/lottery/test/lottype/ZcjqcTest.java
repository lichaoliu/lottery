package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.Zcjqc;

public class ZcjqcTest {

	Zcjqc jqc = new Zcjqc();
	
	@Test
	public void testValidate() {
		Assert.assertTrue(jqc.validate("400301-0,1,2,3,0,1,2,3^", new BigDecimal(200), new BigDecimal(1), 200));
		Assert.assertTrue(jqc.validate("400301-0,1,2,3,0,1,2,3^", new BigDecimal(20000), new BigDecimal(100), 200));
		Assert.assertTrue(jqc.validate("400302-0123,123,2,3,0,1,2,3^", new BigDecimal(2400), new BigDecimal(1), 200));
		Assert.assertTrue(jqc.validate("400302-0123,123,2,3,0,1,2,3^", new BigDecimal(240000), new BigDecimal(100), 200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse() {
		Assert.assertTrue(jqc.validate("400302-0123,113,2,3,0,1,2,3^", new BigDecimal(240000), new BigDecimal(100), 200));
	}
	
	@Test
	public void testPrize() {
		
		//1-1
		System.out.println(jqc.getPrizeLevelInfo("400301-1,0,1,0,2,3,2,2^1,0,1,0,2,3,2,2^", "1,0,1,0,2,3,2,2", BigDecimal.ONE, 200));
		
		//1-1
		System.out.println(jqc.getPrizeLevelInfo("400301-0,1,2,3,0,1,2,3^0,1,2,3,0,1,2,3^", "0,1,*,3,0,1,2,3", BigDecimal.ONE, 200));
		
		//""
		System.out.println(jqc.getPrizeLevelInfo("400301-1,1,2,3,0,1,2,3^1,1,2,3,0,1,2,3^", "0,1,*,3,0,1,2,3", BigDecimal.ONE, 200));
		
		//"1-1"
		System.out.println(jqc.getPrizeLevelInfo("400302-0,1,2,123,0,1,2,3^", "0,1,*,3,0,1,2,3", BigDecimal.ONE, 200));
		
		
		//""
		System.out.println(jqc.getPrizeLevelInfo("400302-1,1,2,123,0,1,2,3^", "0,1,*,3,0,1,2,3", BigDecimal.ONE, 200));
		
		//"1-6"
		System.out.println(jqc.getPrizeLevelInfo("400302-0,1,12,123,0,1,2,3^", "0,1,*,*,0,1,2,3", BigDecimal.ONE, 200));
	}
	
	@Test
	public void testSplit() {
//		List<SplitedLot> split = jqc.split("400301-0,1,2,3,1,2,3,1^", 100, 20000, 200);
//		for(SplitedLot lot:split) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		
		List<SplitedLot> split2 = jqc.split("400302-0123,0123,0123,0123,0123,123,123,12^", 1, 3686400, 200);
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	
	@Test
	public void testaa() {
		List<SplitedLot> split6 = jqc.split("400301-1,1,1,1,2,2,2,2^!400301-1,1,1,1,2,2,2,1^!400301-1,1,1,1,2,2,1,2^!400301-1,1,1,1,2,1,2,2^!400301-1,1,1,1,2,2,2,2^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testbb() {
		List<SplitedLot> split6 = jqc.split("400301-1,1,1,1,2,2,2,2^!400301-1,1,1,1,2,2,2,1^1,1,1,1,2,2,1,2^1,1,1,1,2,1,2,2^1,1,1,1,2,2,2,2^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testcc() {
		List<SplitedLot> split6 = jqc.split("400301-1,1,1,1,2,2,2,2^1,1,1,1,2,2,2,1^1,1,1,1,2,2,1,2^1,1,1,1,2,2,1,2^1,1,1,1,2,1,2,2^!400301-1,1,1,1,3,2,2,2^", 100, 120000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
}
