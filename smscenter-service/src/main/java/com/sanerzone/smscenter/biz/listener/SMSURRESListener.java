package com.sanerzone.smscenter.biz.listener;

import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.message.SMSURRESMessage;
import com.sanerzone.smscenter.biz.utils.MessageExtUtil;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;

@Service
public class SMSURRESListener implements MessageListenerConcurrently{
	private Logger logger = LoggerFactory.getLogger(SMSURRESListener.class);
	
	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		Map<String,String> sendMap;
		try {
			for (MessageExt msg : msgs) {
				SMSURRESMessage smsPrMessage = MessageExtUtil.convertMessageExt(SMSURRESMessage.class, msg);
				
				if (null == smsPrMessage) {
					continue;
				}
				sendMap = Maps.newHashMap();
				String bizid = smsPrMessage.getBizid();
				String pushFlag = "0".equals(smsPrMessage.getResult()) ? "2" : "3";
				sendMap.put("pushFlag", pushFlag);
				sendMap.put("id", smsPrMessage.getBizid());
				sendMap.put("tableName", TableNameHelper.getSmsSendTable(bizid));
				sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsSendMapper.updatePushFlag", sendMap);
			}
			sqlSession.commit();
		}catch(Exception e){
			logger.error("{}", e);
		} finally{
			if(sqlSession != null){
				sqlSession.close();
			}
		}
				
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}
	
}
