/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountAmountLogs;

/**
 * 账务变动日志DAO接口
 * @author zhukc
 * @version 2017-03-03
 */
@MyBatisDao
public interface BaseAccountAmountLogsDao extends CrudDao<BaseAccountAmountLogs> {
	
}