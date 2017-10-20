package com.sanerzone.smscenter.common.utils;
import com.sanerzone.jeebase.common.utils.EhCacheUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;

//签名工具类
public class SignUtils {
	
	//格式化内容
	public static String formatContent(String content){
		if(null == content) return "";
		
		String sign = "【" + get(content) +"】";
		if(sign.length() == 2) return content;
		
		content = content.replace(sign, "").replace("【", "[").replace("】", "]");
		content= sign+content;
		return content;
	}
	
	/**
	 * 获取发送内容，去掉签名
	 * @param content
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String getContent(String content){
	    if (StringUtils.isNotBlank(content)){
	        content = formatContent(content);
	        return content.substring(content.indexOf("】") + 1);
	    }else{
	        return content;
	    }
	}
	
	/**
	 * 获取签名
	 * @param content 
	 * @return 签名小于2个则返回""，签名必须大于等于2个字符
	 */
	public static String get(String content){
		if(null == content)return "";
		
		int startIdx = 0;
		int endIdx = 0;
		
		if(content.startsWith("【")) {
			startIdx = 1;
			endIdx = content.indexOf("】");
		} else if(content.endsWith("】")) {
			startIdx = content.lastIndexOf("【") + 1;
			endIdx = content.length() - 1;
		} else {
			return "";
		}
		
		if ((endIdx - startIdx) < 2) {
			return "";
		}
		String result = content.substring(startIdx, endIdx);
		if(StringUtils.isNotBlank(result)){
			return result.trim();
		}
		return result;
	}
	
	/**
	 * 判断是否验证码短信
	 * @param content
	 * @return true:是  false:否
	 */
	public static boolean isSecurityCode(String content){
		if (StringUtils.isBlank(content))return false;
		//验证码 and (分钟 or 小时 or 秒）
		String strRegex = "^.*验证码{1}.*$";
		if (content.matches(strRegex))return true;
		return false;
	}
	
    /**
     * 获取用户通道签名的接入号
     * 如果获取不到，则往缓存中放入一个值为：-1的接入号
     * @param userId
     * @param gatewayId
     * @param sign
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getSpnumber(String userId, String gatewayId, String sign){
        Object obj = EhCacheUtils.get(CacheKeys.GATEWAY_CACHE, CacheKeys.getGatewaySignKey(userId, gatewayId, sign));
        if(obj == null)return null;
        return (String)obj;
    }
    
}
