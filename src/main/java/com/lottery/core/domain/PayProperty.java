package com.lottery.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "pay_property")
public class PayProperty implements Serializable {

	private static final long serialVersionUID = -768816152885637647L;

	
	/**
	 * 配置表 pay_config ,用于充值等相关配置
	 */
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "config_name")
	@NotNull
	private String configName;//配置名称
	@Column(name = "config_value")
	private String configValue;//配置值
	@Column(name = "pay_channel")// 类别
	private String payChannel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}


	
}
