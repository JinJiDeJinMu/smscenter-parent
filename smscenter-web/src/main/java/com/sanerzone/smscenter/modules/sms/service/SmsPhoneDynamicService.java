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
import com.sanerzone.smscenter.common.utils.UserBlacklistUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsPhoneDynamicDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneDynamic;

/**
 * 营销黑名单Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsPhoneDynamicService extends CrudService<SmsPhoneDynamicDao, SmsPhoneDynamic> {

	@Autowired
	private PhoneConfigInterface phoneConfigInterfaceImpl;
	
	public SmsPhoneDynamic get(String id) {
		return super.get(id);
	}
	
	public List<SmsPhoneDynamic> findList(SmsPhoneDynamic smsPhoneDynamic) {
		return super.findList(smsPhoneDynamic);
	}
	
	public Page<SmsPhoneDynamic> findPage(Page<SmsPhoneDynamic> page, SmsPhoneDynamic smsPhoneDynamic) {
		return super.findPage(page, smsPhoneDynamic);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsPhoneDynamic smsPhoneDynamic) {
		String phones = smsPhoneDynamic.getPhone();
		if(StringUtils.isNotBlank(phones)){
			String[] array = phones.split("\r\n");
			if(array != null && array.length > 0){
				String userid = smsPhoneDynamic.getUserid();
				String type = smsPhoneDynamic.getType();
				String remarks = smsPhoneDynamic.getRemarks();
				User user = UserUtils.getUser();
				SmsPhoneDynamic param;
				for (String phone : array) {
					param = new SmsPhoneDynamic();
					param.setUserid(userid);
					param.setPhone(phone);
					param.setType(type);
					param.setRemarks(remarks);
					param.setCreateBy(user);
					dao.insert(param);
					UserBlacklistUtils.put(userid,phone);
				}
				phoneConfigInterfaceImpl.configUserBlacklist(1, "", userid, array);//广播通知 批量增加营销黑名单
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsPhoneDynamic smsPhoneDynamic) {
		super.delete(smsPhoneDynamic);
		UserBlacklistUtils.del(smsPhoneDynamic.getUserid(), smsPhoneDynamic.getPhone());
		phoneConfigInterfaceImpl.configUserBlacklist(2, smsPhoneDynamic.getPhone(), smsPhoneDynamic.getUserid(), null);//广播通知删除营销黑名单
	}
	
}