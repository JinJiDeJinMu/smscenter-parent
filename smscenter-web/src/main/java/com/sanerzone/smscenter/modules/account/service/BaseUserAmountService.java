/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.dao.BaseUserAmountDao;
import com.sanerzone.smscenter.modules.account.entity.BaseUserAmount;

/**
 * 用户额度Service
 * @author zhukc
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class BaseUserAmountService extends CrudService<BaseUserAmountDao, BaseUserAmount> {

	public BaseUserAmount get(String id) {
		return super.get(id);
	}
	
	public List<BaseUserAmount> findList(BaseUserAmount baseUserAmount) {
		return super.findList(baseUserAmount);
	}
	
	public Page<BaseUserAmount> findPage(Page<BaseUserAmount> page, BaseUserAmount baseUserAmount) {
		return super.findPage(page, baseUserAmount);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseUserAmount baseUserAmount) {
		super.save(baseUserAmount);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseUserAmount baseUserAmount) {
		super.delete(baseUserAmount);
	}
	
}