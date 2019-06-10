package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.lottery.lottype.Dcbf;
import com.lottery.lottype.Dcspf;
import com.lottery.lottype.SplitedLot;

public class DanchangSPF {

	Dcspf spf = new Dcspf();
	
	Dcbf bf = new Dcbf();
	
	
	@Test
	public void test01() {
		boolean flag = spf.validate("500110307-048(3)|049(3)|047(3)|050(3)^", new BigDecimal(2800), BigDecimal.ONE, 200);
		
		System.out.println(flag);
	}
	
	@Test
	public void test02() {
		List<SplitedLot> split = spf.split("500110307-048(3)|049(3)|047(3)|050(3)^", 1, 2800, 200);
		
		for(SplitedLot s:split) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
		}
	}
	
	@Test
	public void test021() {
		List<SplitedLot> split = spf.split("500110307-048(3)|049(3)|047(3)^", 1, 1400, 200);
		
		for(SplitedLot s:split) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
		}
	}
	
	
	@Test
	public void test03() {
		boolean flag = spf.validate("500120307-048(3)#049(3)|047(3)|050(3)^", new BigDecimal(1400), BigDecimal.ONE, 200);
		
		System.out.println(flag);
	}
	
	@Test
	public void test04() {
		List<SplitedLot> split = spf.split("500120307-048(3)#049(3)|047(3)|050(3)^", 1, 1400, 200);
		
		for(SplitedLot s:split) {
			System.out.println(s.getBetcode()+" "+s.getAmt()+" "+s.getLotMulti());
		}
	}
	
	
	@Test
	public void test05() {
		boolean flag = bf.validate("500510501-077(10)|078(21)|079(11)|080(23)|081(90)^", new BigDecimal(200), BigDecimal.ONE, 200);
		
		System.out.println(flag);
	}
}
