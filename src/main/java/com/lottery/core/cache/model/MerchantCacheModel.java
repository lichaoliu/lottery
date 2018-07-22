package com.lottery.core.cache.model;

import com.lottery.common.cache.AbstractKeyValueCacheWithTimeoutModel;
import com.lottery.common.exception.CacheNotFoundException;
import com.lottery.common.exception.CacheUpdateException;
import com.lottery.core.domain.merchant.Merchant;
import com.lottery.core.service.merchant.MerchantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fengqinyun on 16/8/5.
 */
@Service
public class MerchantCacheModel extends AbstractKeyValueCacheWithTimeoutModel<String,Merchant> {

    private Map<String,Merchant> merchantMap=new ConcurrentHashMap<String,Merchant>();
    @Autowired
    private MerchantService merchantService;


    private long cachetimeout=60000;

    @Override
    protected Merchant getFromCacheWithoutTimeout(String key) throws CacheNotFoundException {
        if (!this.merchantMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的LottypeConfig, key=" + key);
        }
        return this.merchantMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(String key, Merchant value) throws CacheUpdateException {

        if(value==null){
            value=new Merchant();
        }
        merchantMap.put(key,value);
    }

    public long getTimeoutMillis(){
        return this.cachetimeout;
    }

    @Override
    protected Merchant getFromSource(String key) throws Exception {
        Merchant source=getFromMC(key);
        if(source==null){
            source=merchantService.get(key);
            if(source!=null){
                setMC(key,source);
            }
        }
        return  source;
    }
}
