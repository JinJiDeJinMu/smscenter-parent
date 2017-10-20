/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.List;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsDaySign;

/**
 * 日签名报表DAO接口
 * @author huangjie
 * @version 2017-08-13
 */
@MyBatisDao
public interface SmsDaySignDao extends CrudDao<SmsDaySign> {
	//从sms_send_XX表中获取用户日发送量
	List<SmsDaySign> findSendList(SmsDaySign param);
	public void batchInsert(SmsDaySign param);
}