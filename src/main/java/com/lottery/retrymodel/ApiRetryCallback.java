/**
 * 
 */
package com.lottery.retrymodel;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fengqinyun
 *
 */
public abstract class ApiRetryCallback<V> implements Callable<V> {
	
	protected transient final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	/**
	 * 任务名称
	 */
	private String name;
	
	/**
	 * 出错重试次数
	 */
	private int retry = 5;
	
	private long retryInterval = 1000;
	
	private boolean warned = false;
	
	/**
	 * 是否处于重试状态
	 */
	private boolean inRetry = false;
	
	@Override
	public V call() throws Exception {
		int retryCount = 0;
		do {
			if (retryCount > 0) {
				// 第一次尝试不输出错误日志
				logger.error("重试{}第{}次", name, retryCount);
				inRetry = true;
			}
			try {
				V result = this.execute();
				return result;
			} catch (Exception e) {
				logger.error("任务({})执行失败",name,e);
			}
			if (!warned) {
				try {
					this.sendPreWarning();
				} catch (Exception e) {
					logger.error("预警处理失败", e);
				}
				warned = true;
			}
			retryCount ++;
			synchronized (this) {
				try {
					this.wait(retryInterval);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} while (retryCount < retry);
		
		try {
			this.sendWarning();
		} catch (Exception e) {
			logger.error("报警处理失败", e);
		}
		
		try {
			errorHandler();
		} catch (Exception e) {
			logger.error("错误处理失败", e);
		}
		
		return null;
	}
	
	abstract protected V execute() throws Exception;
	
	/**
	 * 发送警告的方法，在每次执行失败时被调用
	 * 默认不发送警告，需要发送警告的重试方法需要覆盖此实现
	 */
	protected void sendWarning() {
		
	}
	
	/**
	 * 发送预警，在第一次失败时被调用
	 */
	protected void sendPreWarning() {
		
	}
	
	/**
	 * 重试完成后最终没有执行成功的错误处理器
	 * 默认不做处理
	 */
	protected void errorHandler() {
		
	}
	protected boolean checkRetry() {
	        return true;
	    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public long getRetryInterval() {
		return retryInterval;
	}

	public void setRetryInterval(long retryInterval) {
		this.retryInterval = retryInterval;
	}

	public boolean isInRetry() {
		return inRetry;
	}
}
