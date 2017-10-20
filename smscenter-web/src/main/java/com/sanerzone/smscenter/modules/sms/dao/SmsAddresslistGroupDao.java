/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsAddresslistGroup;

/**
 * 群组管理DAO接口
 * @author zhukc
 * @version 2017-04-01
 */
@MyBatisDao
public interface SmsAddresslistGroupDao extends CrudDao<SmsAddresslistGroup> {
	
}