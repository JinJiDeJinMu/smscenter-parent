package com.sanerzone.smscenter.gateway.http;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.sanerzone.common.support.utils.Encodes;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.gateway.base.BaseGatewayFactory;
import com.sanerzone.smscenter.gateway.base.GateEnum;
import com.sanerzone.smscenter.gateway.base.GateStateEnum;
import com.sanerzone.smscenter.gateway.base.Result;
import com.sanerzone.smscenter.gateway.http.handler.GateWayHttpAbstract;
import com.sanerzone.smscenter.gateway.service.ISmsGatewayService;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  zhangjie
 * @version  [版本号, 2016年8月27日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HttpGatewayFactory extends BaseGatewayFactory 
{
    private static final Logger logger = LoggerFactory.getLogger(HttpGatewayFactory.class);
    
    private Map<String, GateWayHttpAbstract> GATEWAY_MAP = Maps.newConcurrentMap();
    
    private ISmsGatewayService smsGatewayService;
    
    public ISmsGatewayService getSmsGatewayService() {
		return smsGatewayService;
	}

	public void setSmsGatewayService(ISmsGatewayService smsGatewayService) {
		this.smsGatewayService = smsGatewayService;
	}

	@Override
    public void initGateway(String appCode)
    {
		SmsGateway smsGateway = new SmsGateway();
		smsGateway.setAppCode(appCode);
    	List<SmsGateway> list = smsGatewayService.findList(smsGateway);
    	for(SmsGateway tmp : list)
    	{
    		initGate(tmp);
    	}
        //initGate(null);
    }
    
    private synchronized boolean initGate(SmsGateway gateway)
    {
    	 /*gateway = new JmsgGateWayInfo();
         gateway.setId("DX5005");
         gateway.setType("HTTP");
         gateway.setExtClass("com.sanerzone.smscenter.gateway.http.handler.impl.SmsApiJYCSMS");
         gateway.setExtParam("{\"apiUrl\":\"http://smsapi.konsone.com:8808/api\",\"userid\":\"3868--\",\"apikey\":\"dac446aa2abf4f30bc6d6bc53abd432d\"}");
         gateway.setReportGetFlag(1);*/
         
    	if (GATEWAY_MAP.containsKey(gateway.getGwCode())) {
    		return true;
    	}
    	
        if (logger.isInfoEnabled())
        {
            logger.info("初始化网关:[" + gateway + "]\r\n" + "params:" + gateway.toString());
        }
        
        if (GateEnum.HTTP.getValue().equalsIgnoreCase(gateway.getGwProto()))
        {
            if (gateway == null || StringUtils.isBlank(gateway.getGwProtoClass()))
                return false;
            
            String gateWayID = gateway.getGwCode();
            String className = gateway.getGwProtoClass();
            
            try
            {
                Class clazz = Class.forName(className);
                Constructor<GateWayHttpAbstract> constructor = clazz.getConstructor(String.class, String.class);
                GateWayHttpAbstract gatewayImpl = constructor.newInstance(gateWayID, gateway.getGwProtoExtparam());
                GATEWAY_MAP.put(gateWayID, gatewayImpl);
                
                //状态获取方式 0：异步通知 1：主动查询
                if (StringHelper.equals(gateway.getGwReceiveModel(), "1"))
                {
                    String jsonParams = Encodes.unescapeHtml(gateway.getGwProtoExtparam());
                    Map<String, Object> paraMap = JSON.parseObject(jsonParams);
                    
                    int reportSecTime = 0;
                    int deliverSecTime = 0;
                    if (null != paraMap.get("reportSecTime") && StringUtils.isNotBlank(paraMap.get("reportSecTime").toString()))
                    {
                        reportSecTime = Integer.parseInt(paraMap.get("reportSecTime").toString());
                    }
                    if (null != paraMap.get("deliverSecTime") && StringUtils.isNotBlank(paraMap.get("deliverSecTime").toString()))
                    {
                        deliverSecTime = Integer.parseInt(paraMap.get("deliverSecTime").toString());
                    }
                    
                    gatewayImpl.startListener(reportSecTime, deliverSecTime);
                }
                
            }
            catch (Exception e)
            {
                logger.error("初始化网关" + gateway.getId() + "失败", e);
                return false;
            }
        }
        else
        {
            return false;
        }
        
        //修改网关状态
//        gatewayService.updateGatewayStateById(GateStateEnum.ENABLED.getValue(), gateway.getId());
        
        return true;
    }
    
    @Override
    public void closeAll()
    {
    	Iterator<String> gatewayIt = GATEWAY_MAP.keySet().iterator();
        while (gatewayIt.hasNext()) {
        	closeGateway(gatewayIt.next());
        }
    }
    
    @Override
    public boolean closeGateway(String gwCode)
    {
        //修改网关状态
        SmsGateway smsGateway = new SmsGateway();
        smsGateway.setGwCode(gwCode);
        smsGateway.setGwStatus(Integer.parseInt(GateStateEnum.DISABLED.getValue()));
        smsGatewayService.updateStatus(smsGateway);
        
        //关闭队列
//    	GateWay smsGateway = getSmsGateWay(id);
//    	if( smsGateway != null) smsGateway.shutdown();
    	
        GATEWAY_MAP.remove(gwCode);
        return true;
    }
    
    @Override
    public boolean closeGatewayTemp(String id)
    {
        return closeGateway(id);
    }
    
    @Override
    public boolean openGateway(String gwCode)
    {
        Object gate = GATEWAY_MAP.get(gwCode);
        if (gate != null)
        {
            return true;
        }
        SmsGateway smsGateway = smsGatewayService.findByGwCode(gwCode);
        if (smsGateway == null)
        {
            return false;
        }
        try
        {
            boolean res = initGate(smsGateway);
            if (res)
            {
            	smsGateway.setGwStatus(Integer.parseInt(GateStateEnum.ENABLED.getValue()));
            }
            else
            {
            	smsGateway.setGwStatus(Integer.parseInt(GateStateEnum.ERROR.getValue()));
            }
            smsGatewayService.updateStatus(smsGateway);
            return res;
        }
        catch (Exception e)
        {
            smsGateway.setGwStatus(Integer.parseInt(GateStateEnum.ERROR.getValue()));
            smsGatewayService.updateStatus(smsGateway);
            
            logger.error("初始化网关失败", e);
        }
        return false;
    }
    
    @Override
    public boolean hasGateway(String gwCode)
    {
        return GATEWAY_MAP.get(gwCode) != null;
    }
    
    @Override
    public Object getGateway(String gwCode)
    {
        logger.info("{}通道 ", gwCode);
        if (!GATEWAY_MAP.containsKey(gwCode))
        {
            try
            {
            	SmsGateway smsGateway = smsGatewayService.findByGwCode(gwCode);
                
                if (!initGate(smsGateway))
                {
                    return null;
                }
            }
            catch (Exception e)
            {
                return null;
            }
        }
        return (GateWayHttpAbstract)GATEWAY_MAP.get(gwCode);
    }
    
    @Override
    public Result sendMsg(SMSMTMessage msg)
    {
        GateWayHttpAbstract gateWay = (GateWayHttpAbstract)this.getGateway(msg.getGatewayId());
        if (gateWay == null)
        {
            logger.error("{}通道, 未启动", msg.getGatewayId());
            return new Result("F10104", String.format("%s通道, 未启动", msg.getGatewayId()));
        }
        else
        {
        	SMSMTMessage e = (SMSMTMessage)gateWayMessage.convertMTMessage(msg, gateWay.isGatewaySign());
            boolean sendFlg = gateWay.send(e);
            if(sendFlg) {
                return new Result("T100", "成功");
            }else{
                return new Result("F10105", String.format("send error", msg.getGatewayId()));
            }
        }
    }
    
    public void sendSmsSRMessage (Serializable message, String gateWayID) {
    	gateWayMessage.sendSmsSRMessage(message, gateWayID);
    }
    
    @Override
    public GateEnum getGatewayType(String gatewayId)
    {
        return GateEnum.HTTP;
    }
    
    @Override
    public SMSMTMessage getSubmitResult(String gID, String msgid)
    {
        return null;
    }

}
