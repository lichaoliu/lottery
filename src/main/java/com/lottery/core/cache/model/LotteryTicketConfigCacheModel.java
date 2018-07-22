package com.lottery.core.cache.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryTicketConfigService;
@Service
public class LotteryTicketConfigCacheModel extends  AbstractKeyValueCacheWithTimeoutModel<LotteryType,LotteryTicketConfig>{
    @Autowired
	private LotteryTicketConfigService lotteryTicketConfigService;
    protected Map<LotteryType, LotteryTicketConfig> cachedLotteryTicketConfigMap = new ConcurrentHashMap<LotteryType, LotteryTicketConfig>();
	@Override
	protected LotteryTicketConfig getFromCacheWithoutTimeout(LotteryType key)
			throws CacheNotFoundException {
		  if (!this.cachedLotteryTicketConfigMap.containsKey(key)) {
	            throw new CacheNotFoundException("缓存中未找到对应的LotteryTypeTerminalConfigId, key=" + key);
	        }
	        return this.cachedLotteryTicketConfigMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(LotteryType key,
			LotteryTicketConfig value) throws CacheUpdateException {
		  if (value == null) {
	            // ConcurrentHashMap不允许保存null值，但是我们需要保存以节约查询，写入一个长度为零的空数组
	            value = new LotteryTicketConfig();
	        }
	        this.cachedLotteryTicketConfigMap.put(key, value);
		
	}

	@Override
	protected LotteryTicketConfig getFromSource(LotteryType key)
			throws Exception {
		LotteryTicketConfig source=getFromMC(key);
    	if(source==null){
    		source=lotteryTicketConfigService.get(key);
    		if(source!=null){
    			setMC(key,source);
    		}
    	}
        return  source;
	}

}
