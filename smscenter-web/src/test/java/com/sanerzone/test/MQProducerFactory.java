package com.sanerzone.test;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class MQProducerFactory {

	public static String MQNAMESRV = "192.168.1.180:9876";
	
	public static DefaultMQProducer getMQProducer(String producerGroup, String instanceName){
		DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(MQNAMESRV);
		producer.setInstanceName(instanceName);
		return producer;
	}
}
