package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.ThreeD;

public class ThreedTest {

	ThreeD threed = new ThreeD();
	
	@Test
	public void testValidateSingle() {
		Assert.assertTrue(threed.validate("100201-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100201-01,02,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(threed.validate("100201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100202-01,01,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100202-01,01,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(threed.validate("100202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100203-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100203-01,02,03^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(threed.validate("100203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
		
		try {
			threed.validate("100203-01,01,01^", BigDecimal.valueOf(200L),new BigDecimal(1),200);
		}catch(LotteryException e) {
			e.printStackTrace();
			Assert.assertTrue(e.getErrorCode()==ErrorCode.betamount_error);
		}
		
	}
	
	@Test
	public void testValidateDouble() {
		Assert.assertTrue(threed.validate("100211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", BigDecimal.valueOf(200000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", BigDecimal.valueOf(20000000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100212-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(18000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100212-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(1800000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100213-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100213-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(2400000L),new BigDecimal(100),200));
		
	}
	
	
	@Test
	public void testValidateHezhi() {
		Assert.assertTrue(threed.validate("100221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", BigDecimal.valueOf(200000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", BigDecimal.valueOf(20000000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", BigDecimal.valueOf(18000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", BigDecimal.valueOf(1800000L),new BigDecimal(100),200));
		
		Assert.assertTrue(threed.validate("100223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", BigDecimal.valueOf(2400000L),new BigDecimal(100),200));
	}
	
	
	
	@Test
	public void testSplitSingle() {
		List<SplitedLot> split = threed.split("100201-01,02,03^", 1, 200, 200);
		List<SplitedLot> split1 = threed.split("100201-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split2 = threed.split("100201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 1, 1200, 200);
		List<SplitedLot> split3 = threed.split("100201-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 100, 120000, 200);
		
		
		List<SplitedLot> split4 = threed.split("100201-01,02,03^", 1, 200, 200);
		List<SplitedLot> split5 = threed.split("100201-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split6 = threed.split("100202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", 1, 1200, 200);
		List<SplitedLot> split7 = threed.split("100202-01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^01,01,03^", 100, 120000, 200);
		
		
		List<SplitedLot> split8 = threed.split("100203-01,02,03^", 1, 200, 200);
		List<SplitedLot> split9 = threed.split("100203-01,02,03^", 100, 20000, 200);
		List<SplitedLot> split10 = threed.split("100203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 1, 1200, 200);
		List<SplitedLot> split11 = threed.split("100203-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 100, 120000, 200);
		checkSplit(split);
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
		
		List<SplitedLot> split = threed.split("100211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", 1, 200000, 200);
		List<SplitedLot> split2 = threed.split("100211-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00^", 100, 20000000, 200);
		
		List<SplitedLot> split3 = threed.split("100212-00,01,02,03,04,05,06,07,08,09^", 1, 18000, 200);
		List<SplitedLot> split4 = threed.split("100212-00,01,02,03,04,05,06,07,08,09^", 100, 1800000, 200);
		
		List<SplitedLot> split5 = threed.split("100213-00,01,02,03,04,05,06,07,08,09^", 1, 24000L, 200);
		List<SplitedLot> split6 = threed.split("100213-00,01,02,03,04,05,06,07,08,09^", 100, 2400000L, 200);
		checkSplit(split);
		checkSplit(split2);
		checkSplit(split3);
		checkSplit(split4);
		checkSplit(split5);
		checkSplit(split6);
		
	}
	
	
	
	@Test
	public void testSplitHezhi() {
		List<SplitedLot> split = threed.split("100221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", 1, 200000L, 200);
		List<SplitedLot> split1 = threed.split("100221-00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27^", 100, 20000000L, 200);
		
		List<SplitedLot> split2 = threed.split("100222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", 1, 18000L, 200);
		List<SplitedLot> split3 = threed.split("100222-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26^", 100, 1800000L, 200);
		
		List<SplitedLot> split4 = threed.split("100223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", 1, 24000L, 200);
		List<SplitedLot> split5 = threed.split("100223-03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24^", 100, 2400000L, 200);
		checkSplit(split);
		checkSplit(split1);
		checkSplit(split2);
		checkSplit(split3);
		checkSplit(split4);
		checkSplit(split5);

	}
	
	
	@Test
	public void test11() {
		Assert.assertTrue(threed.validate("100212-00,01,02,03,04,05,06,07,08,09^", BigDecimal.valueOf(18000L),new BigDecimal(1),200));
	}
	
	
	private void checkSplit(List<SplitedLot> splits) {
		long totalamt = 0;
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
	}
	
	
	@Test
	public void test() {
//		List<SplitedLot> split5 = threed.split("100213-00,01,02,03,04,05,06,07,08,09^", 1, 24000L, 200);
//		
//		for(SplitedLot lot:split5) {
//			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
//		}
		
		
		List<SplitedLot> split6 = threed.split("100213-01,00,02,03,04,05,06,07,08^", 1, 16800, 200);
		
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
	}
 	
	
	
	
//===============================================
	
	@Test
	public void testNew() {
		
		
		Assert.assertTrue(threed.validate("100204-01,~,~^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100204-01,~,~^~,01,~^~,~,00^~,09,~^~,~,08^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100204-01,~,~^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100204-01,~,~^~,01,~^~,~,00^~,09,~^~,~,08^", BigDecimal.valueOf(10000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100205-01,02,~^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100205-01,02,~^~,01,03^~,00,00^~,09,09^01,~,08^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100205-01,02,~^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100205-01,02,~^~,01,03^~,00,00^~,09,09^01,~,08^", BigDecimal.valueOf(10000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100206-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100206-01,02,03^04,01,03^00,00,00^08,09,09^01,07,08^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100206-01,02,03^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100206-01,02,03^04,01,03^00,00,00^08,09,09^01,07,08^", BigDecimal.valueOf(10000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100214-01|02|~^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100214-01|02|03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100214-01,02|02,03|03^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100214-01|02|~^", BigDecimal.valueOf(4000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100214-01|02|03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100214-01,02|02,03|03^", BigDecimal.valueOf(10000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100215-01|02,03|~^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100215-01|02|03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100215-01,02|02,03|03^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100215-01|02,03|~^", BigDecimal.valueOf(4000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100215-01|02|03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		Assert.assertTrue(threed.validate("100215-01,02|02,03|03^", BigDecimal.valueOf(16000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100216-01,02,03|02,03,04|03,04^", BigDecimal.valueOf(3600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100216-01,02,03|02,03,04|03,04^", BigDecimal.valueOf(36000L),new BigDecimal(10),200));
		
		
		
		
	}
	
	
	@Test
	public void testNew2() {
		Assert.assertTrue(threed.validate("100224-00,01,27^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100224-00,01,27^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100225-01,02,26^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100225-01,02,26^", BigDecimal.valueOf(8000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100233-01,02^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100233-01,02^", BigDecimal.valueOf(4000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100234-04,05^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100234-04,05^", BigDecimal.valueOf(4000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100235-00^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100235-00^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100236-00^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100236-00^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		
		
	}
	
	
	
	@Test
	public void testNew3() {
		Assert.assertTrue(threed.validate("100207-00^02^03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100207-00^02^03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100217-00,02,03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100217-00,02,03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100208-00^02^03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100208-00^02^03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100218-00,02,03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100218-00,02,03^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(threed.validate("100209-00,02^02,03^03,04^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100209-00,02^02,03^03,04^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		
		Assert.assertTrue(threed.validate("100219-00,02,04^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(threed.validate("100219-00,02,04^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
	}
	
	
	@Test
	public void testNewSplit3() {
		
		List<SplitedLot> split1 = threed.split("100207-00^02^03^04^05^06^", 100, 120000, 200);
		for(SplitedLot lot:split1) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split2 = threed.split("100208-00^02^03^04^05^06^", 100, 120000, 200);
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split3 = threed.split("100209-00,02^02,03^03,04^04,05^05,06^06,07^", 100, 120000, 200);
		for(SplitedLot lot:split3) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split4 = threed.split("100217-00,02,03^", 100, 60000, 200);
		for(SplitedLot lot:split4) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split5 = threed.split("100218-00,02,03^", 100, 60000, 200);
		for(SplitedLot lot:split5) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split6 = threed.split("100219-00,02,04^", 100, 60000, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
	}
	
	
	@Test
	public void testNewSplit2() {
		List<SplitedLot> split1 = threed.split("100224-00,01,27^", 100, 60000L, 200);
		for(SplitedLot lot:split1) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split2 = threed.split("100225-01,02,26^", 100, 80000L, 200);
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split3 = threed.split("100233-01,02^", 100, 40000L, 200);
		for(SplitedLot lot:split3) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split4 = threed.split("100234-04,05^", 100, 40000L, 200);
		for(SplitedLot lot:split4) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split5 = threed.split("100235-00^", 100, 20000L, 200);
		for(SplitedLot lot:split5) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split6 = threed.split("100236-00^", 100, 20000L, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
	}
	
	@Test
	public void testNewSplit() {
		List<SplitedLot> split1 = threed.split("100204-01,~,~^~,01,~^~,~,00^~,09,~^~,~,08^~,~,09^", 100, 120000, 200);
		for(SplitedLot lot:split1) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split2 = threed.split("100205-01,02,~^~,01,03^~,00,00^~,09,09^01,~,08^01,~,08^", 100, 120000, 200);
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		List<SplitedLot> split3 = threed.split("100206-01,02,03^04,01,03^00,00,00^08,09,09^01,07,08^01,07,09^", 100, 120000, 200);
		for(SplitedLot lot:split3) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split4 = threed.split("100214-01,02|02,03|03,04^", 100, 120000L, 200);
		for(SplitedLot lot:split4) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split5 = threed.split("100215-01,02|02,03|03^", 100, 160000L, 200);
		for(SplitedLot lot:split5) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
		
		List<SplitedLot> split6 = threed.split("100216-01,02|03,04|05,06^", 100, 160000L, 200);
		for(SplitedLot lot:split6) {
			System.out.println(lot.getBetcode()+" "+lot.getAmt()+" "+lot.getLotMulti());
		}
		
	}
	
	
	@Test
	public void testNewPrize() {
		Assert.assertTrue(isPrizeInfoEqual("1D_3", threed.getPrizeLevelInfo("100204-03,~,~^~,01,~^~,~,00^~,04,~^~,~,06^", "3,4,6", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("2D_3", threed.getPrizeLevelInfo("100205-01,02,~^01,~,03^~,02,03^~,09,09^01,03,~^", "1,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("3T1_1,3T2_2", threed.getPrizeLevelInfo("100206-01,02,03^04,01,03^01,00,03^08,09,09^01,02,08^", "1,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("1D_3", threed.getPrizeLevelInfo("100214-01,02|02,03|03^", "1,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("2D_3", threed.getPrizeLevelInfo("100215-01,02|02,03|03^", "1,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("3T1_1,3T2_5", threed.getPrizeLevelInfo("100216-01,02,03|02,03,04|03,04^", "1,2,3", BigDecimal.ONE,200)));
	}
	
	
	@Test
	public void testNewPrize2() {
		Assert.assertTrue(isPrizeInfoEqual("HS0_1", threed.getPrizeLevelInfo("100224-00,01,27^", "9,9,9", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("HS12_1", threed.getPrizeLevelInfo("100224-00,12,27^", "3,4,5", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100225-01,09,26^", "3,3,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", threed.getPrizeLevelInfo("100225-03,04,05^", "0,0,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("3_1", threed.getPrizeLevelInfo("100225-03,04,05^", "0,1,2", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("DX_1", threed.getPrizeLevelInfo("100233-01,02^", "0,0,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100233-02^", "0,0,3", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("JO_1", threed.getPrizeLevelInfo("100234-04,05^", "1,3,5", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("JO_1", threed.getPrizeLevelInfo("100234-05^", "1,3,5", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("JO_1", threed.getPrizeLevelInfo("100234-04^", "2,4,6", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100234-05^", "2,4,6", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100234-05^", "1,2,3", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("ST_1", threed.getPrizeLevelInfo("100235-00^", "1,1,1", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("ST_1", threed.getPrizeLevelInfo("100235-00^", "2,2,2", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100235-00^", "1,3,5", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("TLJ_1", threed.getPrizeLevelInfo("100236-00^", "1,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("TLJ_1", threed.getPrizeLevelInfo("100236-00^", "3,2,1", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100236-00^", "1,3,5", BigDecimal.ONE,200)));
	
	}
	
	
	@Test
	public void testNewPrize3() {
		Assert.assertTrue(isPrizeInfoEqual("C1D1_3", threed.getPrizeLevelInfo("100207-00^02^03^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C1D1_3", threed.getPrizeLevelInfo("100217-00,02,03^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C1D3_1", threed.getPrizeLevelInfo("100207-00^02^03^", "3,3,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C1D3_1", threed.getPrizeLevelInfo("100217-00,02,03^", "3,3,3", BigDecimal.ONE,200)));
//		
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100208-00^02^03^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D1_1", threed.getPrizeLevelInfo("100208-00^02^03^", "0,2,2", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D1_1", threed.getPrizeLevelInfo("100208-00^02^03^", "2,2,2", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", threed.getPrizeLevelInfo("100218-00^02^03^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D1_1", threed.getPrizeLevelInfo("100218-00,02,03^", "0,2,2", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D1_1", threed.getPrizeLevelInfo("100218-00,02,03^", "2,2,2", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("C2D2_2", threed.getPrizeLevelInfo("100209-00,02^02,03^03,04^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D2_1", threed.getPrizeLevelInfo("100209-00,02^02,03^03,04^", "2,2,3", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("C2D2_1", threed.getPrizeLevelInfo("100219-00,02,04^", "0,2,3", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("C2D2_1", threed.getPrizeLevelInfo("100219-03,02,04^", "2,2,3", BigDecimal.ONE,200)));
		
	}
	
	
	private boolean isPrizeInfoEqual(String str1,String str2) {
		Map<String,String> map1 = new HashMap<String, String>();
		Map<String,String> map2 = new HashMap<String, String>();
		
		if(str1.equals(str2)) {
			return true;
		}
		for(String str:str1.split(",")) {
			map1.put(str.split("_")[0], str.split("_")[1]);
		}
		
		for(String str:str2.split(",")) {
			map2.put(str.split("_")[0], str.split("_")[1]);
		}
		
		if(map1.keySet().size()!=map2.keySet().size()) {
			return false;
		}
		
		
		for(String key:map1.keySet()) {
			if(!map2.get(key).equals(map1.get(key))) {
				return false;
			}
		}
		return true;
	}
}
