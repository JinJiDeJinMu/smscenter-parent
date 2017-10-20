package com.sanerzone.smscenter.biz.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.cache.AccountCacheHelper;
import com.sanerzone.smscenter.biz.entity.SmsTask;
import com.sanerzone.smscenter.biz.entity.SmsTaskData;
import com.sanerzone.smscenter.biz.mapper.SmsTaskMapper;
import com.sanerzone.smscenter.biz.service.ISmsTaskService;
import com.sanerzone.smscenter.biz.utils.StringHelper;
import com.sanerzone.smscenter.common.tools.JedisUtils;

@Service
public class SmsTaskServiceImpl extends ServiceImpl<SmsTaskMapper, SmsTask> implements ISmsTaskService{

	@Autowired
	private SmsTaskDataServiceImpl smsTaskDataServiceImpl;
	
	public SmsTask get(String taskid){
		return baseMapper.get(taskid);
	}
	
	//修改发送行次
	@Transactional(readOnly = false)
	public void updateRowNumber(Map<String,Object> map){
		baseMapper.updateRowNumber(map);
	}
	
	//修改任务发送状态
	@Transactional(readOnly = false)
	public int updateSendStatus(Map<String,Object> map){
		return baseMapper.updateSendStatus(map);
	}
	
	//sendStatus:-2审核不通过 -1审核中,1:待发送,2:发送中,3:发送完成,5:暂停,8:继续发送,9:停止发送
	//taskType 0:普通 1:点对点
	@Transactional(readOnly = false)
	public Map<String,String> createTask(String userid, String taskid, String smsContent, String sendStatus,
			Date sendDatetime,int phoneCount, String taskData,int taskType, String customTaskid,String customSpnumber,
			String customServiceid){
		Map<String,String> result = Maps.newHashMap();
    	result.put("code", "0");
    	result.put("msg","提交成功");
		try{
			int smsSize = StringHelper.smsSize(smsContent);
			if(taskType == 0){//普通短信 是一个内容对应N个号码  需要校验号码并扣费
				//余额
	            String amountKey = AccountCacheHelper.getAmountKey("sms", userid);
	            // 扣款
	            long amount = JedisUtils.decrBy(amountKey, phoneCount * smsSize);
	            if (amount < 0){
	            	JedisUtils.incrBy(amountKey, phoneCount * smsSize);
	            	result.put("code", "3");
	            	result.put("msg","余额不足");
	                return result;
	            }
			}
			
			if(StringHelper.isBlank(sendStatus)){//获取发送状态
				sendStatus = getSendStatus(userid, phoneCount);//获取发送状态
			}
			//入库
			saveTask(userid,taskid,smsContent,sendStatus,sendDatetime,taskType,customTaskid,customSpnumber,smsSize,phoneCount,taskData,customServiceid);
		}catch(Exception e){
			e.printStackTrace();
        	result.put("code", "1");
        	result.put("msg","提交错误");
		}
		
		return result;
	}
	
	private String getSendStatus(String userid,int phoneCount){
		String sendStatus = "1";//待发送
		String nocheck = AccountCacheHelper.getStringValue(userid, "noCheck", "");//0:必审 2:自动审 1:免审
        int reviewCount = AccountCacheHelper.getIntegerValue(userid, "reviewCount", 0);
        if ("2".equals(nocheck)){//自动审核
            if (reviewCount < phoneCount){
            	sendStatus = "-1";
            }
        }
        if("0".equals(nocheck)){
    		sendStatus="-1";
        }
        return sendStatus;
	}
	
	//保存任务
	@Transactional(readOnly = false)
	private void saveTask(String userid,String taskid,String smsContent,String sendStatus,Date sendDatetime,
			int taskType,String customTaskid,String customSpnumber,int smsSize,int phoneCount,String taskData,String customServiceid){
		SmsTask entity = new SmsTask();
		if(sendDatetime == null){
			sendDatetime = new Date();
		}
		String accId = AccountCacheHelper.getStringValue(userid, "accId", "-1");
		entity.setTaskid(taskid);
		entity.setAccId(accId);
		entity.setUserid(userid);
		entity.setSmsContent(smsContent);
		entity.setSmsSize(smsSize);
		entity.setCustomTaskid(customTaskid);
		entity.setCustomSpnumber(customSpnumber);
		entity.setTaskType(taskType);
		entity.setPhoneCount(phoneCount);
		entity.setSendDatetime(sendDatetime);
		entity.setSendStatus(sendStatus);
		entity.setSendCount(phoneCount);
		entity.setCustomServiceid(customServiceid);
		baseMapper.insert(entity);
		
		SmsTaskData smsTaskData = new SmsTaskData();
		smsTaskData.setTaskid(taskid);
		smsTaskData.setTaskData(taskData);
		smsTaskData.setSendTime(sendDatetime);
		smsTaskDataServiceImpl.insert(smsTaskData);
	}
	
	
}
