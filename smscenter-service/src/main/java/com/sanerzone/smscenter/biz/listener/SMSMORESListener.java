package com.sanerzone.smscenter.biz.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.sanerzone.smscenter.biz.mapper.SmsReceiveMapper;
import com.sanerzone.smscenter.biz.message.SMSURRESMessage;
import com.sanerzone.smscenter.biz.utils.MessageExtUtil;

@Service
public class SMSMORESListener implements MessageListenerConcurrently {
	private Logger logger = LoggerFactory.getLogger(SMSMORESListener.class);

	@Autowired
	private SmsReceiveMapper smsReceiveMapper;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		try {
			
			for (MessageExt msg : msgs) {
				logger.info("SMSMORES msgid:{}, key:{}", msg.getMsgId(), msg.getKeys());
				SMSURRESMessage message = MessageExtUtil.convertMessageExt(SMSURRESMessage.class, msg);
				if (null == message){
					continue;
                }
				int count = smsReceiveMapper.updateResult(message);
				if(count == 0){
					logger.error("MORES-ERR bizid:{}",message.getBizid());
				}
				

			}
			logger.info("MORES处理完成!");
		} catch (Exception e) {
			logger.error("MORES-ERR: {}", e);
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
