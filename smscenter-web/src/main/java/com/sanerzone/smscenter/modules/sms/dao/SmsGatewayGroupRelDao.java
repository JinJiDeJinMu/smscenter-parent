/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.List;
import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroupRel;

/**
 * 通道分组关系DAO接口
 * @author zhukc
 * @version 2017-06-30
 */
@MyBatisDao
public interface SmsGatewayGroupRelDao extends CrudDao<SmsGatewayGroupRel> {
	public List<com.sanerzone.smscenter.biz.entity.SmsGatewayGroupRel> findBizByGroupId(Map<String,String> map);
}