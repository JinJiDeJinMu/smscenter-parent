package com.sanerzone.common.support.rocketmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.sanerzone.common.support.config.Global;

public class MQCustomerFactory {
	Logger logger = LoggerFactory.getLogger(MQCustomerFactory.class);
	
	public static DefaultMQPushConsumer getPushConsumer(String consumerGroup) {
		DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(consumerGroup);
		pushConsumer.setNamesrvAddr(Global.getConfig("rocketmq.namesrv.address"));
		pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		return pushConsumer;
	}
	
	public static MQPullConsumerScheduleService getPullScheduleConsumer(String consumerGroup) {
		MQPullConsumerScheduleService scheduleService  = new MQPullConsumerScheduleService(consumerGroup);
		scheduleService.getDefaultMQPullConsumer().setNamesrvAddr(Global.getConfig("rocketmq.namesrv.address"));
        return scheduleService;
	}
}
