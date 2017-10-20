package com.sanerzone.smscenter.common.utils;

import java.util.List;

import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.jeebase.modules.sys.entity.Dict;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.modules.account.entity.BaseAccount;
import com.sanerzone.smscenter.modules.account.entity.BaseUserAmount;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.account.service.BaseUserAmountService;

//账户标签工具类
public class AccountTagUtils {
	
	private static BaseAccountService baseAccountService = SpringContextHolder.getBean(BaseAccountService.class);
	
	private static BaseUserAmountService baseUserAmountService = SpringContextHolder.getBean(BaseUserAmountService.class);
	 
	//获取账户列表 userType 1:业务用户 0：系统用户
	public static List<Dict> getAccountList(String accType,String serviceId){
		BaseAccount param = new BaseAccount();
		param.setAccType(accType);
		param.setServiceId(serviceId);
		String userType = UserUtils.getUser().getUserType();
		if("1".equals(userType)){
			param.setUserid(UserUtils.getUser().getId());
		}
		List<Dict> list =  baseAccountService.getAccountList(param);
		return list;
	}
	
	/**
	 * 获取账户列表 userType 1:业务用户 0：系统用户
	 * @param type lower获取下级用户
	 * @return
	 */
	public static List<Dict> getUserList(String type){
		BaseAccount param = new BaseAccount();
		
		String userType = UserUtils.getUser().getUserType();
		if("1".equals(userType) || "lower".equals(type)){
			param.setUserid(UserUtils.getUser().getId());
		}
		List<Dict> list = baseAccountService.getUserList(param);
		return list;
	}
	
	//根据用户状态获取用户列表
	public static List<Dict> getUserListByUT(String userType){
		BaseAccount param = new BaseAccount();
		if(StringHelper.isNotBlank(userType)){
			param.setUserType(userType);
		}
		List<Dict> list =  baseAccountService.getUserListByUT(param);
		return list;
	}
	
	//获取用户额度
	public static String getDBAmount(String userid){
		BaseUserAmount baseUserAmount = baseUserAmountService.get(userid);
		if(baseUserAmount == null)return "0";
		return baseUserAmount.getAmount();
	}
	
}
