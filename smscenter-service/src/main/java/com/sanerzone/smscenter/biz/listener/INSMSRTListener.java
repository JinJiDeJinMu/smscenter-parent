package com.sanerzone.smscenter.biz.listener;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

@Service
public class INSMSRTListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(INSMSRTListener.class);
	
	@Autowired
	public MQHelper mQUtils;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
		SqlSession sqlSession = SpringContextHelper.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
		try {
			int index = 0;
			DataEntity<SMSRTMessage> dataEntity = null;
			SMSRTMessage smsRTMessage = null;
			SMSURREQMessage sMSURREQMessage = null;
			
			for (MessageExt msg : msgs) {
				smsRTMessage = (SMSRTMessage) FstObjectSerializeUtil.read(msg.getBody());
				
				if(smsRTMessage == null || smsRTMessage.getSmsMt()== null 
						|| smsRTMessage.getSmsMt().getSmsREQMessage() == null ) {
					logger.error("INSMSRT-ERR-NULL,msgid:{}, key:{}", msg.getMsgId(), msg.getKeys());
					continue;
				}
				
				dataEntity = new DataEntity<SMSRTMessage>(TableNameHelper.getSmsReportTable(smsRTMessage.getSmsMt().getId()), smsRTMessage);
				sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsReportMapper.insert", dataEntity);

				if(StringHelper.registeredDelivery(smsRTMessage.getSmsMt().getSmsREQMessage().getRegisteredDelivery())){
                	sMSURREQMessage = new SMSURREQMessage();
					sMSURREQMessage.setAccid(smsRTMessage.getSmsMt().getSmsREQMessage().getAccId());
					sMSURREQMessage.setBizid(smsRTMessage.getSmsMt().getId());
					sMSURREQMessage.setDoneTime(DateHelper.formatCmppDate(smsRTMessage.getSmsMt().getSubmitTime()));
					sMSURREQMessage.setGateWayId(smsRTMessage.getSmsMt().getGatewayId());
					sMSURREQMessage.setMsgid(smsRTMessage.getMsgid());
					sMSURREQMessage.setPhone(smsRTMessage.getSmsMt().getPhone());
					sMSURREQMessage.setSourceGateWayId(smsRTMessage.getSmsMt().getSmsREQMessage().getSourceGateWayId());
					sMSURREQMessage.setSpnumber(smsRTMessage.getSmsMt().getSpnumber());
					sMSURREQMessage.setStat(smsRTMessage.getStat());
					sMSURREQMessage.setSubmitTime(DateHelper.formatCmppDate(smsRTMessage.getSmsMt().getSubmitTime()));
					sMSURREQMessage.setTaskid(smsRTMessage.getSmsMt().getSmsREQMessage().getTaskid());
					sMSURREQMessage.setUserid(smsRTMessage.getSmsMt().getSmsREQMessage().getUserid());
					sMSURREQMessage.setMessageid(smsRTMessage.getSmsMt().getSmsREQMessage().getCustomTaskid());
					
                    mQUtils.sendSmsUR(smsRTMessage.getSmsMt().getId(), sMSURREQMessage);
                }
				
				index++;
				if (index % 200 == 0) {
					sqlSession.commit();
				}
			}
			sqlSession.commit();

		} catch (Exception e) {
			logger.error("INSMSRT-ERR", e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
