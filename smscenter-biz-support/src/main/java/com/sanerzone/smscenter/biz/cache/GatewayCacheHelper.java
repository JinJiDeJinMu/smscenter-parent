package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.smscenter.biz.entity.SmsGateway;

//网关缓存工具类
public class GatewayCacheHelper {
	
	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("sms_gateway", "gateway_info");//网关信息
	
	public static void clearAll(){
		map.clear();
	}
	
	/**
	 * 初始化网关
	 * @param list SmsGateway
	 */
	public static void init(List<SmsGateway> list){
		if(list != null && list.size() > 0){
			for (SmsGateway smsGateway : list) {
				put(smsGateway.getGwCode(), smsGateway);
			}
		}
	}
	
	/**
	 * 缓存网关信息
	 * @param gwCode
	 * @param entity
	 */
	public static void put(String gwCode,SmsGateway entity){
		map.put(gwCode, entity);
	}
	
	/**
	 * 删除网关信息
	 * @param gwCode
	 */
	public static void del(String gwCode){
		map.remove(gwCode);
	}
	
	/**
	 * 获取网关信息
	 * @param gwCode
	 * @return
	 */
	public static SmsGateway get(String gwCode){
		Object obj = map.get(gwCode);
		if(obj == null)return null;
		return (SmsGateway)map.get(gwCode);
	}
	
	//获取应用代码
	public static String getAppCode(String gwCode){
		SmsGateway smsGateway = get(gwCode);
		if(smsGateway == null)return "";
		return smsGateway.getAppCode();
	}
}
