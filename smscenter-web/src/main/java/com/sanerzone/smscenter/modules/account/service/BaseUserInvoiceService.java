/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoice;
import com.sanerzone.smscenter.modules.account.dao.BaseUserInvoiceDao;

/**
 * 用户发票Service
 * @author zhukc
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class BaseUserInvoiceService extends CrudService<BaseUserInvoiceDao, BaseUserInvoice> {

	public BaseUserInvoice get(String id) {
		return super.get(id);
	}
	
	public List<BaseUserInvoice> findList(BaseUserInvoice baseUserInvoice) {
		return super.findList(baseUserInvoice);
	}
	
	public Page<BaseUserInvoice> findPage(Page<BaseUserInvoice> page, BaseUserInvoice baseUserInvoice) {
		return super.findPage(page, baseUserInvoice);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseUserInvoice baseUserInvoice) {
		super.save(baseUserInvoice);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseUserInvoice baseUserInvoice) {
		super.delete(baseUserInvoice);
	}
	
	@Transactional(readOnly = false)
	public void invoiceHandle(BaseUserInvoice baseUserInvoice){
		dao.update(baseUserInvoice);
	}
    
	@Transactional(readOnly = true)
	public  String getUserAmount(BaseUserInvoice baseUserInvoice) {
	    String amount=dao.getUserAmount(baseUserInvoice);
		return amount;
	}
	
}