/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsKeywords;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.common.utils.KeywordsUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsKeywordsDao;

/**
 * 敏感词Service
 * @author zhukc
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class SmsKeywordsService extends CrudService<SmsKeywordsDao, SmsKeywords> {

	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;
	
	public SmsKeywords get(String id) {
		return super.get(id);
	}
	
	public List<SmsKeywords> findList(SmsKeywords smsKeywords) {
		return super.findList(smsKeywords);
	}
	
	public Page<SmsKeywords> findPage(Page<SmsKeywords> page, SmsKeywords smsKeywords) {
		return super.findPage(page, smsKeywords);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsKeywords smsKeywords) {
		smsKeywords.setCreateBy(UserUtils.getUser());
		dao.insert(smsKeywords);
		KeywordsUtils.put(smsKeywords.getKeywords());
		smsConfigInterfaceImpl.configKeywords(1, smsKeywords.getKeywords());
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsKeywords smsKeywords) {
		super.delete(smsKeywords);
		KeywordsUtils.del(smsKeywords.getKeywords());
		smsConfigInterfaceImpl.configKeywords(2, smsKeywords.getKeywords());
	}
	
}