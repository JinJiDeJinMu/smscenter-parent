/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import java.util.List;
import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountOptions;

/**
 * 账号属性DAO接口
 * @author zhukc
 * @version 2017-03-03
 */
@MyBatisDao
public interface BaseAccountOptionsDao extends CrudDao<BaseAccountOptions> {
	public void delByParam(BaseAccountOptions baseAccountOptions);
	public List<Map<String,String>> findOptionsList(Map<String,String> map);
}