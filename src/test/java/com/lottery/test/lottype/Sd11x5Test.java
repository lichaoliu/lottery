package com.lottery.test.lottype;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.lottype.Sd11x5;
import com.lottery.lottype.SplitedLot;

public class Sd11x5Test {

	Sd11x5 sd11x5 = new Sd11x5();
	
	int oneAmount = 200;
	
	@Test
	public void testSingle() {
		Assert.assertTrue(sd11x5.validate("200702-01,02^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200703-01,02,03^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200704-01,02,04,05^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200705-01,02,10,11,09^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200706-01,02,03,04,05,11^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200707-01,02,03,04,05,06,07^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200708-01,02,11,10,09,08,07,06^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200731-01^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200732-01,11^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200733-01,10,11^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200734-01,11^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200735-01,10,11^", BigDecimal.valueOf(200L),new BigDecimal(1),200));
		
		Assert.assertTrue(sd11x5.validate("200702-01,02^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200703-01,02,03^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200704-01,02,04,05^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200705-01,02,10,11,09^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200706-01,02,03,04,05,11^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200707-01,02,03,04,05,06,07^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200708-01,02,11,10,09,08,07,06^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200731-01^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200732-01,11^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200733-01,10,11^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200734-01,11^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200735-01,10,11^", BigDecimal.valueOf(2000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(sd11x5.validate("200702-01,02^01,02^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200703-01,02,03^01,02,03^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200704-01,02,04,05^01,02,04,05^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200705-01,02,10,11,09^01,02,10,11,09^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200706-01,02,03,04,05,11^01,02,03,04,05,11^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200707-01,02,03,04,05,06,07^01,02,03,04,05,06,07^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200708-01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200731-01^01^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200732-01,11^01,11^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200733-01,10,11^01,10,11^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200734-01,11^01,11^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200735-01,10,11^01,10,11^", BigDecimal.valueOf(2000L*2),new BigDecimal(10),200));
	}
	
	@Test
	public void testMulti() {
		Assert.assertTrue(sd11x5.validate("200712-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(11000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200713-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(33000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200714-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(66000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200715-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(92400L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200716-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(92400L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200717-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(66000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200718-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(33000L),new BigDecimal(1),200));
		
		Assert.assertTrue(sd11x5.validate("200712-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(110000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200713-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(330000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200714-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(660000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200715-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(924000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200716-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(924000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200717-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(660000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200718-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(330000L),new BigDecimal(10),200));
		
		Assert.assertTrue(sd11x5.validate("200744-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(11000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200745-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(33000L),new BigDecimal(1),200));
		
		Assert.assertTrue(sd11x5.validate("200744-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(110000L),new BigDecimal(10),200));
		Assert.assertTrue(sd11x5.validate("200745-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(330000L),new BigDecimal(10),200));
		
		
		Assert.assertTrue(sd11x5.validate("200741-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(2200L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200741-01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(22000L),new BigDecimal(10),200));
		
		Assert.assertTrue(sd11x5.validate("200742-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(22000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200742-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(220000L),new BigDecimal(10),200));
		
		Assert.assertTrue(sd11x5.validate("200743-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(198000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200743-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(1980000L),new BigDecimal(10),200));
	}
	
	
	
	@Test
	public void testDantuo() {
		Assert.assertTrue(sd11x5.validate("200722-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200723-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(9000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200724-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200725-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(42000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200726-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(50400L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200727-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(42000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200728-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(24000L),new BigDecimal(1),200));
		
		Assert.assertTrue(sd11x5.validate("200754-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(2000L),new BigDecimal(1),200));
		Assert.assertTrue(sd11x5.validate("200755-01#02,03,04,05,06,07,08,09,10,11^", BigDecimal.valueOf(9000L),new BigDecimal(1),200));
	}
	
	
	
	@Test
	public void testSplitSingle() {
		List<SplitedLot> splits2 = sd11x5.split("200702-01,02^", 100, 20000, 200);
		for(SplitedLot lot:splits2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits3 = sd11x5.split("200703-01,02,03^", 100, 20000, 200);
		for(SplitedLot lot:splits3) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits4 = sd11x5.split("200704-01,02,04,05^", 100, 20000, 200);
		for(SplitedLot lot:splits4) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits5 = sd11x5.split("200705-01,02,10,11,09^", 100, 20000, 200);
		for(SplitedLot lot:splits5) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits6 = sd11x5.split("200706-01,02,03,04,05,11^", 100, 20000, 200);
		for(SplitedLot lot:splits6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits7 = sd11x5.split("200707-01,02,03,04,05,06,07^", 100, 20000, 200);
		for(SplitedLot lot:splits7) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits8 = sd11x5.split("200708-01,02,11,10,09,08,07,06^", 100, 20000, 200);
		for(SplitedLot lot:splits8) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		
		
		List<SplitedLot> splits31 = sd11x5.split("200731-01^", 100, 20000, 200);
		for(SplitedLot lot:splits31) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits32 = sd11x5.split("200732-01,11^", 100, 20000, 200);
		for(SplitedLot lot:splits32) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits33 = sd11x5.split("200733-01,10,11^", 100, 20000, 200);
		for(SplitedLot lot:splits33) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits34 = sd11x5.split("200734-01,11^", 100, 20000, 200);
		for(SplitedLot lot:splits34) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits35 = sd11x5.split("200735-01,10,11^", 100, 20000, 200);
		for(SplitedLot lot:splits35) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplitSingle2() {
		List<SplitedLot> splits2 = sd11x5.split("200702-01,02^01,02^01,02^01,02^01,02^01,02^", 100, 120000, 200);
		for(SplitedLot lot:splits2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits3 = sd11x5.split("200703-01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^01,02,03^", 100, 120000, 200);
		for(SplitedLot lot:splits3) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits4 = sd11x5.split("200704-01,02,04,05^01,02,04,05^01,02,04,05^01,02,04,05^01,02,04,05^01,02,04,05^", 100, 120000, 200);
		for(SplitedLot lot:splits4) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits5 = sd11x5.split("200705-01,02,10,11,09^01,02,10,11,09^01,02,10,11,09^01,02,10,11,09^01,02,10,11,09^01,02,10,11,09^", 100, 120000, 200);
		for(SplitedLot lot:splits5) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits6 = sd11x5.split("200706-01,02,03,04,05,11^01,02,03,04,05,11^01,02,03,04,05,11^01,02,03,04,05,11^01,02,03,04,05,11^01,02,03,04,05,11^", 100, 120000, 200);
		for(SplitedLot lot:splits6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits7 = sd11x5.split("200707-01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^01,02,03,04,05,06,07^", 100, 120000, 200);
		for(SplitedLot lot:splits7) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits8 = sd11x5.split("200708-01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^01,02,11,10,09,08,07,06^", 100, 120000, 200);
		for(SplitedLot lot:splits8) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		
		
		List<SplitedLot> splits31 = sd11x5.split("200731-01^01^01^01^01^01^", 100, 120000, 200);
		for(SplitedLot lot:splits31) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits32 = sd11x5.split("200732-01,11^01,11^01,11^01,11^01,11^01,11^", 100, 120000, 200);
		for(SplitedLot lot:splits32) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits33 = sd11x5.split("200733-01,10,11^01,10,11^01,10,11^01,10,11^01,10,11^01,10,11^", 100, 120000, 200);
		for(SplitedLot lot:splits33) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits34 = sd11x5.split("200734-01,11^01,11^01,11^01,11^01,11^01,11^", 100, 120000, 200);
		for(SplitedLot lot:splits34) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits35 = sd11x5.split("200735-01,10,11^01,10,11^01,10,11^01,10,11^01,10,11^01,10,11^", 100, 120000, 200);
		for(SplitedLot lot:splits35) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplitMulti() {
		/**
		List<SplitedLot> splits2 = sd11x5.split("200712-01,02,03,04,05,06,07,08,09,10,11^", 100, 1100000L, 200);
		for(SplitedLot lot:splits2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits3 = sd11x5.split("200713-01,02,03,04,05,06,07,08,09,10,11^", 100, 3300000L, 200);
		for(SplitedLot lot:splits3) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits4 = sd11x5.split("200714-01,02,03,04,05,06,07,08,09,10,11^", 100, 6600000L, 200);
		for(SplitedLot lot:splits4) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits5 = sd11x5.split("200715-01,02,03,04,05,06,07,08,09,10,11^", 100, 9240000L, 200);
		for(SplitedLot lot:splits5) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits6 = sd11x5.split("200716-01,02,03,04,05,06,07,08,09,10,11^", 100, 9240000L, 200);
		for(SplitedLot lot:splits6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits7 = sd11x5.split("200717-01,02,03,04,05,06,07,08,09,10,11^", 100, 6600000L, 200);
		for(SplitedLot lot:splits7) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits8 = sd11x5.split("200718-01,02,03,04,05,06,07,08,09,10,11^", 100, 3300000L, 200);
		for(SplitedLot lot:splits8) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		
		
		
		List<SplitedLot> splits44 = sd11x5.split("200744-01,02,03,04,05,06,07,08,09,10,11^", 100, 1100000L, 200);
		for(SplitedLot lot:splits44) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits45 = sd11x5.split("200745-01,02,03,04,05,06,07,08,09,10,11^", 100, 3300000L, 200);
		for(SplitedLot lot:splits45) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		*/
		
		
		List<SplitedLot> splits41 = sd11x5.split("200741-01,02,03,04,05,06,07,08,09,10,11^", 100, 220000L, 200);
		for(SplitedLot lot:splits41) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		/**
		List<SplitedLot> splits42 = sd11x5.split("200742-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", 100, 2200000L, 200);
		for(SplitedLot lot:splits42) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits43 = sd11x5.split("200743-01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11;01,02,03,04,05,06,07,08,09,10,11^", 100, 19800000L, 200);
		for(SplitedLot lot:splits43) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		*/
	}
	
	
	@Test
	public void testMSplit() {
//		List<SplitedLot> splits421 = sd11x5.split("200742-01;02,03^", 100, 40000L, 200);
//		for(SplitedLot lot:splits421) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
//		}
//		
//		List<SplitedLot> splits422 = sd11x5.split("200742-01;01,02,03^", 100, 40000L, 200);
//		for(SplitedLot lot:splits422) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
//		}
		
		
//		List<SplitedLot> splits431 = sd11x5.split("200743-01;02,03;04,05^", 100, 80000L, 200);
//		for(SplitedLot lot:splits431) {
//			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
//		}
		
		List<SplitedLot> splits432 = sd11x5.split("200743-01;01,02,03;04,05^", 100, 80000L, 200);
		for(SplitedLot lot:splits432) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
 	
	
	@Test
	public void testSplitDantuo() {
		List<SplitedLot> splits2 = sd11x5.split("200722-01#02,03,04,05,06,07,08,09,10,11^", 100, 200000L, 200);
		for(SplitedLot lot:splits2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits3 = sd11x5.split("200723-01#02,03,04,05,06,07,08,09,10,11^", 100, 900000L, 200);
		for(SplitedLot lot:splits3) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits4 = sd11x5.split("200724-01#02,03,04,05,06,07,08,09,10,11^", 100, 2400000L, 200);
		for(SplitedLot lot:splits4) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits5 = sd11x5.split("200725-01#02,03,04,05,06,07,08,09,10,11^", 100, 4200000L, 200);
		for(SplitedLot lot:splits5) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits6 = sd11x5.split("200726-01#02,03,04,05,06,07,08,09,10,11^", 100, 5040000L, 200);
		for(SplitedLot lot:splits6) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits7 = sd11x5.split("200727-01#02,03,04,05,06,07,08,09,10,11^", 100, 4200000L, 200);
		for(SplitedLot lot:splits7) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits8 = sd11x5.split("200728-01#02,03,04,05,06,07,08,09,10,11^", 100, 2400000L, 200);
		for(SplitedLot lot:splits8) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		
		
		
		List<SplitedLot> splits54 = sd11x5.split("200754-01#02,03,04,05,06,07,08,09,10,11^", 100, 200000L, 200);
		for(SplitedLot lot:splits54) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		List<SplitedLot> splits55 = sd11x5.split("200755-01#02,03,04,05,06,07,08,09,10,11^", 100, 900000L, 200);
		for(SplitedLot lot:splits55) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
	}
	
	
	HashMap<String, Long> awardInfo = new HashMap<String, Long>();
	{
		awardInfo.put("R2", 600L);
		awardInfo.put("R3", 1900L);
		awardInfo.put("R4", 7800L);
		awardInfo.put("R5", 54000L);
		awardInfo.put("R6", 9000L);
		awardInfo.put("R7", 2600L);
		awardInfo.put("R8", 900L);
		awardInfo.put("Z2", 6500L);
		awardInfo.put("Z3", 19500L);
		awardInfo.put("Q1", 1300L);
		awardInfo.put("Q2", 13000L);
		awardInfo.put("Q3", 117000L);
	}
	
	
	
	
	@Test
	public void testPrizeSingle() {
		Assert.assertEquals(600L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200702-01,02^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1900L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200703-01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(7800L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200704-01,02,03,04^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(54000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200705-01,02,03,04,05^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(9000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200706-01,02,03,04,05,06^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2600L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200707-01,02,03,04,05,06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(900L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200708-01,02,03,04,05,06,07,08^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(6500L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200734-01,02^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(19500L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200735-01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1300L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200731-01^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(13000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200732-01,02^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(117000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200733-01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		
		
		Assert.assertEquals(600L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200702-01,02^02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1900L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200703-01,02,03^01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(7800L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200704-01,02,03,04^01,02,03,04^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(54000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200705-01,02,03,04,05^01,02,03,04,05^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(9000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200706-01,02,03,04,05,06^01,02,03,04,05,06^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2600L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200707-01,02,03,04,05,06,07^01,02,03,04,05,06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(900L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200708-01,02,03,04,05,06,07,08^01,02,03,04,05,06,07,08^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		
		Assert.assertEquals(6500L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200734-01,02^01,02^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(19500L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200735-01,02,03^01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1300L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200731-01^01^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(13000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200732-01,02^01,02^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(117000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200733-01,02,03^01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		
	}
	
	
	@Test
	public void testPrizeMulti() {
		Assert.assertEquals(1800L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200712-01,02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1900L*4,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200713-01,02,03,04^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(7800L*5,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200714-01,02,03,04,05^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(54000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200715-01,02,03,04,05,06^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(9000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200716-01,02,03,04,05,06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2600L*3,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200717-01,02,03,04,05,06,07,08^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(900L*4,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200718-01,02,03,04,05,06,07,08,09^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
		Assert.assertEquals(6500L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200744-01,02^", "01,02,03,04,05", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(19500L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200745-01,02,03^", "01,02,03,04,05", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1300L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200741-01^", "01,02,03,04,05", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(13000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200742-01,02^", "01,02,03,04,05", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(117000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200743-01,02,03^", "01,02,03,04,05", new BigDecimal(2),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		
	}
	
	
	@Test
	public void testPrizeDantuo() {
		Assert.assertEquals(600L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200722-01#02,03^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(1900L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200723-01,02#03,04^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(7800L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200724-01,02,03#04,05^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(54000L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200725-01,02,03,04#05,06^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(9000L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200726-01,02,03,04,05#06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(2600L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200727-01,02,03,04,05,06#07,08^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(900L*2,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200728-01,02,03,04,05,06,07#08,09^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(6500L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200754-01#02,03,04,05,06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
		Assert.assertEquals(19500L,sd11x5.caculatePrizeAmt(sd11x5.getPrizeLevelInfo("200755-01,02#03,04,05,06,07^", "01,02,03,04,05", new BigDecimal(1),oneAmount),awardInfo,new BigDecimal(200)).getAfterTaxAmt().longValue());
	
	}
	
 }
