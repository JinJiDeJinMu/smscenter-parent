package com.sanerzone.smscenter.gateway.http.handler.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sanerzone.smscenter.biz.message.*;
import com.sanerzone.smscenter.common.tools.HttpRequest;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Author:         JinMu
 * CreateDate:     2018/5/29 13:23
 * Version:        1.0
 */
public class SmsApiOthers extends GateWayHttpAbstract {

    public SmsApiOthers(String gateWayID, String jsonParams){
        super(gateWayID, jsonParams);
    }
    @Override
    public List<SMSSRMessage> doSend(SMSMTMessage mt) {
        StringBuffer strBuffer = new StringBuffer();
        Map<String, Object> params = new HashMap<String ,Object>();
        try {
            params.put("sn", paramsMaps.get("sn"));
            params.put("pwd", paramsMaps.get("pwd"));
            params.put("mobile", "+86" + mt.getPhone());
            params.put("content", mt.getSmsContent());
            params.put("ext", paramsMaps.get("ext"));
            params.put("stime", paramsMaps.get("stime"));
            params.put("rrid", paramsMaps.get("rrid"));
            params.put("msgfmt", paramsMaps.get("msgfmt"));
        } catch (Exception e) {
            logger.error("网关:{}, 响应:{}", this.gateWayID, e);
        }

        String result = HttpRequest.sendGet(paramsMaps.get("apiUrl"),Joiner.on("&").withKeyValueSeparator("=").join(params),null);
        logger.info("网关:{}, 请求:{}, 响应:{}", this.gateWayID, strBuffer.toString(), result);

        List<SMSSRMessage> resultList = Lists.newArrayList();

        SMSSRMessage message = initGatewayResultMessage(result, mt, "-");
        String resultCode = null;
        String resultMessage = null;
        String taskid = null;
        if (StringUtils.equals(message.getResult(), "F10199")) {
            try {
                if(result.indexOf("-") == -1) {
                        resultCode="000000";
                        taskid = result;
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
            Map<String, String> resultMap = HttpRequest.getParamMap(text);
            SMSRTMessage smsRtMessage = initSmsRtMessage(String.valueOf(resultMap.get("smsMsgId")), "", null,
                    String.valueOf(resultMap.get("status")), "succeed");

            rtList.add(smsRtMessage);
        }
        return new SmsHttpMsgResponse("OK");
    }

    @Override
    public SmsHttpMsgResponse parseDeliver(String text, List<SMSMOMessage> moList) {
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

    public static void main(String[] args) throws IOException {
        String apiUrl = "http://sdk.entinfo.cn:8061/mdsmssend.ashx";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sn","SDK-BBX-010-27883");
        params.put("pwd","D16199775CA9AE56BD6782B03B95CE1E");
        params.put("mobile","15669959631");
        params.put("content","【杭州爱云】测试123");
        params.put("ext","");
        params.put("stime","");
        params.put("rrid","");
        params.put("msgfmt","");

        JSONObject json = new JSONObject();
        json.put("sn","SDK-BBX-010-27883");
        json.put("pwd","D16199775CA9AE56BD6782B03B95CE1E");
        json.put("mobile","15669959631");
        json.put("content","【杭州爱云】测试1234");
        json.put("ext","");
        json.put("stime","");
        json.put("rrid","");
        json.put("msgfmt","");
       // System.out.println(JSON.toJSON(params).toString());
        String result = HttpRequest.sendPost(apiUrl,JSON.toJSON(params).toString(),null);
       // String result = HttpRequest.sendGet(apiUrl,Joiner.on("&").withKeyValueSeparator("=").join(params),null);
        Map map = (Map)JSON.parse(result);
        System.out.println(map);
    }
}