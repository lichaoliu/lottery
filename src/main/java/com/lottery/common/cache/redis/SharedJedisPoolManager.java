package com.lottery.common.cache.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

public class SharedJedisPoolManager {
	 private ShardedJedisPool shardedJedisPool;
	 private JedisPoolConfig poolConfig;
	 private String hostString;//格式:ip:port,ip:port
	 public SharedJedisPoolManager(JedisPoolConfig poolConfig,String hostString){
		 this.poolConfig=poolConfig;
		 this.hostString=hostString;
	 }
	 public ShardedJedis getConnection(){
		 return shardedJedisPool.getResource();
	 }
	 public void releaseConnection(ShardedJedis jedisCommands) {
	        if(jedisCommands != null){
	        	jedisCommands.close();
	        }
	    }
	 
	 protected void init(){
		 String[] hosts=StringUtils.split(hostString, ",");
		 List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		 for(String host:hosts){
			 String[] ipport=StringUtils.split(host, ":");
			 String ip=ipport[0];
			 String port=ipport[1];
			 shards.add(new JedisShardInfo(ip,Integer.valueOf(port)));
		 }
		 shardedJedisPool=new ShardedJedisPool(poolConfig, shards);
	 }
	protected void destroy(){
		if(shardedJedisPool!=null){
			shardedJedisPool.destroy();
		}
	}
	
	
	
	
	 
	 
	 
}
