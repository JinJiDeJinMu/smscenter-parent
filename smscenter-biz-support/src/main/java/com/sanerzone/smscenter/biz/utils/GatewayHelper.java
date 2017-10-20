package com.sanerzone.smscenter.biz.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.google.common.collect.Lists;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.cache.GatewayQueueHelper;
import com.sanerzone.smscenter.biz.cache.GatewayRelHelper;
import com.sanerzone.smscenter.biz.cache.KeywordsHelper;
import com.sanerzone.smscenter.biz.cache.PhoneHelper;
import com.sanerzone.smscenter.biz.cache.SysBlacklistHelper;
import com.sanerzone.smscenter.biz.cache.UserBlacklistHelper;
import com.sanerzone.smscenter.biz.entity.SmsGateway;
import com.sanerzone.smscenter.biz.entity.TopicQueue;
import com.sanerzone.smscenter.biz.message.SMSMTMessage;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;

/**
 * 通道匹配工具类
 * @author Administrator
 *
 */
public class GatewayHelper {
	
	public static List<SMSMTMessage> getGateway(SMSREQMessage reqMeg){
		List<SMSMTMessage> result = Lists.newArrayList();
		if(reqMeg != null && StringHelper.isNotBlank(reqMeg.getPhones())){
			SMSMTMessage entity;
			String userid = reqMeg.getUserid();
			String accId = reqMeg.getAccId();
			String key = userid+"_"+accId;
			String content = reqMeg.getMsgContent();//短信内容
			String code = "T000";
			String[] phones = reqMeg.getPhones().split(",");
			for (String phone : phones) {
				entity = new SMSMTMessage();
				entity.setSmsREQMessage(reqMeg);
				entity.getSmsREQMessage().setPhones("");
				entity.getSmsREQMessage().setMsgContent("");
				entity.setId(new MsgId().toString());//ID
				entity.setContentType("0");//内容类型 默认 0
				entity.setSmsContent(content);//短信内容
				entity.setSmsSize(smsSize(content));//短信长度
				entity.setPhone(phone);
				entity.setSmsContentSign(getSign(content));//短信签名
				entity.setServiceId("0"); //默认业务
				
				code = getPhoneOpt(entity);//获取号码属性
				if("T000".equals(code)){
					code = filter(key, phone, content);//过滤黑名单、营销黑名单、敏感词
				}
				if("T000".equals(code)){//获取通道
					String groupId = AccountCacheHelper.getStringValue(key, "groupId", "-1");//通道分组
					SmsGateway smsGateway= GatewayRelHelper.getGwCode(groupId, entity.getPhoneType(), entity.getPhoneArea());
					if(smsGateway == null){
						code = "F104";//获取网关代码失败
					}else{
						entity.setGatewayId(smsGateway.getGwCode());
						entity.setGatewayGroup(groupId);
						entity.setGatewayAppPort(smsGateway.getAppCode());
						entity.setSpnumber(smsGateway.getGwSpNumber()+reqMeg.getSendnumber());
						
						// 适配队列
						TopicQueue topicQueue = GatewayQueueHelper.getTopicQueue(smsGateway.getGwCode(), entity.getServiceId());
						entity.setSendTopic(topicQueue.getTopic());
						entity.setSendQueue(topicQueue.getQueueId());
					}
				}
				entity.setSendStatus(code);
				entity.setBizTime(new Date());
				result.add(entity);
			}
		}
		return result;
	}
	
	//获取签名
	private static String getSign(String content){
		return SignHelper.get(content);
	}
	
	//获取手机号码属性(运营商+省市代码)
	private static String getPhoneOpt(SMSMTMessage entity){
		String phoneType = "";
		String cityCode = "";
		String code = "T000";
		Map<String,String> phoneMap = PhoneHelper.get(entity.getPhone());
		if(phoneMap == null || phoneMap.size() == 0){
			code = "F100";//号段匹配异常
		}else{
			phoneType = PhoneHelper.getPhoneType(phoneMap);//运营商
			cityCode = PhoneHelper.getCityCode(phoneMap);//省市代码
			
			if(StringHelper.isBlank(cityCode) || StringHelper.isBlank(phoneType)){
				code = "F100";//号段匹配异常
			}
		}
		
		entity.setPhoneArea(cityCode);
		entity.setPhoneType(phoneType);
		return code;
	}
	
	//过滤
	private static String filter(String key, String phone, String content){
		//过滤  黑名单 1：校验 0： 不校验
		int sysBlackListFlag = AccountCacheHelper.getIntegerValue(key, "sysBlackListFlag", 0);
        if (sysBlackListFlag == 1 && SysBlacklistHelper.isExist(phone))return "F101";//系统黑名单
		
        //过滤 营销黑名单
		int userBlackListFlag = AccountCacheHelper.getIntegerValue(key, "userBlackListFlag", 0);//营销黑名单
		if(userBlackListFlag == 1 && UserBlacklistHelper.isExist(key, phone))return "F102";//营销黑名单
		
		//过滤敏感词
		int filterWordFlag = AccountCacheHelper.getIntegerValue(key, "filterWordFlag", 0);
		if(filterWordFlag == 1 && StringHelper.isNotBlank(KeywordsHelper.keywords(content)))return "F103";//包含敏感词
		
		return "T000";
	}
	
	private static int smsSize(String smsContent){
		return StringHelper.smsSize(smsContent);
	}
}
