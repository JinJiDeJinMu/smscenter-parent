/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.dao;

import java.util.List;
import java.util.Map;

import com.sanerzone.jeebase.common.persistence.CrudDao;
import com.sanerzone.jeebase.common.persistence.annotation.MyBatisDao;
import com.sanerzone.jeebase.modules.sys.entity.Dict;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;

/**
 * 账号信息DAO接口
 * @author zhukc
 * @version 2017-06-25
 */
@MyBatisDao
public interface BaseAccountDao extends CrudDao<BaseAccount> {
	
	public List<Dict> getAccountList(BaseAccount param);
	
	public List<Dict> getUserList(BaseAccount param);
	
	public List<Dict> getUserListByUT(BaseAccount param);
	
	public int rechargeAmount(BaseAccount param);
	
	public BaseAccount getByParam(BaseAccount param);
	
	public BaseAccount getByUserid(BaseAccount param);
	
	public void updateAccStatus(BaseAccount param);
	
	public List<Map<String,String>> findAccountMapList(Map<String,String> map);
	
	public Long findUserMoeny(Map<String,String> pMap);
	
	public void updateByFront(BaseAccount param);
}