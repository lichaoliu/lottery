package com.lottery.common.contains;

public enum MailType {
	phone("phone","手机 短信"),
	email("email","邮件"),
	all("all","默认");
	public  String value;
    public  String name;
    
    MailType(String value,String  name){
    	this.value=value;
    	this.name=name;
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
