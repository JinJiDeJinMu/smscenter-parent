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
import com.sanerzone.smscenter.modules.sms.entity.SmsGateway;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayDao;

/**
 * 通道信息Service
 * @author zhukc
 * @version 2017-06-29
 */
@Service
@Transactional(readOnly = true)
public class SmsGatewayService extends CrudService<SmsGatewayDao, SmsGateway> {

	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;
	
	public SmsGateway get(String id) {
		return super.get(id);
	}
	
	public List<SmsGateway> findList(SmsGateway smsGateway) {
		return super.findList(smsGateway);
	}
	
	public Page<SmsGateway> findPage(Page<SmsGateway> page, SmsGateway smsGateway) {
		return super.findPage(page, smsGateway);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsGateway smsGateway) {
		super.save(smsGateway);
		dobboGateway(smsGateway.getGwCode());
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsGateway smsGateway) {
		super.delete(smsGateway);
	}
	
	private void dobboGateway(String gwCode){
		com.sanerzone.smscenter.biz.entity.SmsGateway entity = dao.findBizEntity(gwCode);
		smsConfigInterfaceImpl.configGatewayInfo(1, entity, gwCode);
	}
	
	
}