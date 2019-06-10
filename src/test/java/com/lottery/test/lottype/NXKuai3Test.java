package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.GuangXiKuai3;
import com.lottery.lottype.NXKuai3;
import com.lottery.lottype.SplitedLot;

public class NXKuai3Test {

	NXKuai3 k3 = new NXKuai3();
	
	GuangXiKuai3 gk3 = new GuangXiKuai3();
	
	int oneAmount = 200;
	
	HashMap<String, Long> awardInfo = new HashMap<String, Long>();
	{
		awardInfo.put("1", 24000L);
		awardInfo.put("2", 8000L);
		awardInfo.put("3", 4000L);
		awardInfo.put("4", 2500L);
		awardInfo.put("5", 1600L);
		awardInfo.put("6", 1500L);
		awardInfo.put("7", 1200L);
		awardInfo.put("8", 1000L);
		awardInfo.put("9", 900L);
		awardInfo.put("10", 800L);
	}
	
	
	@Test
	public void test10() {
		
		Assert.assertTrue(gk3.validate("101010-1,1,2^1,1,2^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		
//		Assert.assertTrue(k3.validate("100510-1,1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
//		Assert.assertTrue(k3.validate("100510-1,1,2^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
//		
//		Assert.assertTrue(k3.validate("100510-1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
//		Assert.assertTrue(k3.validate("100510-1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test10E01() {
		Assert.assertTrue(k3.validate("100510-1,3,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test10E02() {
		Assert.assertTrue(k3.validate("100510-1,7,7^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test11() {
		Assert.assertTrue(k3.validate("100511-1,3,2^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100511-1,3,2^", BigDecimal.valueOf(60000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100511-1,3,2,4,5,6^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100511-1,3,2,4,5,6^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test11E01() {
		Assert.assertTrue(k3.validate("100511-1,2,2^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test11E02() {
		Assert.assertTrue(k3.validate("100511-1,7,2^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test11E03() {
		Assert.assertTrue(k3.validate("100511-1,3,2,4,5,6,7^", BigDecimal.valueOf(1400L),new BigDecimal(1),200));
	}
	
	@Test
	public void test12() {
		Assert.assertTrue(k3.validate("100512-1,2*3,4^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100512-1,2*3,4^", BigDecimal.valueOf(80000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100512-1,2,3,4,5*6^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100512-1,2,3,4,5*6^", BigDecimal.valueOf(100000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test12E01() {
		Assert.assertTrue(k3.validate("100512-1,1*5,6^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test12E02() {
		Assert.assertTrue(k3.validate("100512-1,2*2,6^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
	}
	
	@Test
	public void test20() {
		Assert.assertTrue(k3.validate("100520-1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100520-1,2^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100520-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100520-1,2^1,2^1,2^1,2^1,2^1,2^", BigDecimal.valueOf(120000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test20E01() {
		Assert.assertTrue(k3.validate("100520-1,1^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	
	@Test
	public void test21() {
		Assert.assertTrue(k3.validate("100521-1,2,3^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100521-1,2,3^", BigDecimal.valueOf(60000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100521-1,2,3,4,5,6^", BigDecimal.valueOf(3000L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100521-1,2,3,4,5,6^", BigDecimal.valueOf(300000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test21E01() {
		Assert.assertTrue(k3.validate("100521-1,2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test21E02() {
		Assert.assertTrue(k3.validate("100521-1,2,2^", BigDecimal.valueOf(600L),new BigDecimal(1),200));
	}
	
	@Test
	public void test22() {
		Assert.assertTrue(k3.validate("100522-1#2,3^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100522-1#2,3^", BigDecimal.valueOf(40000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100522-1#2,3,4,5,6^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100522-1#2,3,4,5,6^", BigDecimal.valueOf(100000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test22E01() {
		Assert.assertTrue(k3.validate("100522-1#2,2^", BigDecimal.valueOf(400L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test22E02() {
		Assert.assertTrue(k3.validate("100522-1#2^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test30() {
		Assert.assertTrue(k3.validate("100530-1,1,1^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100530-1,1,1^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100530-1,1,1^2,2,2^3,3,3^4,4,4^5,5,5^6,6,6^1,1,1^", BigDecimal.valueOf(1400L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100530-1,1,1^2,2,2^3,3,3^4,4,4^5,5,5^6,6,6^1,1,1^", BigDecimal.valueOf(140000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test30E01() {
		Assert.assertTrue(k3.validate("100530-1,2,1^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test30E02() {
		Assert.assertTrue(k3.validate("100530-7,7,7^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test32() {
		Assert.assertTrue(k3.validate("100532-1^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100532-1^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
	}
	
	@Test
	public void test40() {
		Assert.assertTrue(k3.validate("100540-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100540-1,2,3^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100540-1,2,3^2,3,4^3,4,5^4,5,6^2,4,5^2,4,6^1,3,5^", BigDecimal.valueOf(1400L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100540-1,2,3^2,3,4^3,4,5^4,5,6^2,4,5^2,4,6^1,3,5^", BigDecimal.valueOf(140000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test40E01() {
		Assert.assertTrue(k3.validate("100540-1,1,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test41() {
		Assert.assertTrue(k3.validate("100541-1,2,3,4,5,6^", BigDecimal.valueOf(4000L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100541-1,2,3,4,5,6^", BigDecimal.valueOf(400000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test41E01() {
		Assert.assertTrue(k3.validate("100541-1,2,3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test41E02() {
		Assert.assertTrue(k3.validate("100541-1,2,3,4,5,5^", BigDecimal.valueOf(4000L),new BigDecimal(1),200));
	}
	
	@Test
	public void test42() {
		Assert.assertTrue(k3.validate("100542-1#2,3,4,5,6^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100542-1#2,3,4,5,6^", BigDecimal.valueOf(200000L),new BigDecimal(100),200));
		
		Assert.assertTrue(k3.validate("100542-1,2#3,4,5,6^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100542-1,2#3,4,5,6^", BigDecimal.valueOf(80000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test42E01() {
		Assert.assertTrue(k3.validate("100542-1,2#2,4,5,6^", BigDecimal.valueOf(800L),new BigDecimal(1),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test42E02() {
		Assert.assertTrue(k3.validate("100542-1,2#3^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	@Test
	public void test50() {
		Assert.assertTrue(k3.validate("100550-1^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100550-1^", BigDecimal.valueOf(20000L),new BigDecimal(100),200));
	}
	
	
	@Test
	public void test60() {
		Assert.assertTrue(k3.validate("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", BigDecimal.valueOf(2800L),new BigDecimal(1),200));
		Assert.assertTrue(k3.validate("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", BigDecimal.valueOf(280000L),new BigDecimal(100),200));
	}
	
	@Test(expected=LotteryException.class)
	public void test60E01() {
		Assert.assertTrue(k3.validate("100560-4,5,6,7,9,9,10,11,12,13,14,15,16,17^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
	}
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testSplit10() {
		List<SplitedLot> splits1 = k3.split("100510-1,1,2^", 100, 20000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100510-1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^", 1, 1200, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100510-1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^", 100, 120000, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100510-1,1,2^1,1,2^1,1,2^1,1,2^1,1,2^", 100, 100000, 200);
		printSplotlot(splits4);
	}
	
	
	@Test
	public void testSplit12() {
		List<SplitedLot> splits1 = k3.split("100512-1,2*3,4^", 1, 800, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100512-1,2*3,4^", 100, 80000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100512-1,2*3,4,6^", 1, 1200, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100512-1,2*3,4,6^", 100, 120000, 200);
		printSplotlot(splits4);
	}
	
	@Test
	public void testSplit32() {
		List<SplitedLot> splits1 = k3.split("100532-1^", 100, 20000, 200);
		printSplotlot(splits1);
	}
	
	
	@Test
	public void testSplit50() {
		List<SplitedLot> splits1 = k3.split("100550-1^", 100, 20000, 200);
		printSplotlot(splits1);
	}
	
	@Test
	public void testSplit11() {
		List<SplitedLot> splits1 = k3.split("100511-1,2,3,4,5^", 1, 1000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100511-1,2,3,4,5,6^", 1, 1200, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100511-1,2,3,4,5^", 100, 100000, 200);
		printSplotlot(splits3);
	}
	
	
	@Test
	public void testSplit20() {
		List<SplitedLot> splits1 = k3.split("100520-1,2^3,4^1,5^1,5^1,5^", 1, 1000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100520-1,2^3,4^1,5^1,5^1,5^", 100, 100000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100520-1,2^3,4^1,5^1,5^1,5^1,5^", 1, 1200, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100520-1,2^3,4^1,5^1,5^1,5^1,5^", 100, 120000, 200);
		printSplotlot(splits4);
	}
	
	@Test
	public void testSplit21() {
		List<SplitedLot> splits1 = k3.split("100521-1,2,3^", 1, 600, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100521-1,2,3^", 100, 60000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100521-1,2,3,4^", 1, 1200, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100521-1,2,3,4^", 100, 120000, 200);
		printSplotlot(splits4);
	}
	
	@Test
	public void testSplit22() {
		List<SplitedLot> splits1 = k3.split("100522-1#2,3,4,5,6^", 1, 1000, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100522-1#2,3,4,5,6^", 100, 100000, 200);
		printSplotlot(splits2);
		
	}
	
	@Test
	public void testSplit40() {
		List<SplitedLot> splits1 = k3.split("100540-1,2,3^2,3,4^1,2,3^", 1, 600, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100540-1,2,3^2,3,4^1,2,3^", 100, 60000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100540-1,2,3^2,3,4^1,2,3^1,2,3^2,3,4^1,2,3^", 1, 1200, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100540-1,2,3^2,3,4^1,2,3^1,2,3^2,3,4^1,2,3^", 100, 120000, 200);
		printSplotlot(splits4);
	}
	
	
	@Test
	public void testSplit41() {
		List<SplitedLot> splits1 = k3.split("100541-1,2,3,4^", 1, 800, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100541-1,2,3,4^", 100, 80000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100541-1,2,3,4,5^", 1, 2000, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100541-1,2,3,4,5^", 100, 200000, 200);
		printSplotlot(splits4);
	}
	
	
	@Test
	public void testSplit42_1() {
		List<SplitedLot> splits1 = k3.split("100542-1#2,3,4^", 1, 600, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100542-1#2,3,4^", 100, 60000, 200);
		printSplotlot(splits2);
		
		List<SplitedLot> splits3 = k3.split("100542-1#2,3,4,6^", 1, 1200, 200);
		printSplotlot(splits3);
		
		List<SplitedLot> splits4 = k3.split("100542-1#2,3,4,6^", 100, 120000, 200);
		printSplotlot(splits4);
	}
	
	@Test
	public void testSplit42_2() {
		List<SplitedLot> splits1 = k3.split("100542-1,2#3,4^", 1, 400, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100542-1,2#3,4^", 100, 40000, 200);
		printSplotlot(splits2);
	}
	
	
	@Test
	public void testSplit60() {
		List<SplitedLot> splits1 = k3.split("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", 1, 2800, 200);
		printSplotlot(splits1);
		
		List<SplitedLot> splits2 = k3.split("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", 100, 280000, 200);
		printSplotlot(splits2);
	}
//===================================================================================
	
	@Test
	public void calculateTest_40() {
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100540-4,5,6^4,5,6^", "4,5,6", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100540-4,5,6^4,5,6^", "4,5,5", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_10() {
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100510-4,4,5^4,5,05^", "4,4,5", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100510-4,4,5^4,5,5^", "4,4,5", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100510-4,4,5^4,5,5^", "4,4,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100510-4,5,5^4,5,5^", "4,4,5", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_30() {
		Assert.assertEquals(48000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100530-6,6,6^6,6,6^", "6,6,6", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(96000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100530-6,6,6^6,6,6^", "6,6,6", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100530-7,7,7^8,8,8^", "6,6,6", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_60() {
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", "1,1,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100560-4,5,6,7,8,9,10,11,12,13,14,15,16,17^", "1,8,8", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
	}
	
	@Test
	public void calculateTest_20() {
		Assert.assertEquals(2400,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100520-1,2^2,3^3,4^1,3^", "1,2,3", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(4800,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100520-1,2^2,3^3,4^1,3^", "1,2,3", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(800,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100520-1,2^2,3^3,4^1,3^", "1,2,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
	}
	
	@Test
	public void calculateTest_21() {
		Assert.assertEquals(2400,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100521-1,2,3,4,5,6^", "1,2,3", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(4800,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100521-1,2,3,4,5,6^", "1,2,3", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(800,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100521-1,2,3,4,5,6^", "1,2,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_22() {
		Assert.assertEquals(1600,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100522-1#2,3,4^", "1,2,3", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(3200,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100522-1#2,3,4^", "1,2,3", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(800,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100522-1#2,3,4^", "1,2,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_11() {
		Assert.assertEquals(1500,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100511-1,2,3,4,5^", "1,1,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(3000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100511-1,2,3,4,5^", "1,2,2", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1500,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100511-1,2,3,4,5^", "2,2,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	
	}
	
	@Test
	public void calculateTest_32() {
		Assert.assertEquals(4000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100532-1^", "1,1,1", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100532-1^", "2,2,2", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100532-1^", "1,2,3", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_50() {
		Assert.assertEquals(1000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100550-1^", "3,1,2", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100550-1^", "1,2,3", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100550-1^", "1,2,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_41() {
		Assert.assertEquals(4000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100541-1,2,3,4,5,6^", "1,3,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100541-1,2,3,4,5,6^", "1,3,4", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100541-1,2,3,4,5,6^", "2,2,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_42() {
		Assert.assertEquals(4000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100542-1,2#3,4,5,6^", "1,2,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100542-1,2#3,4,5,6^", "1,2,4", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100542-1,2#3,4,5,6^", "2,2,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void calculateTest_12() {
		Assert.assertEquals(8000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100512-2,3*4,5^", "2,2,4", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100512-2,3*4,5^", "3,3,5", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,k3.caculatePrizeAmt(k3.getPrizeLevelInfo("100512-2,3*4,5^", "2,2,6", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	
	
	private void printSplotlot(List<SplitedLot> splits) {
		System.out.println();
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
}
