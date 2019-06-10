package com.lottery.common.util;

import java.util.Map;

/**
 * key-value绑定器的一个默认实现
 */
public class KeyValueBindManager<K, V> {

    private V defaultValue;

    private Map<K, V> binderMap;

    public V get(K key) {
        if (binderMap.containsKey(key)) {
            return binderMap.get(key);
        }
        return defaultValue;
    }

    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setBinderMap(Map<K, V> binderMap) {
        this.binderMap = binderMap;
    }
}
