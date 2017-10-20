/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.Date;
import java.util.List;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;

/**
 * 日报表DAO接口
 * @author zhukc
 * @version 2017-06-26
 */
@MyBatisDao
public interface SmsDayReportDao extends CrudDao<SmsDayReport> {
	public List<SmsDayReport> findByUserList(SmsDayReport param);
	public List<SmsDayReport> findByGatewayList(SmsDayReport param);
	public List<SmsDayReport> findByUserGatewayList(SmsDayReport param);
	public List<SmsDayReport> findByUserPhoneTypeList(SmsDayReport param);
	public List<SmsDayReport> findSendList(SmsDayReport param);
	public void updateBackFlag(SmsDayReport param);
	public List<SmsDayReport> findRechargeList(Date day);
	public List<SmsDayReport> findDaySendCountList(SmsDayReport param);//用户日发送量
	public List<SmsDayReport> findList4Index(SmsDayReport param);
	public List<SmsDayReport> findListByAccId(SmsDayReport param);
}