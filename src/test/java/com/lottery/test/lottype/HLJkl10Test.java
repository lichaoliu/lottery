package com.lottery.test.lottype;

import java.math.BigDecimal;

import org.junit.Test;

import com.lottery.lottype.HLJkl10;

public class HLJkl10Test {

	HLJkl10 kl10 = new HLJkl10();
	
	
	@Test
	public void test() {
		kl10.validate("110425-01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20^", new BigDecimal(31008), new BigDecimal(1), 200);
	}
}
