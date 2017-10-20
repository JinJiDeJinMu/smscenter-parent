/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.modules.sms.dao.SmsTemplateDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;

/**
 * 短信模板Service
 * @author zhukc
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class SmsTemplateService extends CrudService<SmsTemplateDao, SmsTemplate> {
	
	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;

	public SmsTemplate get(String id) {
		return super.get(id);
	}
	
	public List<SmsTemplate> findList(SmsTemplate smsTemplate) {
		return super.findList(smsTemplate);
	}
	
	public Page<SmsTemplate> findPage(Page<SmsTemplate> page, SmsTemplate smsTemplate) {
		return super.findPage(page, smsTemplate);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsTemplate smsTemplate) {
		
		if("0".equals(smsTemplate.getScope())){//全局:创建人是登录人 
			smsTemplate.setCreateBy(UserUtils.getUser());
		}
		
		if(StringUtils.isNotBlank(smsTemplate.getId())){
			dao.update(smsTemplate);
		}else{
			dao.insert(smsTemplate);
		}
		cacheTemplate(smsTemplate.getId());
		
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsTemplate smsTemplate) {
		super.delete(smsTemplate);
		//广播通知
		smsConfigInterfaceImpl.configTemplate(2, smsTemplate.getId(), null);
	}
	
	//审核
	@Transactional(readOnly = false)
	public void recheckTemplate(SmsTemplate param){
		dao.recheckTemplate(param);
		cacheTemplate(param.getId());
	}
	
	private void cacheTemplate(String templateid){
		//SmsTemplate entity = get(templateid);
		//TemplateCacheHelper.put(templateid, entity);//本地缓存
		Map<String,String> map = Maps.newHashMap();
		map.put("id", templateid);
		com.sanerzone.smscenter.biz.entity.SmsTemplate entity = dao.findBizById(map);
		
		
		//广播通知
		smsConfigInterfaceImpl.configTemplate(1, templateid, entity);
	}
	
}