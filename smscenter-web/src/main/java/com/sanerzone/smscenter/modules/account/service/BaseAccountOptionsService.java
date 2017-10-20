/**
 * Copyright &copy; 2015-2016 SanerZone All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.smscenter.modules.account.dao.BaseAccountOptionsDao;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountOptions;

/**
 * 账号属性Service
 * @author zhukc
 * @version 2017-03-03
 */
@Service
@Transactional(readOnly = true)
public class BaseAccountOptionsService extends CrudService<BaseAccountOptionsDao, BaseAccountOptions> {
	
	public BaseAccountOptions get(String id) {
		return super.get(id);
	}
	
	public List<BaseAccountOptions> findList(BaseAccountOptions baseAccountOptions) {
		return super.findList(baseAccountOptions);
	}
	
	public Page<BaseAccountOptions> findPage(Page<BaseAccountOptions> page, BaseAccountOptions baseAccountOptions) {
		return super.findPage(page, baseAccountOptions);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseAccountOptions baseAccountOptions) {
		super.save(baseAccountOptions);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseAccountOptions baseAccountOptions) {
		super.delete(baseAccountOptions);
	}
	
	/**
	 * 
	 * @param pMap
	 * @return
	 */
	public Map<String,String> findOptionsMap(Map<String,String> pMap){
		Map<String,String> option = Maps.newHashMap();
		List<Map<String,String>> list = dao.findOptionsList(pMap);
		if(list != null && list.size() >0){
			for (Map<String,String> map: list) {
				option.put(map.get("optionKey"), map.get("optionValue"));	
			}
		}
		return option;
	}
	
}