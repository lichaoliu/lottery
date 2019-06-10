package com.lottery.test.lottype;



import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.GuangXikl10;
import com.lottery.lottype.SplitedLot;


public class Guangxikl10Test {

	GuangXikl10 happy = new GuangXikl10();
	
	int oneAmount = 200;
	
	
	@Test
	public void testValidateS() {
		
		
		
		//任选二
		Assert.assertEquals(true, happy.validate("110506-01^02^",new BigDecimal(400), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("110502-01,02^",new BigDecimal(200), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("110502-01,03^",new BigDecimal(200), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("110502-01,02^",new BigDecimal(400), new BigDecimal(2),200));
//		Assert.assertEquals(true, happy.validate("110502-01,03^",new BigDecimal(20000), new BigDecimal(100),200));
//		
//		Assert.assertEquals(true, happy.validate("110502-01,02^01,02^",new BigDecimal(200*2), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("110502-01,03^01,03^",new BigDecimal(200*2), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("110502-01,02^01,02^",new BigDecimal(400*2), new BigDecimal(2),200));
//		Assert.assertEquals(true, happy.validate("110502-01,03^01,03^",new BigDecimal(20000*2), new BigDecimal(100),200));
		
		
		
	}
	
	
	@Test
	public void testValidateM() {
		//数投
		Assert.assertEquals(true, happy.validate("100421-12,13,14^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100421-15,13,14^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100421-12,13,14^",new BigDecimal(1200), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100421-15,13,14^",new BigDecimal(60000), new BigDecimal(100),200));
		
		try {
			happy.validate("100421-19,13,14^",new BigDecimal(600), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		
		Assert.assertEquals(true, happy.validate("100422-12,13,14^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100422-15,13,14^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100422-12,13,14^",new BigDecimal(1200), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100422-15,13,14^",new BigDecimal(60000), new BigDecimal(100),200));
		
		try {
			happy.validate("100422-01,01,03^",new BigDecimal(600), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100423-12,13,14,15^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100423-15,13,14,11^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100423-12,13,14,11^",new BigDecimal(1600), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100423-15,13,14,11^",new BigDecimal(80000), new BigDecimal(100),200));
		
		try {
			happy.validate("100423-01,01,03,04^",new BigDecimal(600), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100424-12,13,14,15,16^",new BigDecimal(1000), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100424-15,13,14,11,16^",new BigDecimal(1000), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100424-12,13,14,11,16^",new BigDecimal(2000), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100424-15,13,14,11,16^",new BigDecimal(100000), new BigDecimal(100),200));
		
		try {
			happy.validate("100424-01,01,03,04,16^",new BigDecimal(1000), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100425-01,02,03,04,05,06^",new BigDecimal(1200), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100425-01,02,03,04,05,06^",new BigDecimal(1200), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100425-01,02,03,04,05,06^",new BigDecimal(2400), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100425-01,02,03,04,05,06^",new BigDecimal(120000), new BigDecimal(100),200));
		
		try {
			happy.validate("100425-01,01,03,04,05,06^",new BigDecimal(1200), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100428-01,02,03^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100428-01,02,03^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100428-01,02,03^",new BigDecimal(1200), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100428-01,02,03^",new BigDecimal(60000), new BigDecimal(100),200));
		
		try {
			happy.validate("100428-01,01,03^",new BigDecimal(600), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100429-01,02,03,04^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100429-01,02,03,04^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100429-01,02,03,04^",new BigDecimal(1600), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100429-01,02,03,04^",new BigDecimal(80000), new BigDecimal(100),200));
		
		try {
			happy.validate("100429-01,02,03,04^",new BigDecimal(800), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100446-01-02,03^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100446-01-02,03^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100446-01-02,03^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100446-01-02,03^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100446-01-02^",new BigDecimal(200), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		Assert.assertEquals(true, happy.validate("100447-01-02-03,04^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100447-01-02-03,04^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100447-01-02-03,04^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100447-01-02-03,04^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100447-01-02-03^",new BigDecimal(200), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
	}
	
	
	@Test
	public void test2() {
		Assert.assertEquals(true, happy.validate("100446-01,02-02,03^",new BigDecimal(600), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100446-01,02-01,02^",new BigDecimal(400), new BigDecimal(1),200));
		
	}
	
	@Test
	public void test3() {
		Assert.assertEquals(true, happy.validate("100447-01,02-02,03-03,04^",new BigDecimal(800), new BigDecimal(1),200));
		
	}
	
	
	
	@Test
	public void testValidateD() {

		Assert.assertEquals(true, happy.validate("100432-12#13,14^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100432-15#13,14^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100432-12#13,14^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100432-15#13,14^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100432-01#01,03^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100433-12,13#14,15^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100433-15,13#14,11^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100433-12,13#14,11^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100433-15,13#14,11^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100433-01,01,03,04^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100434-12,13,14#15,16^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100434-15,13,14#11,16^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100434-12,13,14#11,16^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100434-15,13,14#11,16^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100434-01,01,03#04,16^",new BigDecimal(1000), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100435-01,02,03,04#05,06^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100435-01,02,03,04#05,06^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100435-01,02,03,04#05,06^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100435-01,02,03,04#05,06^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100435-01,01,03,04#05,06^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100438-01#02,03^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100438-01#02,03^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100438-01#02,03^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100438-01#02,03^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100438-01#01,03^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
		Assert.assertEquals(true, happy.validate("100439-01,02#03,04^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100439-01,02#03,04^",new BigDecimal(400), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100439-01,02#03,04^",new BigDecimal(800), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100439-01,02#03,04^",new BigDecimal(40000), new BigDecimal(100),200));
		
		try {
			happy.validate("100439-01,02#03,04^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		Assert.assertEquals(true, happy.validate("100436-01#03,04^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100436-01#03,04^",new BigDecimal(800), new BigDecimal(1),200));
		Assert.assertEquals(true, happy.validate("100436-01#03,04^",new BigDecimal(1600), new BigDecimal(2),200));
		Assert.assertEquals(true, happy.validate("100436-01#03,04^",new BigDecimal(80000), new BigDecimal(100),200));
		Assert.assertEquals(true, happy.validate("100436-01#03^",new BigDecimal(400), new BigDecimal(1),200));
		
		try {
			happy.validate("100436-01#03^",new BigDecimal(400), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
		
		
//		Assert.assertEquals(true, happy.validate("100437-01#02,03,04^",new BigDecimal(3600), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("100437-01#02,03,04^",new BigDecimal(3600), new BigDecimal(1),200));
//		Assert.assertEquals(true, happy.validate("100437-01#02,03,04^",new BigDecimal(7200), new BigDecimal(2),200));
//		Assert.assertEquals(true, happy.validate("100437-01#02,03,04^",new BigDecimal(360000), new BigDecimal(100),200));
		Assert.assertEquals(true, happy.validate("100437-01#02,03^",new BigDecimal(1200), new BigDecimal(1),200));
		
		try {
			happy.validate("100437-01#02,03^",new BigDecimal(1200), new BigDecimal(1),200);
		}catch(LotteryException e) {
			Assert.assertEquals(ErrorCode.betamount_error, e.getErrorCode());
		}
	}
	
	
	@Test
	public void testPrizeS() {
		//选红
		Assert.assertEquals(500, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100410-19^", "19,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100410-19^", "19,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue());
		Assert.assertEquals(3000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100410-19^20^19^", "19,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100410-20^", "19,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		//数投
		Assert.assertEquals(2500, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100411-12^", "12,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(5000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100411-12^", "12,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(10000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100411-12^13^12^", "12,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100411-12^", "19,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		//任选二
		Assert.assertEquals(800, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100412-01,02^", "02,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1600, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100412-01,02^", "02,13,09,14,07,20,01,10", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1600, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100412-01,02^01,03^01,02^", "02,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100412-01,03^", "02,13,09,14,07,20,01,10", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		//任选三
		Assert.assertEquals(2400, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100413-01,02,03^", "02,13,09,14,07,20,01,03", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(4800, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100413-01,02,03^", "02,13,09,14,07,20,01,03", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(4800, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100413-01,02,03^01,02,04^01,02,03^", "02,13,09,14,07,20,01,03", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100413-01,02,04^", "02,13,09,14,07,20,01,03", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		//任选四
		Assert.assertEquals(8000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100414-01,02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(16000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100414-01,02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(16000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100414-01,02,03,04^01,02,03,09^01,02,03,04^", "01,02,03,04,05,06,07,088", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100414-01,02,03,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		//任选五
		Assert.assertEquals(32000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100415-01,02,03,04,05^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(64000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100415-01,02,03,04,05^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(64000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100415-01,02,03,04,05^01,02,03,04,05^01,02,03,04,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100415-01,02,03,04,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		//前二
		Assert.assertEquals(6200, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100416-01,02^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(12400, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100416-03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(12400, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100416-01,02^05,06^01,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100416-01,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		//前三
		Assert.assertEquals(800000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100417-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1600000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100417-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(1600000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100417-01,02,03^01,02,04^01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100417-01,02,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		//组二
		Assert.assertEquals(3100, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100418-01,02^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(6200, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100418-02,01^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(6200, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100418-01,02^02,01^01,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100418-01,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		//组三
		Assert.assertEquals(130000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100419-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(260000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100419-02,03,01^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(260000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100419-01,02,03^02,03,01^01,02,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100419-01,02,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
	}
	
	
	@Test
	public void testPrizeM() {
		//数投
		Assert.assertEquals(2500, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100421-01,13,14^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(5000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100421-01,13,14^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100421-15,13,14^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(2400, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100422-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(4800, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100422-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100422-12,13,14^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(2400*4, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100423-01,02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(2400*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100423-01,02,03,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100423-01,02,14,15^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(true, happy.validate("100424-12,13,14,15,16^",new BigDecimal(1000), new BigDecimal(1),200));
		Assert.assertEquals(8000*5, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100424-01,02,03,04,05^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(8000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100424-01,02,03,04,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100424-01,02,03,09,10^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(32000*6, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100425-01,02,03,04,05,06^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(32000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100425-01,02,03,04,05,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100425-01,02,03,04,09,10^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(3100*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100428-01,02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(3100*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100428-01,02,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100428-01,04,06^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(130000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100429-01,02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(260000, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100429-01,02,04,03^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100429-01,02,05,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(6200*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100446-01,04-02,05^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(6200*4, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100446-01,04-02,05^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100446-01-04,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(800000*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100447-01-02,04-03,05^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(800000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100447-01-02,04-03,05^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100447-01-02-05,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
	}
	
	
	
	@Test
	public void testPrizeD() {
		Assert.assertEquals(800, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100432-01#02,14^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(800*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100432-01#02,14^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100432-12#02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(2400*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100433-01,02#03,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(2400*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100433-01,02#03,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100433-01,09#03,02^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(8000*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100434-01,02,03#04,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(8000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100434-01,02,03#04,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100434-01,02,09#04,05^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(32000*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100435-01,02,03,04#05,09^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(32000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100435-01,02,03,04#05,09^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100435-01,02,03,09#05,06^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(3100*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100438-01#02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(3100*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100438-01#02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100438-05#02,03^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(130000*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100439-01,02#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(130000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100439-01,02#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100439-01,05#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		Assert.assertEquals(6200*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100436-02#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(6200*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100436-02#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100436-01#03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
		
		
		Assert.assertEquals(800000*1, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100437-01#02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(800000*2, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100437-01#02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(2),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		Assert.assertEquals(0, happy.caculatePrizeAmt(happy.getPrizeLevelInfo("100437-05#02,03,04^", "01,02,03,04,05,06,07,08", new BigDecimal(1),oneAmount), happy.getAwardLevels(1004,""),null).getAfterTaxAmt().longValue()) ;
		
	}
	
	
	
	
	@Test
	public void testPrizeD2() {
		System.out.println(happy.getPrizeLevelInfo("100412-05,01^05,04^03,05^04,06^05,07^05,02^03,05^05,09^", "01,02,03,04,05,06,07,08", BigDecimal.ONE,oneAmount));
	}
	
	
	
	
	@Test
	public void testSplitS() {
	
		//任选二
		happy.split("110502-01,02^", 200, 200*200, 200);
		happy.split("110502-01,02^01,02^", 200, 400*200, 200);
		
		
	}
	
	
	@Test
	public void testSplitM() {
		happy.split("100421-12,13,14^", 200, 600*200, 200);
		
		happy.split("100422-12,13,14^", 200, 600*200, 200);
		
		happy.split("100423-12,13,14,15^", 200, 800*200, 200);
		
		happy.split("100424-12,13,14,15,16^", 200, 1000*200, 200);
		
		happy.split("100425-01,02,03,04,05,06^", 200, 1200*200, 200);
		
		happy.split("100428-01,02,03^", 200, 600*200, 200);
		
		happy.split("100429-01,02,03,04^", 200, 800*200, 200);
		
		happy.split("100446-01-02,03^", 200, 400*200, 200);
		
		happy.split("100447-01-02-03,04^", 200, 400*200, 200);
	}
	
	
	
	@Test
	public void testSplitD() {

		happy.split("100432-12#13,14^", 200, 400*200, 200);
		
		happy.split("100433-12,13#14,15^", 200, 400*200, 200);
		
		happy.split("100434-12,13,14#15,16^", 200, 400*200, 200);
		
		happy.split("100435-01,02,03,04#05,06^", 200, 400*200, 200);
		
		happy.split("100438-01#02,03^", 200, 400*200, 200);
		
		happy.split("100439-01,02#03,04^", 200, 400*200, 200);
		
		happy.split("100436-01#03,04^", 200, 800*200, 200);
		
		happy.split("100437-01#02,03,04^", 200, 3600*200, 200);
		
		List<SplitedLot> split = happy.split("100436-01#03^", 1, 400, 200);
		
		for(SplitedLot lot:split) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
		
		
		List<SplitedLot> split2 = happy.split("100437-01#02,03^", 1, 1200, 200);
		
		for(SplitedLot lot:split2) {
			System.out.println(lot.getBetcode()+" "+lot.getLotMulti()+" "+lot.getAmt());
		}
	}
	
	
	@Test
	public void testSplitS5() {
		List<SplitedLot> split = happy.split("100411-12^01^02^03^04^05", 1, 1200*1, 200);
		
		for(SplitedLot s:split) {
			System.out.println(s.getBetcode()+" "+s.getLotMulti()+" "+s.getAmt());
		}
	}
	
	
	@Test
	public void testSplit2() {
		List<SplitedLot> list = happy.split("100447-01,02-02,03-03,04^", 1, 800, 200);
		for(SplitedLot s:list) {
			System.out.println(s.getBetcode());
		}
	}
	
}
