package com.sanerzone.smscenter.biz.listener;

import java.util.List;
import java.util.Map;

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
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.SmsSubmit;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

@Service
public class UPSMSSRListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(UPSMSSRListener.class);
	
	@Autowired
	public MQHelper mQUtils;
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		SqlSession sqlSession = SpringContextHelper.getBean(SqlSessionFactory.class).openSession();
        try
        {
            SMSURREQMessage sMSURREQMessage = null;
            for (MessageExt message : msgs)
            {
                logger.info("UPSMSSR,msgid:{}, key:{}", message.getMsgId(), message.getKeys());
                SMSSRMessage smsSrMessage = (SMSSRMessage)FstObjectSerializeUtil.read(message.getBody());
                if (smsSrMessage == null) {
                	logger.error("UPSMSSR-ERR-NULL,msgid:{}, key:{}", message.getMsgId(), message.getKeys());
                	continue;
                }
                
                //失败更新状态
            	if( StringHelper.equals(smsSrMessage.getResult(), "0") ) {
            		continue;
            	}
                	
                Map<String,Object> map = Maps.newHashMap();
                map.put("id", smsSrMessage.getMessage().getId());
                map.put("gatewayStatus", smsSrMessage.getResult());
                map.put("submitTime", smsSrMessage.getMessage().getSubmitTime());
                map.put("reportTime", smsSrMessage.getMessage().getReportTime());
                map.put("tableName", TableNameHelper.getSmsSendTable(smsSrMessage.getMessage().getId()));
                int count = sqlSession.update("com.sanerzone.smscenter.biz.mapper.SmsSendMapper.batchUpdateGatewayStatus", map);
				
                // 如果num=0,则说明MT还未入库就进行了更新，需要插入重试库，由定时任务扫描入库, 新建重试表，如果超过 48小时未同步，则告警
				if (count == 0)
				{
					SmsSubmit smsSubmit = new SmsSubmit();
                    smsSubmit.setMsgid(smsSrMessage.getMsgid());
                    smsSubmit.setResult(smsSrMessage.getResult());
                    smsSubmit.setBizid(smsSrMessage.getMessage().getId());
                    smsSubmit.setTaskid(smsSrMessage.getMessage().getSmsREQMessage().getTaskid());
                    smsSubmit.setUserid(smsSrMessage.getMessage().getSmsREQMessage().getUserid());
                    smsSubmit.setGatewayId(smsSrMessage.getMessage().getGatewayId());
                    smsSubmit.setReserve(smsSrMessage.getReserve());
                    smsSubmit.setPhone(smsSrMessage.getMessage().getPhone());
                    smsSubmit.setTableName("sms_submit_sync");
                    
                    sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsSubmitSyncMapper.insert", smsSubmit);
				}
				
                if(StringHelper.registeredDelivery(smsSrMessage.getMessage().getSmsREQMessage().getRegisteredDelivery())){
                	sMSURREQMessage = new SMSURREQMessage();
					sMSURREQMessage.setAccid(smsSrMessage.getMessage().getSmsREQMessage().getAccId());
					sMSURREQMessage.setBizid(smsSrMessage.getMessage().getId());
					sMSURREQMessage.setDoneTime(DateHelper.formatCmppDate(smsSrMessage.getMessage().getSubmitTime()));
					sMSURREQMessage.setGateWayId(smsSrMessage.getMessage().getGatewayId());
					sMSURREQMessage.setMsgid(smsSrMessage.getMsgid());
					sMSURREQMessage.setPhone(smsSrMessage.getMessage().getPhone());
					sMSURREQMessage.setSourceGateWayId(smsSrMessage.getMessage().getSmsREQMessage().getSourceGateWayId());
					sMSURREQMessage.setSpnumber(smsSrMessage.getMessage().getSpnumber());
					sMSURREQMessage.setStat(smsSrMessage.getResult());
					sMSURREQMessage.setSubmitTime(DateHelper.formatCmppDate(smsSrMessage.getMessage().getSubmitTime()));
					sMSURREQMessage.setTaskid(smsSrMessage.getMessage().getSmsREQMessage().getTaskid());
					sMSURREQMessage.setUserid(smsSrMessage.getMessage().getSmsREQMessage().getUserid());
					sMSURREQMessage.setMessageid(smsSrMessage.getMessage().getSmsREQMessage().getCustomServiceid());
					
                    mQUtils.sendSmsUR(smsSrMessage.getMessage().getId(), sMSURREQMessage);
                }
            }
        }
        catch (Exception e)
        {
            logger.error("UPSMSSR-ERR", e);
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
