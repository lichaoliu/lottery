package com.lottery.core.service.queue;

import com.lottery.common.contains.QueueName;

import org.apache.camel.Consume;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * 队列消息发送类
 * */
@Service
public class QueueMessageSendService {

	@Produce
	private ProducerTemplate product;
	@Consume
	private ConsumerTemplate consumer;
	/**
	 * 发送消息
	 * @param messageName 队列名字
	 * @param  map 队列map
	 * */
	public void sendMessage(String messageName,Map<String,Object> map) throws Exception{
	
		product.sendBodyAndHeaders(messageName, null,map);
	}
	
	/**
	 * 发送消息
	 * @param queueName 队列enum
	 * @param  map 队列map
	 * */
	public void sendMessage(QueueName queueName,Map<String,Object> map) throws Exception{
		this.sendMessage(queueName.getValue(), map);
	}
	
	/**
	 * 发送消息
	 * @param messageName 队列名字
	 * @param  object 队列 类
	 * */
	public void sendMessage(String messageName,Object object) throws Exception{
		product.sendBody(messageName, object);
	}
	
	
	/**
	 * 发送消息
	 * @param queueName 队列名字
	 * @param  object 队列 类
	 * */
	public void sendMessage(QueueName queueName,Object object) throws Exception{
		this.sendMessage(queueName.getValue(), object);
	}
	
	public Exchange receive(String endpointUri){
		return consumer.receive(endpointUri);
	}
}
