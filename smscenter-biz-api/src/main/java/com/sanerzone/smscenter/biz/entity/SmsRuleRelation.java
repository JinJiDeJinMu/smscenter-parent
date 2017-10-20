/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.biz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 规则关系Entity
 * @author zj
 * @version 2017-03-26
 */
public class SmsRuleRelation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String groupId;			// 分组ID
	private String ruleType;		// 规则分类 1：网址 2：电话 3：关键字 4：正则式
	private String ruleId;			// 规则ID
	
	private SmsRuleInfo jmsgRuleInfo;
	private SmsRuleGroup jmsgRuleGroup;
	private List<SmsRuleInfo> jmsgRuleInfoList;
	private List<SmsRuleGroup> jmsgRuleGroupList;
	private List<String> ruleIdList;//规则ID列表
	
	public SmsRuleRelation() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SmsRuleInfo getJmsgRuleInfo() {
		return jmsgRuleInfo;
	}

	public void setJmsgRuleInfo(SmsRuleInfo jmsgRuleInfo) {
		this.jmsgRuleInfo = jmsgRuleInfo;
	}

	public SmsRuleGroup getJmsgRuleGroup() {
		return jmsgRuleGroup;
	}

	public void setJmsgRuleGroup(SmsRuleGroup jmsgRuleGroup) {
		this.jmsgRuleGroup = jmsgRuleGroup;
	}

	public List<SmsRuleGroup> getJmsgRuleGroupList() {
		return jmsgRuleGroupList;
	}

	public void setJmsgRuleGroupList(List<SmsRuleGroup> jmsgRuleGroupList) {
		this.jmsgRuleGroupList = jmsgRuleGroupList;
	}

	public List<SmsRuleInfo> getJmsgRuleInfoList() {
		return jmsgRuleInfoList;
	}

	public void setJmsgRuleInfoList(List<SmsRuleInfo> jmsgRuleInfoList) {
		this.jmsgRuleInfoList = jmsgRuleInfoList;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public List<String> getRuleIdList() {
		return ruleIdList;
	}

	public void setRuleIdList(List<String> ruleIdList) {
		this.ruleIdList = ruleIdList;
	}
	
}