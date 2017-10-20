package com.sanerzone.smscenter.biz.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.sanerzone.common.support.rocketmq.MQCustomerFactory;
import com.sanerzone.smscenter.biz.entity.MessageTopic;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.gateway.GatewayStartUp;
import com.sanerzone.smscenter.gateway.service.impl.SmsGatewayServiceImpl;

@Service
@Lazy(false)
public class MQStartup {
	Logger logger = LoggerFactory.getLogger(MQStartup.class);
	
	@Autowired
	private SMSREQListener sMSREQListener;
	
	@Autowired
	private INSMSMTListener sMSMTListener;
	
	@Autowired
	private UPSMSSRListener sMSSRListener;
	
	@Autowired
	private INSMSSRListener sMSSRSubmitListener;
	
	@Autowired
	private INSMSRTListener sMSRTListener;
	
	@Autowired
	private UPSMSRTListener sMSRTStatusListener;
	
	@Autowired
	private SMSMORESListener sMSMORESListener;
	
	@Autowired
	private SmsGatewayServiceImpl smsGatewayServiceImpl;
	
	@Autowired
	private MQHelper mqHelper;

	public void init(){
		smsREQConsumer();
		smsMTConsumer();
		smsSRConsumer();
		smsSRSubmitConsumer();
		smsRTConsumer();
		smsRTStatusConsumer();
		
		GatewayStartUp gatewayStartUp = new GatewayStartUp();
		gatewayStartUp.setMqHelper(mqHelper);
		gatewayStartUp.setSmsGatewayServiceImpl(smsGatewayServiceImpl);
		gatewayStartUp.init();
	}
	
	/**
     * SMSREQ 集群消费
     */
    private void smsREQConsumer() {
        DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsREQConsumerGroup");
        try {
            smsStatusConsumer.setInstanceName("smsREQConsumer");
            smsStatusConsumer.subscribe(MessageTopic.SMSREQ, "*");
            smsStatusConsumer.setMessageListener(sMSREQListener);
            smsStatusConsumer.start();
            logger.info("短信{} 集群消费程序已启动", MessageTopic.SMSREQ);
        } catch (MQClientException e) {
            logger.error("短信"+MessageTopic.SMSREQ+" 集群消费程序异常", e);
        }
    }
    
    /**
     * SMSMTV2混合消费
     */
    private void smsMTConsumer() {
        DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsMTConsumerGroup");
        try {
            smsStatusConsumer.setInstanceName("smsMTConsumer");
            smsStatusConsumer.subscribe(MessageTopic.SMSMT, "*");
            smsStatusConsumer.setMessageListener(sMSMTListener);
            smsStatusConsumer.start();
            logger.info("短信混合消费处理程序已启动");
        } catch (MQClientException e) {
            logger.error("短信混合消费处理程序异常", e);
        }
    }
    
    /**
	 * SMSSRV2更新状态
	 */
	private void smsSRConsumer() {
		DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsSRConsumerGroup");
		try {
			smsStatusConsumer.setInstanceName("smsSRConsumer");
			smsStatusConsumer.subscribe(MessageTopic.SMSSR, "*");
			smsStatusConsumer.setMessageListener(sMSSRListener);
			smsStatusConsumer.start();
			logger.info("短信SMSSR更新状态处理程序已启动");
		} catch (MQClientException e) {
			logger.error("短信SMSSR更新状态处理程序异常", e);
		}
	}
	
	/**
	 * SMSSRV2 入库
	 */
	private void smsSRSubmitConsumer() {
		DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsSRSubmitConsumerGroup");
		try {
			smsStatusConsumer.setInstanceName("smsSRSubmitConsumer");
			smsStatusConsumer.subscribe(MessageTopic.SMSSR, "*");
			smsStatusConsumer.setMessageListener(sMSSRSubmitListener);
			smsStatusConsumer.start();
			logger.info("短信SMSSR 入库处理程序已启动");
		} catch (MQClientException e) {
			logger.error("短信SMSSR 入库处理程序异常", e);
		}
	}
	
	/**
	 * SMSRTV2 入库
	 */
	private void smsRTConsumer() {
		DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsRTConsumerGroup");
		try {
			smsStatusConsumer.setInstanceName("smsRTConsumer");
			smsStatusConsumer.subscribe(MessageTopic.SMSRT, "*");
			smsStatusConsumer.setMessageListener(sMSRTListener);
			smsStatusConsumer.start();
			logger.info("短信SMSRT 入库处理程序已启动");
		} catch (MQClientException e) {
			logger.error("短信SMSRT 入库处理程序异常", e);
		}
	}
	
	/**
	 * SMSRTV2更新状态
	 */
	private void smsRTStatusConsumer() {
		DefaultMQPushConsumer smsStatusConsumer = MQCustomerFactory.getPushConsumer("smsRTStatusConsumerGroup");
		try {
			smsStatusConsumer.setInstanceName("smsRTStatusConsumer");
			smsStatusConsumer.subscribe(MessageTopic.SMSRT, "*");
			smsStatusConsumer.setMessageListener(sMSRTStatusListener);
			smsStatusConsumer.start();
			logger.info("短信SMSRTV2更新状态处理程序已启动");
		} catch (MQClientException e) {
			logger.error("短信SMSRTV2更新状态处理程序异常", e);
		}
	}
	
	
	/**
	 * 用户上行HTTP处理程序-SMSMORES
	 */
	public void smsMORESConsumer() {
		DefaultMQPushConsumer smsMORESConsumer = MQCustomerFactory
				.getPushConsumer("smsMORESConsumerGroup");
		try {
			smsMORESConsumer.setInstanceName("smsMORESConsumer");
			smsMORESConsumer.subscribe(MessageTopic.SMSMORES, "HTTP");
			smsMORESConsumer.setMessageListener(sMSMORESListener);
			smsMORESConsumer.start();
			logger.info("MORES服务启动成功, Topic:{}", MessageTopic.SMSMORES);
		} catch (Exception e) {
			logger.error("MORES服务启动异常, Topic:" + MessageTopic.SMSMORES, e);
		}
	}
	
	
	
}
