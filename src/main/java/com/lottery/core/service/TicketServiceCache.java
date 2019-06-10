package com.lottery.core.service;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.ticket.TicketVender;
@Service
public class TicketServiceCache {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	protected SharedJedisPoolManager redisManager;
	public void setTicketFailureTypeCache(String id, TicketFailureType failureType) {
		if (failureType == null) {
			// 没有特殊类型时不做处理
			return;
		}
		String key = String.format("__engine_ticket_failuretype_%s", id);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return;
			}
			jedisCommands.hset(key, String.valueOf(failureType.getValue()), String.valueOf(System.currentTimeMillis()));
			jedisCommands.expire(key, 300);
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}
	}
	public boolean hasTicketFailureTypeCache(String id, TicketFailureType failureType) {
		if (failureType == null) {
			// 没有特殊类型时不做处理
			return false;
		}
		String key = String.format("__engine_ticket_failuretype_%s", id);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return false;
			}
			Boolean result = jedisCommands.hexists(key, String.valueOf(failureType.getValue()));
			if (result == null) {
				return false;
			}
			return result;
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}
	}
	public void setTicketFailureTerminalIdCache(String id, Long terminalId) {
		if (terminalId == null) {
			return;
		}
		String key = String.format("__engine_ticket_failure_terminal_%s", id);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return;
			}
			jedisCommands.hset(key, terminalId.toString(), String.valueOf(System.currentTimeMillis()));
			jedisCommands.expire(key, 300);
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}
	}
	
	public boolean hasTicketFailureTerminalIdCache(String id, Long terminalId) {
		if (terminalId == null) {
			// 没有特殊类型时不做处理
			return false;
		}
		String key = String.format("__engine_ticket_failure_terminal_%s", id);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return false;
			}
			Boolean result = jedisCommands.hexists(key, terminalId.toString());
			if (result == null) {
				return false;
			}
			return result;
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}
	}

	public void setTicketVenderCache(TicketVender ticketVender, Long terminalId) {
		if (terminalId == null) {
			return;
		}
		String key = String.format("lottery_ticket_vender_success_%s_%s", ticketVender.getId(), terminalId);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return;
			}

			if (jedisCommands instanceof ShardedJedis) {
				ShardedJedis jedis = (ShardedJedis) jedisCommands;
				jedis.set(key.getBytes(), SerializationUtils.serialize(ticketVender));
				jedis.expire(key, 120);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}
	}
	
	public TicketVender getTicketVenderCache(String requestId, Long terminalId) {
		if (terminalId == null) {
			return null;
		}
		String key = String.format("lottery_ticket_vender_success_%s_%s", requestId, terminalId);
		ShardedJedis jedisCommands = null;
		try {
			jedisCommands = redisManager.getConnection();
			if (jedisCommands == null) {
				logger.error("获取redis连接失败");
				return null;
			}

			if (jedisCommands instanceof ShardedJedis) {
				ShardedJedis jedis = (ShardedJedis) jedisCommands;
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes == null) {
					return null;
				}
				return (TicketVender) SerializationUtils.deserialize(bytes);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			redisManager.releaseConnection(jedisCommands);
		}

		return null;
	}
	
	
}
