/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.sms.entity.SmsReport;
import com.sanerzone.smscenter.modules.sms.dao.SmsReportDao;

/**
 * 网关状态Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsReportService extends CrudService<SmsReportDao, SmsReport> {

	public SmsReport get(String id) {
		return super.get(id);
	}
	
	public List<SmsReport> findList(SmsReport smsReport) {
		return super.findList(smsReport);
	}
	
	public Page<SmsReport> findPage(Page<SmsReport> page, SmsReport smsReport) {
		return super.findPage(page, smsReport);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsReport smsReport) {
		super.save(smsReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsReport smsReport) {
		super.delete(smsReport);
	}
	
}