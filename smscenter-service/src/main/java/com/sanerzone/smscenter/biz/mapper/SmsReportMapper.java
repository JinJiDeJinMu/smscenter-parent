package com.sanerzone.smscenter.biz.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;

public interface SmsReportMapper extends BaseMapper<SMSRTMessage>{
	/**
	 * sms_report 入库
	 * @param data
	 */
	public Integer insert(DataEntity<SMSRTMessage> data);
}
