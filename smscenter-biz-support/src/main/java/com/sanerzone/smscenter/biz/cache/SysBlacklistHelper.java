package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;

/**
 * 系统黑名单工具类  
 * @author zhukc
 *
 */
public class SysBlacklistHelper{
    
    private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("sys_sms_phone", "sysblacklist");//系统黑名单
    
    public static void clearAll(){
    	map.clear();
    }
    
    /**
     * 初始化黑名单
     * @param list String:phone
     */
    public static void init(List<String> list){
    	put(list);
    }
    
    /**
     * @param phone 手机号
     * @return 判断号码是否存在 true:存在
     */
    public static boolean isExist(String phone){
    	try{
	    	return map.containsKey(phone);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		return false;
    }
    
    /**
     * 存放系统黑名单
     * @param phone
     */
    public static void put(String phone){
    	map.put(phone, 1);
    }
    
    /**
     * 存放系统黑名单
     * @param array
     */
    public static void put(String[] array){
    	if(array != null && array.length > 0){
	    	for (String phone : array) {
				phone = phone.trim();
				put(phone);
			}
    	}
    }
    
    /**
     * 存放系统黑名单
     * @param list
     */
    public static void put(List<String> list){
    	if(list != null && list.size() > 0){
	    	for (String phone : list) {
				phone = phone.trim();
				put(phone);
			}
    	}
    }
    
    /**
     * 删除黑名单
     */
    public static void del(String key){
    	map.remove(key);
    }
    
    /**
     * 批量删除
     * @param array
     */
    public static void del(String[] array){
    	if(array != null && array.length > 0){
	    	for (String phone : array) {
				del(phone);
			}
    	}
    }
    
}
