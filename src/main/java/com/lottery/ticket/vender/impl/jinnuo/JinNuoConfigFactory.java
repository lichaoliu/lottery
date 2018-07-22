package com.lottery.ticket.vender.impl.jinnuo;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
@Component
public class JinNuoConfigFactory extends AbstractVenderConfigFactory {

	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_JINRUO;
	}
	
	
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
			JinNuoConfig config=new JinNuoConfig();
	        try{
	    		for(TerminalProperty property:terminalPropertyList){
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
