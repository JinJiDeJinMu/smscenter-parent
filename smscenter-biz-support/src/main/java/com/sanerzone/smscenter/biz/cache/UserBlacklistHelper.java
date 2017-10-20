package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;

/**
 * 营销(用户)黑名单工具类  
 * @author zhukc
 *
 */
public class UserBlacklistHelper{
    
	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("user_sms_phone", "userblacklist");//营销黑名单
    
	public static void clearAll(){
		map.clear();
	}
	
	/**
	 * 初始化营销黑名单 
	 * @param list String:userid_phone
	 */
    public static void init(List<String> list){
    	if(list != null && list.size() > 0){
    		for (String key : list) {
    			put(key);
			}
    	}
    }
    
    public void clear() {
    	map.clear();
    }
    
    /**
     * 判断是否存在
     * @param userid
     * @param phone
     * @return
     */
	public static boolean isExist(String userid,String phone){
		return isExist(getKey(userid, phone));
	}
	
	/**
	 * 判断是否存在
	 * @param key
	 * @return
	 */
	public static boolean isExist(String key){
		try{
			return map.containsKey(key);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除营销黑名单
	 * @param userid
	 * @param phone
	 */
	public static void del(String userid,String phone){
		del(getKey(userid, phone));
	}
	
	/**
	 * 删除营销黑名单
	 * @param key
	 */
	public static void del(String key){
		map.remove(key);
	}
	
	/**
	 * 缓存营销黑名单
	 * @param userid
	 * @param phone
	 */
	public static void put(String userid,String phone){
		put(getKey(userid, phone));
	}
	
	public static void put(String key){
		map.put(key, 1);
	}
	
	/**
	 * 获取营销黑名单主键
	 * @param userid
	 * @param phone
	 * @return
	 */
	public static String getKey(String userid,String phone){
		return userid+"_"+phone;
	}
	
    /**
     * 存放营销黑名单
     * @param array
     */
    public static void put(String[] array,String userid){
    	if(array != null && array.length > 0){
	    	for (String phone : array) {
				phone = phone.trim();
				put(getKey(userid, phone));
			}
    	}
    }
    
    /**
     * 存放营销黑名单
     * @param list
     */
    public static void put(List<String> list,String userid){
    	if(list != null && list.size() > 0){
	    	for (String phone : list) {
				phone = phone.trim();
				put(getKey(userid, phone));
			}
    	}
    }
    
}
