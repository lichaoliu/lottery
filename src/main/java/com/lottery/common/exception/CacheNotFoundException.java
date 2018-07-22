package com.lottery.common.exception;

public class CacheNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7068251383520988321L;

	public CacheNotFoundException(String message) {
		super(message);
	}

	public CacheNotFoundException() {
		this("缓存未找到");
	}
}
