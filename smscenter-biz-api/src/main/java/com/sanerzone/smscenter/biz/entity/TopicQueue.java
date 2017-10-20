package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;

public class TopicQueue implements Serializable{
	
	private static final long serialVersionUID = 7772732903258123732L;
	
	private String topic;
	private int queueId;
	private int weight;	  //优先级
	
	public TopicQueue(String topic, int queueId, int weight) {
		this.topic = topic;
		this.queueId = queueId;
		this.weight = weight;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	

}
