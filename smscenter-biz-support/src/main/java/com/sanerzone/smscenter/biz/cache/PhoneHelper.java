package com.sanerzone.smscenter.biz.cache;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.smscenter.biz.entity.SmsPhoneInfo;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.biz.utils.ValidatorHelper;

//手机工具类
public class PhoneHelper {

	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("sms_phone_info", "phonesegment");//号段信息
	
	
	public static void clearAll(){
		map.clear();
	}
	
	/**
	 * 初始化号段
	 * 
	 * @param list SmsPhoneInfo
	 */
	public static void init(List<SmsPhoneInfo> list){
		
		if(list != null && list.size() > 0){
			for (SmsPhoneInfo smsPhoneInfo : list) {
				put(smsPhoneInfo.getPhone(), smsPhoneInfo.getPhoneType(), smsPhoneInfo.getPhoneCityCode());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> get(String key){
		if(StringHelper.isNotBlank(key) && ValidatorHelper.isMobile(key)) {
			key = key.substring(0, 7);
			return (Map<String,String>)map.get(key);
		}
		return Maps.newHashMap();
	}
	
	//获取运营商
	public static String getPhoneType(String key){
		Map<String,String> map = get(key);
		if(map == null || map.size() == 0)return "";
		return map.get("pt");
	}
	
	//获取省市代码
	public static String getCityCode(String key){
		Map<String,String> map = get(key);
		if(map == null || map.size() == 0)return "";
		return map.get("cc");
	}
	
	//获取运营商
	public static String getPhoneType(Map<String,String> map){
		if(map == null || map.size() == 0)return "";
		return map.get("pt");
	}
	
	//获取省市代码
	public static String getCityCode(Map<String,String> map){
		if(map == null || map.size() == 0)return "";
		return map.get("cc");
	}
	
	public static void put(String phone,String phoneType,String cityCode){
		Map<String,String> value = Maps.newHashMap();
		value.put("pt", phoneType);
		value.put("cc", cityCode);
		put(phone, value);
	}
	
	public static void put(String phone,Map<String,String> value){
		map.put(phone, (Serializable)value);
	}
	
	public static void del(String key){
		if(StringHelper.isNotBlank(key) && ValidatorHelper.isMobile(key)) {
			key = key.substring(0, 7);
			map.remove(key);
		}
	}
    
}
