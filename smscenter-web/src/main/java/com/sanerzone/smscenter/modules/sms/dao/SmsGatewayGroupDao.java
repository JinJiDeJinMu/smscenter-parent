/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroup;

/**
 * 通道分组DAO接口
 * @author zhukc
 * @version 2017-06-29
 */
@MyBatisDao
public interface SmsGatewayGroupDao extends CrudDao<SmsGatewayGroup> {
	public com.sanerzone.smscenter.biz.entity.SmsGatewayGroup findBizEntity(String id);
	public void updateStatus(SmsGatewayGroup smsGatewayGroup);
}