package com.lottery.common.exception;

import com.lottery.common.contains.ErrorCode;

public class LotteryException extends RuntimeException {

	/**
	 * 自定义异常
	 */
	private static final long serialVersionUID = 3128807123156645674L;
	private ErrorCode errorCode;
	
	public LotteryException(ErrorCode errorCode,String message){
		super(message);
		this.errorCode=errorCode;
	}
	

	public LotteryException(String message){
		super(message);
		this.errorCode=ErrorCode.unknown_error;
	}
	

	public LotteryException(String message,Throwable e){
		super(message, e);
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public LotteryException(ErrorCode errorCode,String message,Throwable e){
		super(message,e);
		this.errorCode=errorCode;
	}
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	
	 
	
	
	
}
