package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.DLT;
import com.lottery.lottype.SplitedLot;

public class DaletouTest {

	DLT dlt = new DLT();
	
	@Test
	public void testValidate01() {
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^", BigDecimal.valueOf(300L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", BigDecimal.valueOf(1800L),new BigDecimal(1),300));
		
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^", BigDecimal.valueOf(30000L),new BigDecimal(100),300));
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", BigDecimal.valueOf(180000L),new BigDecimal(100),300));
	
		Assert.assertTrue(dlt.validate("200101-31,32,33,34,35|11,12^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void testValidate02() {
		Assert.assertTrue(dlt.validate("200102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18|01,02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(113097600L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18|01,02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(169646400L),new BigDecimal(1),300));
	}
	
	@Test(expected = LotteryException.class)
	public void test0001() {
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05|01#02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0002() {
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,04|01,02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0003() {
		Assert.assertTrue(dlt.validate("200101-01,02,03,04,05|01,01^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0004() {
		Assert.assertTrue(dlt.validate("200102-01,02,03,04,05,05|01,02^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0005() {
		Assert.assertTrue(dlt.validate("200102-01,02,03,04,05,06|01,01^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0006() {
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01#02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0007() {
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05|01#02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected = LotteryException.class)
	public void test0008() {
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01,02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test0009() {
		Assert.assertTrue(dlt.validate("200102-05,06,10,11,18,19,21,22,23,34,35|05,06,09,10^", BigDecimal.valueOf(831600),new BigDecimal(1),300));
	}
	
	
	@Test
	public void testvalidate03() {
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01#02,03^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01,02,03^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06|01#02^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05,06|01#02^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05|01#02,03^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06|01,02^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01#02,03^", BigDecimal.valueOf(600L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05|01,02,03^", BigDecimal.valueOf(900L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06|01#02^", BigDecimal.valueOf(600L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05,06|01#02^", BigDecimal.valueOf(1800L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05|01#02,03^", BigDecimal.valueOf(600L),new BigDecimal(1),300));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06|01,02^", BigDecimal.valueOf(600L),new BigDecimal(1),300));
		
		
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06,07,08,09,10|01#02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(13200L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06,07,08,09,10|01#02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(19800L),new BigDecimal(1),300));
		
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06,07,08,09,10|01,02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(79200L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04#05,06,07,08,09,10|01,02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(118800L),new BigDecimal(1),300));
		
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05,06,07,08,09,10|01#02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(554400L),new BigDecimal(1),200));
		Assert.assertTrue(dlt.validate("200103-01,02,03,04,05,06,07,08,09,10|01#02,03,04,05,06,07,08,09,10,11,12^", BigDecimal.valueOf(831600L),new BigDecimal(1),300));
	}
	
	
	@Test
	public void testSplit01() {
		List<SplitedLot> splits = dlt.split("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", 1, 1200L, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		List<SplitedLot> splits2 = dlt.split("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", 1, 1800L, 300);
		
		for(SplitedLot s:splits2) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		List<SplitedLot> splits3 = dlt.split("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", 1, 1500L, 300);
		
		for(SplitedLot s:splits3) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		
		List<SplitedLot> splits4 = dlt.split("200101-01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^01,02,03,04,05|01,02^", 100, 180000L, 300);
		
		for(SplitedLot s:splits4) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
	
	
	@Test
	public void testSplit02() {
		List<SplitedLot> splits = dlt.split("200102-01,02,03,04,05,06|01,02^", 100, 120000L, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		
		List<SplitedLot> splits2 = dlt.split("200102-01,02,03,04,05,06|01,02^", 100, 180000L, 300);
		
		for(SplitedLot s:splits2) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		
		List<SplitedLot> splits3 = dlt.split("200102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15|01,02,03,04,05,06,07,08,09,10,11,12^", 100, 3963960000L, 200);
		
		for(SplitedLot s:splits3) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
		
		
		List<SplitedLot> splits4 = dlt.split("200102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15|01,02,03,04,05,06,07,08,09,10,11,12^", 100, 5945940000L, 300);
		
		for(SplitedLot s:splits4) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
	
	
	@Test
	public void testSplit03() {
		
		//前胆拖前1注，后复式
//		List<SplitedLot> splits = dlt.split("200103-01,02,03,04#05|01,02,03^", 1, 600L, 200);
//		
//		for(SplitedLot s:splits) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
		//前胆拖前多注，后复式
//		List<SplitedLot> splits2 = dlt.split("200103-01,02,03,04#05,06|01#02^", 1, 400L, 200);
//				
//		for(SplitedLot s:splits2) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits3 = dlt.split("200103-01,02,03,04#05|01#02,03^", 1, 400L, 200);
//		
//		for(SplitedLot s:splits3) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits4 = dlt.split("200103-01,02,03,04,05,06|01#02^", 1, 1200L, 200);
//		
//		for(SplitedLot s:splits4) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
		
//		List<SplitedLot> splits5 = dlt.split("200103-01,02,03,04,05|01#02,03^", 1, 400L, 200);
//		
//		for(SplitedLot s:splits5) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits6 = dlt.split("200103-01,02,03,04#05,06|01,02^", 1, 400L, 200);
//		
//		for(SplitedLot s:splits6) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits7 = dlt.split("200103-01,02,03,04#05|01#02,03^", 1, 600L, 300);
//		
//		for(SplitedLot s:splits7) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits8 = dlt.split("200103-01,02,03,04#05|01,02,03^", 1, 900L, 300);
//		
//		for(SplitedLot s:splits8) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits9 = dlt.split("200103-01,02,03,04#05,06|01#02^", 1, 600L, 300);
//		
//		for(SplitedLot s:splits9) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits10 = dlt.split("200103-01,02,03,04,05,06|01#02^", 1, 1800L, 300);
//		
//		for(SplitedLot s:splits10) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits11 = dlt.split("200103-01,02,03,04,05|01#02,03^", 1, 600L, 300);
//		
//		for(SplitedLot s:splits11) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits12 = dlt.split("200103-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18|01#02,03,04,05,06^", 1, 8568000L, 200);
//		
//		for(SplitedLot s:splits12) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits13 = dlt.split("200103-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18|01#02,03,04,05,06^", 100, 1285200000L, 300);
//		
//		for(SplitedLot s:splits13) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
		
		
//		List<SplitedLot> splits14 = dlt.split("200103-01,02,03,04,05|01#02,03^", 100, 60000L, 300);
//		
//		for(SplitedLot s:splits14) {
//			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
//		}
	}
	
	private boolean isPrizeInfoEqual(String str1,String str2) {
		Map<String,String> map1 = new HashMap<String, String>();
		Map<String,String> map2 = new HashMap<String, String>();
		
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
	
	
	
	@Test
	public void testPrize01() {
		
	}
}
