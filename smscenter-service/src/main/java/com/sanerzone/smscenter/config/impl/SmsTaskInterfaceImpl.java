package com.sanerzone.smscenter.config.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.sanerzone.smscenter.biz.iface.SmsTaskInterface;
import com.sanerzone.smscenter.biz.service.impl.SmsTaskServiceImpl;

public class SmsTaskInterfaceImpl implements SmsTaskInterface{

	@Autowired
	private SmsTaskServiceImpl smsTaskServiceImpl;
	
	@Override
	public Map<String,String> configSmsTask(String userid, String taskid, String smsContent, String sendStatus,Date sendDatetime,int phoneCount, String taskData,
			int taskType,String customTaskid,String customSpnumber,String customServiceid) {
		Map<String,String> map = smsTaskServiceImpl.createTask(userid, taskid, smsContent, sendStatus, sendDatetime, 
				phoneCount, taskData, taskType, customTaskid, customSpnumber,customServiceid);
		return map;
	}

}
