package com.lottery.test.data;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.core.service.account.UserRebateInstantService;
import com.lottery.test.SpringBeanTest;

public class RebateTest extends SpringBeanTest{
@Autowired
	protected UserRebateInstantService us;
    @Test
	public void test(){
		//us.betRebate("TY201411130000063486");
		us.hemaiRebate("201411130000000433");
	}
}
