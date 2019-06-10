package com.lottery.ticket.vender.impl.xcs;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Service
public class XCSConfigFactory extends AbstractVenderConfigFactory{

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_XCS;	}

	@Override
	protected void init() {
		configFactoryMap.put(TerminalType.T_XCS, this);		
	}

}
