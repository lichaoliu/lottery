package com.lottery.core.cache.model;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.LottypeConfig;
import com.lottery.core.service.LottypeConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class LottypeConfigCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Integer, LottypeConfig>{
    @Autowired
	private LottypeConfigService configService;
	protected Map<Integer,LottypeConfig> typeConfigMap=new ConcurrentHashMap<Integer, LottypeConfig>();
	@Override
	protected LottypeConfig getFromCacheWithoutTimeout(Integer key)
			throws CacheNotFoundException {
		   if (!this.typeConfigMap.containsKey(key)) {
	            throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
	        }
	        return this.typeConfigMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(Integer key, LottypeConfig value)
			throws CacheUpdateException {
		   if (value == null) {
	            // ConcurrentHashMap不允许保存null值，但是我们需要保存以节约查询，写入一个长度为零的空数组
	            value = new LottypeConfig();
	        }
	        this.typeConfigMap.put(key, value);
		
	}

	@Override
	protected LottypeConfig getFromSource(Integer key) throws Exception {
		LottypeConfig source=getFromMC(key);
		if(source==null){
			source=configService.get(key);
			if(source!=null){
				setMC(key,source);
			}
		}
		return source;
	}

}
