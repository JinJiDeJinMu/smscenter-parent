package com.sanerzone.smscenter.common.utils;

import java.util.Map;

import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;

//账户工具类
public class AccountCacheUtils {
	
	private static final String ACCOUNT_CACHE = "accountCache";//缓存域
	 
	
	/**
	 * 获取账户的某个信息或属性值
	 * @param userid
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getStringValue(String userid,String key, String defaultValue){
		if(getMap(userid) == null) return defaultValue;
		String value = getMap(userid).get(key);
		if(value== null) return defaultValue;
		return value;
	}
	
	public static int getIntegerValue(String userid,String key, int defaultValue){
		if(getMap(userid) == null) return defaultValue;
		String value = getMap(userid).get(key);
		if(value== null) return defaultValue;
		return Integer.parseInt(getMap(userid).get(key));
	}
	
	/**
	 * 获取缓存里账户信息Map
	 * @param userid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getMap(String userid){
		Object obj = EhCacheUtils.get(ACCOUNT_CACHE, userid);
		if(obj != null){
			return (Map<String,String>)obj;
		}
		return null;
	}
	
	/**
	 * 根据账户ID获取用户信息
	 * @param userid
	 * @return
	 */
	public static User getUser(String userid){
		if(StringUtils.isBlank(userid))return new User();
		return UserUtils.get(userid);
	}
	
	/**
	 * 缓存账号信息
	 * @param userid
	 * @param map
	 */
	public static void put(String userid,Map<String,Object> map){
		EhCacheUtils.put(ACCOUNT_CACHE, userid, map);
	}
	
	/**
	 * 删除账户信息
	 * @param userid
	 */
	public static void del(String userid){
		EhCacheUtils.remove(ACCOUNT_CACHE, userid);
	}
	
	/**
	 * 获取key
	 * userid V1版本使用的是userId
	 * 
	 * @param keyType amount、use
	 * @param servType sms、mms
	 * @param userid V1版本使用的是userId
	 * @return
	 */
	public static String getAmountKey(String keyType, String servType, String userid)
	{
		return keyType + "_" + servType + "_" + userid;
	}
	
	/**
	 * 获取key
	 * userid V1版本使用的是userId
	 * 
	 * @param keyType amount、use
	 * @param servType sms、mms
	 * @param userid
	 * @return
	 */
	public static String getAmountKey(String servType, String userid)
	{
		return getAmountKey("amount", servType, userid);
	}
	
	//用户余额
	public static String getAmount(String userId){
		String key = getAmountKey("sms", userId);
		String value = JedisUtils.get(key);
		if(StringUtils.isBlank(value))return "0";
		return value;
	}
	
	
}
