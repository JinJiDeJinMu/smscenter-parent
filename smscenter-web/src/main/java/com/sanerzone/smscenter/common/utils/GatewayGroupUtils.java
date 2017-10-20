package com.sanerzone.smscenter.common.utils;

import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroup;

//分组工具类
public class GatewayGroupUtils {
	
	//缓存分组
    public static void put(SmsGatewayGroup smsGatewayGroup){
        String key = CacheKeys.getCacheGroupKey(smsGatewayGroup.getId());
        EhCacheUtils.put(CacheKeys.GATEWAY_CACHE, key, smsGatewayGroup);
    }
	
	//删除分组
	public static void del(String groupId){
		String key = CacheKeys.getCacheGroupKey(groupId);
		EhCacheUtils.remove(CacheKeys.GATEWAY_CACHE, key);
	}
	
	//判断分组是否存在
	public static boolean isExists(String groupId){
		SmsGatewayGroup smsGatewayGroup = getJmsgGroup(groupId);
		if(smsGatewayGroup == null)return false;
        if ("1".equals(smsGatewayGroup.getStatus()))return true;
        return false;
	}
	
	//获取分组信息 缓存中不存在，则获取数据库并缓存
    public static SmsGatewayGroup getJmsgGroup(String groupId){
        String key = CacheKeys.getCacheGroupKey(groupId);
        Object obj = EhCacheUtils.get(CacheKeys.GATEWAY_CACHE, key);
        if(obj == null) return null;
        return (SmsGatewayGroup)obj;
    }
}
