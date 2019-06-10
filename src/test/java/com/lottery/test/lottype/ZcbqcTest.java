package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.Zcbqc;

public class ZcbqcTest {

	Zcbqc bqc = new Zcbqc();
	
	@Test
	public void testValidate() {
		Assert.assertTrue(bqc.validate("400401-0,1,3,0,1,3,1,3,1,3,1,3^", new BigDecimal(200), BigDecimal.ONE, 200));
		Assert.assertTrue(bqc.validate("400401-0,1,3,0,1,3,1,3,1,3,1,3^", new BigDecimal(20000), new BigDecimal(100), 200));
		
		Assert.assertTrue(bqc.validate("400402-013,103,3,0,1,3,1,3,1,3,1,3^", new BigDecimal(1800), new BigDecimal(1), 200));
		Assert.assertTrue(bqc.validate("400402-013,103,3,0,1,3,1,3,1,3,1,3^", new BigDecimal(180000), new BigDecimal(100), 200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidateFalse() {
		Assert.assertTrue(bqc.validate("400402-013,113,3,0,1,3,1,3,1,3,1,3^", new BigDecimal(1800), new BigDecimal(1), 200));
	}
	
	
	@Test
	public void testPrize() {
		//1-1
		System.out.println(bqc.getPrizeLevelInfo("400401-0,1,3,0,1,3,1,3,1,3,1,3^0,1,3,0,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//1-1
		System.out.println(bqc.getPrizeLevelInfo("400402-0,130,3,0,1,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//""
		System.out.println(bqc.getPrizeLevelInfo("400402-0,130,3,0,3,3,1,3,1,3,1,3^", "0,1,3,0,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
		//"1-6"
		System.out.println(bqc.getPrizeLevelInfo("400402-0,130,3,01,1,3,1,3,1,3,1,3^", "0,*,3,*,1,3,1,3,1,3,1,3", BigDecimal.ONE, 200));
		
	}
	
	
	@Test
	public void testSplit() {
//		List<SplitedLot> split = bqc.split("400401-0,1,3,0,1,3,0,1,3,0,1,3^", 100, 20000, 200);
//		for(SplitedLot lot:split) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
//		}
		
		
		List<SplitedLot> split2 = bqc.split("400402-013,013,013,013,013,013,013,013,13,01,1,3^", 1, 5248800, 200);
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	
	@Test
	public void testaa() {
		List<SplitedLot> split6 = bqc.split("400401-1,1,1,1,1,1,3,3,3,3,3,3^!400401-1,1,1,1,1,1,3,3,0,3,3,3^!400401-1,1,1,1,1,1,3,3,3,0,3,3^!400401-1,1,1,1,1,1,3,3,3,3,0,3^!400401-1,1,1,1,1,1,3,3,3,3,3,0^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testbb() {
		List<SplitedLot> split6 = bqc.split("400401-1,1,1,1,1,1,3,3,3,3,3,3^!400401-1,1,1,1,1,1,3,3,0,3,3,3^1,1,1,1,1,1,3,3,3,0,3,3^1,1,1,1,1,1,3,3,3,3,0,3^1,1,1,1,1,1,3,3,3,3,3,0^", 100, 100000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testcc() {
		List<SplitedLot> split6 = bqc.split("400401-1,1,1,1,1,1,3,3,3,3,3,3^1,1,1,1,1,1,3,3,0,3,3,3^1,1,1,1,1,1,3,3,3,0,3,3^1,1,1,1,1,1,3,3,3,3,0,3^1,1,1,1,1,1,3,3,3,3,3,0^!400401-0,0,0,0,0,0,1,3,1,3,1,3^", 100, 120000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
	
	@Test
	public void testdd() {
		List<SplitedLot> split6 = bqc.split("400401-1,1,1,1,1,1,3,3,3,3,3,3^1,1,1,1,1,1,3,3,0,3,3,3^1,1,1,1,1,1,3,3,3,0,3,3^", 10, 6000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti() + " "+lot.getAmt());
		}
	}
}
