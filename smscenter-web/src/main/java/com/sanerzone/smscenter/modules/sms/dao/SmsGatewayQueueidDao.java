/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.List;
import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayQueueid;

/**
 * 网关队列DAO接口
 * @author zhukc
 * @version 2017-07-13
 */
@MyBatisDao
public interface SmsGatewayQueueidDao extends CrudDao<SmsGatewayQueueid> {
	public List<com.sanerzone.smscenter.biz.entity.SmsGatewayQueueid> findBizByGwCode(Map<String,String> map);
}