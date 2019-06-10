package com.lottery.test.lottype;

import java.math.BigDecimal;

import org.junit.Test;

import com.lottery.lottype.SdKuailepk3;

public class Sdklpk3Test{

	
	SdKuailepk3 pk3 = new SdKuailepk3();
	
	int oneAmount = 200;
	
	
	@Test
	public void testValidate() {
		pk3.validate("200901-01^02^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200902-01,02^01,03^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200903-01,02,03^02,03,04^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200904-01,02,03,04^02,03,04,05^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200905-01,02,03,04,05^02,03,04,05,06^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200906-01,02,03,04,05,06^02,03,04,05,06,07^", new BigDecimal(400), BigDecimal.ONE, 200);
		
		pk3.validate("200911-01,02^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200912-01,02,03^", new BigDecimal(600), BigDecimal.ONE, 200);
		pk3.validate("200913-01,02,03,04^", new BigDecimal(800), BigDecimal.ONE, 200);
		pk3.validate("200914-01,02,03,04,05^", new BigDecimal(1000), BigDecimal.ONE, 200);
		pk3.validate("200915-01,02,03,04,05,06^", new BigDecimal(1200), BigDecimal.ONE, 200);
		pk3.validate("200916-01,02,03,04,05,06,07^", new BigDecimal(1400), BigDecimal.ONE, 200);
		
		pk3.validate("200922-01#02,03^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200923-01,02#03,04^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200924-01,02,03#04,05^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200925-01,02,03,04#05,06^", new BigDecimal(400), BigDecimal.ONE, 200);
		pk3.validate("200926-01,02,03,04,05#06,07^", new BigDecimal(400), BigDecimal.ONE, 200);
	}
	
}
