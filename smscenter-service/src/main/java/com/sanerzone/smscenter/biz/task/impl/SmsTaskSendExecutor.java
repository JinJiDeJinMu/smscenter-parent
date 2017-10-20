package com.sanerzone.smscenter.biz.task.impl;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.entity.SmsTask;
import com.sanerzone.smscenter.biz.message.SMSREQMessage;
import com.sanerzone.smscenter.biz.service.impl.SmsTaskDataServiceImpl;
import com.sanerzone.smscenter.biz.service.impl.SmsTaskServiceImpl;
import com.sanerzone.smscenter.biz.task.SmsSendTask;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.biz.utils.SignHelper;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.tools.JedisUtils;
import com.sanerzone.smscenter.common.tools.MQHelper;
import com.sanerzone.smscenter.common.tools.SpringContextHelper;

public class SmsTaskSendExecutor implements Runnable {

	public static Logger logger = LoggerFactory.getLogger(SmsTaskSendExecutor.class);
	private static SmsTaskServiceImpl smsTaskService = SpringContextHelper.getBean(SmsTaskServiceImpl.class);
	private static SmsTaskDataServiceImpl smsTaskDataService = SpringContextHelper.getBean(SmsTaskDataServiceImpl.class);
    private MQHelper mQUtils = SpringContextHelper.getBean(MQHelper.class);
	protected RateLimiter limiter = RateLimiter.create(20);
	
	private String taskid;
	private int version;
	private int rowNumber;//记录暂停时发送条数
	private int taskType;//0:普通 1:点对点
	private String customServiceid;
	
	private int index = 0;

	public SmsTaskSendExecutor(String taskid,int version,int rowNumber, int taskType, String customServiceid) {
		this.taskid = taskid;
		this.version = version;
		this.rowNumber = rowNumber;
		this.taskType = taskType;
		this.customServiceid = customServiceid;
	}

	@Override
	public void run() {

		long startTime = System.currentTimeMillis();
		logger.info("任务开始执行,TASKID{},时间{}" , taskid, DateHelper.getDateTime());
		
		try {
			while(true) {
				
				//更新状态为运行中
				Map<String, Object> sMap = Maps.newHashMap();
				sMap.put("taskid", taskid);
				
				Map<String,String> result = taskRunResult(sMap, index);
				if("0".equals(result.get("runFlag")))return;
				
				String userId = result.get("userId");
				String smsContent = result.get("smsContent");
				String accId = result.get("accId");
				String sign = SignHelper.get(smsContent);
				String customTaskid = result.get("customTaskid");
				String customSpnumber = result.get("customSpnumber");
				if(taskType == 0){//普通短信
					if(StringHelper.isBlank(sign)) {
						sign = AccountCacheHelper.getStringValue(userId, "forceSign", "");//强制签名
						if(StringHelper.isNotBlank(sign)) {
							smsContent = "【"+sign+"】" + smsContent;
						}
					}
				}
				
				
				String feeType = AccountCacheHelper.getStringValue(userId, "feeType", "1");//计费状态
				String feePayment = AccountCacheHelper.getStringValue(userId, "feePayment", "1");
				
				String taskData = smsTaskDataService.getTaskData(taskid);
				String[] dataArray = taskData.split("\r\n");
				
				//校验余额
				if(!checkAmount(taskType, dataArray, userId)){
					sMap.put("sendStatus", "4");// 发送状态为发送完成，余额不足
					sMap.put("version", "");// 发送状态为发送完成
					smsTaskService.updateSendStatus(sMap);
					SmsSendTask.smsTaskSendExecMap.remove(taskid);// 任务执行完成
					logger.info("任务执行失败，余额不足,TASKID{},USERID{}",taskid,userId);
	            	return;
				}

				
				try {
					for (String data : dataArray) {
						limiter.acquire();
						index++;
						if(rowNumber > 0){
							if(rowNumber > index) continue;
							rowNumber = 0;
						}
						String phone = "";
						if(taskType == 1){//点对点
							String[] phoneData = data.split("	");
							if(phoneData != null && phoneData.length == 2){
								phone = phoneData[0];
								smsContent = phoneData[1];
							}else{
								smsContent = "";
							}
						}else{
							phone = data;
						}
						
						if(index % 1000 == 0){//执行1000条
							Map<String,String> resultMap = taskRunResult(sMap, index);
							if("0".equals(resultMap.get("runFlag")))return;
						}
						
						mQUtils.sendSmsREQ("BATCH", taskid, getREQMessage(taskid, userId, accId, feeType, feePayment, phone, customTaskid, smsContent, customSpnumber));
						
					}
				}catch(Exception e){
					logger.error("TASKID{},批量任务发送失败{}",taskid,e);
				}

				sMap.put("sendStatus", "3");// 发送状态为发送完成
				sMap.put("version", "");// 发送状态为发送完成
				smsTaskService.updateSendStatus(sMap);
				SmsSendTask.smsTaskSendExecMap.remove(taskid);// 任务执行完成
				break;
			}
		} catch (Exception e) {
			logger.error("TASKID{},批量任务发送失败{}",taskid,e);
		}

		long endTime = System.currentTimeMillis();
		logger.info("任务执行完成,TASKID{},结束时间{},耗时：{}}",taskid, DateHelper.getDateTime(), (endTime - startTime)/1000);
	}
	
	/**
	 * 任务发送结果
	 * 
	 * @param map
	 * @param index
	 * @return
	 */
	private Map<String,String> taskRunResult(Map<String,Object> map,int index){
		
		Map<String,String> resultMap = Maps.newHashMap();
		String runFlag = "1";
		String userId = "";
		String smsContent="";
		String accId = "";
		String customTaskid = "";
		String customSpnumber = "";
		SmsTask task = smsTaskService.get(taskid);// 获取任务信息
		if(task != null){
		
			userId = task.getUserid();//获取用户ID
			smsContent = task.getSmsContent();//获取短信内容
			accId = task.getAccId();
			customTaskid = task.getCustomTaskid();
			customSpnumber = task.getCustomSpnumber();
			String sendStatus = task.getSendStatus();// 任务状态
			
			if ("5".equals(sendStatus)||"9".equals(sendStatus)) {// 任务暂停 或任务停止
				if(index > rowNumber)rowNumber=index;
				map.put("rowNumber", rowNumber);
				smsTaskService.updateRowNumber(map);//修改暂停前发送条数
				SmsSendTask.smsTaskSendExecMap.remove(taskid);// 剔除线程
				runFlag = "0";
			}
	
			if ("1".equals(sendStatus) || "8".equals(sendStatus)) {// 发送状态为待发送、继续发送
				map.put("sendStatus", "2");// 运行中
				map.put("version",version);
				if(0 == smsTaskService.updateSendStatus(map)){
					SmsSendTask.smsTaskSendExecMap.remove(taskid);// 剔除线程
					runFlag = "0";
				}
			}
		}else{
			SmsSendTask.smsTaskSendExecMap.remove(taskid);// 剔除线程
			runFlag="0";
		}
		
		resultMap.put("runFlag", runFlag);
		resultMap.put("userId", userId);
		resultMap.put("smsContent", smsContent);
		resultMap.put("accId",accId);
		resultMap.put("customTaskid",customTaskid);
		resultMap.put("customSpnumber",customSpnumber);
		return resultMap;
	}
	
	private SMSREQMessage getREQMessage(String taskId, String userId, String accId, String feeType, String feePayment, String phones, String customSpnumber, String smsContent, String customTaskid){
		SMSREQMessage message = new SMSREQMessage();

		message.setTaskid(taskId);
    	message.setAccId(accId);
    	message.setUserid(userId);
    	message.setFeeType(feeType);
    	message.setFeePayment(feePayment);
        message.setPhones(phones);
        message.setSendnumber(customSpnumber);
        message.setCustomTaskid(customTaskid);
        message.setMsgContent(smsContent);
        message.setSmsType("1");//短信
    	message.setMassFlag("2");//群发
    	message.setSendTime(new Date());
    	message.setReceiveTime(new Date());
    	message.setCustomServiceid(customServiceid);
    	return message;
	}
	
	//校验余额 taskType 0:普通 1：点对点
	private boolean checkAmount(int taskType ,String[] dataArray, String userId){
		if(taskType == 1){//点对点
			if(dataArray != null && dataArray.length > 0){
				int payCount = 0 ;
				for (String data : dataArray) {
					String[] array = data.split("	");
					if(array != null && array.length > 0){
						payCount+=StringHelper.smsSize(array[1]);
					}
				}
	            String amountKey = AccountCacheHelper.getAmountKey("sms", userId);//key
	            long amount = JedisUtils.decrBy(amountKey, payCount);//扣款
	            if (amount < 0){
	            	JedisUtils.incrBy(amountKey, payCount);
	            	return false;
	            }
			}
		}
		return true;
	}
}
