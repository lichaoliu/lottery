package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.QXC;
import com.lottery.lottype.SplitedLot;

public class QxcTest {

	
	QXC qxc = new QXC();
	
	int oneAmount = 200;
	
	Map<String, Long> map = new HashMap<String, Long>();
	{
		map.put("1", 10000000L);
		map.put("2", 5000000L);
		map.put("3", 180000L);
		map.put("4", 30000L);
		map.put("5", 2000L);
		map.put("6", 500L);
		
	}
	
	
	@Test
	public void testValidate01() {
		Assert.assertTrue(qxc.validate("200401-00,02,03,04,05,06,07^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,09,00^01,02,03,04,05,07,08^01,02,03,04,05,06,07^01,01,01,01,01,01,01^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		
		Assert.assertTrue(qxc.validate("200401-00,02,03,04,05,06,07^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^", BigDecimal.valueOf(100000L),new BigDecimal(100),200));
		Assert.assertTrue(qxc.validate("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,09,00^01,02,03,04,05,07,08^01,02,03,04,05,06,07^01,01,01,01,01,01,01^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
		
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidate02() {
		Assert.assertTrue(qxc.validate("200402-01|02|03|04|05|06|07^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	
	@Test(expected=LotteryException.class)
	public void testValidate03() {
		Assert.assertTrue(qxc.validate("200402-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05,06|06|06^", BigDecimal.valueOf(4000000L),new BigDecimal(1),200));
	}
	
	
	@Test
	public void testValidate04() {
		Assert.assertTrue(qxc.validate("200402-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05|06|07^", BigDecimal.valueOf(2000000L),new BigDecimal(1),200));
	}
	
	
	
	@Test
	public void testSplit00() {
		List<SplitedLot> splits = qxc.split("200401-00,02,03,04,05,06,07^", 1, 200, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	@Test
	public void testSplit02() {
		List<SplitedLot> splits = qxc.split("200401-00,02,03,04,05,06,07^", 100, 20000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit03() {
		List<SplitedLot> splits = qxc.split("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,00^01,01,01,01,01,00,00^", 1, 1200, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit04() {
		List<SplitedLot> splits = qxc.split("200401-09,09,09,09,09,09,09^08,08,08,08,08,08,08^01,02,03,04,05,06,07^01,02,03,04,05,08,09^01,02,03,04,05,00,09^01,01,01,01,01,00,00^", 100, 120000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	
	@Test
	public void testSplit05() {
		List<SplitedLot> splits = qxc.split("200402-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05|06|07^", 3, 6000000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	
	
	@Test
	public void testSplit06() {
		List<SplitedLot> splits = qxc.split("200402-01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|05,06|06|07^", 3, 12000000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplit07() {
		List<SplitedLot> splits = qxc.split("200402-01,02,03,04,05,06,07,08,09,00|01,05|01,02|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01,02,03,04,05,06,07,08,09,00|01^", 1, 8000000, 200);
		
		for(SplitedLot lot:splits) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	
	
	@Test
	public void testPrize(){
//		Assert.assertEquals(8000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,06,07^", "01,02,03,04,05,06,07", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000000+4000000L, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200402-01,02|02|03|04|05|06|07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000000+4000000L+180000L+30000L, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200402-01,02|02|03|04|05|06,08|07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(8000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(4000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,06,08^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(4000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-02,02,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(4000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,06,08^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(4000000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-02,02,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,05,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-02,02,03,04,05,06,08^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-03,04,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,05,05,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-02,02,03,04,05,06,08^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(180000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-03,04,03,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(30000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,04,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(30000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,02,03,04,05,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(30000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,03,04,05,06,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(30000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,04,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(2000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,03,00,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,02,03,04,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,03,04,05,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,04,05,06,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,00,05,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,02,00,00,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,02,03,00,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,03,04,00,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,04,05,00,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,00,05,06,00^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-00,00,00,00,00,06,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(0, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200401-01,03,03,05,05,07,07^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(17337500, qxc.caculatePrizeAmt(qxc.getPrizeLevelInfo("200402-01,02|02,03|03,04|04,05|05,06|06,07|07,08^", "1,2,3,4,5,6,7", new BigDecimal("1"),oneAmount), map,null).getAfterTaxAmt().longValue());
		
	}
}
