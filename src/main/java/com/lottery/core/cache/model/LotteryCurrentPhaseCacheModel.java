package com.lottery.core.cache.model;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.service.LotteryPhaseService;
@Service
public class LotteryCurrentPhaseCacheModel extends AbstractKeyValueCacheWithTimeoutModel<Integer, LotteryPhase>{

	private long timeout=2000;//2秒
	@Autowired
	private LotteryPhaseService LotteryPhaseService;
	@Autowired
	protected LotteryTicketConfigCacheModel lotteryTicketConfigCacheModel;
	protected Map<Integer,LotteryPhase> lotteryPhaseMap=new ConcurrentHashMap<Integer, LotteryPhase>();
	@Override
	protected LotteryPhase getFromCacheWithoutTimeout(Integer key)
			throws CacheNotFoundException {
		   if (!this.lotteryPhaseMap.containsKey(key)) {
	            throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
	        }

	        return this.lotteryPhaseMap.get(key);
	}

	@Override
	protected void setCacheWithoutTimeout(Integer key,
			LotteryPhase value) throws CacheUpdateException {
		if(value==null){
			throw new CacheUpdateException("终端信息为空，不允许保存，key=" + key);
		}
		 this.lotteryPhaseMap.put(key, value);
		
	}
	public long getTimeoutMillis() {
		return this.timeout;
	}
	@Override
	protected LotteryPhase getFromSource(Integer key) throws Exception {
		LotteryPhase source=getFromMC(key);
    	if(source==null){
    		source=LotteryPhaseService.getCurrent(key);
    		if(source!=null){
    			setMC(key,source,60);
    		}
    	}else{
            if (source.getEndSaleTime().getTime()-getEndSaleFoward(key)-System.currentTimeMillis()<=0){
                source=LotteryPhaseService.getCurrent(key);
                if(source!=null){
                    setMC(key,source,60);
                }
            }
        }
        return  source;
	}
	
	
	private long getEndSaleFoward(int lotteryType) {
		try {
			LotteryTicketConfig lotteryTicketConfig = lotteryTicketConfigCacheModel.get(LotteryType.getPhaseType(lotteryType));
			if (lotteryTicketConfig != null) {
				if (lotteryTicketConfig.getEndSaleForward() != null) {
					return lotteryTicketConfig.getEndSaleForward().longValue();
				}
			}
		}catch(Exception e) {
			logger.error("获取提前截止时间出错",e);
		}
		return 0;
	}

}
