package com.sanerzone.smscenter.biz.listener;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

@Service
public class INSMSSRListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(INSMSSRListener.class);

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		SqlSession sqlSession = SpringContextHelper.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
        try
        {
            int num = 0;
            DataEntity<SMSSRMessage> dataEntity = null;
            
            for (MessageExt message : msgs)
            {
                logger.info("INSMSSR:msgid:{}, key:{}", message.getMsgId(), message.getKeys());
                SMSSRMessage smsSrMessage = (SMSSRMessage)FstObjectSerializeUtil.read(message.getBody());
                if (smsSrMessage == null || smsSrMessage.getMessage() == null
                		|| smsSrMessage.getMessage().getSmsREQMessage() == null ) {
                	logger.error("INSMSSR-ERR-NULL, msgid:{}, key:{}, 解析异常", message.getMsgId(), message.getKeys());
                	continue;
                }
                
            	dataEntity = new DataEntity<SMSSRMessage>(TableNameHelper.getSmsSubmitTable(smsSrMessage.getMessage().getId()), smsSrMessage);
                sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsSubmitMapper.insert", dataEntity);
                
                num++;
                
                if (num % 200 == 0)
                {
                    sqlSession.commit();
                }
            }
            sqlSession.commit();
        }
        catch (Exception e)
        {
            logger.error("INSMSSR-ERR", e);
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
