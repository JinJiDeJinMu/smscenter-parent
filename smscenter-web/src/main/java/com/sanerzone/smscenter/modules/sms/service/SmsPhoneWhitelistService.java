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
import com.sanerzone.smscenter.common.utils.WhitelistUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsPhoneWhitelistDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneWhitelist;

/**
 * 系统白名单Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsPhoneWhitelistService extends CrudService<SmsPhoneWhitelistDao, SmsPhoneWhitelist> {

	@Autowired
	private SmsPhoneBlacklistService smsPhoneBlacklistService;
	
	@Autowired
	private PhoneConfigInterface phoneConfigInterfaceImpl;
	
	public SmsPhoneWhitelist get(String id) {
		return super.get(id);
	}
	
	public List<SmsPhoneWhitelist> findList(SmsPhoneWhitelist smsPhoneWhitelist) {
		return super.findList(smsPhoneWhitelist);
	}
	
	public Page<SmsPhoneWhitelist> findPage(Page<SmsPhoneWhitelist> page, SmsPhoneWhitelist smsPhoneWhitelist) {
		return super.findPage(page, smsPhoneWhitelist);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsPhoneWhitelist smsPhoneWhitelist) {
		String phones = smsPhoneWhitelist.getPhone();
		if(StringUtils.isNotBlank(phones)){
			String remarks = smsPhoneWhitelist.getRemarks();
			String[] array = phones.split("\r\n");
			if(array != null && array.length > 0){
				User user = UserUtils.getUser();
				SmsPhoneWhitelist param;
				for (String phone : array) {
					param = new SmsPhoneWhitelist();
					param.setPhone(phone);
					param.setRemarks(remarks);
					param.setCreateBy(user);
					dao.insert(param);
					if(SysBlacklistUtils.isExist(phone)){
						smsPhoneBlacklistService.delete(phone);//剔除黑名单
					}
					WhitelistUtils.put(phone);//缓存白名单
				}
				phoneConfigInterfaceImpl.configWhitelist(1, "", array);//广播通知 批量增加白名单
				phoneConfigInterfaceImpl.configSysBlacklist(3, "", array);//广播通知 批量删除黑名单
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsPhoneWhitelist smsPhoneWhitelist) {
		delete(smsPhoneWhitelist.getPhone());
	}
	
	@Transactional(readOnly = false)
	public void delete(String phone) {
		dao.deleteByPhone(phone);
		WhitelistUtils.del(phone);//删除白名单
		
		phoneConfigInterfaceImpl.configWhitelist(2, phone, null);//广播通知 删除白名单
	}
	
}