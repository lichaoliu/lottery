package com.lottery.ticket.vender.impl.tianjinfc;

import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Service
public class TianJinFXConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_TJFC;
	}
	@Override
	protected void init(){
		configFactoryMap.put(TerminalType.T_TJFC, this);
	}

}
