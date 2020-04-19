package com.sanerzone.smscenter.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpResponse;

public class SmsApi2CRF {

	protected static Logger logger = LoggerFactory.getLogger(SmsApi2CRF.class);
	private static String account = "axu0002";
	private static String password = "vgy123";
	private static String apiUrl = "http://42.121.1.167:8080/";
	
	public static void sendSMS(String phone, String smsContent) {
		StringBuilder strBuffer = new StringBuilder();
        try
        {
            strBuffer.append("account=").append(account);
            strBuffer.append("&password=").append(password);
            //短信类型 0普通短信 1长短信 可为空,值为空时默认为0,以普通短信发送
            strBuffer.append("&mobile=").append(phone);
            strBuffer.append("&content=").append(URLEncoder.encode(smsContent,"utf-8"));
            strBuffer.append("&longSms=").append("");
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error("网关",  e);
        }
        
        HttpRequest httpRequest = HttpRequest.get(apiUrl + "/submit?" + strBuffer.toString());
        HttpResponse httpResponse = httpRequest.execute();
        String json =  httpResponse.body();
        logger.info("网关:{}, 请求:{}, 响应:{}", "CRF", strBuffer.toString(), json);
        if(StringUtils.indexOf(json, ",") > -1) {
	        String[] lines = json.split("\n");
	    	if(lines.length > 1) {
	    		String[] line1 = lines[0].split(",");
	    		 String resultCode = line1[0];
	             String resultMessage = line1[1];
	             
	             String[] line2 = lines[1].split(",");
	             String taskid = line2[1];
	             
	             logger.info("状态:{}, 描述:{}, ID:{}", resultCode, resultMessage, taskid);
	    	}
        }
	}
	
	public static void main(String[] args) {
		sendSMS("15669959631", "验证码：123456");
	}
}
