package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.PLW;
import com.lottery.lottype.SplitedLot;

public class PlwTest {

	PLW plw = new PLW();
	
	
	@Test
	public void testValidate01() {
		Assert.assertTrue(plw.validate("200301-00,02,03,04,05^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^01,01,01,01,01^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		
		Assert.assertTrue(plw.validate("200301-01,02,03,04,05^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^", BigDecimal.valueOf(100000L),new BigDecimal(100),200));
		Assert.assertTrue(plw.validate("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^01,01,01,01,01^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidate02() {
		Assert.assertTrue(plw.validate("200302-01|02|03|04|05^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidate03() {
		Assert.assertTrue(plw.validate("200302-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05,06^", BigDecimal.valueOf(4000000L),new BigDecimal(1),200));
	}
	
	
	@Test
	public void testValidate04() {
		Assert.assertTrue(plw.validate("200302-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05^", BigDecimal.valueOf(2000000L),new BigDecimal(1),200));
		Assert.assertTrue(plw.validate("200302-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05,06^", BigDecimal.valueOf(4000000L),new BigDecimal(1),200));
	}
	
	
	
	@Test
	public void testSplit00() {
		List<SplitedLot> splits = plw.split("200301-00,02,03,04,05^", 1, 200, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	@Test
	public void testSplit02() {
		List<SplitedLot> splits = plw.split("200301-00,02,03,04,05^", 100, 20000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit03() {
		List<SplitedLot> splits = plw.split("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^01,01,01,01,01^", 1, 1200, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit04() {
		List<SplitedLot> splits = plw.split("200301-09,09,09,09,09^08,08,08,08,08^01,02,03,04,05^01,02,03,04,05^01,02,03,04,05^01,01,01,01,01^", 100, 120000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	
	@Test
	public void testSplit05() {
		List<SplitedLot> splits = plw.split("200302-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05^", 3, 6000000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit06() {
		List<SplitedLot> splits = plw.split("200302-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05,06^", 3, 12000000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testPrize01() {
		Assert.assertTrue(isPrizeInfoEqual("1_1", plw.getPrizeLevelInfo("200301-09,09,09,09,09^", "9,9,9,9,9", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", plw.getPrizeLevelInfo("200301-09,09,08,09,09^", "9,9,9,9,9", BigDecimal.ONE,200)));
		
		Assert.assertTrue(isPrizeInfoEqual("1_1", plw.getPrizeLevelInfo("200301-09,09,09,09,09^09,09,08,09,09^", "9,9,9,9,9", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("1_2", plw.getPrizeLevelInfo("200301-09,09,08,09,09^09,09,08,09,09^", "9,9,8,9,9", BigDecimal.ONE,200)));
	}
	
	
	@Test
	public void testPrize02() {
		Assert.assertTrue(isPrizeInfoEqual("1_1", plw.getPrizeLevelInfo("200302-09,08|09,07|09,04,03|09,05,04|09,07,06^", "9,7,3,4,6", BigDecimal.ONE,200)));
		Assert.assertTrue(isPrizeInfoEqual("", plw.getPrizeLevelInfo("200302-09,08|09,07|09,04,03|09,05,04|09,07,06^", "9,7,3,4,8", BigDecimal.ONE,200)));
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
