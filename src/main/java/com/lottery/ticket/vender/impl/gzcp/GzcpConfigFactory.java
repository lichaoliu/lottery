package com.lottery.ticket.vender.impl.gzcp;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;


@Component
public class GzcpConfigFactory extends AbstractVenderConfigFactory {
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_GZCP;
	}
	
	
}
