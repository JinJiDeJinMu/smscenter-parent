package com.sanerzone.smscenter.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsSubmitSync;

public interface SmsSubmitSyncMapper extends BaseMapper<SmsSubmitSync>{
	/**
	 * sms_submit_sync 入库
	 * @param data
	 * @return 
	 */
	public Integer insert(SmsSubmitSync data);
	
	public List<SmsSubmitSync> findList(SmsSubmitSync smsSubmitSync);
	
	public void delete(SmsSubmitSync data);
}
