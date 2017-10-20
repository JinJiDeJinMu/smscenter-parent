/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 规则分组Entity
 * @author zj
 * @version 2017-03-26
 */
public class SmsRuleGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupName;		// 分组名称
	private String description;		// 分组描述
	private String status;		// 状态 0：启用 1：禁用
	private Date createtime;		// 创建时间
	
	public SmsRuleGroup() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}