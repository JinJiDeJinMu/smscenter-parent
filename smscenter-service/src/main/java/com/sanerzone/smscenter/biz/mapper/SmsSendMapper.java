package com.sanerzone.smscenter.biz.mapper;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.entity.SmsSend;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;

public interface SmsSendMapper extends BaseMapper<SmsSend>{
	/**
	 * sms_send_x 入库
	 * @param data
	 */
	public Integer insert(DataEntity<SMSMTMessage> data);
	
	public void batchUpdateSendStatus(Map<String,Object> map);
	
	public void batchUpdateGatewayStatus(Map<String,Object> map);
	
	public void updatePushFlag(Map<String,Object> map);
}
