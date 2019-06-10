package com.lottery.test.lottype;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.Dcbf;

public class BeidanBfTest {

	
	Dcbf bf = new Dcbf();
	
	@Test
	public void test() {
		boolean flag = bf.validate("500510101-163(10)|164(20)^", new BigDecimal(400), BigDecimal.ONE, 200);
		Assert.assertTrue(flag);
	}
}
