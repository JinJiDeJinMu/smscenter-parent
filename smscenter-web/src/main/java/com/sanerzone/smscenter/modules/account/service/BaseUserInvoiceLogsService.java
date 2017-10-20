/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoice;
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoiceLogs;
import com.sanerzone.smscenter.modules.account.dao.BaseUserInvoiceLogsDao;

/**
 * 用户发票明细Service
 * @author zhukc
 * @version 2017-08-16
 */
@Service
@Transactional(readOnly = true)
public class BaseUserInvoiceLogsService extends CrudService<BaseUserInvoiceLogsDao, BaseUserInvoiceLogs> {

	@Autowired
	private BaseUserInvoiceService baseUserInvoiceService;
	
	public BaseUserInvoiceLogs get(String id) {
		return super.get(id);
	}
	
	public List<BaseUserInvoiceLogs> findList(BaseUserInvoiceLogs baseUserInvoiceLogs) {
		return super.findList(baseUserInvoiceLogs);
	}
	
	public Page<BaseUserInvoiceLogs> findPage(Page<BaseUserInvoiceLogs> page, BaseUserInvoiceLogs baseUserInvoiceLogs) {
		return super.findPage(page, baseUserInvoiceLogs);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseUserInvoiceLogs baseUserInvoiceLogs) {
		super.save(baseUserInvoiceLogs);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseUserInvoiceLogs baseUserInvoiceLogs) {
		super.delete(baseUserInvoiceLogs);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(BaseUserInvoiceLogs baseUserInvoiceLogs) {
		invoiceHandle(baseUserInvoiceLogs.getStatus(), baseUserInvoiceLogs.getAmount(), baseUserInvoiceLogs.getUserid());
		dao.updateStatus(baseUserInvoiceLogs);
	}
	
	
	/**
	 * 索取发票: 增加冻结开票金额，减少开票金额
	 * 不开票    ：增加开票金额，减少冻结开票金额
	 *   开票    ：减少冻结开票金额，增加累计开票金额
	 * @param status 状态 -1:索取发票 0:不开票 1:开票
	 * @param source 开票金额
	 * @param userid 用户ID
	 */
	@Transactional(readOnly = false)
	public void invoiceHandle(String status,Long source, String userid){
		Long amount = 0L;				//可开票金额
		Long totalAmount = 0L;			// 累计开票金额
		Long freezeAmount = -source;	// 冻结开票金额 
		
		BaseUserInvoice baseUserInvoice = new BaseUserInvoice();
		if("0".equals(status)){//0:不开票
			amount = source;
		}else if("1".equals(status)){//1:开票
			totalAmount = source;
		}else if("-1".equals(status)){//索取发票
			amount = -source;
			freezeAmount = source;
		}
		baseUserInvoice.setAmount(amount);
		baseUserInvoice.setTotalAmount(totalAmount);
		baseUserInvoice.setFreezeAmount(freezeAmount);
		baseUserInvoice.setUserid(userid);
		
		baseUserInvoiceService.invoiceHandle(baseUserInvoice);
	}
	
}