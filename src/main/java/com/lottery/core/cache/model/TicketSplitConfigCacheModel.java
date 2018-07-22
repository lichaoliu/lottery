package com.lottery.core.cache.model;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.ticket.TicketSplitConfig;
import com.lottery.core.domain.ticket.TicketSplitConfigPK;
import com.lottery.core.service.TicketSplitConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fengqinyun on 2016/12/25.
 */
@Service
public class TicketSplitConfigCacheModel extends AbstractKeyValueCacheWithTimeoutModel<TicketSplitConfigPK,TicketSplitConfig> {
    @Autowired
    private TicketSplitConfigService ticketSplitConfigService;

    protected Map<TicketSplitConfigPK, TicketSplitConfig> cachedTicektSplitConfigMap = new ConcurrentHashMap<TicketSplitConfigPK, TicketSplitConfig>();
    @Override
    protected TicketSplitConfig getFromCacheWithoutTimeout(TicketSplitConfigPK key) throws CacheNotFoundException {
        if (!this.cachedTicektSplitConfigMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
        }
        return this.cachedTicektSplitConfigMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(TicketSplitConfigPK key, TicketSplitConfig value) throws CacheUpdateException {
        if(value==null){
            throw new CacheUpdateException("终端信息为空，不允许保存，key=" + key);
        }
        this.cachedTicektSplitConfigMap.put(key, value);
    }

    @Override
    protected TicketSplitConfig getFromSource(TicketSplitConfigPK key) throws Exception {
        TicketSplitConfig source=getFromMC(key);
        if(source==null){
            source= ticketSplitConfigService.get(key);
            if(source!=null){
                setMC(key,source);
            }
        }
        return  source;
    }
}
