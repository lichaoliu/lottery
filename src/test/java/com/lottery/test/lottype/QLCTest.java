package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.QLC;
import com.lottery.lottype.SplitedLot;

public class QLCTest {

	QLC qiLeCai = new QLC();
	
	int oneAmount = 200;
	
	@Test
	public void testValidateSingle() {
		Assert.assertTrue(qiLeCai.validate("100301-01,02,03,04,05,06,07^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(qiLeCai.validate("100301-01,07,10,11,12,13,18^04,05,08,11,19,21,28^06,09,11,12,27,28,30^04,08,09,13,22,29,30^01,02,04,15,16,17,28^", BigDecimal.valueOf(1000L),new BigDecimal(1),200));
		Assert.assertTrue(qiLeCai.validate("100301-01,07,10,11,12,13,18^04,05,08,11,19,21,28^06,09,11,12,27,28,30^04,08,09,13,22,29,30^01,02,04,15,16,17,28^01,02,04,15,16,17,28^", BigDecimal.valueOf(1200L),new BigDecimal(1),200));
	}
	
	
	@Test
	public void testValidateMuti() {
		Assert.assertTrue(qiLeCai.validate("100302-01,02,03,04,05,06,07,08,09,10^", BigDecimal.valueOf(24000L), new BigDecimal(1),200));
		Assert.assertTrue(qiLeCai.validate("100302-03,05,13,14,22,23,27,29,30^", BigDecimal.valueOf(7200L), new BigDecimal(1),200));
	}
	
	
	@Test
	public void testValidateDantuo() {
		Assert.assertTrue(qiLeCai.validate("100303-01,02,03#04,05,06,07,08,09,10^", BigDecimal.valueOf(7000L), new BigDecimal(1),200));
		Assert.assertTrue(qiLeCai.validate("100303-01#05,07,11,14,16,19,23^", BigDecimal.valueOf(1400L), new BigDecimal(1),200));
	}
	


	
	Map<String, Long> map = new HashMap<String, Long>();
	{
		map.put("1", 1000000L);
		map.put("2", 200000L);
		map.put("3", 100000L);
		map.put("4", 20000L);
		map.put("5", 5000L);
		map.put("6", 1000L);
		map.put("7", 500L);
		
	}
	@Test
	public void testPrizeSingle(){
		Assert.assertEquals(1600000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,05,06,07", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(400000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,05,06,08", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(200000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,05,06,09", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(40000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,05,08,11", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(10000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,12,04,05,11,03", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(2000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,08,11,12", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(1000L, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,04,11,12,13", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,02,03,09,11,12,13", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
		Assert.assertEquals(0, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100301-01,15,03,09,11,12,13", "01,02,03,04,05,06,07|08", new BigDecimal(2),oneAmount), map,null).getAfterTaxAmt().longValue());
	}
	
	@Test
	public void testPrizeDouble(){
		//1 2
		Assert.assertEquals(800000L+200000*7, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100302-01,02,03,04,05,06,07,08", "01,02,03,04,05,06,07|08", new BigDecimal(1),oneAmount), map,null).getAfterTaxAmt().longValue());
		// 1 2 3
		Assert.assertEquals(800000L+200000*7+100000L*7+20000L*21, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100302-01,02,03,04,05,06,07,08,09", "01,02,03,04,05,06,07|08", new BigDecimal(1),oneAmount), map,null).getAfterTaxAmt().longValue());
		// 1 2 3 4 5 6 7
		Assert.assertEquals(800000L+200000L*7+100000L*21+20000L*63+5000L*63+1000L*35*3+500L*35, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100302-01,02,03,04,05,06,07,08,09,10,11", "01,02,03,04,05,06,07|08", new BigDecimal(1),oneAmount), map,null).getAfterTaxAmt().longValue());
		
	}
	
	@Test
	public void testPrizeDantuo(){
		Assert.assertEquals(800000L+200000L*4+100000L*12+20000L*18+5000L*18+1000L*12+500L*4, qiLeCai.caculatePrizeAmt(qiLeCai.getPrizeLevelInfo("100303-01,02,03#04,05,06,07,08,09,10,11", "01,02,03,04,05,06,07|08", new BigDecimal(1),oneAmount), map,null).getAfterTaxAmt().longValue());
	}
	

	@Test
	public void testSplitSingle() {
		List<SplitedLot> split1 = qiLeCai.split("100301-01,02,03,04,05,06,07^", 1, 200, 200);
		checkSplit(split1, 200);
		List<SplitedLot> split2 = qiLeCai.split("100301-01,02,03,04,05,06,07^", 100, 20000, 200);
		checkSplit(split2, 20000);
		List<SplitedLot> split3 = qiLeCai.split("100301-01,07,10,11,12,13,18^04,05,08,11,19,21,28^06,09,11,12,27,28,30^04,08,09,13,22,29,30^01,02,04,15,16,17,28^01,02,04,15,16,17,28^", 100, 120000, 200);
		checkSplit(split3, 120000);
		List<SplitedLot> split4 = qiLeCai.split("100301-01,07,10,11,12,13,18^04,05,08,11,19,21,28^06,09,11,12,27,28,30^04,08,09,13,22,29,30^01,02,04,15,16,17,28^01,02,04,15,16,17,28^", 1, 1200, 200);
		checkSplit(split4, 1200);
	}
	
	
	
	@Test
	public void testSplitDouble() {
		List<SplitedLot> split1 = qiLeCai.split("100302-01,02,03,04,05,06,07,08,09^", 1, 7200, 200);
		checkSplit(split1, 7200);
		List<SplitedLot> split2 = qiLeCai.split("100302-01,02,03,04,05,06,07,08,09^", 100, 720000, 200);
		checkSplit(split2, 720000);
	}
	
	
	@Test
	public void testSplitDantuo() {
		List<SplitedLot> split1 = qiLeCai.split("100303-01,02,03,04#05,06,07,08,09,10,11,12,13,14,15,16,17,18,19^", 1, 91000, 200);
		checkSplit(split1, 91000);
		List<SplitedLot> split2 = qiLeCai.split("100303-01,02,03,04#05,06,07,08,09,10,11,12,13,14,15,16,17,18,19^", 100, 9100000, 200);
		checkSplit(split2, 9100000);
		List<SplitedLot> split3 = qiLeCai.split("100303-01#05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20^", 1, 1601600, 200);
		checkSplit(split3, 1601600);
		List<SplitedLot> split4 = qiLeCai.split("100303-01#05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20^", 100, 160160000, 200);
		checkSplit(split4, 160160000);
	}
	
	
	private void checkSplit(List<SplitedLot> splits,long amt) {
		long totalamt = 0;
		for(SplitedLot s:splits) {
			qiLeCai.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
			totalamt = totalamt + s.getAmt();
		}
		if(totalamt!=amt) {
			throw new RuntimeException();
		}
	}
}
