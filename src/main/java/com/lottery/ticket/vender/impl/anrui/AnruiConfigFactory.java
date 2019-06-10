package com.lottery.ticket.vender.impl.anrui;

import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.vender.AbstractVenderConfigFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnruiConfigFactory extends AbstractVenderConfigFactory {
	public  static  String JCZQ_MATCH="jczq_match";
	public  static  String JCLQ_MATCH="jclq_match";
	@Override
	protected	TerminalType getTerminalType() {
		return TerminalType.T_ARUI;
	}
	@Override
	protected void init(){
		configFactoryMap.put(TerminalType.T_ARUI, this);
	}
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
			AnRuiConfig config=new AnRuiConfig();
			try{
				for(TerminalProperty property:terminalPropertyList){
					if(JCZQ_MATCH.equals(property.getTerminalKey())){
						if(StringUtils.isBlank(property.getTerminalValue())) {
							logger.error("TerminalProperty中key={}的值为空", property.getTerminalKey());
							config = null;
							break;
						}
						config.setJczqMatch(property.getTerminalValue());
					}
					if(JCLQ_MATCH.equals(property.getTerminalKey())){
						if(StringUtils.isBlank(property.getTerminalValue())) {
							logger.error("TerminalProperty中key={}的值为空", property.getTerminalKey());
							config = null;
							break;
						}
						config.setJclqMatch(property.getTerminalValue());
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
