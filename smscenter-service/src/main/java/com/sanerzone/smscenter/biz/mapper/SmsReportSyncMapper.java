package com.sanerzone.smscenter.biz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sanerzone.smscenter.biz.entity.SmsReportSync;

public interface SmsReportSyncMapper extends BaseMapper<SmsReportSync>{
	/**
	 * sms_report_sync 入库
	 * @param data
	 * @return 
	 */
	public Integer insert(SmsReportSync data);
	
	public List<SmsReportSync> findList(SmsReportSync data);
	
	public void delete(SmsReportSync data);
}
