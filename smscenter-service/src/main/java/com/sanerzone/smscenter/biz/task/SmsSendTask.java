package com.sanerzone.smscenter.biz.task;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import com.google.common.collect.Maps;
import com.sanerzone.smscenter.biz.entity.SmsTask;
import com.sanerzone.smscenter.biz.mapper.SmsTaskMapper;
import com.sanerzone.smscenter.biz.task.impl.SmsTaskSendExecutor;

//定时任务
@Service
@Lazy(false)
public class SmsSendTask {
	public static Logger logger = LoggerFactory.getLogger(SmsSendTask.class);
	public static Map<String, SmsTaskSendExecutor> smsTaskSendExecMap = Maps.newConcurrentMap(); 
	
	@Autowired
	private ThreadPoolTaskExecutor poolTaskExecutor;
	
	@Autowired
	private SmsTaskMapper taskMapper;
	
	//每10秒执行一次
	@Scheduled(cron = "*/10 * * * * ?")
	public void exec(){
		
		int poolTaskSize = smsTaskSendExecMap.size();//线程数
		
		if(poolTaskSize <20){
			//查询任务表中待发送的任务，取前20条
			List<SmsTask> list = taskMapper.findPendingSendTask();
			
			logger.info("当前线程数："+poolTaskSize);
			
			if(list != null && list.size() >0){
				for (SmsTask smsTask : list) {
					if(poolTaskSize>=30){
						break;
					}
					String taskid=smsTask.getTaskid();
					SmsTaskSendExecutor send = new SmsTaskSendExecutor(taskid,smsTask.getVersion(),smsTask.getRowNumber(),smsTask.getTaskType(), smsTask.getCustomServiceid());
					synchronized (send) {
						if(!smsTaskSendExecMap.containsKey(taskid)){
							smsTaskSendExecMap.put(taskid,send);
							poolTaskExecutor.execute(send); //执行作务
						}
					}
				}
			}
		}
	}
	
	
}
