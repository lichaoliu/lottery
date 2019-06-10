package com.lottery.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, 
		DirtiesContextTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath*:/META-INF/spring/applicationContext.xml","classpath*:/META-INF/spring/applicationContext-check.xml"})

public class SpringBeanTest{

}
