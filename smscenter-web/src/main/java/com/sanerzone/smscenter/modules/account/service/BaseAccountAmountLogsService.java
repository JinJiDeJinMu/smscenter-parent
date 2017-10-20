/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.dao.BaseAccountAmountLogsDao;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountAmountLogs;

/**
 * 账务变动日志Service
 * @author zhukc
 * @version 2017-03-03
 */
@Service
@Transactional(readOnly = true)
public class BaseAccountAmountLogsService extends CrudService<BaseAccountAmountLogsDao, BaseAccountAmountLogs> {

	public BaseAccountAmountLogs get(String id) {
		return super.get(id);
	}
	
	public List<BaseAccountAmountLogs> findList(BaseAccountAmountLogs baseAccountAmountLogs) {
		return super.findList(baseAccountAmountLogs);
	}
	
	public Page<BaseAccountAmountLogs> findPage(Page<BaseAccountAmountLogs> page, BaseAccountAmountLogs baseAccountAmountLogs) {
		return super.findPage(page, baseAccountAmountLogs);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseAccountAmountLogs baseAccountAmountLogs) {
		super.save(baseAccountAmountLogs);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseAccountAmountLogs baseAccountAmountLogs) {
		super.delete(baseAccountAmountLogs);
	}
	
}