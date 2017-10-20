package com.sanerzone.smscenter.biz.listener;

import java.util.List;

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
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

@Service
public class INSMSMTListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(INSMSMTListener.class);
	
	@Autowired
	public MQHelper mQUtils;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		SqlSession sqlSession = SpringContextHelper.getBean(SqlSessionFactory.class).openSession();
		
		int index =0;
		DataEntity<SMSMTMessage> dataEntity = null;
		SMSMTMessage smsMTMessage = null;
		SMSURREQMessage sMSURREQMessage = null;
			
		try {
			for (MessageExt msg : msgs) 
			{
				logger.info("mt listener recv message: topic:{}, tags:{}, msgid:{}, key:{}", msg.getTopic(), msg.getTags(),
						msg.getMsgId(), msg.getKeys());
				
				smsMTMessage = (SMSMTMessage)FstObjectSerializeUtil.read(msg.getBody());
			
				if (null != smsMTMessage && smsMTMessage.getSmsREQMessage() != null)
                {
					index++;
					dataEntity = new DataEntity<SMSMTMessage>(TableNameHelper.getSmsSendTable(smsMTMessage.getId()), smsMTMessage);
					
                    sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsSendMapper.insert", dataEntity);
                    
                    if(index % 500 == 0) {
                        sqlSession.commit();
                    }
                    
                    //推送失败UR
                    if(smsMTMessage.getSendStatus().startsWith("F") && 
                    		StringHelper.registeredDelivery(smsMTMessage.getSmsREQMessage().getRegisteredDelivery())){
                    	String time = DateHelper.formatCmppDate(smsMTMessage.getSubmitTime());
                    	sMSURREQMessage = new SMSURREQMessage();
						sMSURREQMessage.setAccid(smsMTMessage.getSmsREQMessage().getAccId());
						sMSURREQMessage.setBizid(smsMTMessage.getId());
						sMSURREQMessage.setDoneTime(time);
						sMSURREQMessage.setGateWayId(smsMTMessage.getGatewayId());
						sMSURREQMessage.setMsgid(smsMTMessage.getId());
						sMSURREQMessage.setPhone(smsMTMessage.getPhone());
						sMSURREQMessage.setSourceGateWayId(smsMTMessage.getSmsREQMessage().getSourceGateWayId());
						sMSURREQMessage.setSpnumber(smsMTMessage.getSmsREQMessage().getSendnumber());
						sMSURREQMessage.setStat(smsMTMessage.getSendStatus());
						sMSURREQMessage.setSubmitTime(time);
						sMSURREQMessage.setTaskid(smsMTMessage.getSmsREQMessage().getTaskid());
						sMSURREQMessage.setUserid(smsMTMessage.getSmsREQMessage().getUserid());
						
                        mQUtils.sendSmsUR(smsMTMessage.getId(), sMSURREQMessage);
                    }
                }
				else
				{
					logger.error("INSMSMT-ERR：msgid:{}, key:{}", msg.getMsgId(), msg.getKeys());
				}
			}
			
			sqlSession.commit();
		} 
		catch (Exception e) 
		{
			logger.error("INSMSMT-ERR", e);
		}
		finally
		{
			if (sqlSession != null)
            {
                sqlSession.close();
            }
		}
			
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
