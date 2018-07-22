package com.lottery.core.cache.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.ListSerializable;
import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.terminal.TerminalProperty;
import com.lottery.core.service.TerminalPropertyService;

/**
 * @author fengqinyun
 */
@Service
public class TerminalPropertyCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Long, ListSerializable<TerminalProperty>> {
    @Autowired
    private TerminalPropertyService terminalService;

    protected Map<Long, ListSerializable<TerminalProperty>> cachedTerminalMap = new ConcurrentHashMap<Long, ListSerializable<TerminalProperty>>();

    @Override
    protected ListSerializable<TerminalProperty> getFromCacheWithoutTimeout(Long key) throws CacheNotFoundException {
        if (!this.cachedTerminalMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的TerminalProperty, key=" + key);
        }
        return this.cachedTerminalMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(Long key, ListSerializable<TerminalProperty> value) throws CacheUpdateException {
        if (value == null) {
             value=new ListSerializable<TerminalProperty>();
        }
        this.cachedTerminalMap.put(key, value);
    }

    @Override
    protected ListSerializable<TerminalProperty> getFromSource(Long key) throws Exception {
    	ListSerializable<TerminalProperty> source=getFromMC(key);
    	if(source==null){
    		
    		List<TerminalProperty> list=terminalService.getList(key);
    		if(list!=null){
    			source=new ListSerializable<TerminalProperty>();
    			source.setList(list);
    			setMC(key,source);
    		}
    	}
        return source; 
    }



}
