package com.lottery.scheduler.fetcher.sp.impl;

import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.scheduler.fetcher.sp.MatchSpDao;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;

@Component("redis")
public class RedisSpDaoImpl implements MatchSpDao {
	private final Logger logger= LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SharedJedisPoolManager shareJedisPoolManager;
	
	@Override
	public void save(String key, MatchSpDomain spDomain) {
		merge(key, spDomain);
	}

	@Override
	public MatchSpDomain merge(String tablename, MatchSpDomain spDomain) {
		ShardedJedis jedis=null;
		try{
			jedis=shareJedisPoolManager.getConnection();
			String rkey = tablename + spDomain.getMatchNum();
			jedis.set(rkey.getBytes(), SerializationUtils.serialize(spDomain));
			//jedis.set(rkey.getBytes(), new ObjectMapper().writeValueAsBytes(spDomain));
		}catch(Exception e){
			logger.error("redis merge出错 tablename:{},matchnum:{}", new Object[]{tablename, spDomain.getMatchNum()}, e);
		}finally{
			shareJedisPoolManager.releaseConnection(jedis);
		}
		return spDomain;
	}

	@Override
	public MatchSpDomain get(String matchNum, String tablename) {
		ShardedJedis jedis=null;
		try{
			jedis=shareJedisPoolManager.getConnection();
			String rkey = tablename + matchNum;
			byte[] bytes = jedis.get(rkey.getBytes());
			if (bytes == null) {
				return null;
			}
            return (MatchSpDomain) SerializationUtils.deserialize(bytes);
            //return new ObjectMapper().readValue(bytes, MatchSpDomain.class);
		}catch(Exception e){
			logger.error("redis get出错 tablename:{},matchnum:{}", new Object[]{tablename, matchNum}, e);
			return null;
		}finally{
			shareJedisPoolManager.releaseConnection(jedis);
		}
	}

}
