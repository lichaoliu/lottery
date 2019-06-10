package com.lottery.common.contains;

public enum PlatformType {
	
	phone("102","手机平台"),
	website("101","网站");
	
	private String value;
	
	private String name;
	
	PlatformType(String value,String name){
		this.value = value;
		this.name = name;
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
