package com.lottery.core.service.topic;

import com.lottery.common.contains.TopicName;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * Topic消息发送类
 * */
@Service
public class TopicMessageSendService {

	@Produce
	private ProducerTemplate produce;
	/**
	 * 发送消息
	 * @param messageName topic名字
	 * @param  map topic map
	 * */
	public void sendMessage(String messageName,Map<String,Object> map) throws Exception{
		produce.sendBodyAndHeaders(messageName, null,map);
	}
	
	/**
	 * 发送消息
	 * @param topicName topicenum
	 * @param  map topic map
	 * */
	public void sendMessage(TopicName topicName,Map<String,Object> map) throws Exception{
		this.sendMessage(topicName.getValue(), map);
	}
	
	/**
	 * 发送消息
	 * @param messageName topic名字
	 * @param  object topic 类
	 * */
	public void sendMessage(String messageName,Object object) throws Exception{
		produce.sendBody(messageName, object);
	}
	
	
	/**
	 * 发送消息
	 * @param topicName topic名字
	 * @param  object topic 类
	 * */
	public void sendMessage(TopicName topicName,Object object) throws Exception{
		this.sendMessage(topicName.getValue(), object);
	}
}
