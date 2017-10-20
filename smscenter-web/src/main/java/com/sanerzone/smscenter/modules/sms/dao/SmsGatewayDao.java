/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsGateway;

/**
 * 通道信息DAO接口
 * @author zhukc
 * @version 2017-06-29
 */
@MyBatisDao
public interface SmsGatewayDao extends CrudDao<SmsGateway> {
	public com.sanerzone.smscenter.biz.entity.SmsGateway findBizEntity(String gwCode);
}