package com.sanerzone.smscenter.common.utils;
import java.util.List;

import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.GatewayResult;
import com.sanerzone.smscenter.modules.sms.entity.SmsGateway;

//通道工具类
public class GatewayUtils {
	
	//匹配通道信息 校验签名
	public static GatewayResult getGateway(String userId,String groupId,String phoneType,String provinceId,String sign){
		return getGateway(userId, groupId, phoneType, provinceId, sign, true);
	}
	
	//匹配通道信息  不校验签名
	public static GatewayResult getGateway(String groupId,String phoneType,String provinceId){
		return getGateway("", groupId, phoneType, provinceId, "", false);
	}
	
	public static GatewayResult getGateway(String userId,String groupId,String phoneType,String provinceId,String sign,boolean signFlag){
		GatewayResult result = new GatewayResult();
		result.setErrorCode("F007");//匹配通道失败
		
		//验证分组是否存在
		if(!GatewayGroupUtils.isExists(groupId)){
			result.setErrorCode("F0071");//分组不存在
			return result;
		}
		
		List<String> list = GatewayGroupRelUtils.get(groupId, phoneType, provinceId);
		if(list != null && list.size() >0){
			for (String gatewayId : list) {
				String spNumber = getSpNumber(userId, gatewayId, sign, signFlag);//接入号
				if(StringUtils.isNotBlank(spNumber)){//配到到通道
					result.setErrorCode("T000");
					result.setGatewayId(gatewayId);
					result.setSpNumber(spNumber);
					return result;
				}
			}
		}else{
			result.setErrorCode("F0072");//通道分组不存在
			return result;
		}
		
		return result;
		
	}
	
	//是否到匹配通道
	private static String getSpNumber(String userId,String gatewayId,String sign,boolean signFlag){
		String spNumber = "";
		if(signFlag){//验证签名
			spNumber = SignUtils.getSpnumber(userId, gatewayId, sign);
			if(StringUtils.isBlank(spNumber) || !StringUtils.isNumeric(spNumber)) {
				return "";
			}
		}
		
		SmsGateway info = GatewayUtils.getGatewayInfo(gatewayId);
		if(info != null && StringUtils.isNotBlank(info.getId())){
			if(!"1".equals(info.getGwStatus()))return"";//停用状态
			if(signFlag){
				return info.getGwSpNumber() + spNumber;
			}else{
				if("0".equals(info.getSmsSignModel())){
					return info.getGwSpNumber();
				}else{
					return "";
				}
			}
		}else{
			return "";
		}
	}
	
	//获取应用代码
	public static String getAppCode(String gatewayId){
		SmsGateway smsGateway = getGatewayInfo(gatewayId);
		if(smsGateway == null)return "";
		return smsGateway.getAppCode();
	}
	
	//获取通道信息
    public static SmsGateway getGatewayInfo(String gatewayId){
    	String key = CacheKeys.getCacheGatewayInfoKey(gatewayId);
    	Object obj = EhCacheUtils.get(CacheKeys.GATEWAY_CACHE, key);
    	if(obj != null)return (SmsGateway)obj;
    	return null;
    }
	
	public static void del(String gatewayId){
		String key = CacheKeys.getCacheGatewayInfoKey(gatewayId);
		EhCacheUtils.remove(CacheKeys.GATEWAY_CACHE, key);
	}
	
	public static void put(String gatewayId,Object value){
		String key = CacheKeys.getCacheGatewayInfoKey(gatewayId);
		EhCacheUtils.put(CacheKeys.GATEWAY_CACHE, key,value);
	}
}
