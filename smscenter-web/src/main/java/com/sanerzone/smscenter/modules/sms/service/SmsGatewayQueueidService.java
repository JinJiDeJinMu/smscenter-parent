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
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayQueueidDao;

/**
 * 网关队列Service
 * @author zhukc
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true)
public class SmsGatewayQueueidService extends CrudService<SmsGatewayQueueidDao, SmsGatewayQueueid> {

	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;
	
	public SmsGatewayQueueid get(String id) {
		return super.get(id);
	}
	
	public List<SmsGatewayQueueid> findList(SmsGatewayQueueid smsGatewayQueueid) {
		return super.findList(smsGatewayQueueid);
	}
	
	public Page<SmsGatewayQueueid> findPage(Page<SmsGatewayQueueid> page, SmsGatewayQueueid smsGatewayQueueid) {
		return super.findPage(page, smsGatewayQueueid);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsGatewayQueueid smsGatewayQueueid) {
		super.save(smsGatewayQueueid);
		dubboGatewayQueue(smsGatewayQueueid.getGwCode());
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsGatewayQueueid smsGatewayQueueid) {
		super.delete(smsGatewayQueueid);
		dubboGatewayQueue(smsGatewayQueueid.getGwCode());
	}
	
	//dubbo通知网关队列
	public void dubboGatewayQueue(String gwCode){
		List<com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid> list = findBizByGwCode(gwCode);
		smsConfigInterfaceImpl.configGatewayQueue(1, gwCode, list);
	}
	
	private List<com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid> findBizByGwCode(String gwCode){
		Map<String,String> pMap = Maps.newHashMap();
		pMap.put("gwCode", gwCode);
		return dao.findBizByGwCode(pMap);
	}
	
}