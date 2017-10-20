/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.service.CrudService;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsSend;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.common.utils.PhoneUtils;
import com.sanerzone.smscenter.common.utils.SysBlacklistUtils;
import com.sanerzone.smscenter.common.utils.UserBlacklistUtils;
import com.sanerzone.smscenter.modules.sms.dao.SmsSendDao;

/**
 * 短信发送Service
 * @author zhukc
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true)
public class SmsSendService extends CrudService<SmsSendDao, SmsSend> {

	public SmsSend get(String id) {
		return super.get(id);
	}
	
	public SmsSend getV2(SmsSend param) {
		return dao.getV2(param);
	}
	
	public List<SmsSend> findList(SmsSend smsSend) {
		return super.findList(smsSend);
	}
	
	public Page<SmsSend> findPage(Page<SmsSend> page, SmsSend smsSend) {
		return super.findPage(page, smsSend);
	}
	
	@Transactional(readOnly = false)
	public void save(SmsSend smsSend) {
		super.save(smsSend);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmsSend smsSend) {
		super.delete(smsSend);
	}
	
	/**
	 * 
	 * @param userId 用户ID
	 * @param accId 账号ID
	 * @param tableName 
	 * @param taskId
	 * @param phone 手机号码
	 * @param smsContent 短信内容
	 * @param sign 签名
	 * @param smsSize 扣费条数
	 * @param feeType 计费类型
	 * @param pushFlag 推送标识
	 * @param smsType 短信类型 1行业短信、2验证码、3、营销短信、4群发短信
	 * @param checkContent true:校验内容 false:否
	 * @return
	 */
	public SmsSend createSendDetail(String userId, String accId, String tableName, String taskId, String phone, String smsContent, 
									 String sign, int smsSize, String feeType, String pushFlag, String smsType,boolean checkContent){
		SmsSend smsSend = new SmsSend();
		smsSend.setId(new MsgId().toString());
		smsSend.setTaskid(taskId);//任务我ID
		smsSend.setPhone(phone);//手机号码
		smsSend.setSmsContent(smsContent);//短信内容
		smsSend.setSmsType(smsType);//短信类型
		smsSend.setSmsSize(smsSize);//扣费条数
		smsSend.setUserid(userId);//用户ID
		smsSend.setAccId(accId);//账户ID
		smsSend.setSmsContentSign(sign);
		String sendStatus = "T000";//待发
		String phoneType = "";//运营商
		String phoneArea = "";//省市代码
		
		boolean runFlag = true;
		
		if(checkContent){
			
			/**
			String filResult = RuleUtils.filtrate(user.getId(), smsContent);
			if (!StringUtils.equals(filResult, "T0000")){
				logger.info(filResult);
				sendStatus = "F009"+filResult;//短信内容匹配规则失败
				runFlag = false;
			}
			
			
			if("1".equals(user.getFilterWordFlag())){//过滤敏感字
				String keywords = KeywordsUtils.keywords(smsContent);
				if(StringUtils.isNotBlank(keywords)){
					sendStatus = "F020";//发送内容包含敏感词["+keywords+"]";
					runFlag = false;
				}
			}
			**/
		}
		
		if(runFlag){
			Map<String,String> phoneMap = PhoneUtils.get(phone);
			if(phoneMap == null||phoneMap.size() <2){
				sendStatus = "F0170";//号段匹配异常
			}else{
				phoneType = PhoneUtils.getPhoneType(phoneMap);//运营商
				phoneArea = PhoneUtils.getCityCode(phoneMap);//省市代码
				
				if(StringUtils.isBlank(phoneArea) || StringUtils.isBlank(phoneType)){
					sendStatus = "F0170";//号段匹配异常
				}else{
					//1：校验 0： 不校验
		            if (AccountCacheUtils.getIntegerValue(userId, "sysBlackListFlag", 0) == 1 && SysBlacklistUtils.isExist(phone)){
		                sendStatus = "F002";// 判断是否系统黑名单
		            }
		            else if (AccountCacheUtils.getIntegerValue(userId, "userBlackListFlag", 0) == 1 && UserBlacklistUtils.isExist(phone)){
		                sendStatus = "F008";// 判断是否营销黑名单
		            }else{
		            	/**
		            	GatewayResult gatewayResult = gatewayMap(user.getSignFlag(),user.getGroupId(), phoneType, cityCode.substring(0, 2), sign, user.getId());
						if(gatewayResult.isExists()){
							smsSend.setChannelCode(gatewayResult.getGatewayId());//通道代码
							String spNumber = gatewayResult.getSpNumber();
							if( 0 == user.getSignFlag() ){
								spNumber = spNumber + user.getId();
							}
							spNumber = spNumber + "188";
							if(spNumber.length() > 20) {
								spNumber = spNumber.substring(0, 20);
							}
							smsSend.setSpnumber(spNumber);//接入号
						}else{
							sendStatus = gatewayResult.getErrorCode();//匹配通道失败
						}
						**/
		            }
				}
			}
		}
		
		smsSend.setPhoneType(phoneType);//运营商
		smsSend.setPhoneArea(phoneArea);//省市代码
		smsSend.setFeeType(feeType);//扣费方式
		smsSend.setUserNotifyStatus(pushFlag);//推送标识
		
		smsSend.setSendStatus(sendStatus);//发送状态
//		smsSend.setTopic(CacheKeys.getSmsBatchTopic());//发送队列
		smsSend.setSourceGatewayId("HTTP");
		smsSend.setTableName(tableName);
		
		return smsSend;
	}
	
}