package com.sanerzone.smscenter.gateway.base;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.common.message.Message;
import com.sanerzone.common.support.utils.FstObjectSerializeUtil;
import com.sanerzone.smscenter.biz.entity.MessageTopic;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.common.tools.MQHelper;

/**
 * 调用RocketMq,将接收的网关消息推送到业务系统 状态报告、短信上行
 * 
 * @author mac
 */
public abstract class GateWayMessageAbstract {

	private Logger logger = LoggerFactory.getLogger(GateWayMessageAbstract.class);
	private String appCode;
	private MQHelper mqHelper;
	
	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public MQHelper getMqHelper() {
		return mqHelper;
	}

	public void setMqHelper(MQHelper mqHelper) {
		this.mqHelper = mqHelper;
	}

	public void sendSmsSRMessage(Serializable message, String gateWayID) {
		SMSSRMessage smsSrMessage = convertSRMessage(message);
		try
        {
		    Message msg = new Message(MessageTopic.SMSSR, appCode, smsSrMessage.getMsgid(),
                FstObjectSerializeUtil.write(smsSrMessage));
		    mqHelper.send(msg, null);
        }
        catch (Exception e)
        {
            logger.error("网关响应异常", e);
        }
	}

	public void sendSmsRTMessage(Serializable message, String gateWayID) {
		SMSRTMessage smsRtMessage = convertRTMessage(message);
		smsRtMessage.setGateWayID(gateWayID);
		try {
			Message msg = new Message(MessageTopic.SMSRT, appCode, smsRtMessage.getMsgid(),
					FstObjectSerializeUtil.write(smsRtMessage));
			mqHelper.send(msg, null);
		} catch (Exception e) {
			logger.info(appCode + "状态报告消息序列化异常", e);
		}

	}

	public void sendSmsMOMessage(Serializable message, String gateWayID) {
		SMSMOMessage smsMoMessage = convertMOMessage(message);
		smsMoMessage.setGateWayID(gateWayID);
		try {
			Message msg = new Message(MessageTopic.SMSMO, appCode, smsMoMessage.getMsgid(),
					FstObjectSerializeUtil.write(smsMoMessage));
			mqHelper.send(msg, null);
		} catch (Exception e) {
			logger.info(appCode + "用户上行消息序列化异常", e);
		}
	}

	/**
	 * 提交网关结果
	 * 
	 * @param smsSrMessage
	 * @return
	 */
	protected abstract SMSSRMessage convertSRMessage(Serializable smsSrMessage);

	protected abstract SMSRTMessage convertRTMessage(Serializable smsRtMessage);

	protected abstract SMSMOMessage convertMOMessage(Serializable smsRoMessage);

	public abstract Serializable convertMTMessage(SMSMTMessage smsMtMessage, boolean gatewaySign);
}
