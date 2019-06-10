package com.lottery.ticket.vender.impl.gaode;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;

@Component
public class GaoDeConfigFactory extends AbstractVenderConfigFactory {
	private String DC_URL="dc_url";
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_GAODE;
	}
	
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
			GaodeConfig config=new GaodeConfig();
			try{
				for(TerminalProperty property:terminalPropertyList){
					if(DC_URL.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setDcUrl(property.getTerminalValue());
	    			}
					configCommon(config, property);
				}
				return config;
			}catch(Exception e){
				logger.error("获取配置出错",e);
				return null;
			}
		}
		return null;
	}
}
