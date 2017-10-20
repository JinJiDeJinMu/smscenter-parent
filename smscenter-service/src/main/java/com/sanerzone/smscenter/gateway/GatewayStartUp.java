package com.sanerzone.smscenter.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.sanerzone.common.support.rocketmq.MQCustomerFactory;
import com.sanerzone.smscenter.biz.entity.MessageTopic;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.gateway.http.HttpGatewayFactory;
import com.sanerzone.smscenter.gateway.http.handler.listener.GateWayMTListener;
import com.sanerzone.smscenter.gateway.http.message.HttpGateWayMessage;
import com.sanerzone.smscenter.gateway.service.impl.SmsGatewayServiceImpl;

public class GatewayStartUp {
	
	private static final Logger logger = LoggerFactory.getLogger(GatewayStartUp.class);
	
	private MQHelper mqHelper;
	
	public MQHelper getMqHelper() {
		return mqHelper;
	}

	public void setMqHelper(MQHelper mqHelper) {
		this.mqHelper = mqHelper;
	}
	
	private SmsGatewayServiceImpl smsGatewayServiceImpl;
	
	public SmsGatewayServiceImpl getSmsGatewayServiceImpl() {
		return smsGatewayServiceImpl;
	}

	public void setSmsGatewayServiceImpl(SmsGatewayServiceImpl smsGatewayServiceImpl) {
		this.smsGatewayServiceImpl = smsGatewayServiceImpl;
	}

	public static HttpGateWayMessage httpGateWayMessage;
	public static HttpGatewayFactory gatewayFactory;
	
	public void init() {
		System.out.println("================");
		httpGateWayMessage = new HttpGateWayMessage();
		httpGateWayMessage.setAppCode("8987");
		httpGateWayMessage.setMqHelper(mqHelper);
		
		// 初始化网关
		gatewayFactory = new HttpGatewayFactory();
		gatewayFactory.setGateWayMessage(httpGateWayMessage);
		gatewayFactory.setSmsGatewayService(smsGatewayServiceImpl);
		gatewayFactory.initGateway("8989");
		
		// 启动队列监听
		GateWayMTListener gateWayMTListener = new GateWayMTListener();
		gateWayMTListener.setGatewayFactory(gatewayFactory);
		
		DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("SMSMTSendCGrp");
        try {
            smsStatusConsumer.setInstanceName("SMSMTSendConsumer");
            smsStatusConsumer.subscribe(MessageTopic.SMSMT, "T");
            smsStatusConsumer.setMessageListener(gateWayMTListener);
            smsStatusConsumer.start();
            logger.info("短信SMSMT 集群消费程序已启动");
        } catch (MQClientException e) {
            logger.error("短信SMSMT 集群消费程序异常", e);
        }
		
	}
	
	
	
	
	
}
