package com.lottery.ticket.vender.impl.zch;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ZchConfigFactory extends AbstractVenderConfigFactory {

	private static String JC_URL="jc_url";
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_FCBY;
	}
	@Override
	protected void init(){
		configFactoryMap.put(TerminalType.T_ZCH, this);
	}
	
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
			ZchConfig config=new ZchConfig();
	        try{
	    		for(TerminalProperty property:terminalPropertyList){
	    			if(JC_URL.equals(property.getTerminalKey())){
	    				if(StringUtils.isBlank(property.getTerminalValue())){
	    					logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
	    					config=null;
	    					break;
	    				}
	    				config.setJcUrl(property.getTerminalValue());
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
