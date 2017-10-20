package com.sanerzone.test;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.MessageTopic;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;

public class TestMQ {
	public static void main(String[] args) {
		DefaultMQProducer smsSRProducer = MQProducerFactory.getMQProducer("TingfvTestSMSSRGroup","TingfvTestSMSSRInstance");
        
        try {
			smsSRProducer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
        
        //RT
        SMSRTMessage sMSRTMessage = new SMSRTMessage();
        SMSMTMessage sMSMTMessage = new SMSMTMessage();
        SMSREQMessage sMSREQMessage = new SMSREQMessage();
        
        Message msg;
		try {
			sMSREQMessage.setUserid("6");
			sMSREQMessage.setTaskid(new MsgId().toString());
			sMSREQMessage.setMsgContent("上行测试");
			sMSMTMessage.setSmsREQMessage(sMSREQMessage);
			
			sMSMTMessage.setId(new MsgId().toString());
			sMSMTMessage.setPhone("15996480329");
			
			sMSRTMessage.setMsgid(new MsgId().toString());
			sMSRTMessage.setGateWayID("2");
			sMSRTMessage.setSpnumber("15996480329");
			sMSRTMessage.setPhone("1234567");
			sMSRTMessage.setStat("DELIVRD");
			sMSRTMessage.setSmscSequence("123456");
			sMSRTMessage.setDoneTime(formatDate(new Date()));
			sMSRTMessage.setSubmitTime(formatDate(new Date()));
			
			sMSRTMessage.setSmsMt(sMSMTMessage);
			
			msg = new Message(MessageTopic.SMSRT, "8988", "123456789", FstObjectSerializeUtil.write(sMSRTMessage));
			
			SendResult result = smsSRProducer.send(msg);
			System.out.println(JSON.toJSONString(result));
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private static String formatDate(Date date)
    {
        String time = DateFormatUtils.format(new Date(), "yyMMddHHmm");
        
        if (null != date)
        {
            time = DateFormatUtils.format(date, "yyMMddHHmm");
        }
        
        return time;
    }
}
