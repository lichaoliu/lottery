package com.lottery.test.terminal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.handler.PrizeErrorHandler;
import com.lottery.core.service.TerminalService;
import com.lottery.test.SpringBeanTest;

public class TerminalTest extends SpringBeanTest {
	@Autowired
    TerminalService ts;
	@Autowired
	protected PrizeErrorHandler handler;
	@Test
	public void testTerminal(){
		Terminal t=new Terminal();
		t.setIsEnabled(YesNoStatus.yes.getValue());
		t.setIsPaused(YesNoStatus.yes.getValue());
		t.setTerminalType(TerminalType.T_TJFC.getValue());
		t.setName(TerminalType.T_TJFC.getName());
		handler.changeTerminalId(2l, 1l, 1001, 0);
		
	}
}
