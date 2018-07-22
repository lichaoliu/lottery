package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.service.TerminalConfigService;

@Service
public class PlayTypeTerminalConfigIdCacheModel extends AbstractKeyValueCacheWithTimeoutModel<PlayType, Long[]> {
    @Autowired
    private TerminalConfigService terminalConfigService;

    protected Map<PlayType, Long[]> cachedTerminalConfigIdMap = new ConcurrentHashMap<PlayType, Long[]>();

    @Override
    protected Long[] getFromCacheWithoutTimeout(PlayType key) throws CacheNotFoundException {
        if (!this.cachedTerminalConfigIdMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的终端配置, key=" + key);
        }
        return this.cachedTerminalConfigIdMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(PlayType key, Long[] value) throws CacheUpdateException {
        if (value == null) {
            // ConcurrentHashMap不允许保存null值，但是我们需要保存以节约查询，写入一个长度为零的空数组
            value = new Long[0];
        }
        this.cachedTerminalConfigIdMap.put(key, value);
    }

    @Override
    protected Long[] getFromSource(PlayType key) throws Exception {
    	Long[] source=getFromMC(key);
		if(source==null){
			if(key==PlayType.ALL){
				source=terminalConfigService.getAllTerminalConfigId();
			}else{
				source=terminalConfigService.getTerminalConfigIdListByPlayType(key);
			}
			
			if(source!=null){
				setMC(key,source);
			}
		}
		return source;

       
    }


}
