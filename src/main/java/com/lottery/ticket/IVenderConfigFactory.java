package com.lottery.ticket;

import java.util.List;

import com.lottery.core.domain.terminal.TerminalProperty;


/**
 * 获得出票商的config配置
 * */
public interface IVenderConfigFactory {

	
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList);
}
