/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.dao;

import java.util.List;
import com.sanerzone.smscenter.modules.sms.entity.SmsSign;
import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;

/**
 * 通道签名DAO接口
 * @author zhukc
 * @version 2016-07-29
 */
@MyBatisDao
public interface SmsSignDao extends CrudDao<SmsSign> {
	public SmsSign getByParam(SmsSign param);
	
	/**
	 * 获取用户通道签名
	 * @param param
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SmsSign> getUserGatewaySign(SmsSign param);
	
	/**
	 * 账户下面已分配通道分组下的已经分配好的签名信息
	 * @param param
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<SmsSign> getUserGatewaySingList(SmsSign param);
}