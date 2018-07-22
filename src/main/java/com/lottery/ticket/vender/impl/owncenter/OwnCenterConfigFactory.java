package com.lottery.ticket.vender.impl.owncenter;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;


@Component
public class OwnCenterConfigFactory extends AbstractVenderConfigFactory {
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_OWN_CENTER;
	}
	
	
}
