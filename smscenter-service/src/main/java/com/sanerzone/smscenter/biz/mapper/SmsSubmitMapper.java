package com.sanerzone.smscenter.biz.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.DataEntity;
import com.sanerzone.smscenter.biz.entity.SmsSubmit;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;

public interface SmsSubmitMapper extends BaseMapper<SmsSubmit>{
	/**
	 * sms_submit 入库
	 * @param data
	 * @return 
	 */
	public Integer insert(DataEntity<SMSSRMessage> data);
}
