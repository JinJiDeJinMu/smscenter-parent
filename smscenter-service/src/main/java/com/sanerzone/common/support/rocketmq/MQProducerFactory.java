package com.sanerzone.common.support.rocketmq;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.sanerzone.common.support.config.Global;

public class MQProducerFactory {

	public static DefaultMQProducer getMQProducer(String producerGroup, String instanceName){
		DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(Global.getConfig("rocketmq.namesrv.address"));
		producer.setInstanceName(instanceName);
		return producer;
	}
}
