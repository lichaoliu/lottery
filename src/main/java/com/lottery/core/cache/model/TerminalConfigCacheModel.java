package com.lottery.core.cache.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.service.TerminalConfigService;

/**
 * @author fengqinyun
 */
@Service
public class TerminalConfigCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Long, TerminalConfig> {
    @Autowired
    private TerminalConfigService terminalConfigService;
    protected Map<Long, TerminalConfig> cachedTerminalConfigMap = new ConcurrentHashMap<Long, TerminalConfig>();

    @Override
    protected TerminalConfig getFromCacheWithoutTimeout(Long key) throws CacheNotFoundException {
        if (!this.cachedTerminalConfigMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的TerminalConfig, key=" + key);
        }
        return this.cachedTerminalConfigMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(Long key, TerminalConfig value) throws CacheUpdateException {
        if (value == null) {
            // ConcurrentHashMap不允许保存null值
          //  throw new CacheUpdateException("终端配置信息为空，不允许保存，key=" + key);
        	value=new TerminalConfig();
        }
        this.cachedTerminalConfigMap.put(key, value);
    }

    public void remove(Long key){
        super.remove(key);
        cachedTerminalConfigMap.remove(key);
    }


    @Override
    protected TerminalConfig getFromSource(Long key) throws Exception {
    	TerminalConfig source=getFromMC(key);
    	if(source==null){
    		source=terminalConfigService.get(key);
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return source;
    }

}
