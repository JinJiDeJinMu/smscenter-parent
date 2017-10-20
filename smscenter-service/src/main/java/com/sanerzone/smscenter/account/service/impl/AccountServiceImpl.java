package com.sanerzone.smscenter.account.service.impl;

import com.sanerzone.smscenter.account.entity.Account;
import com.sanerzone.smscenter.account.mapper.AccountMapper;
import com.sanerzone.smscenter.account.service.IAccountService;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资金账户信息表 服务实现类
 * </p>
 *
 * @author XuRui
 * @since 2017-06-12
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {
	
	private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	public List<Account> selectByCustom() {
		return baseMapper.selectByCustom();
	}
	
	public List<Map<String,Object>> findAccountList() {
		return baseMapper.findAccountList();
	}
	
	public List<Map<String,Object>> findAccountListV2() {
		return baseMapper.findAccountListV2();
	}
	
	public Map<String,Object> findAccountOptionMap(Map<String,String> pMap){
		Map<String,Object> map = Maps.newHashMap();
		List<Map<String,Object>> list = baseMapper.findAccountOptionList(pMap);
		if(list != null && list.size() > 0){
			for (Map<String, Object> option : list) {
				String key =  StringHelper.valueof(option.get("optionKey"));
				String value = StringHelper.valueof(option.get("optionValue"));
				map.put(key, value);
			}
		}
		return map;
	}
	
	
	
	//初始化账号信息
	public void initAccountInfo(){
		AccountCacheHelper.clearAll();
		int size = 0;
		Map<String,String> pMap = Maps.newHashMap();

		List<Map<String,Object>> list = baseMapper.findAccountListV2();
		if(list != null && list.size() >0){
			size = list.size();
			for (Map<String, Object> map : list) {
				String userid = StringHelper.valueof(map.get("userid"));
				String accId = StringHelper.valueof(map.get("accId"));
				pMap.put("userid", userid);
				pMap.put("accId", accId);
				map.putAll(findAccountOptionMap(pMap));
			}
		}
		AccountCacheHelper.init(list);
		logger.info("账号属性缓存加载完成,账号个数:"+size);
	}
	
	//初始化账号信息
	public void initAccountInfoV2(){
		AccountCacheHelper.clearAll();
		int size = 0;
		Map<String,String> pMap = Maps.newHashMap();

		List<Map<String,Object>> list = baseMapper.findAccountListV2();
		if(list != null && list.size() >0){
			size = list.size();
			for (Map<String, Object> map : list) {
				String userid = StringHelper.valueof(map.get("userid"));
				pMap.put("userid", userid);
				map.putAll(findAccountOptionMap(pMap));
			}
		}
		AccountCacheHelper.init(list);
		logger.info("账号属性缓存加载完成,账号个数:"+size);
	}
	
	//初始化账号公共信息
	public void initPubAccountInfo(){
		
		List<Map<String,Object>> result = Lists.newArrayList();
		Map<String,String> pMap = Maps.newHashMap();
		pMap.put("accId", "0");
		List<String> list = baseMapper.findAllUserid();
		if(list != null && list.size() >0){
			for (String userid : list) {
				pMap.put("userid", userid);
				Map<String,Object> rMap = findAccountOptionMap(pMap);
				if(rMap != null && rMap.size() > 0){
					rMap.put("key", userid+"_0");
					result.add(rMap);
				}
			}
		}
		AccountCacheHelper.initPub(result);
		logger.info("用户属性缓存加载完成,用户个数:"+result.size());
	}
}
