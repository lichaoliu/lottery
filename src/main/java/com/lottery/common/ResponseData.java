package com.lottery.common;

import java.io.Serializable;

/**
 * 统一请求返回类
 * @author fengqinyun
 * */
public class ResponseData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String errorCode;
	public Object value;

	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}


}
