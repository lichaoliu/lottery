package com.lottery.test.chase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.service.DcRaceService;
import com.lottery.core.service.LotteryCaseLotService;
import com.lottery.core.service.PrizeService;
import com.lottery.test.SpringBeanTest;
import com.lottery.ticket.phase.thread.AsyncDcPhaseRunnable;
import com.lottery.ticket.phase.thread.AsyncJclqPhaseRunnable;
import com.lottery.ticket.vender.process.huay.GetDcPhaseFromHuay;
import com.lottery.ticket.vender.process.huay.HuayInternalDcService;

public class LotteryOrderChaseTest extends SpringBeanTest {

	
	@Autowired
	GetDcPhaseFromHuay dcPhaseFromHuay;
	//@Autowired
	HuayInternalDcService  huayInternalDcService;
	@Autowired
	IdGeneratorService igenGeneratorService;
	@Autowired
	AsyncDcPhaseRunnable asyncDcPhaseRunnable;
	@Autowired
	DcRaceService dcRaceService;
	
	@Test
	public void chaseTest(){
		//DcRace dcRace= dcRaceService.getByPhaseAndNum("140508", 79);
		//System.out.println(dcRace);
		//System.out.println(1);
		/*String[] phases = new String[]{
				"140401","140402","140403","140404",
				"140405","140406","140407","140408",
				"140409","140410","140411","140412","140413",
				"140414","140415","140416","140417",
				"140501","140502","140503","140504","140505","140506","140507"};
		for (String string : phases) {
			asyncDcPhaseRunnable.match(LotteryType.DC_SPF, string);
			asyncDcPhaseRunnable.proccessResult(string);
		}
		*/
		asyncDcPhaseRunnable.match(LotteryType.DC_SPF, "140509");
		//String messageid = igenGeneratorService.getMessageId();
		//System.out.println(messageid);
		//System.out.println(huayInternalDcService.getBjdcPhase(null));
		//List<LotteryOrderChase> chaseList=cd.getByLotteryTypeAndPhaseStatus(1004, "20140323039", ChaseType.normal_end.value, CommonStatus.undo.getValue());
	}
	
	//@Autowired
	//UserAchievementService achievementService;
	
	
	
	
	@Autowired
	PrizeService prizeService;
	@Test
	public void achievementTest(){
		System.out.println(prizeService);
		//prizeService.sendCaselotOrderEncaseEnd("123");
		try {
			Thread.sleep(200000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(caseLotListener);
		
		//caseLotListener.effectiveAchievement("TY201405220000000020");
		//caseLotListener.ineffectiveAchievement(1001, "2014001", "");
	}
	
	@Autowired
	LotteryCaseLotService lotteryCaseLotService;
	

	
	@Test
	public void cancelTest(){

		try {
			Thread.sleep(200000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//lotteryCaseLotService.cancelEncaseHemai("201405230000000008");
		//caseLotListener.onOrderEncash("ZT201405230000000008");
	}
	

	
	@Autowired
	LotteryOrderDAO lotteryOrderDAO;
	@Test
	public void  testModifyTime(){
		List<Integer> lotteryType = new ArrayList<Integer>();
		lotteryType.add(LotteryType.JCZQ_BF.value);
		//lotteryOrderDAO.getByMatchNums(lotteryType, "20140421", "20140421051");
		//prizeErrorHandler.modifyTime(LotteryType.DC_SF.value, "1", "1", false);
	}
}
