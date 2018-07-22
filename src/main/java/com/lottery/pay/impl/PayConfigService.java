package com.lottery.pay.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.ListSerializable;
import com.lottery.common.contains.pay.PayChannel;
import com.lottery.core.cache.model.PayPropertyCacheModel;
import com.lottery.core.domain.PayProperty;
import com.lottery.pay.IPayConfig;
import com.lottery.pay.IPayConfigFactory;

@Service
public class PayConfigService {
	protected final Logger logger=LoggerFactory.getLogger(getClass().getName());
	@Autowired
	private PayPropertyCacheModel payConfigCacheModel;
	@Resource(name = "payFactoryMap")
	protected Map<PayChannel, IPayConfigFactory> payFactoryMap;
	public IPayConfig getByPayChannel(PayChannel payChannel){
		List<PayProperty> payConfigs = null;
		String key=null;
		try {
			key=payChannel.getValue();
			ListSerializable<PayProperty> listSerializable=payConfigCacheModel.get(key);
			if(listSerializable!=null){
				payConfigs=listSerializable.getList();
			}
		} catch (Exception e) {
			logger.error("获取渠道[{}]充值配置出错",key,e);
		}
		if (payConfigs==null||payFactoryMap.get(payChannel) == null) {
            logger.error("key={}配置为空",key);
			return null;
		}
		IPayConfig config = payFactoryMap.get(payChannel).getVenderConfig(payConfigs);
		return config;
	}
}
