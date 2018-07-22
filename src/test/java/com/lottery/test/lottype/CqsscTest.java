package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.Cqssc;
import com.lottery.lottype.SplitedLot;

public class CqsscTest {

	Cqssc ssc = new Cqssc();
	
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
		awardInfo.put("5T5", 2000000L);
		awardInfo.put("5T2", 2000L);
		awardInfo.put("5T3", 20000L);
	}
	
	
	@Test
	public void testValidateS() {
		Assert.assertTrue(ssc.validate("100701-3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100702-1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100703-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100705-1,2,0,9,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100706-1,1,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100707-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("100701-1^2^3^4^5^6^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100702-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100703-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100705-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100706-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100707-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		
		
		Assert.assertTrue(ssc.validate("100701-1^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100702-1,2^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100703-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100705-1,2,0,9,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100706-1,1,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100707-1,2,3^", BigDecimal.valueOf(200L*2),new BigDecimal(2),200));
		
		Assert.assertTrue(ssc.validate("100701-1^2^3^4^5^6^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100702-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100703-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100705-1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^1,2,0,9,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100706-1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^1,1,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100707-1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateF() {
		Assert.assertTrue(ssc.validate("100711-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100712-0,1;1,2^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100713-0,1;1,2;2,3^", BigDecimal.valueOf(1600L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100715-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("100711-0,1,2,3,4,5,6,7,8,9^", BigDecimal.valueOf(2000L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100712-0,1;1,2^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100713-0,1;1,2;2,3^", BigDecimal.valueOf(1600L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100715-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testValidateO() {
		Assert.assertTrue(ssc.validate("100721-0,1,2,3^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100723-0,1,2,3^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100724-0,1,2,3^", BigDecimal.valueOf(2400L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100725-0,1,2,3^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100726-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L),new BigDecimal(1),200));
		Assert.assertTrue(ssc.validate("100727-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L),new BigDecimal(1),200));
		
		Assert.assertTrue(ssc.validate("100721-0,1,2,3^", BigDecimal.valueOf(2000L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100723-0,1,2,3^", BigDecimal.valueOf(1200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100724-0,1,2,3^", BigDecimal.valueOf(2400L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100725-0,1,2,3^", BigDecimal.valueOf(800L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100726-1,2,4,5;1,2,4,5^", BigDecimal.valueOf(3200L*2),new BigDecimal(2),200));
		Assert.assertTrue(ssc.validate("100727-0,1;1,2;2,3;2,3;2,3^", BigDecimal.valueOf(6400L*2),new BigDecimal(2),200));
	}
	
	
	
	@Test
	public void testSplitS() {
		List<SplitedLot> splits1 = ssc.split("100701-1^", 100, 20000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = ssc.split("100701-1^2^3^4^5^6^", 100, 120000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitF() {
		List<SplitedLot> splits1 = ssc.split("100712-0,1;1,2^", 100, 80000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = ssc.split("100715-0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;0,1,2,3,4,5,6,7,8,9;1,2^", 100, 400000000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplitO() {
//		List<SplitedLot> splits1 = ssc.split("100721-0,1,2,3^", 100, 200000, 200);
//		printSplotlot(splits1);
//		
//		List<SplitedLot> splits2 = ssc.split("100726-1,2,4,5;1,2,4,5^", 100, 320000, 200);
//		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = ssc.split("100727-0,1;1,2;2,3;2,3;2,3^", 100, 640000, 200);
		printSplotlot(splits3);
	}
	
	
	@Test
	public void testPrizeS() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100701-1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100701-1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100701-1^1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100701-1^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100702-2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100702-2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100702-2,1^2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100702-1,2^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100703-3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100703-3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100703-3,2,1^3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100703-3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100705-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100705-5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100705-5,4,3,2,1^5,4,3,2,1^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100705-5,4,3,2,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100706-1,1,3^", "5,4,1,3,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100706-1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100706-1,1,3^1,1,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100706-1,3,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100707-1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100707-1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*4, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100707-1,2,3^1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100707-1,2,4^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeF() {
		Assert.assertEquals(1000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100711-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100711-0,1,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100711-0,2,3,4,5,6,7,8,9^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100712-0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100712-0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100712-0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(100000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100713-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100713-3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100713-3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000000*4/5, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100715-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000000*4/5*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100715-4,5;4,3;3,1;0,2;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100715-4,5;4,3;3,1;0,1;1,2^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testPrizeO() {
		Assert.assertEquals(10000L, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100721-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000L*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100721-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100721-0,1,2,3^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(5000L, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100723-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(5000L*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100723-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100723-0,1,2,3^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(32000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100724-0,1,2,3^", "5,4,3,1,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100724-0,1,2,3^", "5,4,3,1,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100724-0,1,2,4^", "5,4,3,2,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(16000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100725-0,1,2,3^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100725-0,1,2,3^", "5,4,3,2,1", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100725-0,1,2,4^", "5,4,3,2,9", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(400, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100726-1;5^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(400*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100726-2;4^", "5,4,3,7,2", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100726-1;2^", "5,4,3,2,3", new BigDecimal(2),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(1644000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100727-5;4;3;2;1^", "5,4,3,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000*2, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100727-5;4;3;2;1^", "5,4,1,2,1", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(22000, ssc.caculatePrizeAmt(ssc.getPrizeLevelInfo("100727-5;4;3;2;1^", "5,4,3,2,3", new BigDecimal(1),oneAmount), awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	private void printSplotlot(List<SplitedLot> splits) {
		System.out.println();
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
}
