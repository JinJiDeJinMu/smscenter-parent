/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;
import com.sanerzone.jeebase.modules.sys.entity.User;

/**
 * 短信模板Entity
 * @author zhukc
 * @version 2017-06-27
 */
public class SmsTemplate extends DataEntity<SmsTemplate> {
	
	private static final long serialVersionUID = 1L;
	private String name;			// 模板名称
	private String sign;			// 模板签名
	private String content;			// 模板内容
	private String type;			// 类型(1行业短信、2验证码、3、营销短信、4群发短信)
	private Date createtime;		// 创建时间
	private Date createtimeQ;		// 创建时间
	private Date createtimeZ;		// 创建时间
	private String status;			// 状态
	private String recheckUserId;	// 审核人
	private Date recheckTime;		// 审核时间
	private String recheckRemarks;	// 审核备注
	private String scope;			// 范围 0:全局 1:用户
	private User user;
	private String templateId;		// 源模板编号
	private String templateSrc;		// 模板来源(0:本地,1:移动开放平台)
	private String templateContent; // 源模板内容
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateSrc() {
		return templateSrc;
	}

	public void setTemplateSrc(String templateSrc) {
		this.templateSrc = templateSrc;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatetimeQ() {
		return createtimeQ;
	}

	public void setCreatetimeQ(Date createtimeQ) {
		this.createtimeQ = createtimeQ;
	}

	public Date getCreatetimeZ() {
		return createtimeZ;
	}

	public void setCreatetimeZ(Date createtimeZ) {
		this.createtimeZ = createtimeZ;
	}

	public SmsTemplate() {
		super();
	}

	public SmsTemplate(String id){
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRecheckUserId() {
		return recheckUserId;
	}

	public void setRecheckUserId(String recheckUserId) {
		this.recheckUserId = recheckUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRecheckTime() {
		return recheckTime;
	}

	public void setRecheckTime(Date recheckTime) {
		this.recheckTime = recheckTime;
	}
	
	public String getRecheckRemarks() {
		return recheckRemarks;
	}

	public void setRecheckRemarks(String recheckRemarks) {
		this.recheckRemarks = recheckRemarks;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
}