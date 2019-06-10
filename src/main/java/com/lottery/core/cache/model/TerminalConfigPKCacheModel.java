package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.service.TerminalConfigService;

@Service
public class TerminalConfigPKCacheModel extends AbstractKeyValueCacheWithTimeoutModel<TerminalConfigCachePK, TerminalConfig> {
	@Autowired
	private TerminalConfigService terminalConfigService;

	protected Map<TerminalConfigCachePK, TerminalConfig> cachedTerminalConfigPKMap = new ConcurrentHashMap<TerminalConfigCachePK, TerminalConfig>();

	@Override
	protected TerminalConfig getFromCacheWithoutTimeout(TerminalConfigCachePK key) throws CacheNotFoundException {
		 if (!this.cachedTerminalConfigPKMap.containsKey(key)) {
	            throw new CacheNotFoundException("缓存中未找到对应的TerminalConfig, key=" + key);
	        }
	        return this.cachedTerminalConfigPKMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(TerminalConfigCachePK key, TerminalConfig value) throws CacheUpdateException {
	    if (value == null) {
          
            throw new CacheUpdateException("终端配置信息为空，不允许保存，key=" + key);
        }
        this.cachedTerminalConfigPKMap.put(key, value);

	}

	@Override
	protected TerminalConfig getFromSource(TerminalConfigCachePK key) throws Exception {
		TerminalConfig source=getFromMC(key);
    	if(source==null){
    		source=terminalConfigService.get(key.getLotteryType(), key.getTerminalId(), key.getPlayType());
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return source; 
	
		
	}

}
