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
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.iface.PhoneConfigInterface;
import com.sanerzone.smscenter.common.utils.SysBlacklistUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsPhoneBlacklistDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneBlacklist;

/**
 * 系统黑名单Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsPhoneBlacklistService extends CrudService<SmsPhoneBlacklistDao, SmsPhoneBlacklist> {
	
	@Autowired
	private PhoneConfigInterface phoneConfigInterfaceImpl;
	
	public SmsPhoneBlacklist get(String id) {
		return super.get(id);
	}
	
	public List<SmsPhoneBlacklist> findList(SmsPhoneBlacklist smsPhoneBlacklist) {
		return super.findList(smsPhoneBlacklist);
	}
	
	public Page<SmsPhoneBlacklist> findPage(Page<SmsPhoneBlacklist> page, SmsPhoneBlacklist smsPhoneBlacklist) {
		return super.findPage(page, smsPhoneBlacklist);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsPhoneBlacklist smsPhoneBlacklist) {
		String phones = smsPhoneBlacklist.getPhone();
		if(StringUtils.isNotBlank(phones)){
			String remarks = smsPhoneBlacklist.getRemarks();
			String[] array = phones.split("\r\n");
			if(array != null && array.length > 0){
				User user = UserUtils.getUser();
				SmsPhoneBlacklist param;
				for (String phone : array) {
					param = new SmsPhoneBlacklist();
					param.setPhone(phone);
					param.setRemarks(remarks);
					param.setCreateBy(user);
					dao.insert(param);
					SysBlacklistUtils.put(phone);//缓存系统黑名单
				}
				phoneConfigInterfaceImpl.configSysBlacklist(1, "", array);//广播通知 批量增加黑名单
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsPhoneBlacklist smsPhoneBlacklist) {
		delete(smsPhoneBlacklist.getPhone());
	}
	
	@Transactional(readOnly = false)
	public void delete(String phone) {
		dao.deleteByPhone(phone);
		SysBlacklistUtils.del(phone);//删除缓存系统黑名单
		
		phoneConfigInterfaceImpl.configSysBlacklist(2, phone, null);//广播通知 删除黑名单
	}
	
}