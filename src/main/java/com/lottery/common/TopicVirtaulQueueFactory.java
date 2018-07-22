package com.lottery.common;

import org.apache.commons.lang.StringUtils;

import com.lottery.common.contains.TopicName;

/***
 * topic 虚拟成queue
 * */
public class TopicVirtaulQueueFactory {

	private static final String DEFAULT_PREFIX="VirtualTopicConsumers";
	private static final String DEFAULT_JMS="jms:queue:";
	/**
	 * 活动虚拟队列的名字
	 * @param topicName topic名字
	 * @param prefix 分割符号
	 * @param virtaulQueueName 虚拟的队列名
	 * */
	
	public static String getQueueName(String topicName,String prefix,String virtaulQueueName){
		String[] str=StringUtils.split(topicName, ":");
		StringBuffer sb=new StringBuffer();
		sb.append(DEFAULT_JMS);
		sb.append(prefix);
		sb.append(".");
		sb.append(virtaulQueueName);
		sb.append(".");
		sb.append(str[str.length-1]);
		return sb.toString();
	}
	/**
	 * 活动虚拟队列的名字
	 * @param topicName topic名字
	 * @param prefix 分割符号
	 * @param virtaulQueueName 虚拟的队列名
	 * */
	public static String getQueueName(TopicName topicName,String prefix,String virtaulQueueName){
		return getQueueName(topicName.value,prefix,virtaulQueueName);
	}
	/**
	 * 活动虚拟队列的名字
	 * @param topicName topic名字
	 * @param virtaulQueueName 虚拟的队列名
	 * */
	public static String getQueueName(String topicName,String virtaulQueueName){
		return getQueueName(topicName,DEFAULT_PREFIX,virtaulQueueName);
	}
	
	/**
	 * 活动虚拟队列的名字
	 * @param topicName topic名字
	 * @param virtaulQueueName 虚拟的队列名
	 * */
	public static String getQueueName(TopicName topicName,String virtaulQueueName){
		return getQueueName(topicName,DEFAULT_PREFIX,virtaulQueueName);
	}
	
	public static String getPrefix(){
		return DEFAULT_PREFIX;
	}
	
}
