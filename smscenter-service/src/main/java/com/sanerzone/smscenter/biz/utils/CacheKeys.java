package com.sanerzone.smscenter.biz.utils;

/**
 * 缓存主键工具类
 * 
 * @author zhukc
 *
 */
public class CacheKeys {
	
	//短信一天内发送次数key
	public static String getCacheDaySmsSendKey(String param){
		return "smsd_"+param;
	}
	
	//短信一个月内发送次数key
	public static String getCacheMonthSmsSendKey(String param){
		return "smsm_"+param;
	}
	
	//短信重新发送次数key
	public static String getCacheAgainSmsSendKey(String param){
		return "smsa_"+param;
	}
	
	//网关key 分组ID_运营商_省份
	public static String getCacheGatewayGroupKey(String groupId,String phoneType,String provinceId){
		return groupId+"_"+phoneType+"_"+provinceId;
	}
	
	//通道信息key
	public static String getCacheGatewayInfoKey(String gatewayId){
		return "gatewayInfo_"+gatewayId;
	}
	
	
	//分组ID
	public static String getCacheGroupKey(String groupId){
		return "gatewaygroup_"+groupId;
	}
	
	//用户签名 key
	public static String getCacheUserSignKey(String userId){
		return "user_sign_"+userId;
	}
	
	/**
     * 获取用户通道签名key 
     * 用户ID_通道ID_签名
     * @param userId
     * @param gatewayId
     * @param sign
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getGatewaySignKey(String userId, String gatewayId, String sign)
    {
        return userId + "_" + gatewayId + "_" + sign;
    }
    
    /**
     * 获取水务最大发送ID Key
     * @return
     */
    public static String getSWSendIdKey(){
    	return "sw_maxid";
    }

}
