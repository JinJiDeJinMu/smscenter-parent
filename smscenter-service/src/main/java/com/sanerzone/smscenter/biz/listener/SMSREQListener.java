package com.sanerzone.smscenter.biz.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.common.support.utils.SystemClock;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.utils.GatewayHelper;
import com.sanerzone.smscenter.common.tools.MQHelper;

@Service
public class SMSREQListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(SMSREQListener.class);
	
	@Autowired
	public MQHelper mQUtils;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		for (MessageExt msg : msgs) {
			logger.info("SMSREQ listener recv message: topic:{}, tags:{}, msgid:{}, key:{}", msg.getTopic(), msg.getTags(),
					msg.getMsgId(), msg.getKeys());
			
			long startTime = System.currentTimeMillis();
			
			try {
				SMSREQMessage sMSREQMessage = (SMSREQMessage)FstObjectSerializeUtil.read(msg.getBody());
				
				logger.info("CMPP模拟网关,开始处理放库发送: 任务ID:{}, 用户:{}, 手机号码：{}, 接收时间:{} ", sMSREQMessage.getTaskid(), sMSREQMessage.getUserid(), sMSREQMessage.getPhones(), startTime);
				
				// 调用方法把 REQ 转换为 MT
				List<SMSMTMessage> list = GatewayHelper.getGateway(sMSREQMessage);
				for (SMSMTMessage mtMsg : list)
				{
					String msgid = mQUtils.sendSmsMT(mtMsg.getId(), mtMsg);
					
					if (msgid == null)
					{
						logger.error("网关：{}, body:{}",mtMsg.getGatewayId(), JSONUtils.toJSONString(mtMsg));
					}
				}
				
				logger.info("SMSREQ 模拟网关,发送结束: 任务ID:{}, 耗时:{}ms",sMSREQMessage.getTaskid(), SystemClock.now() - startTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
}
