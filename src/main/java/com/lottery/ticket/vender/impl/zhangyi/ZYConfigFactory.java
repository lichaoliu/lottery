package com.lottery.ticket.vender.impl.zhangyi;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Component
public class ZYConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_ZY;
	}
	

}
