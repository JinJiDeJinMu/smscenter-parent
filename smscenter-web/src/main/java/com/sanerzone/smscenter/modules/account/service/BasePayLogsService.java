/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.entity.BasePayLogs;
import com.sanerzone.smscenter.modules.account.dao.BasePayLogsDao;

/**
 * 自助充值日志Service
 * @author zhukc
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class BasePayLogsService extends CrudService<BasePayLogsDao, BasePayLogs> {

	public BasePayLogs get(String id) {
		return super.get(id);
	}
	
	public List<BasePayLogs> findList(BasePayLogs basePayLogs) {
		return super.findList(basePayLogs);
	}
	
	public Page<BasePayLogs> findPage(Page<BasePayLogs> page, BasePayLogs basePayLogs) {
		return super.findPage(page, basePayLogs);
	}
	
	@Transactional(readOnly = false)
	public void save(BasePayLogs basePayLogs) {
		super.save(basePayLogs);
	}
	
	@Transactional(readOnly = false)
	public void delete(BasePayLogs basePayLogs) {
		super.delete(basePayLogs);
	}
	
	public BasePayLogs getByParam(BasePayLogs basePayLogs){
		return dao.getByParam(basePayLogs);
	}
	
	@Transactional(readOnly = false)
	public int updateStatus(BasePayLogs basePayLogs){
		return dao.updateStatus(basePayLogs);
	}
	
}