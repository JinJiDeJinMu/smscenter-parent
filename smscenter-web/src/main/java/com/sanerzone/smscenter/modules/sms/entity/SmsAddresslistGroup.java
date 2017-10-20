/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 群组管理Entity
 * @author zhukc
 * @version 2017-04-01
 */
public class SmsAddresslistGroup extends DataEntity<SmsAddresslistGroup> {
	
	private static final long serialVersionUID = 1L;
	private String pid;
	private SmsAddresslistGroup parent;		// 上级ID
	private String parentIds;					// 所有的上级ID
	private String name;						// 通讯录组
	private String sort;						// 排序（值越小 越靠前）
	private String companyId;					// 创建公司
	private Date createtime;					// 创建时间
	private Date updatetime;					// 修改时间
	private String status;						// 状态(1:正常 0:删除)
	private String userId;						// 用户ID 
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SmsAddresslistGroup() {
		super();
	}

	public SmsAddresslistGroup(String id){
		super(id);
	}

	@JsonBackReference
	public SmsAddresslistGroup getParent() {
		return parent;
	}

	public void setParent(SmsAddresslistGroup parent) {
		this.parent = parent;
	}
	
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}