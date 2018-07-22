package com.lottery.core.cache.model;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.service.JczqRaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JczqRaceCacheModel extends AbstractKeyValueCacheWithTimeoutModel<String, JczqRace> {

	@Autowired
	private JczqRaceService jczqRaceService;
	
	protected Map<String, JczqRace> cachedTerminalMap = new ConcurrentHashMap<String, JczqRace>();


	@Override
	protected JczqRace getFromCacheWithoutTimeout(String key) throws CacheNotFoundException {
		if (!this.cachedTerminalMap.containsKey(key)) {
			throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
		}
		return this.cachedTerminalMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(String key, JczqRace value) throws CacheUpdateException {
		if(value==null){
			throw new CacheUpdateException("终端信息为空，不允许保存，key=" + key);
		}
		this.cachedTerminalMap.put(key, value);
	}

	@Override
	protected JczqRace getFromSource(String key) throws Exception {
		JczqRace source=getFromMC(key);
		if(source==null){
			source= jczqRaceService.get(key);
			if(source!=null){
				setMC(key,source);
			}
		}
		return  source;
	}
}
