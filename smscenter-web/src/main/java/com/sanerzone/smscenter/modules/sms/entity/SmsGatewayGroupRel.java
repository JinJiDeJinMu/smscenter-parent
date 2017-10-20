/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 通道分组关系Entity
 * @author zhukc
 * @version 2017-06-30
 */
public class SmsGatewayGroupRel extends DataEntity<SmsGatewayGroupRel> {
	
	private static final long serialVersionUID = 1L;
	private String groupId;			// 分组ID
	private String groupName;		// 分组名称
	private String phoneType;		// 运营商(联通、移动、电信)
	private String provinceId;		// 省份
	private String gwCode;			// 网关编码
	private String gwName;			// 网关名称
	private String level;			// 等级
	private Date createTime;		// create_time
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGwName() {
		return gwName;
	}

	public void setGwName(String gwName) {
		this.gwName = gwName;
	}

	public SmsGatewayGroupRel() {
		super();
	}

	public SmsGatewayGroupRel(String id){
		super(id);
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getGwCode() {
		return gwCode;
	}

	public void setGwCode(String gwCode) {
		this.gwCode = gwCode;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}