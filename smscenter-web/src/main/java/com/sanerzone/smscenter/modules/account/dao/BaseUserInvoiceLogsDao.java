/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoiceLogs;

/**
 * 用户发票明细DAO接口
 * @author zhukc
 * @version 2017-08-16
 */
@MyBatisDao
public interface BaseUserInvoiceLogsDao extends CrudDao<BaseUserInvoiceLogs> {
	public void updateStatus(BaseUserInvoiceLogs baseUserInvoiceLogs);
}