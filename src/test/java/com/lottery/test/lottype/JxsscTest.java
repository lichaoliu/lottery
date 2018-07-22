package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.Jxssc;
import com.lottery.lottype.SplitedLot;

public class JxsscTest {

	Jxssc ssc = new Jxssc();
	
	int oneAmount = 200;
	
	HashMap<String, Long> awardInfo = new HashMap<String, Long>();
	{
		awardInfo.put("1D", 1000L);
		awardInfo.put("2D", 10000L);
		awardInfo.put("3D", 100000L);
		awardInfo.put("5D", 10000000L);
		awardInfo.put("2Z", 5000L);
		awardInfo.put("Z3", 32000L);
		awardInfo.put("Z6", 16000L);
		awardInfo.put("DD", 400L);
		awardInfo.put("H2", 10000L);
		awardInfo.put("5T5", 2046000L);
		awardInfo.put("5T2", 3000L);
		awardInfo.put("5T3", 20000L);
	}
	
	
	@Test
	public void testValidateS() {
		Assert.assertTrue(ssc.validate("101101-3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101102-1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101103-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101104-1,2,3,4^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101105-1,2,0,9,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101106-1,1,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101107-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101108-1,~,~,~,~^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101109-1,2,~,~,~^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101101-1^2^3^4^5^6^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101102-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101103-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101104-1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101105-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101106-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101107-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101108-1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101109-1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		
		
		Assert.assertTrue(ssc.validate("101101-1^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101102-1,2^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101103-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101104-1,2,3,4^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101105-1,2,0,9,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101106-1,1,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101107-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101108-1,~,~,~,~^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101109-1,2,~,~,~^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		
		Assert.assertTrue(ssc.validate("101101-1^2^3^4^5^6^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101102-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101103-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101104-1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^1,2,3,4^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101105-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101106-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101107-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101108-1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^1,~,~,~,~^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101109-1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^1,2,~,~,~^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateF() {
		Assert.assertTrue(ssc.validate("101111-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101112-0,1;1,2^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101113-0,1;1,2;2,3^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101114-0,1;1,2;2,3;3,4^", BigDecimal.valueOf(3200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101115-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101118-1,2;2,3;~;~;~^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101119-1,2;2,3;~;~;~^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101111-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101112-0,1;1,2^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101113-0,1;1,2;2,3^", BigDecimal.valueOf(1600L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101114-0,1;1,2;2,3;3,4^", BigDecimal.valueOf(3200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101115-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101118-1,2;2,3;~;~;~^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101119-1,2;2,3;~;~;~^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateO() {
		Assert.assertTrue(ssc.validate("101121-0,1,2,3^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101123-0,1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101124-0,1,2,3^", BigDecimal.valueOf(2400L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101125-0,1,2,3^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101126-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101127-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101121-0,1,2,3^", BigDecimal.valueOf(2000L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101123-0,1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101124-0,1,2,3^", BigDecimal.valueOf(2400L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101125-0,1,2,3^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101126-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101127-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testSplitS() {
		List<SplitedLot> splits1 = ssc.split("101101-1^", 100, 20000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = ssc.split("101101-1^2^3^4^5^6^", 100, 120000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitF() {
		List<SplitedLot> splits1 = ssc.split("101112-0,1;1,2^", 100, 80000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = ssc.split("101115-0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;1,2^", 100, 400000000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitF2() {
//		List<SplitedLot> splits2 = ssc.split("101111-1,2,3,4,5,6^", 1, 1200, 200);
//		printSplotlot(splits2);
		
		
//		List<SplitedLot> splits2 = ssc.split("101118-1,2;~;~;7,8;3,4^", 1, 1200, 200);
//		printSplotlot(splits2);
		
		List<SplitedLot> splits2 = ssc.split("101119-1,2;~;~;7,8;3,4^", 1, 2400, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitO() {
//		List<SplitedLot> splits1 = ssc.split("101121-0,1,2,3^", 100, 200000, 200);
//		printSplotlot(splits1);
//		
//		List<SplitedLot> splits2 = ssc.split("101126-1,2,4,5;1,2,4,5^", 100, 320000, 200);
//		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = ssc.split("101127-0,1;1,2;2,3;2,3;2,3^", 100, 640000, 200);
		printSplotlot(splits3);
	}
	
	
	@Test
	public void testPrizeS() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101101-1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101101-1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101101-1^1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101101-1^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101102-2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101102-2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101102-2,1^2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101102-1,2^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101103-3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101103-3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101103-3,2,1^3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101103-3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101105-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101105-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101105-5,4,3,2,1^5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101105-5,4,3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101106-1,1,3^", "5,4,1,3,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101106-1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101106-1,1,3^1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101106-1,3,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101107-1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101107-1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101107-1,2,3^1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101107-1,2,4^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeF() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101111-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101111-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101111-0,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101112-0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101112-0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101112-0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101113-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101113-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101113-3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101115-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101115-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101115-4,5;4,3;3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeO() {
		Assert.assertEquals(10000L, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101121-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000L*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101121-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101121-0,1,2,3^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(5000L, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101123-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(5000L*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101123-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101123-0,1,2,3^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101124-0,1,2,3^", "5,4,3,1,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101124-0,1,2,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101124-0,1,2,4^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101125-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101125-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101125-0,1,2,4^", "5,4,3,2,9", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(400, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101126-1;5^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(400*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101126-2;4^", "5,4,3,7,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101126-1;2^", "5,4,3,2,3", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(1636800, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101127-5;4;3;2;1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(3000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101127-5;4;3;2;1^", "5,4,1,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(23000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101127-5;4;3;2;1^", "5,4,3,2,3", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	private void printSplotlot(List<SplitedLot> splits) {
		System.out.println();
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
}
