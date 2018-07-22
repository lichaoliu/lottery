package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.service.LotteryPhaseService;
@Service
public class LotteryPhaseCacheModel extends AbstractKeyValueCacheWithTimeoutModel<LotteryPhaseKey, LotteryPhase>{
	@Autowired
	private LotteryPhaseService LotteryPhaseService;
	protected Map<LotteryPhaseKey,LotteryPhase> lotteryPhaseMap=new ConcurrentHashMap<LotteryPhaseKey, LotteryPhase>();
	@Override
	protected LotteryPhase getFromCacheWithoutTimeout(LotteryPhaseKey key)
			throws CacheNotFoundException {
		   if (!this.lotteryPhaseMap.containsKey(key)) {
	            throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
	        }
	        return this.lotteryPhaseMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(LotteryPhaseKey key,
			LotteryPhase value) throws CacheUpdateException {
		if(value==null){
			value=new LotteryPhase();
		}
		 this.lotteryPhaseMap.put(key, value);
		
	}
	@Override
	protected LotteryPhase getFromSource(LotteryPhaseKey key) throws Exception {
		LotteryPhase source=getFromMC(key);
    	if(source==null){
    		source=LotteryPhaseService.getByTypeAndPhase(key.getLotteryType(), key.getPhase());
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return  source;
	//	return LotteryPhaseService.getByTypeAndPhase(key.getLotteryType(), key.getPhase());
		
	}

}
