package com.sanerzone.smscenter.gateway.http.handler.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;

public class SmsApiJIESMS extends GateWayHttpAbstract
{

    public SmsApiJIESMS(String gateWayID, String jsonParams)
    {
        super(gateWayID, jsonParams);
    }

    @Override
    public List<SMSSRMessage> doSend(SMSMTMessage mt)
    {
        StringBuilder strBuffer = new StringBuilder();
        try
        {
            strBuffer.append("account=").append(paramsMaps.get("account"));
            strBuffer.append("&password=").append(paramsMaps.get("password"));
            //短信类型 0普通短信 1长短信 可为空,值为空时默认为0,以普通短信发送
            strBuffer.append("&mobile=").append(mt.getPhone());
            strBuffer.append("&content=").append(URLEncoder.encode(mt.getSmsContent(),"utf-8"));
            strBuffer.append("&longSms=").append(paramsMaps.get("longSms"));
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error("网关:{}, 响应:{}", this.gateWayID, e);
        }
        
        String json =  HttpRequest.sendGet(paramsMaps.get("apiUrl") + "/submit", strBuffer.toString(), null, "UTF-8", 10000);
        
        logger.info("网关:{}, 请求:{}, 响应:{}", this.gateWayID, strBuffer.toString(), json);
        
        List<SMSSRMessage> resultList = Lists.newArrayList();
        
        SMSSRMessage message = initGatewayResultMessage(json, mt, ",");
        String resultCode = null;
        String resultMessage = null;
        String taskid = null;
        
        if(StringUtils.equals(message.getResult(), "F10199")) {
        	
        	String[] lines = json.split("\n");
        	if(lines.length > 1) {
        		String[] line1 = lines[0].split(",");
        		 resultCode = line1[0];
                 resultMessage = line1[1];
                 
                 String[] line2 = lines[1].split(",");
                 taskid = line2[1];
        	}
        	message.setMsgkey(mt.getPhone());
            setGatewayResultCode(message, taskid, resultCode, resultMessage, "0");
        }
        
        resultList.add(message);
        return resultList;
    }
    
    @Override
    public SmsHttpMsgResponse parseReport(String text, List<SMSRTMessage> rtList)
    {
        logger.info("{}网关推送状态报告:{}", gateWayID, text);
        try {
        	if(StringUtils.isNotBlank(text)) {
        		String[] lines = text.split("\n");
        		if(lines.length > 0) {
        			String[] line = lines[0].split(",");
        			if(StringUtils.equals(line[0], "0")) {
        				SMSRTMessage smsRtMessage ;
        				for (int i = 1; i < lines.length; i++) {
        					if(StringUtils.isNotBlank(lines[i])) {
	        					line = lines[i].split(",");
	        					smsRtMessage = initSmsRtMessage(line[0], line[1], line[1], line[2], "1");
	            				rtList.add(smsRtMessage);
        					}
						}
        			}
        		}
        	}
        } catch(Exception e) {
        	 return new SmsHttpMsgResponse("ERR");
        }
        return new SmsHttpMsgResponse("OK");
    }

    @Override
    public SmsHttpMsgResponse parseDeliver(String text, List<SMSMOMessage> moList)
    {
    	logger.info("{}网关推送用户上行:{}", gateWayID, text);
        try {
        	if(StringUtils.isNotBlank(text)) {
        		String[] lines = text.split("\n");
        		if(lines.length > 0) {
        			String[] line = lines[0].split(",");
        			if(StringUtils.equals(line[0], "0")) {
        				SMSMOMessage smsMoMessage;
        				for (int i = 1; i < lines.length; i++) {
        					if(StringUtils.isNotBlank(lines[i])) {
	        					line = lines[i].split(",");
	        					smsMoMessage = new SMSMOMessage();
	                			smsMoMessage.setSpnumber("");
	                			smsMoMessage.setPhone(line[1]);
	                			smsMoMessage.setMsgContent(line[2]);
	                			smsMoMessage.setMsgid(UUID.randomUUID().toString());
	                			smsMoMessage.setGateWayID(gateWayID);
	                			
	                			moList.add(smsMoMessage);
        					}
						}
        			}
        		}
        	}
        } catch (Exception e) {
        	
        }
        return null;
    }

    @Override
    public String queryReport(String jsonText)
    {
    	StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("account=").append(paramsMaps.get("account"));
        strBuffer.append("&password=").append(paramsMaps.get("password"));
        return HttpRequest.sendGet(paramsMaps.get("apiUrl") + "/report", strBuffer.toString(), null);
    }

    @Override
    public String queryDeliver(String jsonText)
    {
    	StringBuffer strBuffer = new StringBuffer();
        strBuffer.append("account=").append(paramsMaps.get("account"));
        strBuffer.append("&password=").append(paramsMaps.get("password"));
        System.out.println(strBuffer.toString());
        return HttpRequest.sendGet(paramsMaps.get("apiUrl") + "/mo", strBuffer.toString(), null);
    }
    
    public String balance()
    {
        return "";
    }
    
    public static void main(String[] args)
    {
    	SmsApiJIESMS api = new SmsApiJIESMS("JIESMS", "{\"account\":\"lihua\",\"password\":\"llh@9988\",\"longSms\":\"\",\"apiUrl\":\"http://www.88dx.cn\",\"reportSecTime\":\"5\", \"deliverSecTime\":\"5\"}");
    	SMSMTMessage mt = new SMSMTMessage();
    	mt.setPhone("13666672546,18671024100,18162361207");
    	mt.setSmsContent("【千橡科技】验证码：123456，10分钟内有效，如非本人操作，请忽略！");
    	List<SMSSRMessage> list = api.doSend(mt);
    	System.out.println(JSON.toJSONString(list));
    }
    
}