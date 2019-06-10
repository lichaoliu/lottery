package com.lottery.test.chase;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.ChaseType;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.test.SpringBeanTest;

public class ChaseBuyTest extends SpringBeanTest {
	@Autowired
	private LotteryChaseBuyService lotteryChaseBuyService;
	@Test
	public void testchase(){
		List<LotteryChaseBuy> chaseBuys = lotteryChaseBuyService.getByLotteryTypePhaseChaseType(2007, "14121634", ChaseType.normal_end.value);
	    System.out.println(chaseBuys.size());
	}
}
