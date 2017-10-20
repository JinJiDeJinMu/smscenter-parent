package com.sanerzone.smscenter.biz.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.utils.TableNameHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

@Service
public class UPSMSRTListener implements MessageListenerConcurrently {

	public static Logger logger = LoggerFactory.getLogger(UPSMSRTListener.class);
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,ConsumeConcurrentlyContext context) {
		SqlSession sqlSession = SpringContextHelper.getBean(SqlSessionFactory.class).openSession(ExecutorType.BATCH, false);
		try {
			int index = 0;
			SMSRTMessage smsRTMessage = null;
			
			for (MessageExt msg : msgs) {
				smsRTMessage = (SMSRTMessage) FstObjectSerializeUtil.read(msg.getBody());
				if(smsRTMessage == null || smsRTMessage.getSmsMt() == null){
					logger.error("UPSMSRT-ERR-NULL:msgid:{}, key:{}", msg.getMsgId(), msg.getKeys());
					continue;
				}
				
				
				//更新状态
				Map<String,Object> map = Maps.newHashMap();
				map.put("id", smsRTMessage.getSmsMt().getId());
				if("DELIVRD".equals(smsRTMessage.getStat())){//成功
					map.put("gatewayStatus", "T100");
				}else{
					map.put("gatewayStatus", "F2" + smsRTMessage.getStat());//失败
				}
				map.put("submitTime", smsRTMessage.getSmsMt().getSubmitTime());
                map.put("reportTime", smsRTMessage.getReportReceiveTime());
                map.put("tableName", TableNameHelper.getSmsSendTable(smsRTMessage.getSmsMt().getId()));
				int num = sqlSession.update("com.sanerzone.smscenter.biz.mapper.SmsSendMapper.batchUpdateGatewayStatus", map);
				
				
				if (num == 0)
				{
					Map<String, Object> mapO = new HashMap<String, Object>();
					mapO.put("tableName", "sms_report_sync");
					mapO.put("data", smsRTMessage);
					
					sqlSession.insert("com.sanerzone.smscenter.biz.mapper.SmsReportSyncMapper.insert", mapO);
				}
					
				index++;
				if (index % 200 == 0) {
					sqlSession.commit();
				}
			}
			sqlSession.commit();

		} catch (Exception e) {
			logger.error("UPSMSRT-ERR", e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

}
