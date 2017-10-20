/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 账号属性Entity
 * @author zhukc
 * @version 2017-03-03
 */
public class BaseAccountOptions extends DataEntity<BaseAccountOptions> {
	
	private static final long serialVersionUID = 1L;
	private String userid;			// userid
	private String accId;			// acc_id
	private String optionKey;		// option_key
	private String optionValue;		// option_value
	private Date modifyTime;		// modify_time
	private String modifyBy;		// modify_by
	
	public BaseAccountOptions() {
		super();
	}

	public BaseAccountOptions(String id){
		super(id);
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getAccId() {
		return accId;
	}

	public void setAccId(String accId) {
		this.accId = accId;
	}
	
	public String getOptionKey() {
		return optionKey;
	}

	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}
	
	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}
	
}