/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsReceive;

/**
 * 上行短信记录DAO接口
 * @author zhukc
 * @version 2017-06-26
 */
@MyBatisDao
public interface SmsReceiveDao extends CrudDao<SmsReceive> {
	
}