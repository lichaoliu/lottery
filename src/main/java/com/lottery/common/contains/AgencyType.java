package com.lottery.common.contains;

import java.util.Arrays;
import java.util.List;

/**
 * 注册渠道类型
 * */
public enum AgencyType {
	web("1","互联网"),
	android_client("2","android客户端"),
	ios_client("3","ios客户端"),
	merchant("4","出票渠道"),
	html5("5","html5"),
	webchat("6","微信"),

	virtual("-1","虚拟渠道"),
	all("0","全部")
	;
	public String value;
	public String name;
	
	AgencyType(String value,String name){
		this.name=name;
		this.value=value;
	}
	public List<AgencyType> get(){
		return Arrays.asList(values());
	}



	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
