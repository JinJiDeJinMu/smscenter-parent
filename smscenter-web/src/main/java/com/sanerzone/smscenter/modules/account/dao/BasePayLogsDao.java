/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.account.entity.BasePayLogs;

/**
 * 自助充值日志DAO接口
 * @author zhukc
 * @version 2017-08-21
 */
@MyBatisDao
public interface BasePayLogsDao extends CrudDao<BasePayLogs> {
	public BasePayLogs getByParam(BasePayLogs basePayLogs);
	public int updateStatus(BasePayLogs basePayLogs);
}