package com.lottery.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;
import com.lottery.test.SpringBeanTest;

public class JczqRaceServiceTest extends SpringBeanTest {
	
	@Autowired
	private JczqRaceService jrs;
	
	@Test
	public void test(){
		List<JczqRace> list = jrs.getJczqResult("2014-6-6");
		System.out.println(list.size());
	}

}
