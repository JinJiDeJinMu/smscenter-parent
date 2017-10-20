package com.sanerzone.smscenter.gateway.http.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;

public class SmsApiJYCSMS extends GateWayHttpAbstract {

	public SmsApiJYCSMS(String gateWayID, String jsonParams) {
		super(gateWayID, jsonParams);
	}

	@Override
	public List<SMSSRMessage> doSend(SMSMTMessage mt) {
		Long ts = System.currentTimeMillis();

		StringBuffer strBuffer = new StringBuffer();

		try {
			strBuffer.append(paramsMaps.get("apiUrl") + "/sms/send?");
			strBuffer.append("&userid=").append(paramsMaps.get("userid"));
			strBuffer.append("&ts=").append(ts);
			strBuffer.append("&sign=")
					.append(HttpRequest.md5(paramsMaps.get("userid") + ts + paramsMaps.get("apikey")));
			strBuffer.append("&mobile=").append(mt.getPhone());
			strBuffer.append("&msgcontent=").append(URLEncoder.encode(mt.getSmsContent(), "utf-8"));
			strBuffer.append("&messageid=").append(mt.getSmsREQMessage().getTaskid());
			// strBuffer.append("&time=").append("");

		} catch (UnsupportedEncodingException e) {
			logger.error("网关:{}, 响应:{}", this.gateWayID, e);
		}

		String json = HttpRequest.sendPost(strBuffer.toString(), null, null);

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
				resultMessage = String.valueOf(map.get("msg"));
				if(StringUtils.equals(resultCode, "0")) {
					Map data = (Map) map.get("data");
					if(data != null) {
						taskid = String.valueOf(data.get("taskid"));
					}
				}
			} catch (Exception e) {
				logger.error("解析错误", e);
			}
			message.setMsgkey(mt.getPhone());
			setGatewayResultCode(message, taskid, resultCode, resultMessage, "0");
		}

		resultList.add(message);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SmsHttpMsgResponse parseReport(String text, List<SMSRTMessage> rtList) {
		logger.info("{}网关推送状态报告:{}", gateWayID, text);
		
		if(StringUtils.isNotBlank(text)) {
			List<Map<String, Object>> list = JSON.parseObject(text, List.class);
	        for(Map<String, Object> resultMap : list ) {
		        SMSRTMessage smsRtMessage = initSmsRtMessage(String.valueOf(resultMap.get("taskid")), String.valueOf(resultMap.get("mobile")),String.valueOf(resultMap.get("mobile")), 
	                    String.valueOf(resultMap.get("code")), "DELIVRD");
		            
		        rtList.add(smsRtMessage);
	        }
    	}
		
		return new SmsHttpMsgResponse("OK");
	}

	@SuppressWarnings("unchecked")
	@Override
	public SmsHttpMsgResponse parseDeliver(String text, List<SMSMOMessage> moList) {
		logger.info("{}网关推送用户上行:{}", gateWayID, text);
		
		if(StringUtils.isNotBlank(text)) {
    		Map<String, Object> resultMap = JSON.parseObject(text, Map.class);
    		
    		SMSMOMessage sMSMOMessage = new SMSMOMessage();
    		sMSMOMessage.setGateWayID(gateWayID);
    		sMSMOMessage.setMsgContent(String.valueOf(resultMap.get("msgcontent")));
    		sMSMOMessage.setMsgid(String.valueOf(resultMap.get("id")));
    		sMSMOMessage.setPhone(String.valueOf(resultMap.get("mobile")));
    		sMSMOMessage.setSpnumber(String.valueOf(resultMap.get("srcid")));
    		sMSMOMessage.setUuid(UUID.randomUUID().toString());
    		
    		moList.add(sMSMOMessage);
    	}
		
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

	public String balance() {
		return "";
	}

	public static void main(String[] args) {
//		SmsApiJYCSMS api = new SmsApiJYCSMS("JYCSMS",
//				"{\"userId\":\"3862\",\"passWord\":\"6c022671511e4fb89bea0d6a61fc5dc4\",\"templateId\":\"1447\",\"apiUrl\":\"http://114.55.90.98:8083/sismsapi.go\"}");
		
//		SmsApiJYCSMS api = new SmsApiJYCSMS("JYCSMS",
//				"{\"userid\":\"3862\",\"passWord\":\"6c022671511e4fb89bea0d6a61fc5dc4\",\"templateId\":\"1447\",\"apiUrl\":\"http://192.168.1.110:8080/jyc-flow/api\"}");
//
//		SMSREQMessage req = new SMSREQMessage();
//		req.setTaskid(new MsgId().toString());
//		
//		SMSMTMessage mt = new SMSMTMessage();
//		mt.setPhone("13666672546");
//		mt.setSmsContent("【宽信科技】验证码:123456");
//		mt.setSmsREQMessage(req);
//		
//		List<SMSSRMessage> list = api.doSend(mt);
//
//		System.out.println(JSON.toJSONString(list));
		
		
		String json = "{\"data\":{\"taskid\":\"29b59878ce514572ae85d916bafce847\"},\"code\":\"0\",\"msg\":\"success\"}";
		
		Map map = (Map) JSON.parse(json);
		String resultCode = String.valueOf(map.get("code"));
		String resultMessage = String.valueOf(map.get("msg"));
		String taskid = null;
		if(StringUtils.equals(resultCode, "0")) {
			Map data = (Map) map.get("data");
			if(data != null) {
				taskid = String.valueOf(data.get("taskid"));
			}
		}
		
		System.out.println(taskid);
	}

}