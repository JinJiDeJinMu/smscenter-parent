package com.sanerzone.smscenter.biz.iface;

import java.util.Date;
import java.util.Map;

/**
 * 短信发送任务接口
 * @author Administrator
 */
public interface SmsTaskInterface {
	/**
	 * 生成任务
	 * 
	 * @param userid
	 * @param taskid
	 * @param smsContent
	 * @param sendStatus "":没有状态 "-1":需要审核 "1":待发送
	 * @param sendDatetime null
	 * @param phoneCount
	 * @param taskData
	 * @param taskType 0:普通 1:点对点
	 * @param customTaskid
	 * @param customSpnumber
	 * @param customServiceid
	 * @return
	 */
	public Map<String,String> configSmsTask(String userid, String taskid, String smsContent, String sendStatus,Date sendDatetime,int phoneCount, String taskData,
			int taskType,String customTaskid,String customSpnumber,String customServiceid);
}
