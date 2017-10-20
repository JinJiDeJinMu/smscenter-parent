/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 通道分组Entity
 * @author zhukc
 * @version 2017-06-29
 */
public class SmsGatewayGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;			//分组ID
	private String name;		// 分组名称
	private String remark;		// 备注
	private String sort;		// 排序
	private String status;		// 分组状态
	private Date createtime;	// createtime
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}