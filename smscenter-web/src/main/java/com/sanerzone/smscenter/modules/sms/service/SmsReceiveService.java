/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.sms.entity.SmsReceive;
import com.sanerzone.smscenter.modules.sms.dao.SmsReceiveDao;

/**
 * 上行短信记录Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsReceiveService extends CrudService<SmsReceiveDao, SmsReceive> {

	public SmsReceive get(String id) {
		return super.get(id);
	}
	
	public List<SmsReceive> findList(SmsReceive smsReceive) {
		return super.findList(smsReceive);
	}
	
	public Page<SmsReceive> findPage(Page<SmsReceive> page, SmsReceive smsReceive) {
		return super.findPage(page, smsReceive);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsReceive smsReceive) {
		super.save(smsReceive);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsReceive smsReceive) {
		super.delete(smsReceive);
	}
	
}