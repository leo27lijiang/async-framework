package com.lefu.async;

/**
 * 事件发送属性定义
 * @author jiang.li
 *
 */
public class PublishPair {
	private String queueName;
	private EventData data;
	
	public PublishPair() {
		
	}
	
	public PublishPair(String queueName, EventData data) {
		this.queueName = queueName;
		this.data = data;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public EventData getData() {
		return data;
	}

	public void setData(EventData data) {
		this.data = data;
	}
}
