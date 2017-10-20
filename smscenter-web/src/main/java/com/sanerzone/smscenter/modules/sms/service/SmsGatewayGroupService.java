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
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroup;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayGroupDao;

/**
 * 通道分组Service
 * @author zhukc
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class SmsGatewayGroupService extends CrudService<SmsGatewayGroupDao, SmsGatewayGroup> {

	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;
	
	public SmsGatewayGroup get(String id) {
		return super.get(id);
	}
	
	public List<SmsGatewayGroup> findList(SmsGatewayGroup smsGatewayGroup) {
		return super.findList(smsGatewayGroup);
	}
	
	public Page<SmsGatewayGroup> findPage(Page<SmsGatewayGroup> page, SmsGatewayGroup smsGatewayGroup) {
		return super.findPage(page, smsGatewayGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsGatewayGroup smsGatewayGroup) {
		super.save(smsGatewayGroup);
		dobboGatewayGroup(smsGatewayGroup.getId());
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsGatewayGroup smsGatewayGroup) {
		super.delete(smsGatewayGroup);
		smsConfigInterfaceImpl.configGatewayGroup(2, null, smsGatewayGroup.getId());
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(SmsGatewayGroup smsGatewayGroup){
		dao.updateStatus(smsGatewayGroup);
		dobboGatewayGroup(smsGatewayGroup.getId());
	}
	
	//dobbo通知
	private void dobboGatewayGroup(String id){
		com.sanerzone.smscenter.biz.entity.SmsGatewayGroup entity = dao.findBizEntity(id);
		smsConfigInterfaceImpl.configGatewayGroup(1, entity, id);
	}
	
}