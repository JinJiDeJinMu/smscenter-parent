/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 通道分组Entity
 * @author zhukc
 * @version 2017-06-29
 */
public class SmsGatewayGroup extends DataEntity<SmsGatewayGroup> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分组名称
	private String remark;		// 备注
	private String sort;		// 排序
	private String status;		// 分组状态
	private Date createtime;	// createtime
	
	public SmsGatewayGroup() {
		super();
	}

	public SmsGatewayGroup(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}