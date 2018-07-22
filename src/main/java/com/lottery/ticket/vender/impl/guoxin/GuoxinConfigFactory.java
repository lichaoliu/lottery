package com.lottery.ticket.vender.impl.guoxin;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;

import org.springframework.stereotype.Component;



@Component
public class GuoxinConfigFactory extends AbstractVenderConfigFactory {
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_GX;
	}
	
	
}
