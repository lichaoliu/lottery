package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.PLS;
import com.lottery.lottype.SplitedLot;

public class PLSTest {

	PLS pls = new PLS();
	
	@Test
	public void testValidateSingle() {
		Assert.assertTrue(pls.validate("200201-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200201-01,02,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(pls.validate("200201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200202-01,01,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200202-01,01,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(pls.validate("200202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200203-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200203-01,02,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(pls.validate("200203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		
		try {
			pls.validate("200203-01,01,01^", BigDecimal.valueOf(200L),new BigDecimal(1),200);
		}catch(LotteryException e) {
			e.printStackTrace();
			Assert.assertTrue(e.getErrorCode()==ErrorCode.betamount_error);
		}
		
	}
	
	@Test
	public void testValidateDouble() {
		Assert.assertTrue(pls.validate("200211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", BigDecimal.valueOf(200000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", BigDecimal.valueOf(20000000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200212-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(18000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200212-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(1800000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200213-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200213-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(2400000L),new BigDecimal(100),200));
		
	}
	
	
	@Test
	public void testValidateHezhi() {
		Assert.assertTrue(pls.validate("200221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", BigDecimal.valueOf(200000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", BigDecimal.valueOf(20000000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", BigDecimal.valueOf(18000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", BigDecimal.valueOf(1800000L),new BigDecimal(100),200));
		
		Assert.assertTrue(pls.validate("200223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		Assert.assertTrue(pls.validate("200223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", BigDecimal.valueOf(2400000L),new BigDecimal(100),200));
	}
	
	@Test
	public void testPrize() {
		System.out.println(pls.getPrizeLevelInfo("200212-03,06^", "2,2,6", BigDecimal.ONE, 200));
	}
	
	@Test
	public void testSplitSingle() {
//		List<SplitedLot> split = pls.split("200201-01,02,03^", 1, 200, 200);
		List<SplitedLot> split1 = pls.split("200201-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split2 = pls.split("200201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 1, 1200, 200);
		List<SplitedLot> split3 = pls.split("200201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 100, 120000, 200);
		
		
		List<SplitedLot> split4 = pls.split("200201-01,02,03^", 1, 200, 200);
		List<SplitedLot> split5 = pls.split("200201-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split6 = pls.split("200202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", 1, 1200, 200);
		List<SplitedLot> split7 = pls.split("200202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", 100, 120000, 200);
		
		
		List<SplitedLot> split8 = pls.split("200203-01,02,03^", 1, 200, 200);
		List<SplitedLot> split9 = pls.split("200203-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split10 = pls.split("200203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 1, 1200, 200);
		List<SplitedLot> split11 = pls.split("200203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 100, 120000, 200);
//		checkSplit(split);
		checkSplit(split1);
		checkSplit(split2);
		checkSplit(split3);
		checkSplit(split4);
		checkSplit(split5);
		checkSplit(split6);
		checkSplit(split7);
		checkSplit(split8);
		checkSplit(split9);
		checkSplit(split10);
		checkSplit(split11);
	}
	
	
	@Test
	public void testSplitDouble() {
		
		List<SplitedLot> split = pls.split("200211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", 1, 200000, 200);
		List<SplitedLot> split2 = pls.split("200211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", 100, 20000000, 200);
		
		List<SplitedLot> split3 = pls.split("200212-00,01,02,03,04,05,06,07,08,09^", 1, 18000, 200);
		List<SplitedLot> split4 = pls.split("200212-00,01,02,03,04,05,06,07,08,09^", 100, 1800000, 200);
		
		List<SplitedLot> split5 = pls.split("200213-00,01,02,03,04,05,06,07,08,09^", 1, 24000L, 200);
		List<SplitedLot> split6 = pls.split("200213-00,01,02,03,04,05,06,07,08,09^", 100, 2400000L, 200);
		checkSplit(split);
		checkSplit(split2);
		checkSplit(split3);
		checkSplit(split4);
		checkSplit(split5);
		checkSplit(split6);
		
	}
	
	
	
	@Test
	public void testSplitHezhi() {
		List<SplitedLot> split = pls.split("200221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", 1, 200000L, 200);
		List<SplitedLot> split1 = pls.split("200221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", 100, 20000000L, 200);
		
		List<SplitedLot> split2 = pls.split("200222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", 1, 18000L, 200);
		List<SplitedLot> split3 = pls.split("200222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", 100, 1800000L, 200);
		
		List<SplitedLot> split4 = pls.split("200223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", 1, 24000L, 200);
		List<SplitedLot> split5 = pls.split("200223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", 100, 2400000L, 200);
		checkSplit(split);
		checkSplit(split1);
		checkSplit(split2);
		checkSplit(split3);
		checkSplit(split4);
		checkSplit(split5);

	}
	
	
	private void checkSplit(List<SplitedLot> splits) {
		long totalamt = 0;
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
	}
	
	@Test
	public void test() {
		List<SplitedLot> split3 = pls.split("200212-00,01,02,03,04,05,06,07,08,09^", 1, 18000, 200);
		
		for(SplitedLot lot:split3) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
//		List<SplitedLot> split4 = pls.split("200212-00,01,02,03,04,05,06,07,08^", 1, 14400, 200);
//		
//		for(SplitedLot lot:split4) {
//			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
//		}
		
//		List<SplitedLot> split5 = pls.split("200213-00,01,02,03,04,05,06,07,08,09^", 1, 24000L, 200);
//		
//		for(SplitedLot lot:split5) {
//			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
//		}
		
		
//		List<SplitedLot> split6 = pls.split("200213-00,01,02,03,04,05,06,07,08^", 1, 16800, 200);
//		
//		for(SplitedLot lot:split6) {
//			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
//		}
	}
	
}
