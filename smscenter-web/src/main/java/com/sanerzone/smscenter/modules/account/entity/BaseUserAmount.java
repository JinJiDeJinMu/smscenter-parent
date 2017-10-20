/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 用户额度Entity
 * @author zhukc
 * @version 2017-08-16
 */
public class BaseUserAmount extends DataEntity<BaseUserAmount> {
	
	private static final long serialVersionUID = 1L;
	private String userid;			// userid
	private String accType;			// acc_type
	private String amount;			// amount
	private Date createTime;		// create_time
	
	public BaseUserAmount() {
		super();
	}

	public BaseUserAmount(String id){
		super(id);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}