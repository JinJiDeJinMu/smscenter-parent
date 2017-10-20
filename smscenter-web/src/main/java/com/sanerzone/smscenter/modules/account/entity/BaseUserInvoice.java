/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 用户发票Entity
 * @author zhukc
 * @version 2017-08-16
 */
public class BaseUserInvoice extends DataEntity<BaseUserInvoice> {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String userid;				// 用户ID
	private Long amount;				// 可开票金额
	private Long totalAmount;			// 累计开票金额
	private Long freezeAmount;			// 冻结开票金额
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BaseUserInvoice() {
		super();
	}

	public BaseUserInvoice(String id){
		super(id);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public Long getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Long freezeAmount) {
		this.freezeAmount = freezeAmount;
	}


	
}