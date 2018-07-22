/**
 * 
 */
package com.lottery.retrymodel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fengqinyun
 *
 */

public class ApiRetryTaskExecutor {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private ExecutorService executor;
	
	public synchronized void init() {
		logger.info("初始化线程池");
		if (executor == null) {
			executor = Executors.newCachedThreadPool();
		}
	}
	
	public synchronized void destroy() {
		logger.info("销毁线程池");
		if (executor != null && !executor.isShutdown()) {
			executor.shutdown();
		}
		executor = null;
	}
	
	/**
	 * 异步执行并返回Futrue对象供调用者进行后续操作
	 * @param callback
	 * @return
	 */
	public synchronized Future<Object> invokeAsync(ApiRetryCallback<Object> callback) {
		if (executor == null) {
			logger.error("线程池未初始化");
			return null;
		}
		if (executor.isShutdown()) {
			logger.error("线程池已被关闭");
			return null;
		}
		logger.info("开始执行重试任务：{}，重试次数：{}，重试间隔：{}",
				new Object[] {callback.getName(), callback.getRetry(), callback.getRetryInterval()});
		Future<Object> future = executor.submit(callback);
		return future;
	}
	
	/**
	 * 异步执行并支持获取返回结果
	 * @param callback
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public Object invokeAsyncWithResult(ApiRetryCallback<Object> callback, long timeout) throws Exception {
		Future<Object> future = this.invokeAsync(callback);
		if (future == null) {
			throw new Exception("线程池已关闭");
		}
		try {
			Object result = null;
			if (timeout == 0) {
				result = future.get();
			} else {
				result = future.get(timeout, TimeUnit.MILLISECONDS);
			}
			return result;
		} catch (Exception e) {
			logger.error("异步读取重试任务的返回结果失败", e);
			throw e;
		}
	}
}
