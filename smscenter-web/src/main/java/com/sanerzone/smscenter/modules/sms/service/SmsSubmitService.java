/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.sms.entity.SmsSubmit;
import com.sanerzone.smscenter.modules.sms.dao.SmsSubmitDao;

/**
 * 网关提交Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsSubmitService extends CrudService<SmsSubmitDao, SmsSubmit> {

	public SmsSubmit get(String id) {
		return super.get(id);
	}
	
	public List<SmsSubmit> findList(SmsSubmit smsSubmit) {
		return super.findList(smsSubmit);
	}
	
	public Page<SmsSubmit> findPage(Page<SmsSubmit> page, SmsSubmit smsSubmit) {
		return super.findPage(page, smsSubmit);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsSubmit smsSubmit) {
		super.save(smsSubmit);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsSubmit smsSubmit) {
		super.delete(smsSubmit);
	}
	
}