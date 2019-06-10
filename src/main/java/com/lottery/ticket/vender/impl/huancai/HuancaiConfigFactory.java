package com.lottery.ticket.vender.impl.huancai;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;

import org.springframework.stereotype.Component;

@Component
public class HuancaiConfigFactory extends AbstractVenderConfigFactory {
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_HUANCAI;
	}
	
	
}
