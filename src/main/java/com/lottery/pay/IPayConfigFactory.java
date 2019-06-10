package com.lottery.pay;

import java.util.List;

import com.lottery.core.domain.PayProperty;

/**
 * 获得充值的config配置
 * */
public interface IPayConfigFactory {

	
	public IPayConfig getVenderConfig(List<PayProperty> payConfigList);
}
