package com.sanerzone.smscenter.gateway.http.handler.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.common.tools.AESUtil;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;
import com.xiaoleilu.hutool.http.HttpRequest;
import com.xiaoleilu.hutool.http.HttpResponse;

public class SmsApiTUOBAO extends GateWayHttpAbstract {

	public SmsApiTUOBAO(String gateWayID, String jsonParams) {
		super(gateWayID, jsonParams);
	}

	@Override
	public List<SMSSRMessage> doSend(SMSMTMessage mt) {

		// 组装参数
		Map<String,String> reqParams = Maps.newHashMap();
		reqParams.put("tradeNo", mt.getSmsREQMessage().getTaskid());
		reqParams.put("userPassword", AESUtil.MD5(paramsMaps.get("userPassword")));
		reqParams.put("userName", paramsMaps.get("userName"));
		String signContent = JSON.toJSONString(reqParams);
		reqParams.put("sign", AESUtil.encrypt(signContent, paramsMaps.get("key")));
		reqParams.put("phones", mt.getPhone());
		reqParams.put("etnumber", "");
		reqParams.put("content", mt.getSmsContent());
		
		// 提交网络
		String reqContent = JSON.toJSONString(reqParams);
		HttpRequest request = HttpRequest.post(paramsMaps.get("apiUrl") + "/openCard");
		request.timeout(120000);
		request.body(reqContent, "application/json");
		HttpResponse response = request.execute();
		String json = response.body();
		logger.info("网关:{}, 请求:{}, 响应:{}", this.gateWayID, reqContent, json);

		// 解析结果
		List<SMSSRMessage> resultList = Lists.newArrayList();
		SMSSRMessage message = initGatewayResultMessage(json, mt, "resCode");
		String resultCode = null;
		String resultMessage = null;
		String taskid = null;

		if (StringUtils.equals(message.getResult(), "F10199")) {
			try {
				Map map = (Map) JSON.parse(json);
				resultCode = String.valueOf(map.get("resCode"));
				resultMessage = String.valueOf(map.get("resMsg"));
				taskid = String.valueOf(map.get("liuliuOrderId"));
			} catch (Exception e) {
				logger.error("解析错误", e);
			}
			message.setMsgkey(mt.getPhone());
			setGatewayResultCode(message, taskid, resultCode, resultMessage, "P00000");
		}

		resultList.add(message);
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SmsHttpMsgResponse parseReport(String text, List<SMSRTMessage> rtList) {
		logger.info("{}网关推送状态报告:{}", gateWayID, text);
		
		SmsHttpMsgResponse response = new SmsHttpMsgResponse("OK", SmsHttpMsgResponse.CONTENTTYPE_JSON, "utf-8");
		if(StringUtils.isNotBlank(text)) {
			Map<String, Object> map = JSON.parseObject(text, Map.class);
			int type = (int) map.get("type");
			if(type == 3) {
				String reportStr = String.valueOf(map.get("liuliuOrderId"));
				String[] rows = reportStr.split(";");
				for(String row : rows) {
					String[] cos = row.split(",");
					SMSRTMessage smsRtMessage = initSmsRtMessage(cos[0], cos[1], cos[1], cos[2], "DELIVRD");
					rtList.add(smsRtMessage);
				}
			}
			response.setResult("{\"tradeNo\":\""+map.get("tradeNo")+"\", \"resCode\", \"P00000\"}");
    	}
		
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SmsHttpMsgResponse parseDeliver(String text, List<SMSMOMessage> moList) {
		logger.info("{}网关推送用户上行:{}", gateWayID, text);
		SmsHttpMsgResponse response = new SmsHttpMsgResponse("", SmsHttpMsgResponse.CONTENTTYPE_JSON, "utf-8");
		if(StringUtils.isNotBlank(text)) {
    		Map<String, Object> resultMap = JSON.parseObject(text, Map.class);
    		SMSMOMessage sMSMOMessage = new SMSMOMessage();
    		sMSMOMessage.setGateWayID(gateWayID);
    		sMSMOMessage.setMsgContent(String.valueOf(resultMap.get("mgs")));
    		sMSMOMessage.setMsgid(String.valueOf(resultMap.get("tradeNo")));
    		sMSMOMessage.setPhone(String.valueOf(resultMap.get("orderId")));
    		sMSMOMessage.setSpnumber(String.valueOf(resultMap.get("liuliuOrderId")));
    		sMSMOMessage.setUuid(UUID.randomUUID().toString());
    		moList.add(sMSMOMessage);
    		response.setResult("{\"tradeNo\":\""+resultMap.get("tradeNo")+"\", \"resCode\", \"P00000\"}");
    	}
		return response;
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
		
		SmsApiTUOBAO api = new SmsApiTUOBAO("TUOBAO",
				"{\"userName\":\"tuobaoceshi\",\"userPassword\":\"0p4dlSLE\",\"key\":\"ZlMiMk80JmvhSXlq\",\"apiUrl\":\"http://apis.duanxb.com/sms\"}");

		SMSREQMessage req = new SMSREQMessage();
		req.setTaskid(new MsgId().toString());
		
		SMSMTMessage mt = new SMSMTMessage();
		mt.setPhone("13666672546");
		mt.setSmsContent("【宽信科技】验证码:123456");
		mt.setSmsREQMessage(req);
		
		List<SMSSRMessage> list = api.doSend(mt);
		System.out.println(JSON.toJSONString(list));
		
		
//		String json = "{\"data\":{\"taskid\":\"29b59878ce514572ae85d916bafce847\"},\"code\":\"0\",\"msg\":\"success\"}";
//		
//		Map map = (Map) JSON.parse(json);
//		String resultCode = String.valueOf(map.get("code"));
//		String resultMessage = String.valueOf(map.get("msg"));
//		String taskid = null;
//		if(StringUtils.equals(resultCode, "0")) {
//			Map data = (Map) map.get("data");
//			if(data != null) {
//				taskid = String.valueOf(data.get("taskid"));
//			}
//		}
//		
//		System.out.println(taskid);
	}

}