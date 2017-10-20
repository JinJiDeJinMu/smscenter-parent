/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoice;

/**
 * 用户发票DAO接口
 * @author zhukc
 * @version 2017-08-16
 */
@MyBatisDao
public interface BaseUserInvoiceDao extends CrudDao<BaseUserInvoice> {
	
   public String getUserAmount(BaseUserInvoice baseUserInvoice);
	   
   
}