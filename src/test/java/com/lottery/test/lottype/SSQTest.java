package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SSQ;
import com.lottery.lottype.SplitedLot;

public class SSQTest{

	
	SSQ ssq = new SSQ();
	
	int oneAmount = 200;
	
	protected static String ssq_01 = "(100101)[-]((0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){5}[|](0[1-9]|1[0-6])[^]){1,5}";
	
	protected static String ssq_02 = "(100102)[-](0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){5,16}[|](0[1-9]|1[0-6]){1,16}[\\^]";
	
	@Test
	public void testValidate() {
		ssq.validate("100101-01,02,03,04,05,06|07^", new BigDecimal(200), BigDecimal.ONE, 200);
		
		ssq.validate("100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^", new BigDecimal(600), BigDecimal.ONE, 200);
		
		ssq.validate("100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^", new BigDecimal(1200), BigDecimal.ONE, 200);
		
		ssq.validate("100101-01,02,03,04,05,06|07^", new BigDecimal(20000), new BigDecimal(100), 200);
		
		ssq.validate("100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^", new BigDecimal(60000), new BigDecimal(100), 200);
		
		
		ssq.validate("100102-01,02,03,04,05,06,07,08,09,10|07,08,09,10^", new BigDecimal(168000), BigDecimal.ONE, 200);
		
		ssq.validate("100102-01,02,03,04,05,06,07,08,09,10|07,08,09,10^", new BigDecimal(16800000), new BigDecimal(100), 200);

		
		ssq.validate("100103-01#06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21|01,02,03^", new BigDecimal(2620800), BigDecimal.ONE, 200);
		
		ssq.validate("100103-01#06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21|01,02,03^", new BigDecimal(262080000), new BigDecimal(100), 200);
		
		ssq.validate("100103-01#06,07,08,09,10|01,02,03^", new BigDecimal(600), new BigDecimal(1), 200);
		
		try {
			ssq.validate("100103-01#06,07,08,09,10|01^", new BigDecimal(600), new BigDecimal(1), 200);
		}catch(LotteryException e) {
			Assert.assertTrue(e.getErrorCode()==ErrorCode.betamount_error);
		}
		
		
		
	}
	
	@Test
	public void test() {
		ssq.validate("100103-29,30,31,32,33#01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17|02^", new BigDecimal(3400), new BigDecimal(1), 200);
	}
	
	
	
	@Test
	public void testSplit() {
		
		
		List<SplitedLot> splits0 = ssq.split("100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^", 1, 1200, 200);
		
		for(SplitedLot s:splits0) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits01 = ssq.split("100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^", 1, 1000, 200);
		
		for(SplitedLot s:splits01) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits = ssq.split("100101-01,02,03,04,05,06|07^", 99, 19800, 200);
		
		for(SplitedLot s:splits) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		List<SplitedLot> splits2 = ssq.split("100102-01,02,03,04,05,06,07,08,09,10|07,08,09,10^", 100, 16800000, 200);
		
		for(SplitedLot s:splits2) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		List<SplitedLot> splits3 = ssq.split("100102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16|07,08,09,10^", 1, 6406400, 200);
		
		for(SplitedLot s:splits3) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits4 = ssq.split("100102-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16|07,08,09,10^", 100, 640640000, 200);
		
		for(SplitedLot s:splits4) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits5 = ssq.split("100103-01,02,03,04,05#06,07,08,09,10|01,02,03,04,05^", 100, 500000, 200);
		
		for(SplitedLot s:splits5) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits6 = ssq.split("100103-01#06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21|01,02,03,04,05^", 1, 4368000, 200);
		
		for(SplitedLot s:splits6) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits7 = ssq.split("100103-01#06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21|01,02,03,04,05^", 100, 436800000, 200);
		
		for(SplitedLot s:splits7) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
		
		
		List<SplitedLot> splits8 = ssq.split("100103-11#06,07,08,09,10|01,02,03,04,05^", 1, 1000, 200);
		
		for(SplitedLot s:splits8) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
			ssq.validate(s.getBetcode(), new BigDecimal(s.getAmt()), new BigDecimal(s.getLotMulti()), 200);
		}
	}
	
	
	@Test
	public void testPrize() {
		Assert.assertTrue(isPrizeInfoEqual("1_1", ssq.getPrizeLevelInfo("100101-01,02,03,04,05,06|07^", "01,02,03,04,05,06|07", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("5_28", ssq.getPrizeLevelInfo("100102-11,12,13,14,15,16,20,24,25,26,30,31|03^", "13,15,18,20,24,28|09", BigDecimal.ONE,oneAmount)));
		
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-01,06,15,19,28,29|14^", "01,06,15,19,28,29|10", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-02,13,16,18,24,30|09^", "30,24,02,18,13,16|12", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-03,05,08,19,20,27|13^", "03,05,08,19,20,27|09", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-03,12,16,17,18,27|10^", "03,12,16,17,18,27|08", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-04,05,09,27,29,31|04^", "04,05,09,27,29,31|13", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-04,07,09,10,17,27|04^", "04,07,09,10,17,27|05", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1", ssq.getPrizeLevelInfo("100101-05,06,07,12,13,18|15^", "05,06,07,12,13,18|12", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_4", ssq.getPrizeLevelInfo("100101-08,13,19,23,26,28|05^", "13,16,19,23,26,28|05", new BigDecimal(4),oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_5", ssq.getPrizeLevelInfo("100101-01,05,09,10,19,28|03^", "04,05,09,10,19,28|03", new BigDecimal(5),oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_8,6_1008,5_560,4_140", ssq.getPrizeLevelInfo("100102-01,03,08,09,11,13,15,20,22,27,29,31,33|02^", "01,08,11,20,21,29|02", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("6_672,5_224,4_28", ssq.getPrizeLevelInfo("100102-02,03,08,09,11,13,19,23,26,28,29,30|08^", "02,05,11,23,24,29|08", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("6_8008", ssq.getPrizeLevelInfo("100102-02,04,06,08,10,12,14,16,18,20,22,24,26,28,30,32|08^", "01,09,11,21,26,32|08", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_3,5_10,4_15", ssq.getPrizeLevelInfo("100102-02,08,12,13,15,18,19,27|04^", "08,13,29,12,19,02|04", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_3,5_10,4_15", ssq.getPrizeLevelInfo("100102-03,05,07,08,17,21,31,32|15^", "03,08,17,21,25,32|15", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("6_672,5_224,4_28", ssq.getPrizeLevelInfo("100102-03,08,10,13,15,18,19,22,26,29,32,33|14^", "03,08,20,24,26,32|14", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1,5_225,4_36", ssq.getPrizeLevelInfo("100102-05,07,08,11,13,14,20,22,25,26,28,29|16^", "07,08,14,25,26,28|13", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_3,5_10,4_15", ssq.getPrizeLevelInfo("100102-05,10,17,22,23,25,26,31|07^", "05,14,17,22,23,25|07", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_1,5_45,4_18", ssq.getPrizeLevelInfo("100102-09,11,13,15,22,23,30,31,32|03^", "09,11,23,30,31,32|06", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_3", ssq.getPrizeLevelInfo("100102-05,06,12,14,19,23|06,11,13^", "05,06,12,14,19,23|09", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("5_200,4_50", ssq.getPrizeLevelInfo("100102-01,03,06,07,13,22|02,06,07,09,13^", "01,06,12,13,22,31|07", new BigDecimal(50),oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_2,4_12", ssq.getPrizeLevelInfo("100102-01,07,08,12,14,16,21|10,16^", "01,07,08,12,16,21|01", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_1,6_462,5_216,4_36", ssq.getPrizeLevelInfo("100103-03,22#06,07,08,11,12,14,17,21,26,27,30,32,33|07^", "03,06,11,17,21,31|07", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_4,5_4,4_12", ssq.getPrizeLevelInfo("100103-07,15,20#03,04,18,26,27,33|14^", "07,15,18,19,20,26|14", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_3,5_3,4_9", ssq.getPrizeLevelInfo("100103-08,10,15,19#09,12,13,14,26,30|11^", "09,10,12,14,15,19|11", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_8,6_70,5_168,4_84", ssq.getPrizeLevelInfo("100103-15,32#02,07,08,12,14,17,18,21,24,28,29|14^", "02,15,18,27,28,32|14", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_8,2_1,1_1,5_6,4_14", ssq.getPrizeLevelInfo("100103-03,11,17,31#04,06,21,22,28,30|07,12^", "03,06,11,17,21,31|07", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("3_6,5_170,4_60", ssq.getPrizeLevelInfo("100103-16,17,24#02,03,18,19,20,25,29,33|02,04,08,10,14,16^", "16,17,18,24,25,30|08", BigDecimal.ONE,oneAmount)));
		Assert.assertTrue(isPrizeInfoEqual("2_2,5_18,4_18", ssq.getPrizeLevelInfo("100103-19,28,32#02,06,18,22,26,29|10,14^", "06,18,19,26,28,32|12", BigDecimal.ONE,oneAmount)));
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
}
