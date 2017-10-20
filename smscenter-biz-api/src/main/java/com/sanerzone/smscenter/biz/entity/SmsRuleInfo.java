/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 规则管理Entity
 * @author zj
 * @version 2017-03-26
 */
public class SmsRuleInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String ruleCode;		// 规则代码
	private String ruleName;		// 规则名称
	private String ruleContent;		// 规则内容
	private String ruleType;		// 规则分类 1：网址 2：电话 3：关键字 4：正则式
	private String ispass;			// 检验通过 0：通过 1：不通过
	private String status;			// 状态 0：启用 1：禁用
	private String description;		// 描述
	private Date createtime;		// 创建时间
	private String groupId;			// 分组ID
	private List<SmsRuleGroup> groupList;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public List<SmsRuleGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<SmsRuleGroup> groupList) {
		this.groupList = groupList;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public SmsRuleInfo() {
		super();
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}
	
	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	public String getIspass() {
		return ispass;
	}

	public void setIspass(String ispass) {
		this.ispass = ispass;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}