/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsAddresslistGroup;
import com.sanerzone.smscenter.modules.sms.dao.SmsAddresslistGroupDao;

/**
 * 群组管理Service
 * @author zhukc
 * @version 2017-04-01
 */
@Service
@Transactional(readOnly = true)
public class SmsAddresslistGroupService extends CrudService<SmsAddresslistGroupDao, SmsAddresslistGroup> {

	public SmsAddresslistGroup get(String id) {
		return super.get(id);
	}
	
	public List<SmsAddresslistGroup> findList(SmsAddresslistGroup smsAddresslistGroup) {
		return super.findList(smsAddresslistGroup);
	}
	
	public Page<SmsAddresslistGroup> findPage(Page<SmsAddresslistGroup> page, SmsAddresslistGroup smsAddresslistGroup) {
		return super.findPage(page, smsAddresslistGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsAddresslistGroup smsAddresslistGroup) {
		SmsAddresslistGroup parent;
		
		if(smsAddresslistGroup.getParent() != null && StringUtils.isNotBlank(smsAddresslistGroup.getParent().getId()) && !"0".equals(smsAddresslistGroup.getParent().getId())){
			parent = get(smsAddresslistGroup.getParent().getId());
		}else{
			parent = new SmsAddresslistGroup();
			parent.setId("0");
			parent.setParentIds("");
			smsAddresslistGroup.setParent(parent);
		}
		smsAddresslistGroup.setParentIds(parent.getParentIds()+smsAddresslistGroup.getParent().getId()+",");
		if(StringUtils.isBlank(smsAddresslistGroup.getId())){
			smsAddresslistGroup.setCompanyId(UserUtils.getUser().getCompany().getId());
		}
		super.save(smsAddresslistGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsAddresslistGroup smsAddresslistGroup) {
		super.delete(smsAddresslistGroup);
	}
	
}