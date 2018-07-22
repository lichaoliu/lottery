package com.lottery.listener;

import org.apache.camel.builder.RouteBuilder;

import com.lottery.common.TopicVirtaulQueueFactory;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TopicName;

public class LotteryRouteBuilder extends RouteBuilder {

	protected QueueName queueName;
	protected TopicName topicName;
	protected int concurrentConsumers=1;
	protected int maxConcurrentConsumers;
	protected boolean async=false;
	protected boolean topicFlag=false;
	
	protected String virtaulQueue;
	
	protected String endpoint;
	private String routId;
	@Override
	public void configure() throws Exception {
		
     from(getFromName()).to(endpoint);
	}
	
	protected String getFromName(){
		StringBuffer fromBf=new StringBuffer();
		fromBf.append("?concurrentConsumers=").append(this.concurrentConsumers);
		if(maxConcurrentConsumers>0){
			fromBf.append("&maxConcurrentConsumers=").append(maxConcurrentConsumers);
		}
		if(async==true){
			fromBf.append("&asyncConsumer=true");
		}
		String nameStr=fromBf.toString();
		if(queueName!=null){
			return queueName.value+nameStr;
		}
		if(topicName!=null){
			return TopicVirtaulQueueFactory.getQueueName(topicName, virtaulQueue)+nameStr;
		}
		return null;
	}
	
	public QueueName getQueueName() {
		return queueName;
	}
	public void setQueueName(QueueName queueName) {
		this.queueName = queueName;
	}
	public TopicName getTopicName() {
		return topicName;
	}
	public void setTopicName(TopicName topicName) {
		this.topicName = topicName;
	}
	public int getConcurrentConsumers() {
		return concurrentConsumers;
	}
	public void setConcurrentConsumers(int concurrentConsumers) {
		this.concurrentConsumers = concurrentConsumers;
	}
	public int getMaxConcurrentConsumers() {
		return maxConcurrentConsumers;
	}
	public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
		this.maxConcurrentConsumers = maxConcurrentConsumers;
	}
	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public boolean isTopicFlag() {
		return topicFlag;
	}
	public void setTopicFlag(boolean topicFlag) {
		this.topicFlag = topicFlag;
	}

	public String getVirtaulQueue() {
		return virtaulQueue;
	}

	public void setVirtaulQueue(String virtaulQueue) {
		this.virtaulQueue = virtaulQueue;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getRoutId() {
		return routId;
	}

	public void setRoutId(String routId) {
		this.routId = routId;
	}



	
	
	
	
	

	
}
