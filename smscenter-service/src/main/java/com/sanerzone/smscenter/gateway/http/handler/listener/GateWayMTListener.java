package com.sanerzone.smscenter.gateway.http.handler.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.gateway.base.GatewayFactory;
import com.sanerzone.smscenter.gateway.base.Result;

public class GateWayMTListener implements MessageListenerConcurrently
{
    Logger logger = LoggerFactory.getLogger(GateWayMTListener.class);
    
    private GatewayFactory gatewayFactory;
    
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context)
    {
        Result result = null;
        for (MessageExt message : msgs)
        {
            logger.info("http listener recv mt, topic:{}, tag:{}, msgid:{}, key:{}", message.getTopic(), message.getTags(), message.getMsgId(), message.getKeys());
            SMSMTMessage smsMtMessage = null;
            try
            {
                smsMtMessage = (SMSMTMessage)FstObjectSerializeUtil.read(message.getBody());
                if (smsMtMessage != null)
                {
                    result = gatewayFactory.sendMsg(smsMtMessage);
                }
                else
                {
                    result = new Result("F10106", "消息解析无数据");
                    smsMtMessage = new SMSMTMessage();
                    smsMtMessage.setId(message.getKeys());
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                result = new Result("F10107", "消息解析异常");
                smsMtMessage = new SMSMTMessage();
                smsMtMessage.setId(message.getKeys());
            }
            //失败，放入队列
            if (!result.isSuccess())
            {
            	SMSSRMessage smsSrMessage = new SMSSRMessage(message.getMsgId(), result.getErrorCode(), smsMtMessage);
                smsSrMessage.setReserve(result.getErrorMsg());
                try
                {
                	 this.gatewayFactory.sendSmsSRMessage(smsSrMessage, smsMtMessage.getGatewayId());
                }
                catch (Exception e)
                {
                    logger.error("msgid:{}, key:{}, 消息解析异常:{}", message.getMsgId(), message.getKeys(), e.getMessage());
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
    
    public void setGatewayFactory(GatewayFactory gatewayFactory)
    {
        this.gatewayFactory = gatewayFactory;
    }

}
