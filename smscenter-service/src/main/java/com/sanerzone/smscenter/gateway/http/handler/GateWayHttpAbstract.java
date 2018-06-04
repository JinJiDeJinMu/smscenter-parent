package com.sanerzone.smscenter.gateway.http.handler;

import io.netty.util.concurrent.Future;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sanerzone.common.support.sequence.CachedMillisecondClock;
import com.sanerzone.common.support.storedMap.BDBStoredMapFactoryImpl;
import com.sanerzone.common.support.utils.Encodes;
import com.sanerzone.common.support.utils.EventLoopGroupFactory;
import com.sanerzone.common.support.utils.ExitUnlimitCirclePolicy;
import com.sanerzone.smscenter.biz.message.SMSMOMessage;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSRTMessage;
import com.sanerzone.smscenter.biz.message.SMSSRMessage;
import com.sanerzone.smscenter.gateway.GatewayStartUp;
import com.sanerzone.smscenter.gateway.http.message.SmsHttpMsgResponse;

public abstract class GateWayHttpAbstract
{
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static String STOREDKEY_FORMART = "%s_%s";
    
    protected String gateWayID = "";
    
    protected Map<String, String> paramsMaps = Maps.newHashMap();
    
    private boolean gatewaySign = false;
    
    private Map<String, Serializable> storedMap ;
    
    public boolean isGatewaySign()
    {
        return gatewaySign;
    }
    
    @SuppressWarnings("unchecked")
    public GateWayHttpAbstract(String gateWayID, String jsonParams)
    {
        this.gateWayID = gateWayID;
        if (StringUtils.isNotBlank(jsonParams))
        {
            jsonParams = Encodes.unescapeHtml(jsonParams);
            try
            {
                paramsMaps = (Map<String, String>)JSON.parseObject(jsonParams, Map.class);
                this.gatewaySign =
                    (StringUtils.isBlank(paramsMaps.get("gateway_sign")) || StringUtils.equals(paramsMaps.get("gateway_sign"),
                        "0")) ? false : true;
                
                storedMap = BDBStoredMapFactoryImpl.INS.buildMap("http", "message_" + gateWayID);
            }
            catch (Exception e)
            {
                logger.error("初始化通道:{},参数出错，参数:{}，错误信息：{}", gateWayID, jsonParams, e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    public SMSSRMessage initGatewayResultMessage(String httpBody, SMSMTMessage message, String key)
    {
    	SMSSRMessage srMessage = new SMSSRMessage();
    	message.setSubmitTime(new Date());
        srMessage.setMessage(message);
        if (StringUtils.isBlank(httpBody))
        { //返因结果为空 121111
            srMessage.setResult("F10101");
            srMessage.setReserve("接口返回空");
        }
        else if (StringUtils.startsWith(httpBody, "JYC.ERROR.HTTP."))
        { //HTTP调用异常 13112开头
            srMessage.setResult("F10102" + StringUtils.substring(httpBody, 16));
            srMessage.setReserve("接口调用异常");
        }
        /*else if (httpBody.indexOf(key) == -1)
        { //检查关键字段是否存在
            srMessage.setResult("F10103");
            srMessage.setReserve("接口返回格式错");
        }*/
        else if (httpBody.indexOf(key) != -1)
        { //检查关键字段是否存在
            srMessage.setResult("F10103");
            srMessage.setReserve("接口返回格式错");
        }
        else
        { //请求成功未报错, 如果最终还是这个状态，可能是 解析结果失败
            srMessage.setResult("F10199");
            srMessage.setReserve("通过");
        }
        return srMessage;
    }
    
    public void setGatewayResultCode(SMSSRMessage message, String msgid, String resultCode, String resultMessage,
        String successCode)
    {
        message.setMsgid(msgid);
        if (StringUtils.equals(successCode, resultCode))
        {//提交通道成功
            message.setResult("0");
        }
        else
        {
            message.setResult("F10001");
            message.setReserve(resultCode + ":" + resultMessage);
        }
    }
    
    public SMSRTMessage initSmsRtMessage(String msgid, String msgkey, String destTermID, String stat, String successStat){
    	SMSRTMessage smsRtMessage = new SMSRTMessage();
    	smsRtMessage.setGateWayID(gateWayID);
    	smsRtMessage.setMsgid(msgid);
    	smsRtMessage.setPhone(destTermID);
		smsRtMessage.setStat(stat);
		if(StringUtils.equals(stat, successStat)) {
			smsRtMessage.setStat("DELIVRD");
		}
		
		smsRtMessage.setSubmitTime(DateFormatUtils.format(CachedMillisecondClock.INS.now(),"yyMMddHHmmss"));
		smsRtMessage.setDoneTime(DateFormatUtils.format(CachedMillisecondClock.INS.now(),"yyMMddHHmmss"));
		
		logger.info("initSmsRtMessage cacheKey: {}_{}", msgid, msgkey);
		SMSMTMessage smsMtMessage = (SMSMTMessage) storedMap.remove(String.format(STOREDKEY_FORMART, msgid, msgkey));
    	if( null != smsMtMessage) {
    		smsRtMessage.setSmsMt(smsMtMessage);
    		smsRtMessage.setSpnumber(smsMtMessage.getSpnumber());
    		smsRtMessage.setSmscSequence("0");
    		//smsRtMessage.setSubmitTime(smsMtMessage.getSubmitTime());
    	}
    	return smsRtMessage;
    }
    
    public boolean send(SMSMTMessage mt)
    {
        List<SMSSRMessage> srList = doSend(mt);
        
        for(SMSSRMessage message : srList) {
        	
        	logger.info("initSmsRtMessage cacheKey: {}_{}", message.getMsgid(), message.getMsgkey());
        	if(StringUtils.isNotBlank(message.getMsgid()))
        		storedMap.put(String.format(STOREDKEY_FORMART, message.getMsgid(),  message.getMsgkey()), message.getMessage());
        	
        	GatewayStartUp.httpGateWayMessage.sendSmsSRMessage(message, this.gateWayID);
        }
        
        return true;
    }
    
    public SmsHttpMsgResponse report(String text, boolean isQuery)
    {
        List<SMSRTMessage> rtList = Lists.newArrayList();
        if (isQuery)
        { // 执行查询
            text = queryReport(text);
        }
        SmsHttpMsgResponse smsHttpMsgResponse = parseReport(text, rtList);
        
        for (SMSRTMessage message : rtList)
        {
        	GatewayStartUp.httpGateWayMessage.sendSmsRTMessage(message, this.gateWayID);
        }
        
        return smsHttpMsgResponse;
    }
    
    public SmsHttpMsgResponse deliver(String text, boolean isQuery)
    {
        List<SMSMOMessage> moList = Lists.newArrayList();
        Map<String, String> retMap = Maps.newConcurrentMap();
        if (isQuery)
        { // 执行查询
            text = queryDeliver(text);
        }
        SmsHttpMsgResponse smsHttpMsgResponse = parseDeliver(text, moList);
        
        for (SMSMOMessage message : moList)
        {
        	GatewayStartUp.httpGateWayMessage.sendSmsMOMessage(message, this.gateWayID);
        }
        
        return smsHttpMsgResponse;
    }
    
    /**
     * 余额查询
     * @param mt
     * @see [类、类#方法、类#成员]
     */
    public String balance()
    {
        return "未实现";
    }
    
    public void startListener(int reportSecTime, int deliverSecTime){
        if (reportSecTime > 0)
        {
            EventLoopGroupFactory.INS.submitUnlimitCircleTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    report("", true);
                    return true;
                }
            }, new ExitUnlimitCirclePolicy() {
                @Override
                public boolean notOver(Future future) {
                    return true;
                }
            }, reportSecTime * 1000);
        }
        
        if (deliverSecTime > 0)
        {
            EventLoopGroupFactory.INS.submitUnlimitCircleTask(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    deliver("", true);
                    return true;
                }
            }, new ExitUnlimitCirclePolicy() {
                @Override
                public boolean notOver(Future future) {
                    return true;
                }
            }, deliverSecTime * 1000);
        }
    }
    
    /**
     * 发送 
     * 如果实现群发功能，SmsMtMessage中phone字段以逗号分隔多个号码
     * @param mt
     * @see [类、类#方法、类#成员]
     */
    public abstract List<SMSSRMessage> doSend(SMSMTMessage mt);
    
    /**
     * 解析状态报告
     * @param mt
     * @see [类、类#方法、类#成员]
     */
    public abstract SmsHttpMsgResponse parseReport(String text, List<SMSRTMessage> rtList);
    
    /**
     * 解析短信上行
     * @param mt
     * @see [类、类#方法、类#成员]
     */
    public abstract SmsHttpMsgResponse parseDeliver(String text, List<SMSMOMessage> moList);
    
    /**
     * 状态报告查询
     * @param mt
     * @see [类、类#方法、类#成员]
     */
    public abstract String queryReport(String jsonText);
    
    public abstract String queryDeliver(String jsonText);
    
}
