package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.DLT;

public class DaletouPrizeTest {

	DLT dlt = new DLT();
	
	int oneAmount = 200;
	
	Map<String, Long> map = new HashMap<String, Long>();
	{
		map.put("1A", 500000000L);
		map.put("2A", 200000000L);
		map.put("3A", 100000000L);
		map.put("4A", 300000L);
		map.put("5A", 60000L);
		map.put("6A", 10000L);
		map.put("7A", 1000L);
		map.put("8", 500L);
	}
	
	
	@Test
	public void testCaculatePrize() {
		
		//单式
		Assert.assertEquals(500000000L*4/5, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-08,12,15,19,28|02,07^", "08,12,15,19,28|02,07", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//1
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,05|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//2
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,05|01,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//3
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,05|03,04^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//4
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,06|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//5
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,06|01,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//6
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,06,07|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,04,06|03,04^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//7
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,06,07|01,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,06,07,08|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//8
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,03,06,07|03,04^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		System.out.println(dlt.getPrizeLevelInfo("200101-01,02,06,07,08|01,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		System.out.println(dlt.getPrizeLevelInfo("200101-01,06,07,08,09|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		System.out.println(dlt.getPrizeLevelInfo("200101-06,07,08,09,10|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		
		//复式
		System.out.println(dlt.getPrizeLevelInfo("200102-01,02,03,04,05,06|01,02^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		
		
		Assert.assertEquals(500000000L*4/5 + 300000L*5, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-01,02,03,04,05,06|01,02^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,2,2,1,,
		System.out.println(dlt.getPrizeLevelInfo("200102-01,02,03,04,05,06|01,02,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		//7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,7,7,6,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,5,5,4,2,2,1,,
		System.out.println(dlt.getPrizeLevelInfo("200102-01,02,03,04,05,06,07|01,02,03^", "01,02,03,04,05|01,02",new BigDecimal(1),oneAmount));
		
	}
	
	@Test
	public void testCaculatePrizeDanshi() {
		
		Assert.assertEquals(500000000L*4/5, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(true, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).isBig());
		
		Assert.assertEquals(200000000L*4/5, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|02,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(true, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|02,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).isBig());
		
		Assert.assertEquals(100000000L*4/5, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|04,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(true, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,01|04,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).isBig());
		
		Assert.assertEquals(300000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(60000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,09|02,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(10000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,04,09|04,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,08,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(1000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,07,09|02,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,12,08,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(500L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,03,02,07,09|04,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,13,12,08,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,03,02,07,09|02,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500L, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,13,12,08,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(false, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,13,12,08,09|02,01^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).isBig());
		
		Assert.assertEquals(0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,03,02,07,09|04,03^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,13,12,08,09|02,11^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-05,13,12,08,09|12,11^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,13,12,08,09|02,11^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200101-15,13,12,08,09|12,11^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void testCaculatePrizeFushi() {
		Assert.assertEquals(500000000L*4/5*1 + 200000000L*4/5*4 + 100000000L*4/5*1 + 300000L*15 + 60000L*60 + 10000L*45 + 1000L*130 + 500L*70, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,01,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*3 + 100000000L*4/5*3 + 300000L*0 + 60000L*45 + 10000L*45 + 1000L*90 + 500L*120, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,01,07,08,09|12,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*6 + 300000L*0 + 60000L*0 + 10000L*90 + 1000L*0 + 500L*180, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,01,07,08,09|12,11,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*4 + 60000L*16 + 10000L*28 + 1000L*120 + 500L*124, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,11,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*12 + 10000L*12 + 1000L*72 + 500L*144, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,11,07,08,09|12,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*24 + 1000L*0 + 500L*144, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,04,11,07,08,09|12,11,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*10 + 1000L*70 + 500L*146, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,14,11,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*30 + 500L*120, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,14,11,07,08,09|12,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*0 + 500L*60, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,02,14,11,07,08,09|12,11,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*20 + 500L*116, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,12,14,11,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*0 + 500L*60, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,03,12,14,11,07,08,09|12,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*0 + 500L*56, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-05,13,12,14,11,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*0 + 200000000L*4/5*0 + 100000000L*4/5*0 + 300000L*0 + 60000L*0 + 10000L*0 + 1000L*0 + 500L*56, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200102-15,13,12,14,11,07,08,09|02,01,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	
	@Test
	public void testCaculatePrizeDantuo() {
		Assert.assertEquals(500000000L*4/5*1 + 200000000L*4/5*2 + 100000000L*4/5*0 + 300000L*4 + 60000L*8 + 10000L*0 + 1000L*0 + 500L*0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200103-01,02,03,04#05,06,07,08,09|01#02,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*1 + 200000000L*4/5*4 + 100000000L*4/5*1 + 300000L*4 + 60000L*16 + 10000L*4 + 1000L*0 + 500L*0, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200103-01,02,03,04#05,06,07,08,09|01,02,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(500000000L*4/5*1 + 200000000L*4/5*2 + 100000000L*4/5*0 + 300000L*20 + 60000L*40 + 10000L*60 + 1000L*160 + 500L*85, dlt.caculatePrizeAmt(dlt.getPrizeLevelInfo("200103-01,02,03,04,05,06,07,08,09|01#02,03,04^", "01,02,03,04,05|01,02", new BigDecimal(1),oneAmount), map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	

}
