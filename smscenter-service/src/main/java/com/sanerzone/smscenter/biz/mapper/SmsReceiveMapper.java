package com.sanerzone.smscenter.biz.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.entity.SmsReceive;
import com.sanerzone.smscenter.biz.message.SMSURREQMessage;
import com.sanerzone.smscenter.biz.message.SMSURRESMessage;

public interface SmsReceiveMapper extends BaseMapper<SmsReceive>{
	/**
	 * sms_receive 入库
	 * @param data
	 */
	public Integer insert(DataEntity<SmsReceive> data);
	
	public Integer insertSmsReceive(SMSURREQMessage reqMessage);
	
	public Integer updateResult(SMSURRESMessage message);
}
