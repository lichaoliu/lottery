package com.lottery.core.handler;

import com.lottery.common.contains.EnabledStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.cache.model.TerminalConfigCacheModel;
import com.lottery.core.cache.model.TerminalConfigCachePK;
import com.lottery.core.cache.model.TerminalConfigPKCacheModel;
import com.lottery.core.cache.model.TerminalTypeTerminalCacheModel;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.service.TerminalConfigService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.ticket.IVenderConfig;
import com.lottery.ticket.IVenderConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@Service
public class VenderConfigHandler {
	protected final Logger logger=LoggerFactory.getLogger(getClass());
	@Resource(name="configFactoryMap")
	private Map<TerminalType,IVenderConfigFactory> configFactoryMap;
	@Autowired
	private ITerminalSelector terminalSelector;
	@Autowired
	private TerminalTypeTerminalCacheModel terminalTypeTerminalCacheModel;
	
	 @Autowired
	protected TerminalConfigPKCacheModel pkTerminalConfigPKCacheModel;
	@Autowired
	private TerminalConfigService terminalConfigService;
	@Autowired
	private TerminalConfigCacheModel terminalConfigCacheModel;
	
	protected Lock lock = new ReentrantLock();
	/**
	 * 根据终端类型，终端id获得终端配置
	 * @param terminalType 终端类型
	 * @param terminalId 终端id
	 * */
 	public IVenderConfig getTerminalByTypeAndId(TerminalType terminalType,Long terminalId){
		IVenderConfigFactory configFactory = configFactoryMap.get(terminalType);
		if(configFactory==null){
			logger.error("{}的配置工厂不存在",terminalType);
			return null;
		}
		List<TerminalProperty> terminalPropertyList = terminalSelector.getTerminalPropertyFromCache(terminalId);
		IVenderConfig config = configFactory.getVenderConfig(terminalPropertyList);
		return config;
	}

	public IVenderConfig getVenderConfig(TerminalType terminalType,List<TerminalProperty> terminalPropertyList){
		IVenderConfigFactory configFactory = configFactoryMap.get(terminalType);
		if(configFactory==null){
			logger.error("{}的配置工厂不存在",terminalType);
			return null;
		}
		IVenderConfig config = configFactory.getVenderConfig(terminalPropertyList);
		return config;
	}


	/**
	 * 根据终端id获得终端配置
	 * @param terminalId 终端id
	 * */
 	public IVenderConfig getByTerminalId(Long terminalId){
 		Terminal terminal=terminalSelector.getTerminal(terminalId);
 		if(terminal==null){
 			logger.error("id为{}的终端不存在",terminalId);
 			return null;
 		}
 	    Integer terminalTypeId=terminal.getTerminalType();
 	    if(terminalTypeId==null){
 	   	logger.error("id为{}的终端类型不存在",terminalId);
			return null;
 	    }
 		TerminalType terminalType=TerminalType.get(terminalTypeId);
		IVenderConfigFactory configFactory = configFactoryMap.get(terminalType);
		if(configFactory==null){
			logger.error("{}的配置工厂不存在",terminalType);
			return null;
		}
		List<TerminalProperty> terminalPropertyList = terminalSelector.getTerminalPropertyFromCache(terminalId);
		IVenderConfig config = configFactory.getVenderConfig(terminalPropertyList);
		return config;
	}
 	/**
 	 * 根据终端id获取终端类型
 	 * @param terminalId 终端id
 	 * @return {@link TerminalType}
 	 * */
 	public TerminalType getTypeByTerminalId(Long terminalId){
 		Terminal terminal=terminalSelector.getTerminal(terminalId);
 		if(terminal==null){
 			logger.error("id为{}的终端不存在",terminalId);
 			return null;
 		}
 	    Integer terminalTypeId=terminal.getTerminalType();
 	    if(terminalTypeId==null){
 	   	logger.error("id为{}的终端类型不存在",terminalId);
			return null;
 	    }
 		TerminalType terminalType=TerminalType.get(terminalTypeId);
 		return terminalType;
 	}

	public Terminal getById(Long terminalId){
		return terminalSelector.getTerminal(terminalId);
	}
	
	public List<IVenderConfig> getAllByTerminalType(TerminalType terminalType){
		List<IVenderConfig> configList=new ArrayList<IVenderConfig>();
		IVenderConfigFactory configFactory = configFactoryMap.get(terminalType);
		if(configFactory==null){
			logger.error("终端{}没有相关配置",terminalType);
			return configList;
		}
		
		Long[] ids=null;
		try {
			ids = terminalTypeTerminalCacheModel.get(terminalType);
		} catch (CacheNotFoundException e) {

		} catch (CacheUpdateException e) {

		}catch (Exception e) {
			logger.error("获取缓存出错",e); 
		}
		if(ids==null||ids.length==0){
			return configList;
		}
		for(int i=0;i<ids.length;i++){
			Long terminalId=ids[i];
			List<TerminalProperty> terminalPropertyList = terminalSelector.getTerminalPropertyFromCache(terminalId);
			IVenderConfig config = configFactory.getVenderConfig(terminalPropertyList);
			if(config!=null){
				configList.add(config);
			}
		}
		return configList;
			
	}

	

	public TerminalConfig  getTerminalConfig(int lotteryType,Long terminalId,int playType){
		TerminalConfig terminalConfig=null;
		try {
			 terminalConfig=pkTerminalConfigPKCacheModel.get(new TerminalConfigCachePK(lotteryType, terminalId, playType));
		}catch (Exception e) {
			
		}
		return terminalConfig;
	}
	
	public Terminal getTerminal(Long terminalId){
		return terminalSelector.getTerminal(terminalId);
	}



	public List<TerminalConfig> getTerminalConfigList(int lotteryType,int playType){
		try {
			return terminalSelector.getEnabledTerminalConfigList(LotteryType.get(lotteryType), PlayType.get(playType));
		}catch (Exception e){
			return null;
		}
	}


	public boolean closeTerminaConfig(Long terminalId,int lotteryType){
		try{
			lock.lock();
			List<TerminalConfig> terminalConfigList=this.getTerminalConfigList(lotteryType,PlayType.mix.value);
			if(terminalConfigList!=null&&terminalConfigList.size()>1){
				for(TerminalConfig terminalConfig:terminalConfigList){
					if (terminalConfig.getTerminalId().intValue()==terminalId.intValue()){
						terminalConfig.setIsEnabled(EnabledStatus.disabled.value);
						terminalConfigService.update(terminalConfig);
						terminalConfigCacheModel.remove(terminalConfig.getId());
						return true;
					}
				}
			}else {
				logger.error("彩种:{},只有一个出票终端({}),不能进行关闭",lotteryType,terminalId);
			}
			return false;
		}catch (Exception e){
			logger.error("关闭终端失败",e);
			logger.error("彩种:{},出票终端({}),关闭失败",lotteryType,terminalId);
			return false;
		}finally {
			lock.unlock();
		}



	}

}
