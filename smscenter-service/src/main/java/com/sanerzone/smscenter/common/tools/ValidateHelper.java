package com.sanerzone.smscenter.common.tools;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sanerzone.smscenter.api.entity.ApiResult;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;

public class ValidateHelper{
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> validate(String userId, HttpServletRequest request){
        Map map = Maps.newHashMap();
        
        // 校验 userId
        if (StringHelper.isBlank(userId)){
            map.put("code", "2");
            map.put("msg", "userid参数不能为空");
            return map;
        }
        
        String sendtermid = request.getParameter("extnum");//扩展号
        if (StringHelper.isNotBlank(sendtermid)){
            if (sendtermid.length() > 6){
                map.put("code", "9");
                map.put("msg", "扩展号格式错误");
                return map;
            }
        }
        
        String phones = request.getParameter("mobile");//号码
        if (StringHelper.isBlank(phones)){
            map.put("code", "2");
            map.put("msg", "mobile参数不能为空");
            return map;
        }
        
        String md5 = request.getParameter("sign");//签名
        if (StringHelper.isBlank(md5)){
            map.put("code", "2");
            map.put("msg", "sign参数不能为空");
            return map;
        }
        
        String ip = IPHelper.getIpAddr(request);
        String smsContent = request.getParameter("msgcontent");
        if (StringHelper.isBlank(smsContent)){
            map.put("code", "2");
            map.put("msg", "msgcontent参数不能为空");
            return map;
        }
        
        String ts = request.getParameter("ts");
        if (StringHelper.isBlank(ts)){
            map.put("code", "2");
            map.put("msg", "ts参数不能为空");
            return map;
        }
        
        Date now = new Date();
        long between = (now.getTime() - Long.parseLong(ts)) / (1000 * 60);
        if (between > 5){
            map.put("code", "14");
            map.put("msg", "发送超时");
            return map;
        }
        
        String curUserId = AccountCacheHelper.getStringValue(userId, "userid", "");
        if (StringHelper.isBlank(curUserId)){//用户不存在
            map.put("code", "4");
            map.put("msg", "用户不存在");
            return map;
        }else{
        	int usedFlag = AccountCacheHelper.getIntegerValue(userId, "usedFlag", 1);
			if(usedFlag == 0) {  //账户禁用发送功能
				 map.put("code", "4");
		         map.put("msg", "用户不存在");
		         return map;
			}
        	
        	
            String whiteIP = AccountCacheHelper.getStringValue(userId, "whiteIP", "");
            if (StringHelper.isBlank(whiteIP) || ("," + whiteIP + ",").indexOf("," + ip + ",") >= 0)
            {//验证IP
                String apikey = AccountCacheHelper.getStringValue(userId, "apikey", "");
                String myMD5 = HttpRequest.md5(userId + ts + apikey);//MD5加密 md5(userid + ts + apikey)
                if (md5.equals(myMD5)){//MD5验证
                    map.put("code", "0");//验证通过
                    map.put("msg", "提交成功");
                }else{
                    map.put("code", "6");
                    map.put("msg", "sign校验失败");
                }
            }else{
                map.put("code", "7");
                map.put("msg", "IP校验失败");
            }
        }
        
        return map;
    }
    
    @SuppressWarnings("rawtypes")
	public static String validateSmsParameter(Map pMap,HttpServletRequest request, HttpServletResponse response){
        
        String sendtermid = StringHelper.valueof(pMap.get("extnum"));//扩展号
        if (StringHelper.isNotBlank(sendtermid)){
            if (sendtermid.length() > 6){
                return result("9", "扩展号格式错误", null, response);
            }
        }
        
        String phones = StringHelper.valueof(pMap.get("mobile"));//号码
        if (StringHelper.isBlank(phones)){
            return result("2", "mobile参数不能为空", null, response);
        }
        
        return "0";
    }
    
    @SuppressWarnings("rawtypes")
	public static String validateMsgContent(Map pMap,HttpServletRequest request, HttpServletResponse response){
        
        String phones = StringHelper.valueof(pMap.get("msgcontent"));//内容
        if (StringHelper.isBlank(phones)){
            return result("2", "msgcontent参数不能为空", null, response);
        }
        
        return "0";
    }

    
    public static String validate(String userId, String ts, String sign, HttpServletRequest request, HttpServletResponse response){
        // 校验 userId
        if (StringHelper.isBlank(userId)){
            return result("2", "userid参数不能为空", null,  response);
        }
        // 校验 apikey
        if (StringHelper.isBlank(sign)){
            return result("2", "sign参数不能为空", null, response);
        }
        
        if (StringHelper.isBlank(ts)){
            return result("2", "ts参数不能为空", null, response);
        }
        
        
        Date now = new Date();
        long between = (now.getTime() - Long.parseLong(ts)) / (1000 * 60);
        if (between > 5){
            return result("14", "ts过期", null, response);
        }
        
        //验证用户是否存在
        String curUserId = AccountCacheHelper.getStringValue(userId, "userid", "");
        
        if (StringHelper.isBlank(curUserId)){
            return result("4", "用户不存在", null, response);
        }else{
        	int usedFlag = AccountCacheHelper.getIntegerValue(userId, "usedFlag", 1);
			if(usedFlag == 0) {  //账户禁用发送功能
				return result("4", "用户不存在", null, response);
			}
        	
            String whiteIP = AccountCacheHelper.getStringValue(userId, "whiteIP", "");
            String ip = IPHelper.getIpAddr(request);
            
            if (StringHelper.isBlank(whiteIP) || ("," + whiteIP + ",").indexOf("," + ip + ",") >= 0){//验证IP
            	String apikey = AccountCacheHelper.getStringValue(userId, "apikey", "");
                String myMD5 = HttpRequest.md5(userId + ts + apikey);//MD5加密 md5(userid + ts + apikey)
                if (!sign.equals(myMD5)){//MD5验证
                    return result("6", "MD5校验失败", null, response);
                }
            }else{
                return result("7", "IP校验失败", null, response);
            }
        }
        
        return "0";
    }
    
    public static String validate(String userId, String accId, String ts, String sign, HttpServletRequest request, HttpServletResponse response){
        // 校验 userId
        if (StringHelper.isBlank(userId)){
            return result("2", "userid参数不能为空", null,  response);
        }
        
        // 校验 accId
        if (StringHelper.isBlank(accId)){
            return result("2", "appid参数不能为空", null,  response);
        }
        // 校验 apikey
        if (StringHelper.isBlank(sign)){
            return result("2", "sign参数不能为空", null, response);
        }
        
        if (StringHelper.isBlank(ts)){
            return result("2", "ts参数不能为空", null, response);
        }
        
        
        Date now = new Date();
        long between = (now.getTime() - Long.parseLong(ts)) / (1000 * 60);
        if (between > 5){
            return result("14", "ts过期", null, response);
        }
        
        String key = userId+"_"+accId;
        
        //验证用户是否存在
        String curUserId = AccountCacheHelper.getStringValue(key, "userid", "");
        String curAccId = AccountCacheHelper.getStringValue(key, "accId", "");
        if (StringHelper.isBlank(curUserId)){
            return result("4", "用户不存在", null, response);
        }else if(StringHelper.isBlank(curAccId)){
        	return result("4", "应用不存在", null, response);
        }else{
        	int usedFlag = AccountCacheHelper.getIntegerValue(key, "accStatus", -1);
			if(usedFlag != 1) {  //账户禁用发送功能
				return result("4", "应用状态错误", null, response);
			}
        	
            String whiteIP = AccountCacheHelper.getStringValue(key, "whiteIP", "");
            String ip = IPHelper.getIpAddr(request);
            
            if (StringHelper.isBlank(whiteIP) || ("," + whiteIP + ",").indexOf("," + ip + ",") >= 0){//验证IP
            	String apikey = AccountCacheHelper.getStringValue(key, "apikey", "");
                String myMD5 = HttpRequest.md5(userId + accId + ts + apikey);//MD5加密 md5(userid + accId + ts + apikey)
                if (!sign.equals(myMD5)){//MD5验证
                    return result("6", "MD5校验失败", null, response);
                }
            }else{
                return result("7", "IP校验失败", null, response);
            }
        }
        
        return "0";
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String result(String code, String msg, Map<String,String> data, HttpServletResponse response){
        ApiResult entity = new ApiResult();
        entity.setCode(code);
        entity.setMsg(msg);
        entity.setData(data);
        return renderString(response, entity);
    }
    
    /**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
    public static String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JSON.toJSONString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
    public static String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
    
}
