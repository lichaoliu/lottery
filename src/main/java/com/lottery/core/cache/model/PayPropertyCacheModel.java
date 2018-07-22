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
import com.lottery.core.domain.PayProperty;
import com.lottery.core.service.PayPropertyService;

@Service
public class PayPropertyCacheModel extends AbstractKeyValueCacheWithTimeoutModel<String, ListSerializable<PayProperty>> {
    @Autowired
    private PayPropertyService payConfigService;

    protected Map<String, ListSerializable<PayProperty>> cachePayConfigMap = new ConcurrentHashMap<String, ListSerializable<PayProperty>>();

    @Override
    protected ListSerializable<PayProperty> getFromCacheWithoutTimeout(String key) throws CacheNotFoundException {
        if (!this.cachePayConfigMap.containsKey(key)) {
            throw new CacheNotFoundException("缓存中未找到对应的PayConfig, key=" + key);
        }
        return this.cachePayConfigMap.get(key);
    }

    @Override
    protected void setCacheWithoutTimeout(String key, ListSerializable<PayProperty> value) throws CacheUpdateException {
        if (value == null) {
             value=new ListSerializable<PayProperty>();
        }
        this.cachePayConfigMap.put(key, value);
    }

    @Override
    protected ListSerializable<PayProperty> getFromSource(String key) throws Exception {
    	List<PayProperty> list=payConfigService.getPayConfigList(key);
    	ListSerializable<PayProperty> ls=new ListSerializable<PayProperty>();
    	ls.setList(list);
        return ls; 
    }

}
