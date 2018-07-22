package com.lottery.test.lottype;

import java.math.BigDecimal;

import org.junit.Test;

import com.lottery.lottype.Gd11x5;

public class Gd11x5Test {

	
	Gd11x5 gd = new Gd11x5();
	
	
	@Test
	public void test() {
		System.out.println(gd.getPrizeLevelInfo("200513-01,02,03,04,05^", "01,02,03,04,05", BigDecimal.ONE, 200));
	}
}
