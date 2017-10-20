package com.sanerzone.smscenter.gateway.http.message;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.gateway.base.GateWayMessageAbstract;

public class HttpGateWayMessage extends GateWayMessageAbstract
{
    
    @Override
    protected SMSSRMessage convertSRMessage(Serializable smsSrMessage)
    {
        return (SMSSRMessage) smsSrMessage;
    }
    
    @Override
    protected SMSRTMessage convertRTMessage(Serializable smsRtMessage)
    {
    	SMSRTMessage message = (SMSRTMessage)smsRtMessage;
        message.setPhone(StringHelper.resetPhone(message.getPhone()));
        return message;
    }
    
    @Override
    protected SMSMOMessage convertMOMessage(Serializable smsRoMessage)
    {
    	SMSMOMessage message = (SMSMOMessage)smsRoMessage;
        message.setPhone(StringHelper.resetPhone(message.getPhone()));
        return message;
    }
    
    @Override
    public Serializable convertMTMessage(SMSMTMessage message, boolean gatewaySign)
    {
//        String msgContent = message.getSmsContent();
//        
//        if (gatewaySign && StringUtils.isNotBlank(msgContent))
//        {
//            //去除短信头部签名
//            message.setMsgContent(msgContent.replaceAll("^\\【(.*?)\\】", ""));
//        }
//        message.setMsgContent(msgContent);
        
        return message;
    }
    
}
