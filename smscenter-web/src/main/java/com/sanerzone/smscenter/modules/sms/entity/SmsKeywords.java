/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sanerzone.jeebase.common.persistence.DataEntity;

/**
 * 敏感词Entity
 * @author zhukc
 * @version 2017-06-27
 */
public class SmsKeywords extends DataEntity<SmsKeywords> {
	
	private static final long serialVersionUID = 1L;
	private String keywords;		// 敏感词
	private Date createtime;		// 创建日期
	
	public SmsKeywords() {
		super();
	}

	public SmsKeywords(String id){
		super(id);
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}