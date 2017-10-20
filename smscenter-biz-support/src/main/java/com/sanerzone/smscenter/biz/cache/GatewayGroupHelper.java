package com.sanerzone.smscenter.biz.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.smscenter.biz.entity.SmsGatewayGroup;

//分组工具类
public class GatewayGroupHelper {
	
	private static Map<String, Serializable> map = BDBStoredMapFactoryImpl.INS.buildMap("sms_gateway_group", "group_info");//通道分组信息
	
	public static void clearAll(){
		map.clear();
	}
	/**
	 * 初始化通道分组
	 * @param list SmsGatewayGroup
	 */
	public static void init(List<SmsGatewayGroup> list){
		if(list != null && list.size() > 0){
			for (SmsGatewayGroup smsGatewayGroup : list) {
				put(smsGatewayGroup.getId(),smsGatewayGroup);
			}
		}
	}
	
	//缓存分组
    public static void put(String groupId,SmsGatewayGroup smsGatewayGroup){
    	map.put(groupId, smsGatewayGroup);
    }
	
	//删除分组
	public static void del(String groupId){
		map.remove(groupId);
	}
	
	//判断分组是否存在
	public static boolean isExists(String groupId){
		SmsGatewayGroup smsGatewayGroup = get(groupId);
		if(smsGatewayGroup == null)return false;
        if ("1".equals(smsGatewayGroup.getStatus()))return true;
        return false;
	}
	
	//获取分组信息 缓存中不存在，则获取数据库并缓存
    public static SmsGatewayGroup get(String groupId){
    	Object obj = map.get(groupId);
    	if(obj == null)return null;
    	return (SmsGatewayGroup)obj;
    }
}
