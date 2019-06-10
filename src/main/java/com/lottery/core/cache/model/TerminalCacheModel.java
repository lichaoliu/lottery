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
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.service.TerminalService;

/**
 *@author fengqinyun
 */
@Service
public class TerminalCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Long, Terminal> {

	@Autowired
    private TerminalService terminalService;

    protected Map<Long, Terminal> cachedTerminalMap = new ConcurrentHashMap<Long, Terminal>();

    @Override
    protected Terminal getFromCacheWithoutTimeout(Long key) throws CacheNotFoundException {
        if (!this.cachedTerminalMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的Terminal, key=" + key);
        }
        return this.cachedTerminalMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(Long key, Terminal value) throws CacheUpdateException {
        if (value == null) {
            // ConcurrentHashMap不允许保存null值
            //throw new CacheUpdateException("终端信息为空，不允许保存，key=" + key);
        	value=new Terminal();
        }
        this.cachedTerminalMap.put(key, value);
    }



    @Override
    protected Terminal getFromSource(Long key) throws Exception {
    	Terminal source=getFromMC(key);
    	if(source==null){
    		source=terminalService.get(key);
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return  source;
         
    }

    
    
   
}
