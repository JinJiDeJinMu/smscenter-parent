package com.sanerzone.smscenter.common.tools;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.MessageTopic;
import com.sanerzone.smscenter.biz.entity.TopicQueue;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.message.SMSURRESMessage;
import com.xiaoleilu.hutool.util.BeanUtil;

@Service
public class MQHelper {
	
	public Logger logger = LoggerFactory.getLogger(MQHelper.class);

	@Autowired(required=true)
	private DefaultMQProducer defaultMQProducer;
	
	
	/**
	 * 
	 * @param massFlag BATCH/SINGLE
	 * @param taskid
	 * @param smsReqMsg
	 * @return
	 */
	public String sendSmsREQ(String massFlag, String taskid, SMSREQMessage smsReqMsg) {
		try {
			return sendSyncMsgId(MessageTopic.SMSREQ, massFlag, taskid, smsReqMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSmsMT(String bizid, SMSMTMessage message) {
		try {
			return sendSyncMsgId(MessageTopic.SMSMT, message.getSendStatus().substring(0, 1), bizid, message
					, new TopicQueue(message.getSendTopic(), message.getSendQueue(), 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSmsUR(String bizid, SMSURREQMessage message) {
		try {
			return sendSyncMsgId(MessageTopic.SMSTREQ, message.getSourceGateWayId(), bizid, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSMSTRES(String bizid, SMSURRESMessage message) {
		try {
			return sendSyncMsgId(MessageTopic.SMSTRES, message.getUserid(), bizid, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSmsMOREQ(String bizid, SMSURREQMessage message) {
		try {
			return sendSyncMsgId(MessageTopic.SMSMOREQ, message.getSourceGateWayId(), bizid, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSmsMORES(String bizid, SMSURREQMessage message) {
		try {
			return sendSyncMsgId(MessageTopic.SMSMORES, message.getSourceGateWayId(), bizid, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String sendSyncMsgId(String topic, String tag, String key, Serializable body){
		return sendSyncMsgId(topic, tag, key, body, null);
	}
	
	public String sendSyncMsgId(String topic, String tag, String key, Serializable body, TopicQueue topicQueue) {
		SendResult sendResult = null;
		try {
			sendResult = send(topic, tag, key, FstObjectSerializeUtil.write(body), topicQueue);
		} catch (Exception e) {
			logger.error("序列化消息异常, {}", BeanUtil.beanToMap(body) ,e);
		}
		
		if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
			return null;
		}
		return sendResult.getMsgId();
	}
	
//	public SendResult send(String topic, String tag, String key, Serializable body, TopicQueue topicQueue) throws Exception {
//		return send(topic, tag, key, FstObjectSerializeUtil.write(body), topicQueue);
//	}
	
	public SendResult send(String topic, String tag, String key, byte[] body, TopicQueue topicQueue) {
		Message msg = new Message(topic, tag, key, body);
		return send(msg, topicQueue);
		
	}
	
	public SendResult send(Message msg, TopicQueue topicQueue) {
		try {
//			if(topicQueue == null) {
				return defaultMQProducer.send(msg);
//			} else {
//				return defaultMQProducer.send(msg, new MessageQueueSelector() {
//					@Override
//					public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//						return mqs.get((int) arg);
//					}
//				}, topicQueue.getQueueId());
//			}
		} catch (Exception e) {
			logger.error("消息队列异常:{}", msg.toString(), e);
		}
		return null;
	}
	
}
