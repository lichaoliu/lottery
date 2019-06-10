package com.lottery.ticket.vender.impl.ruiying;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;

@Component
public class RuiYingConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected TerminalType getTerminalType() {
		return TerminalType.T_RUIYING;
	}

	

	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if (terminalPropertyList != null && terminalPropertyList.size() > 0) {
			RuiYingConfig config = new RuiYingConfig();
			try {
				for (TerminalProperty property : terminalPropertyList) {
					configCommon(config, property);
				}
				return config;
			} catch (Exception e) {
				logger.error("获取配置出错",e);
				return null;
			}
		}
		return null;
	}

}
