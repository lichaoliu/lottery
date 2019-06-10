package com.lottery.common.exception;

public class CacheUpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7085688523212587897L;

	 public CacheUpdateException(String message) {
			super(message);
		}
		public CacheUpdateException() {
			this("缓存更新出错");
		}
}
