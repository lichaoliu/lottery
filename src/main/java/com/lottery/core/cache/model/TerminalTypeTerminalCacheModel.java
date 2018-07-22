package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.service.TerminalService;

/**
 * @author fengqinyun
 */
@Service
public class TerminalTypeTerminalCacheModel extends AbstractKeyValueCacheWithTimeoutModel<TerminalType, Long[]> {
    @Autowired
	private TerminalService terminalService;
	
    protected Map<TerminalType, Long[]> cachedTerminalMap = new ConcurrentHashMap<TerminalType, Long[]>();

    @Override
    protected Long[] getFromCacheWithoutTimeout(TerminalType key) throws CacheNotFoundException {
        if (!this.cachedTerminalMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的TerminalTypeTerminal, key=" + key);
        }
        return this.cachedTerminalMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(TerminalType key, Long[] terminalIds) throws CacheUpdateException {
        if (terminalIds == null) {
        	terminalIds=new Long[0];
        }
        this.cachedTerminalMap.put(key, terminalIds);
    }

    @Override
    protected Long[] getFromSource(TerminalType key) throws Exception {
    	Long[] source=getFromMC(key);
    	if(source==null){
    		source=terminalService.getIdsAllByTerminalType(key.getValue());
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return source; 
    }

	
}
