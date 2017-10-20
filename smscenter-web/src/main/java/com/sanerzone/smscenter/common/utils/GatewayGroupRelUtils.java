package com.sanerzone.smscenter.common.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroupRel;

//通道分组工具类
public class GatewayGroupRelUtils {


	//组装通道组Map
	private static Map<String,List<String>> gatewayGroupMap(List<SmsGatewayGroupRel> list) {
		Map<String,List<String>> map = Maps.newHashMap();
		for (SmsGatewayGroupRel entity : list) {
			String gwCode = entity.getGwCode();
			String key = CacheKeys.getCacheGatewayGroupKey(entity.getGroupId(), entity.getPhoneType(), entity.getProvinceId());
			if(map.containsKey(key)){
				List<String> gatewayIds = map.get(key);
				gatewayIds.add(gwCode);
			}else{
				List<String> gatewayIds = Lists.newArrayList();
				gatewayIds.add(gwCode);
				map.put(key, gatewayIds);
			}
		}
		return map;
	}
	
	public static void put(String key,List<String> gatewayIds){
		EhCacheUtils.put(CacheKeys.GATEWAY_CACHE, key, gatewayIds);
	}
	
	public static void put(List<SmsGatewayGroupRel> list){
		if(list != null && list.size() >0){
			Map<String,List<String>> map = gatewayGroupMap(list);
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				put(entry.getKey(),entry.getValue());
			}
		}
	}
	
	public static void put(String groupId,String phoneType,String provinceId,Object obj){
		String key = CacheKeys.getCacheGatewayGroupKey(groupId, phoneType, provinceId);
		EhCacheUtils.put(CacheKeys.GATEWAY_CACHE, key, obj);
	}
	
	public static void del(String groupId,String phoneType,String provinceId,String gatewayId){
		String key = CacheKeys.getCacheGatewayGroupKey(groupId, phoneType, provinceId);
		del(key, gatewayId);
	}
	
	
	@SuppressWarnings("unchecked")
	public static void del(String key,String gatewayId){
		Object obj = EhCacheUtils.get(CacheKeys.GATEWAY_CACHE, key);
		if(obj != null){
			List<String> gatewayList = (List<String>)obj;
			if(gatewayList != null && gatewayList.size() >0){
				int index = 0;
				for (String result : gatewayList) {
					if(gatewayId.equals(result)){
						gatewayList.remove(index);
						return;
					}
					index++;
				}
			}
		}
	}
	
	//获取通道列表 缓存中获取不到，从数据库获取并缓存
	@SuppressWarnings("unchecked")
	public static List<String> get(String groupId,String phoneType,String provinceId){
		
		String key = CacheKeys.getCacheGatewayGroupKey(groupId, phoneType, provinceId);
		Object obj = EhCacheUtils.get(CacheKeys.GATEWAY_CACHE, key);
		
		if(obj == null)return null;
		if(obj instanceof List){
			return (List<String>)obj;
		}else{
			return null;
		}
	} 
}
