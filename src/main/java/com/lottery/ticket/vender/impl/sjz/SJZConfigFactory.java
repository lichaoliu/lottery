package com.lottery.ticket.vender.impl.sjz;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;


@Component
public class SJZConfigFactory extends AbstractVenderConfigFactory {
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_SJZ;
	}
	
	
}
