package com.lottery.ticket.vender.impl.yinxi;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Service
public class YinxiConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_YX;
	}
	@PostConstruct
	protected void init(){
		configFactoryMap.put(TerminalType.T_YX, this);
	}

}
