/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneInfo;
import com.sanerzone.smscenter.common.utils.PhoneUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsPhoneInfoDao;

/**
 * 号段Service
 * @author zhukc
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class SmsPhoneInfoService extends CrudService<SmsPhoneInfoDao, SmsPhoneInfo> {

	public SmsPhoneInfo get(String id) {
		return super.get(id);
	}
	
	public List<SmsPhoneInfo> findList(SmsPhoneInfo smsPhoneInfo) {
		return super.findList(smsPhoneInfo);
	}
	
	public Page<SmsPhoneInfo> findPage(Page<SmsPhoneInfo> page, SmsPhoneInfo smsPhoneInfo) {
		return super.findPage(page, smsPhoneInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsPhoneInfo smsPhoneInfo) {
		//super.save(smsPhoneInfo);
		dao.insert(smsPhoneInfo);
		PhoneUtils.put(smsPhoneInfo.getPhone(), smsPhoneInfo.getPhoneType(), smsPhoneInfo.getPhoneCityCode());//缓存号段
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsPhoneInfo smsPhoneInfo) {
		super.delete(smsPhoneInfo);
	}
	
}