package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;
import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.smscenter.biz.utils.StringHelper;

//账户工具类
public class AccountCacheHelper {
	
	private static Logger logger = LoggerFactory.getLogger(AccountCacheHelper.class);
	
	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("base_account", "account_info");//用户信息
	
	
	public static void clearAll(){
		map.clear();
	}
	
	/**
	 * 初始化账户信息
	 * 
	 * @param list Map<String,Object>
	 */
	public static void init(List<Map<String,Object>> list){
		if(list != null && list.size() > 0){
			for (Map<String, Object> valueMap : list) {
				String userid = StringHelper.valueof(valueMap.get("userid"));
				String accId = StringHelper.valueof(valueMap.get("accId"));
				String key = userid+"_"+accId;
				put(key, valueMap);
			}
		}
	}
	
	/**
	 * 初始化账户信息
	 * 
	 * @param list Map<String,Object>
	 */
	public static void initPub(List<Map<String,Object>> list){
		if(list != null && list.size() > 0){
			for (Map<String, Object> valueMap : list) {
				String key = StringHelper.valueof(valueMap.get("key"));
				valueMap.remove("key");
				put(key, valueMap);
			}
		}
	}
	
	/**
	 * 获取账户的某个信息或属性值
	 * @param accId
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getStringValue(String accId,String key, String defaultValue){
		if(getMap(accId) == null) return defaultValue;
		Object value = getMap(accId).get(key);
		if(value== null || value== "") return defaultValue;
		return String.valueOf(value);
	}
	
	public static int getIntegerValue(String accId,String key, int defaultValue){
		if(getMap(accId) == null) return defaultValue;
		Object value = getMap(accId).get(key);
		if(value== null || value.equals("")) return defaultValue;
		if(value instanceof String){
			return Integer.parseInt((String) value);
		}
		return (Integer) value;
	}
	
	/**
	 * 获取缓存里账户信息Map
	 * @param accId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getMap(String accId){
		Object obj = map.get(accId);
		if(obj != null){
			return (Map<String,Object>)obj;
		}
		return null;
	}
	
	/**
	 * 缓存账号信息
	 * @param accId
	 * @param map
	 */
	public static void put(String accId,Map<String,Object> valueMap){
		logger.info("{}, update account cache: {}", accId, valueMap);
		int speed = StringHelper.getIntegerValue(valueMap.get("httpSpeed"),0);
		if(speed > 0){
			RateLimiter limiter = RateLimiter.create(speed);
			AccountCacheHelper.putHttpSpeed(accId, limiter);
		}
		map.put(accId, (Serializable) valueMap);
	}
	
	/**
	 * http速率
	 * @param accId
	 * @param rateLimiter
	 */
	public static void putHttpSpeed(String accId,RateLimiter rateLimiter){
		map.put("http_speed_"+accId, (Serializable) rateLimiter);
	}
	
	/**
	 * 获取速率
	 * @param accId
	 * @return
	 */
	public static RateLimiter getHttpSpeed(String accId){
		return (RateLimiter)map.get("http_speed_"+accId);
	}
	
	/**
	 * 删除账户信息
	 * @param accId
	 */
	public static void del(String accId){
		map.remove(accId);
		map.remove("http_speed_"+accId);
	}
	
	/**
	 * 获取key
	 * accId V1版本使用的是userId
	 * 
	 * @param keyType amount、use
	 * @param servType sms、mms
	 * @param accId V1版本使用的是userId
	 * @return
	 */
	public static String getAmountKey(String keyType, String servType, String accId){
		return keyType + "_" + servType + "_" + accId;
	}
	
	/**
	 * 获取key
	 * accId V1版本使用的是userId
	 * 
	 * @param servType sms、mms
	 * @param accId
	 * @return
	 */
	public static String getAmountKey(String servType, String accId){
		return getAmountKey("amount", servType, accId);
	}
}
