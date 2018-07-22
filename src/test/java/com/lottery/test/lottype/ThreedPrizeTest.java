package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.ThreeD;

public class ThreedPrizeTest {

	Map<String, Long> map = new HashMap<String, Long>();
	{
		map.put("1", 100000L);
		map.put("2", 32000L);
		map.put("3", 16000L);
		
	}
	
	ThreeD t = new ThreeD();
	
	int oneAmount = 200;
	
	@Test
	public void test01() {
		//单倍未中奖
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-05,09,09^02,01,02^", "1,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-05,06,04^", "1,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//单倍中奖
		Assert.assertEquals(100000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-00,01,07^04,06,06^", "0,1,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-03,04,07^", "3,4,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//多倍中奖
		Assert.assertEquals(200000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-03,09,02^", "3,9,2", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(200000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-02,03,09^01,03,07^04,08,09^04,06,06^01,03,02^", "1,3,2", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//多倍未中奖
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-03,09,02^", "1,2,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100201-02,03,09^01,03,07^04,08,09^04,06,06^01,03,02^", "0,1,7", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test02() {
		//单倍中奖
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-05,05,09^", "5,5,9", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-02,05,05^05,06,06^02,02,07^03,08,08^00,00,04^", "5,5,2", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//单倍未中奖（开奖号为组三格式）
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-03,03,05^", "1,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-01,09,09^00,00,09^", "3,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//单倍未中奖（开奖号不为组三格式）
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-03,03,05^", "1,2,3", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-01,09,09^00,00,09^", "1,2,3", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//多倍中奖
		Assert.assertEquals(64000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-05,05,09^", "5,5,9", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(64000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-05,05,09^05,09,09^", "5,5,9", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//多倍未中奖（开奖号为组三格式）
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-03,03,05^", "1,4,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-01,09,09^00,00,09^", "3,4,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		//多倍未中奖（开奖号不为组三格式）
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-03,03,05^", "1,2,3", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100202-01,09,09^00,00,09^", "1,2,3", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test03() {
		//单倍中奖
		Assert.assertEquals(16000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-03,05,07^", "5,7,3", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-00,04,05^00,04,07^00,05,07^00,06,07^00,05,08^", "4,0,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//单倍未中奖(开奖为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-00,02,04^", "1,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-01,05,06^05,06,08^", "1,4,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//单倍未中奖(开奖为组六)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-01,05,07^", "3,4,1", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-03,04,08^02,04,05^00,03,06^00,01,08^01,05,06^", "3,5,1", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//多倍中奖
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-03,05,07^", "5,7,3", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-00,04,05^00,04,07^00,05,07^00,06,07^00,05,08^", "4,0,7", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//多倍未中奖(开奖为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-00,02,04^", "1,4,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-01,05,06^05,06,08^", "1,4,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//多倍为中奖(开奖为组六)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-01,05,07^", "3,4,1", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100203-03,04,08^02,04,05^00,03,06^00,01,08^01,05,06^", "3,5,1", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test21() {
		//中奖(单倍)
		Assert.assertEquals(100000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-06^", "4,2,0", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(100000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-06,105^", "4,2,0", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//中奖(多倍)
		Assert.assertEquals(200000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-06^", "4,2,0", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(300000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-06,05^", "4,2,0", new BigDecimal(3),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(单倍)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-01^", "2,4,2", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-02^", "1,3,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(多倍)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-01^", "2,4,2", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100221-02^", "1,3,7", new BigDecimal(3),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test22() {
		//中奖(单倍)
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-13^", "4,5,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-13,12^", "4,5,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//中奖(多倍)
		Assert.assertEquals(64000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-13^", "4,5,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(64000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-13,12^", "4,5,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三格式)单倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12^", "3,3,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12,13^", "3,3,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三格式)多倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12^", "3,3,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12,13^", "3,3,4", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三格式)单倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12^", "3,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12,13^", "3,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三格式)多倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12^", "3,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100222-12,13^", "3,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test23() {
		//中奖单倍
		Assert.assertEquals(16000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12^", "3,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(16000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12,13^", "3,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//中奖多倍
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12^", "3,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12,13^", "3,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三)单倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-13^", "4,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-13,13^", "4,4,5", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三)多倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-13^", "4,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-13,13^", "4,4,5", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三)单倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12^", "4,5,6", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12,11^", "4,5,6", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三)多倍
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12^", "4,5,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100223-12,11", "4,5,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test11() {
		//中奖
		Assert.assertEquals(100000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100211-01,02|03,04|05,06^", "2,4,6", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(200000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100211-01,02|03,04|05,06^", "2,4,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(200000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100211-00,01,02,03,04,05,06,07,08,09|00,01,02,03,04,05,06,07,08,09|00,01,02,03,04,05,06,07,08,09^", "2,4,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//不中奖
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100211-0102|0304|0506^", "3,4,6", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100211-0102|0304|0506^", "3,4,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test12() {
		//中奖
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "3,3,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(64000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "5,5,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "6,6,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "6,6,7", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "3,5,6", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100212-03,04,05,06^", "3,5,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void test13() {
		//中奖
		Assert.assertEquals(16000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "3,5,4", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(32000,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "3,5,6", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "6,6,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "6,6,7", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		//未中奖(开奖不为组三)
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "3,5,7", new BigDecimal(1),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(0,t.caculatePrizeAmt(t.getPrizeLevelInfo("100213-03,04,05,06^", "3,5,7", new BigDecimal(2),oneAmount),map,new BigDecimal(200)).getAfterTaxAmt().longValue());
	}
	

}
