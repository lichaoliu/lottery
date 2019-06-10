package com.lottery.ticket.vender.impl.fcby;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Service
public class FcbyConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_FCBY;
	}
	@PostConstruct
	protected void init(){
		configFactoryMap.put(TerminalType.T_FCBY, this);
	}

}
