package com.lottery.ticket.vender.impl.huay;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Component
public class HuayConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_HY;
	}
	

}
