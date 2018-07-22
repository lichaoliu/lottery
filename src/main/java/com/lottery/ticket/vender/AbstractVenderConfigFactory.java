package com.lottery.ticket.vender;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.TerminalPropertyConstant;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConfigFactory;
import com.lottery.ticket.vender.impl.BaseConfig;


public abstract class AbstractVenderConfigFactory implements IVenderConfigFactory{
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Resource(name="configFactoryMap")
	protected Map<TerminalType,IVenderConfigFactory> configFactoryMap;
	protected abstract TerminalType getTerminalType();
	public IVenderConfig getVenderConfig(List<TerminalProperty> terminalPropertyList){
		
				if(terminalPropertyList!=null&&terminalPropertyList.size()>0){
					BaseConfig config=new BaseConfig();
			        try{
			    		for(TerminalProperty property:terminalPropertyList){
			    			configCommon(config, property);
			    			if(config==null){
			    				break;
			    			}
						}
						return config;
			        }catch(Exception e){
			        	logger.error("获取配置出错",e);
			        	return null;
			        }
				}
				return null;	
		
		
	}
	
	protected void configCommon(AbstractVenderConfig config,TerminalProperty property){
		config.setTerminalId(property.getTerminalId());
		if(StringUtils.isBlank(property.getTerminalKey())){
			logger.error("获取到的terminalProperty中的key为null");
			config=null;
			return;
		}
		if(TerminalPropertyConstant.URL.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setRequestUrl(property.getTerminalValue().trim());
			
		}
		if(TerminalPropertyConstant.PORT.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setPort(Integer.valueOf(property.getTerminalValue().trim()));
			
		}
		if(TerminalPropertyConstant.KEY.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setKey(property.getTerminalValue().trim());
			
		}
		if(TerminalPropertyConstant.PUBLIC_KEY.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setPublicKey(property.getTerminalValue().trim());
			
		}
		if(TerminalPropertyConstant.PRIVATE_KEY.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setPrivateKey(property.getTerminalValue().trim());
			
		}
		if(TerminalPropertyConstant.SEND_COUNT.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());

				return;
			}
			config.setSendCount(Integer.valueOf(property.getTerminalValue().trim()));
			
		}
		if(TerminalPropertyConstant.CHECK_COUNT.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue().trim())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());

				return;
			}
			config.setCheckCount(Integer.valueOf(property.getTerminalValue()));
			
		}
		if(TerminalPropertyConstant.TIME_SECOND.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());

				return;
			}
			config.setTimeOutSecondForCheck(Integer.valueOf(property.getTerminalValue().trim()));
			
		}
		if(TerminalPropertyConstant.AGENT_CODE.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setAgentCode(property.getTerminalValue().trim());
			
		}
		if(TerminalPropertyConstant.PASSWD.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				config=null;
				return;
			}
			config.setPasswd(property.getTerminalValue().trim());
		
		}
		if(TerminalPropertyConstant.CHECK_URL.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				return;
			}
			config.setCheckUrl(property.getTerminalValue().trim());
	
		}

		if(TerminalPropertyConstant.SYNC_CHECK.equals(property.getTerminalKey())){
			if(StringUtils.isBlank(property.getTerminalValue())){
				logger.error("TerminalProperty中key={}的值为空",property.getTerminalKey());
				return;
			}
			if("false".equals(property.getTerminalValue().trim())){
				config.setSyncTicketCheck(Boolean.FALSE);
			}else {
				config.setSyncTicketCheck(Boolean.TRUE);
			}


		}
	}

	@PostConstruct
   	protected void init(){
		configFactoryMap.put(getTerminalType(), this);
	}
}
