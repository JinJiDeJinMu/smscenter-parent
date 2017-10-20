/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;

/**
 * 短信模板DAO接口
 * @author zhukc
 * @version 2017-06-27
 */
@MyBatisDao
public interface SmsTemplateDao extends CrudDao<SmsTemplate> {
	public void recheckTemplate(SmsTemplate param);
	public com.sanerzone.smscenter.biz.entity.SmsTemplate findBizById(Map<String,String> map);
}