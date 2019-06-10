package com.lottery.common.contains;

public enum EnabledStatus {
	enabled(1,"已启用"),
	disabled(0,"已禁用"),
	all(-1,"默认");
	public int value;
	
	public String name;
	
	 EnabledStatus(int value,String name) {
		this.value=value;
		this.name=name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
