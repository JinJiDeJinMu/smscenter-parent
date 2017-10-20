/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.IdGen;
import com.sanerzone.jeebase.modules.sys.entity.Dict;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.iface.AccountConfigInterface;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.modules.account.dao.BaseAccountAmountLogsDao;
import com.sanerzone.smscenter.modules.account.dao.BaseAccountDao;
import com.sanerzone.smscenter.modules.account.dao.BaseAccountOptionsDao;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountAmountLogs;
import com.sanerzone.smscenter.modules.account.entity.BaseAccountOptions;
import com.sanerzone.smscenter.modules.account.entity.BaseUserAmount;

/**
 * 账号信息Service
 * @author zhukc
 * @version 2017-06-25
 */
@Service
@Transactional(readOnly = true)
public class BaseAccountService extends CrudService<BaseAccountDao, BaseAccount> {
	
	@Autowired
	private BaseAccountAmountLogsDao baseAccountAmountLogsDao;
	
	@Autowired
	private BaseAccountOptionsDao baseAccountOptionsDao;
	
	@Autowired
	private BaseAccountOptionsService baseAccountOptionsService;
	
	@Autowired
	private AccountConfigInterface accountConfigInterface;
	
	@Autowired
	private BaseUserAmountService baseUserAmountService;
	
	public BaseAccount get(String id) {
		return super.get(id);
	}
	
	public BaseAccount getByParam(BaseAccount baseAccount) {
		return dao.getByParam(baseAccount);
	}
	
	public BaseAccount getByUserid(BaseAccount baseAccount) {
		return dao.getByUserid(baseAccount);
	}
	
	public List<BaseAccount> findList(BaseAccount baseAccount) {
		return super.findList(baseAccount);
	}
	
	public Page<BaseAccount> findPage(Page<BaseAccount> page, BaseAccount baseAccount) {
		return super.findPage(page, baseAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseAccount baseAccount) {
		super.save(baseAccount);
	}
	
	//广播通知
	@Transactional(readOnly = false)
	public void save(BaseAccount baseAccount,Map<String,Object> map,boolean broadcastFlag) {
		super.save(baseAccount);
		String accId = baseAccount.getId();
		String userid = baseAccount.getUserid();
		saveOptions(map, accId, userid, broadcastFlag);
		
	}
	
	@Transactional(readOnly = false)
	private void saveOptions(Map<String, Object> map, String accId, String userid, boolean broadcastFlag) {
		if(map != null){
			BaseAccountOptions baseAccountOptions = new BaseAccountOptions();
			baseAccountOptions.setUserid(userid);
			
			for(Map.Entry<String, Object> entry:map.entrySet()){
				if("feeType".equals(entry.getKey())){
					baseAccountOptions.setAccId("0");
				}else{
					baseAccountOptions.setAccId(accId);
				}
				Object obj = entry.getValue();
				String value = "";
				if(obj != null){
					value = String.valueOf(obj);
				}
				baseAccountOptions.setOptionKey(entry.getKey());
				baseAccountOptions.setOptionValue(value);
				baseAccountOptions.setModifyBy(UserUtils.getUser().getId());
				baseAccountOptionsDao.insert(baseAccountOptions);
			}
		}
		if(broadcastFlag){
			cacheAccount(userid,accId);
		}
	}
	
	@Transactional(readOnly = false)
	public void save(BaseAccount baseAccount,Map<String,Object> map) {
		save(baseAccount,map,true);
	}
	
	//缓存并通知
	public void cacheAccount(String userid, String accId){
		Map<String,Object> dbMap = getDBMap(accId);
		Map<String,Object> pubMap = getPubOptions(userid);
		String dbKey = userid+"_"+accId;
		String pubKey = userid+"_0";
		AccountCacheUtils.put(dbKey, dbMap);//本地缓存
		AccountCacheUtils.put(pubKey, pubMap);//本地缓存
		//广播调用 缓存账户信息
		accountConfigInterface.configAccount(1, dbKey, dbMap);
		//广播调用 缓存账户信息
		accountConfigInterface.configAccount(1, pubKey, pubMap);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseAccount baseAccount) {
		super.delete(baseAccount);
	}
	
	/**
	 * 修改账户状态 -2:待上线 -1:申请上线 0:下线 1:上线
	 * @param baseAccount
	 * @param broadcastFlag 是否广播通知
	 */
	@Transactional(readOnly = false)
	public void updateAccStatus(BaseAccount baseAccount,boolean broadcastFlag) {
		dao.updateAccStatus(baseAccount);
		if(broadcastFlag){
			cacheAccount(baseAccount.getUserid(),baseAccount.getId());
		}
	}
	
	//修改账户状态
	@Transactional(readOnly = false)
	public void updateAccStatus(BaseAccount baseAccount) {
		updateAccStatus(baseAccount, true);
	}
	
	/**
     * 1 select acc_amount, acc_amount_version from base_account where id=#{id}
     * 2 更新update base_account set acc_amount_version = acc_amount_version +1, acc_amount= acc_amount+#{充值的值} where id=#{id} and acc_amount_version=#{第一步查出来的版本号}
     * 3 判断 列新影响行数，如果为0则更新失败，需要重试。
	 */
	/**
	  CZ00 	充值转入 	    changeType 	变动类型 	10 	
	  XF00 	充值转出 	    changeType 	变动类型 	40 	
	  CZ01 	手动返充 	    changeType 	变动类型 	20 	
	  XF01 	手动扣款 	    changeType 	变动类型 	30 	
	 */
	
	/**
	 * 
	 * @param param
	 * @param selUser   被操作人员信息
	 * @param curUser   操作人员信息
	 * @param adminFlag 管理员标志  管理员不需要充值，或者是扣费
	 * @return
	 */
	@Transactional(readOnly = false)
	public int recharge(BaseAccount param, User selUser, User curUser, boolean adminFlag){
		int code = 0;
		String userid = selUser.getId();
		String userName = selUser.getName();
		
		try{
			//用户ID
			String transId = IdGen.uuid();//交易号
			
			if("CZ00".equals(param.getChangeType())){//充值转入
				code = rechargeAmount("0", userid, "CZ00", param.getAccAmount(), "sms", "10", "0", "充值操作(帐转入【"+curUser.getName()+"】) "+param.getRemark(), curUser.getId(), transId, "",false);
				if(code == 0)return code;
				code = rechargeAmount("0", curUser.getId(), "XF00", "-"+param.getAccAmount(), "sms", "10", "0", "充值操作(帐转出【"+userName+"】) "+param.getRemark(), curUser.getId(), transId, "", adminFlag);
				if(code == 0)return code;
			}else if("CZ01".equals(param.getChangeType())){//手动返充
				code = rechargeAmount("0", userid, "CZ01", param.getAccAmount(), "sms", "10", "0", "手动返充(帐转入【"+curUser.getName()+"】) "+param.getRemark(), curUser.getId(), transId, "", false);
				if(code == 0)return code;
				code = rechargeAmount("0", curUser.getId(), "XF01", "-"+param.getAccAmount(), "sms", "10", "0", "手动扣款(帐转出【"+userName+"】) "+param.getRemark(), curUser.getId(), transId, "", adminFlag);
				if(code == 0)return code;
			}else if("XF01".equals(param.getChangeType())){//手动扣款
				code = rechargeAmount("0", userid, "XF01", "-"+param.getAccAmount(), "sms", "10", "0", "手动扣款(帐转出【"+curUser.getName()+"】) "+param.getRemark(), curUser.getId(), transId, "", false);
				if(code == 0)return code;
				code = rechargeAmount("0", curUser.getId(), "CZ01", param.getAccAmount(), "sms", "10", "0", "手动返充(帐转入【"+userName+"】) "+param.getRemark(), curUser.getId(), transId, "", adminFlag);
				if(code == 0)return code;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return code;
		
	}
	
	
	/**
	 * 
	 * @param accId 账号ID
	 * @param userid 用户ID
	 * @param changeType 变动类型
	 * @param amount 变动金额
	 * @param remark 备注
	 * @param createUserId 创建人
	 * @return
	 */
	@Transactional(readOnly = false)
	public int rechargeAmount(String accId, String userid, String changeType, String amount, String remark, String createUserId) {
		return rechargeAmount(accId, userid, changeType, amount, "sms", "10", "0", remark, createUserId, IdGen.uuid(), "", false);
	}
	
	
	/**
	 * 额度变动
	 * 
	 * @param accId 账户ID
	 * @param userId 用户ID
	 * @param changeCode 变动类型
	 * @param accAmount 变动额度
	 * @param accType 账户类型
	 * @param serviceId 业务ID
	 * @param remark 备注
	 * @param createUserId 创建人
	 * @param transId 交易ID
	 * @param payment 支付方式
	 * @param payid 支付订单号
	 * @param adminFlag 管理员标志 true:是系统管理员(不需要充值或者扣费)
	 */
	@Transactional(readOnly = false)
	public int rechargeAmount(String accId, String userid, String changeType, String amount, String accType, 
							  String serviceId, String payment, String remark, String createUserId, String transId, 
							  String payid, boolean adminFlag) {
		int code = adminFlag ? 1 : 0;
		String accAmount = "0";//变动前额度
		if(!adminFlag){//普通用户可以充值或者是扣费
			BaseAccount param = new BaseAccount();
			BaseUserAmount entity = baseUserAmountService.get(userid);
			if(entity != null){
				accAmount = entity.getAmount();
				param.setUserid(userid);//用户ID
				param.setAccAmount(amount);//变动额度
				code = dao.rechargeAmount(param);//操作成功
			}
		}
		if( 1 == code || adminFlag){//保存日志
			saveAccountAmountLogs(accId, userid, changeType, accAmount, amount, accType, serviceId, payment, remark, createUserId, transId, payid);
		}
		return code;
	}
	
	//保存变动日志
	@Transactional(readOnly = false)
	public void saveAccountAmountLogs(String accId,String userid, String changeType,String accAmount,String amount,
									  String accType,String serviceId,String payment,String remark, String createUserId, 
									  String transId,String payid){
		BaseAccountAmountLogs param = new BaseAccountAmountLogs();
		param.setAccId(accId);
		param.setUserid(userid);
		param.setAccType(accType);
		param.setServiceId(serviceId);
		param.setAccAmount(accAmount);
		param.setAmount(amount);
		param.setChangeType(changeType);
		param.setPayment(payment);//手动
		param.setPayid(payid);//支付订单号
		param.setRemark(remark);
		param.setTransId(transId);
		param.setCreateBy(new User(createUserId));
		baseAccountAmountLogsDao.insert(param);
	}
	
	/**
	 * 根据账户ID 从数据库  获取账户信息和账户属性
	 * @param accId
	 * @return
	 */
	public Map<String,Object> getDBMap(String accId){
		Map<String,Object> resultMap = Maps.newHashMap();
		Map<String,String> pMap = Maps.newHashMap();
		pMap.put("accId", accId);
		resultMap.putAll(accInfoMap(pMap));//获取账户信息
		resultMap.putAll(baseAccountOptionsService.findOptionsMap(pMap));//获取账户属性
		return resultMap;
	}
	
	//获取账号信息 map
	public Map<String,String> accInfoMap(Map<String,String> pMap){
		
		List<Map<String,String>> mapList = dao.findAccountMapList(pMap);
		if(mapList != null && mapList.size() > 0){
			for (Map<String, String> map : mapList) {
				return map;
			}
		}
		return Maps.newHashMap();
	}
	
	public List<Dict> getAccountList(BaseAccount param){
		return dao.getAccountList(param);
	}
	
	public List<Dict> getUserList(BaseAccount param){
		return dao.getUserList(param);
	}
	
	public List<Dict> getUserListByUT(BaseAccount param){
		return dao.getUserListByUT(param);
	}
	
	/**
	 * 获取用户余额
	 * @param userid
	 * @param accType
	 * @return
	 */
	public Long findUserMoeny(String userid,String accType){
		Map<String,String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("accType", "sms");
		Long money = dao.findUserMoeny(map);
		if(money == null){
			money = 0L;
		}
		return money;
	}
	
	/**
	 * 获取用户余额
	 * @param userid
	 * @param accType
	 * @return
	 */
	public Long findUserMoeny(String userid){
		return findUserMoeny(userid, "sms");
	}
	
	@Transactional(readOnly = false)
	public void updateByFront(BaseAccount baseAccount, Map<String,Object> map, boolean broadcastFlag){
		dao.updateByFront(baseAccount);
		saveOptions(map, baseAccount.getId(), baseAccount.getUserid(), broadcastFlag);
	}
	
	/**
	 * 获取公共属性(查询userid + accId=0)
	 * 现在用户公共属性：feeType
	 * @param userid   
	 * @return
	 */
	public Map<String,Object> getPubOptions(String userid){
		Map<String,String> pMap = Maps.newHashMap();
		pMap.put("userid", userid);
		Map<String,Object> result = Maps.newHashMap();
		result.putAll(baseAccountOptionsService.findOptionsMap(pMap));
		return result;
	}
	
}