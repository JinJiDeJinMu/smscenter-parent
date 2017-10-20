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
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.biz.iface.SmsConfigInterface;
import com.sanerzone.smscenter.modules.sms.dao.SmsGatewayGroupRelDao;

/**
 * 通道分组关系Service
 * @author zhukc
 * @version 2017-06-30
 */
@Service
@Transactional(readOnly = true)
public class SmsGatewayGroupRelService extends CrudService<SmsGatewayGroupRelDao, SmsGatewayGroupRel> {

	@Autowired
	private SmsConfigInterface smsConfigInterfaceImpl;
	
	public SmsGatewayGroupRel get(String id) {
		return super.get(id);
	}
	
	public List<SmsGatewayGroupRel> findList(SmsGatewayGroupRel smsGatewayGroupRel) {
		return super.findList(smsGatewayGroupRel);
	}
	
	public Page<SmsGatewayGroupRel> findPage(Page<SmsGatewayGroupRel> page, SmsGatewayGroupRel smsGatewayGroupRel) {
		return super.findPage(page, smsGatewayGroupRel);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsGatewayGroupRel smsGatewayGroupRel) {
		super.save(smsGatewayGroupRel);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsGatewayGroupRel smsGatewayGroupRel) {
		super.delete(smsGatewayGroupRel);
	}
	
	public List<com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel> findBizByGroupId(String groupId) {
		Map<String,String> map = Maps.newHashMap();
		map.put("groupId", groupId);
		return dao.findBizByGroupId(map);
	}
	
	//dubbo通知 网关关系
	public void dubboGatewayRel(String groupId){
		List<com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel> list = findBizByGroupId(groupId);
		smsConfigInterfaceImpl.configGatewayRel(1, groupId, list);
	}
	
	//dubbo通知 刷新所有的 网关关系 
	public void refreshCache(){
		List<com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel> list = findBizByGroupId("");
		smsConfigInterfaceImpl.configGatewayRel(3, "", list);
	}
	
}