package com.sanerzone.smscenter.gateway.http.handler.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.common.tools.Base64Helper;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;

public class  SmsApiCmccOpen extends GateWayHttpAbstract {

	public SmsApiCmccOpen(String gateWayID, String jsonParams) {
		super(gateWayID, jsonParams);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<SMSSRMessage> doSend(SMSMTMessage mt) {
		StringBuffer strBuffer = new StringBuffer();
		HashMap<String, String> postHeaders = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String ,Object>();

		try {
			postHeaders.put("Authorization", getAuthorization("SDP","UsernameToken","AppKey"));
			postHeaders.put("X-WSSE", getXwsse(paramsMaps.get("username"),paramsMaps.get("passwordDigest")));
//			postHeaders.put("Content-Type", "application/json;charset=UTF-8");
			postHeaders.put("Host", paramsMaps.get("Host"));
			
			JSONObject jsonObject = JSON.parseObject(mt.getSmsREQMessage().getParamValue());
			
			Map<String, String> map = new LinkedHashMap<String, String>();
	        //map.put("code", mt.getSmsContent());
			int index = 0;
			for (Object str : jsonObject.getJSONArray("paramValue"))
			{
				/*if (index == 0)
				{
					map.put("code", (String) str);
				}*/
				
				map.put("msg" + index, (String) str);
				index ++;
			}

	        params.put("from", paramsMaps.get("from"));
	        //params.put("smsTemplateId", paramsMaps.get("smsTemplateId"));
	        params.put("smsTemplateId", jsonObject.getString("templateId"));
	        params.put("paramValue", JSON.toJSON(map)); //模板参数值
	        params.put("to", "+86" + mt.getPhone());
	        params.put("notifyURL", paramsMaps.get("notifyURL"));
	        
		} catch (UnsupportedEncodingException e) {
			logger.error("网关:{}, 响应:{}", this.gateWayID, e);
		} catch (Exception e) {
			logger.error("网关:{}, 响应:{}", this.gateWayID, e);
		}

//		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		String json = HttpRequest.sendPostJson(paramsMaps.get("apiUrl") + "sendTemplateSms/v1", JSON.toJSON(params).toString(), postHeaders, 3000);
		logger.info("网关:{}, 请求:{}, 响应:{}", this.gateWayID, strBuffer.toString(), json);

		List<SMSSRMessage> resultList = Lists.newArrayList();

		SMSSRMessage message = initGatewayResultMessage(json, mt, "code");
		String resultCode = null;
		String resultMessage = null;
		String taskid = null;

		if (StringUtils.equals(message.getResult(), "F10199")) {
			try {
				Map map = (Map) JSON.parse(json);
				resultCode = String.valueOf(map.get("code"));
				resultMessage = String.valueOf(map.get("description"));
				if(StringUtils.equals(resultCode, "000000")) {
					Map data = (Map) map.get("result");
					if(data != null) {
						taskid = String.valueOf(data.get("smsMsgId"));
					}
				}
			} catch (Exception e) {
				logger.error("解析错误", e);
			}
			message.setMsgkey("");
			setGatewayResultCode(message, taskid, resultCode, resultMessage, "000000");
		}

		resultList.add(message);
		return resultList;
	}

	@Override
	public SmsHttpMsgResponse parseReport(String text, List<SMSRTMessage> rtList) {
		logger.info("{}网关推送状态报告:{}", gateWayID, text);
		
		if(StringUtils.isNotBlank(text)) {
//			smsMsgId=100001200101130511090326000001&status=succeed
			//Map<String, Object> resultMap = JSON.parseObject(text, Map.class);
			Map<String, String> resultMap = HttpRequest.getParamMap(text);
	        
	        SMSRTMessage smsRtMessage = initSmsRtMessage(String.valueOf(resultMap.get("smsMsgId")), "", null, 
                    String.valueOf(resultMap.get("status")), "succeed");
	            
	        rtList.add(smsRtMessage);
    	}
		
		return new SmsHttpMsgResponse("OK");
	}

	@Override
	public SmsHttpMsgResponse parseDeliver(String text,
			List<SMSMOMessage> moList) {
		return null;
	}

	@Override
	public String queryReport(String jsonText) {
		return null;
	}

	@Override
	public String queryDeliver(String jsonText) {
		return null;
	}
	
	private static String getXwsse(String appKey, String appSecret) throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("Username", appKey);
		// 生成NONCE
		String nonce = UUID.randomUUID().toString().toUpperCase();

		// 生成时间戳Created
		String created = null;
		// 时间Created
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		created = sdf.format(calendar.getTime());

		byte[] passwd = (nonce + created + "e47bf413ad412f0f").getBytes();

		MessageDigest alga = MessageDigest.getInstance("sha-256");
		alga.update(passwd);
		String passwordDigest = Base64Helper.encode(alga.digest());
		map.put("PasswordDigest", passwordDigest);
		map.put("Nonce", nonce);
		map.put("Created", created);
		String result = convertMapToFormParam(map);
		String authorization = "UsernameToken %s";
		return String.format(authorization, result);
	}

	private String getAuthorization(String realm,String profile, String type){
        Map<String,String> map = new LinkedHashMap<String,String>();
        map.put("realm",realm);
        map.put("profile",profile);
        map.put("type",type);
        String result = convertMapToFormParam(map);
        String authorization = "WSSE %s";
        return String.format(authorization,result);
    }
	
	private static String convertMapToFormParam(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		for (String key : map.keySet()) {
			if (map.get(key) != null && !"".equals(map.get(key))) {
				sb.append(key).append("=").append("\"").append(map.get(key))
						.append("\"").append(",");
			}
		}
		String result = sb.toString();
		return result.substring(0, result.length() - 1);
	}
	
	public static void main(String[] args) {
		//{"templateId":"201706091122","paramValue":["123456","5"]}

		String apiUrl = "http://aep.api.cmccopen.cn/sms/";
		
		//String notifyURL = "/api/sms/report/pr/cmcc";
		
		SmsApiCmccOpen api = new SmsApiCmccOpen("JYCSMS",
				"{\"from\":\"106575261108111\",\"username\":\"08165079f8064d13832871ee54aca41b\",\"passwordDigest\":\"e47bf413ad412f0f\",\"smsTemplateId\":\"a5d63947-84f0-4e47-9d35-1b3de22745d7\",\"apiUrl\":\""+apiUrl+"\",\"Host\":\"aep.api.cmccopen.cn\"}");
		
		String [] strs = {"654321","5"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateId", "a5d63947-84f0-4e47-9d35-1b3de22745d7");
		map.put("paramValue", strs);
		
		SMSREQMessage req = new SMSREQMessage();
		req.setParamValue(JSON.toJSONString(map));
		
		SMSMTMessage mt = new SMSMTMessage();
		//mt.setPhone("13666672546");
		mt.setPhone("15669959631");
		mt.setSmsContent("666");
		mt.setSmsREQMessage(req);
		List<SMSSRMessage> list = api.doSend(mt);

		System.out.println(JSON.toJSONString(list));
	}
}