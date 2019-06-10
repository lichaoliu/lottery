package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.SplitedLot;
import com.lottery.lottype.Tjssc;

public class TjsscTest {

	Tjssc ssc = new Tjssc();
	
	int oneAmount = 200;
	
	HashMap<String, Long> awardInfo = new HashMap<String, Long>();
	{
		awardInfo.put("1D", 1000L);
		awardInfo.put("2D", 10000L);
		awardInfo.put("3D", 100000L);
		awardInfo.put("4D", 980000L);
		awardInfo.put("5D", 10000000L);
		awardInfo.put("2Z", 5000L);
		awardInfo.put("Z3", 32000L);
		awardInfo.put("Z6", 16000L);
		awardInfo.put("DD", 400L);
		awardInfo.put("H2", 10000L);
		awardInfo.put("5T5", 2000000L);
		awardInfo.put("5T2", 2000L);
		awardInfo.put("5T3", 20000L);
		awardInfo.put("QW1", 18000L);
		awardInfo.put("QW2", 1500L);
		awardInfo.put("QJ", 48000L);
		awardInfo.put("1R", 1000L);
		awardInfo.put("2R", 9800L);
		awardInfo.put("3R", 98000L);
	}
	
	
	@Test
	public void testValidateS() {
		Assert.assertTrue(ssc.validate("101601-3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101602-1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101603-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101604-1,2,3,4^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101605-1,2,0,9,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101606-1,1,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101607-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101601-1^2^3^4^5^6^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101602-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101603-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101604-1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101605-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101606-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101607-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		
		
		Assert.assertTrue(ssc.validate("101601-1^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101602-1,2^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101603-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101604-1,2,3,4^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101605-1,2,0,9,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101606-1,1,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101607-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		
		Assert.assertTrue(ssc.validate("101601-1^2^3^4^5^6^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101602-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101603-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101604-1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^1,2,0,9^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101605-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101606-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101607-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateF() {
		Assert.assertTrue(ssc.validate("101611-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101612-0,1;1,2^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101613-0,1;1,2;2,3^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101614-0,1;1,2;2,3;4^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101615-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101611-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101612-0,1;1,2^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101613-0,1;1,2;2,3^", BigDecimal.valueOf(1600L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101614-0,1;1,2;2,3;4^", BigDecimal.valueOf(1600L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101615-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateO() {
		Assert.assertTrue(ssc.validate("101623-0,1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101624-0,1,2,3^", BigDecimal.valueOf(2400L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101625-0,1,2,3^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101626-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101627-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101623-0,1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101624-0,1,2,3^", BigDecimal.valueOf(2400L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101625-0,1,2,3^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101626-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("101627-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
	}
	
	
	@Test
	public void testValidateO2() {
		Assert.assertTrue(ssc.validate("101628-1;3;4^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101629-2;3;4^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101628-1,2;2,3;4,5^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101629-1,2;2,3;4,5^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101628-1,2;2,3;4,5^", BigDecimal.valueOf(16000L),new BigDecimal(10),200));
		Assert.assertTrue(ssc.validate("101629-1,2;2,3;4,5^", BigDecimal.valueOf(16000L),new BigDecimal(10),200));
	}
	
	@Test
	public void testValidateR() {
		Assert.assertTrue(ssc.validate("101631-~;1,2,3;~;~;~^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101632-~;1,2,3;1,2,3;~;~^", BigDecimal.valueOf(1800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("101633-~;1,2,3;1,2,3;1,2;~^", BigDecimal.valueOf(3600L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("101631-~;1,2,3;~;~;~^", BigDecimal.valueOf(6000L),new BigDecimal(10),200));
		Assert.assertTrue(ssc.validate("101632-~;1,2,3;1,2,3;~;~^", BigDecimal.valueOf(18000L),new BigDecimal(10),200));
		Assert.assertTrue(ssc.validate("101633-~;1,2,3;1,2,3;1,2;~^", BigDecimal.valueOf(36000L),new BigDecimal(10),200));
	}
	
	
	@Test
	public void testSplitS() {
//		List<SplitedLot> splits1 = ssc.split("101601-1^", 100, 20000, 200);
//		printSplotlot(splits1);
//		
//		List<SplitedLot> splits2 = ssc.split("101601-1^2^3^4^5^6^", 100, 120000, 200);
//		printSplotlot(splits2);
		
//		List<SplitedLot> splits3 = ssc.split("101606-3,3,1^1,1,3^1,1,3^1,1,3^1,1,3^4,4,3^", 1, 1200, 200);
//		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = ssc.split("101607-3,2,1^1,2,3^1,2,3^1,2,3^1,2,3^6,9,4^", 1, 1200, 200);
		printSplotlot(splits4);
	}
	
	
	@Test
	public void testSplitF() {
		List<SplitedLot> splits1 = ssc.split("101612-0,1;1,2^", 100, 80000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = ssc.split("101615-0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;1,2^", 100, 400000000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitO() {
//		List<SplitedLot> splits1 = ssc.split("101621-0,1,2,3^", 100, 200000, 200);
//		printSplotlot(splits1);
//		
//		List<SplitedLot> splits2 = ssc.split("101626-1,2,4,5;1,2,4,5^", 100, 320000, 200);
//		printSplotlot(splits2);
		
//		List<SplitedLot> splits3 = ssc.split("101627-0,1;1,2;2,3;2,3;2,3^", 100, 640000, 200);
//		printSplotlot(splits3);
		
//		List<SplitedLot> splits4 = ssc.split("101623-3,2,1,0^", 1, 1200, 200);
//		printSplotlot(splits4);
		
		List<SplitedLot> splits5 = ssc.split("101624-3,2,1,0^", 1, 2400, 200);
		printSplotlot(splits5);
		
		List<SplitedLot> splits6 = ssc.split("101625-3,2,1,0^", 1, 800, 200);
		printSplotlot(splits6);
	}
	
	
	@Test
	public void testPrizeS() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101601-1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101601-1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101601-1^1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101601-1^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101602-2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101602-2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101602-2,1^2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101602-1,2^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101603-3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101603-3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101603-3,2,1^3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101603-3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(980000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101604-4,3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(980000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101604-4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(980000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101604-4,3,2,1^4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101604-4,3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101605-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101605-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101605-5,4,3,2,1^5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101605-5,4,3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101606-1,1,3^", "5,4,1,3,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101606-1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101606-1,1,3^1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101606-1,3,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101607-1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101607-1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101607-1,2,3^1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101607-1,2,4^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeF() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101611-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101611-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101611-0,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101612-0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101612-0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101612-0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101613-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101613-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101613-3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(980000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101614-4;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(980000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101614-4;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101614-4;3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101615-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101615-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101615-4,5;4,3;3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeO() {
		
		Assert.assertEquals(5000L, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101623-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(5000L*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101623-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101623-0,1,2,3^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101624-0,1,2,3^", "5,4,3,1,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101624-0,1,2,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101624-0,1,2,4^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101625-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101625-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101625-0,1,2,4^", "5,4,3,2,9", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(400, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101626-1;5^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(400*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101626-2;4^", "5,4,3,7,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101626-1;2^", "5,4,3,2,3", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
//		Assert.assertEquals(1644000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101627-5;4;3;2;1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
//		Assert.assertEquals(2000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101627-5;4;3;2;1^", "5,4,1,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
//		Assert.assertEquals(22000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101627-5;4;3;2;1^", "5,4,3,2,3", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(18000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;4^", "5,4,3,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(18000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;4^", "5,4,3,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1500, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;4^", "5,4,6,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1500*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;4^", "5,4,6,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;3^", "5,4,3,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101628-1;5;3^", "5,4,3,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	
	@Test
	public void testPrizeR() {
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101631-~;~;1,2;5;4^", "5,4,3,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101631-~;~;1,2;5;4^", "5,4,3,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(9800*3, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101632-~;~;1,3;5;4^", "5,4,3,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(9800*3*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101632-~;~;1,3;5;4^", "5,4,3,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(98000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101633-5;~;1,3;5;4^", "5,4,3,5,4", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(98000*4*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("101633-5;~;1,3;5;4^", "5,4,3,5,4", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	private void printSplotlot(List<SplitedLot> splits) {
		System.out.println();
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
}
