package com.sanerzone.smscenter.modules.sms.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.smscenter.modules.sms.service.SmsDayReportService;
import com.sanerzone.smscenter.modules.sms.service.SmsDaySignService;
import com.sanerzone.smscenter.modules.sms.service.SmsTaskService;

//定时任务 短信日报表、任务统计
@Service
@Lazy(false)
public class SmsDayReportTask{
	
	public static Logger logger = LoggerFactory.getLogger(SmsDayReportTask.class);

	@Autowired
	private SmsDayReportService smsDayReportService = SpringContextHolder.getBean(SmsDayReportService.class);
	
	@Autowired
	private SmsTaskService smsTaskService = SpringContextHolder.getBean(SmsTaskService.class);
	@Autowired
	
	private SmsDaySignService smsDaySignService =SpringContextHolder.getBean(SmsDaySignService.class); 
	//每5分钟统计一次日报表
	@Scheduled(fixedDelay=300000)
	public void exec(){
		logger.info("进入报表定时任务:短信日报表");
		String tableName = "sms_send_"+DateUtils.getDayOfMonth(0);
		smsDayReportService.saveSmsDayReport(false, tableName, null);
	}
	
	/*
	 * 短信签名日报
	 */
		@Scheduled(fixedDelay=300000)
		public void execp(){
			logger.info("进入报表定时任务:短信签名日报表");
			String tableName = "sms_send_"+DateUtils.getDayOfMonth(-1);
			smsDaySignService.saveSmsDaySignReport(tableName, null);
		}
	
	//指定特定日期生成报表
	public void execDay(Date day){
		logger.info("指定特定日期生成报表");
		boolean flag = false;
		String tableIndex = DateUtils.getTableIndex(day);
		String tableName = "sms_send_"+tableIndex;
		if(tableIndex.startsWith("history_")){
			flag = true;
		}
		smsDayReportService.saveSmsDayReport(flag, tableName, day);
	}
	
	//每5分钟统计一次日报表
	@Scheduled(fixedDelay=360000)
	public void execTask(){
		logger.info("进入报表定时任务:短信任务统计");
		String tableName = "sms_send_"+DateUtils.getDayOfMonth(0);
		smsTaskService.smsTaskReport(tableName, new Date());
	}
	
}
