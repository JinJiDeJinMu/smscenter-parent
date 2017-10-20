/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.List;
import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsTask;
/**
 * 短信任务DAO接口
 * @author zhukc
 * @version 2017-06-26
 */
@MyBatisDao
public interface SmsTaskDao extends CrudDao<SmsTask> {
	public void recheckSmsContent(SmsTask smsTask);
	public List<SmsTask> findSmsTaskReport(SmsTask smsTask);
	public void updateReport(SmsTask smsTask);
	public List<SmsTask> findPendingSendTask();
	public void updateRowNumber(Map<String,Object> map);
	public int updateSendStatus(Map<String,Object> map);
	public void updateStatus(Map<String,String> map);
}