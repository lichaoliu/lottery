package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.service.TerminalConfigService;

/**
 * fengiqnyun
 */
@Service
public class TerminalLotteryTypeConfigIdCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Long, Long[]> {
    @Autowired
    private TerminalConfigService terminalConfigService;

    protected Map<Long, Long[]> cachedTerminalConfigIdMap = new ConcurrentHashMap<Long, Long[]>();

    @Override
    protected Long[] getFromCacheWithoutTimeout(Long key) throws CacheNotFoundException {
        if (!this.cachedTerminalConfigIdMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的TerminalLotteryTypeConfigId, key=" + key);
        }
        return this.cachedTerminalConfigIdMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(Long key, Long[] value) throws CacheUpdateException {
        if (value == null) {
            // ConcurrentHashMap不允许保存null值，但是我们需要保存以节约查询，写入一个长度为零的空数组
            value = new Long[0];
        }
        this.cachedTerminalConfigIdMap.put(key, value);
    }

    @Override
    protected Long[] getFromSource(Long key) throws Exception {
    	Long[] source=getFromMC(key);
    	if(source==null){
    		source=terminalConfigService.getByTerminalId(key);
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return source; 
      //  return this.terminalConfigService.getByTerminalId(key);
    }

    public void setTerminalConfigService(TerminalConfigService terminalConfigService) {
        this.terminalConfigService = terminalConfigService;
    }
}
