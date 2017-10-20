package com.sanerzone.smscenter.modules.sms.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;
import com.sanerzone.smscenter.modules.sms.service.SmsDayReportService;

@Service
@Lazy(false)
public class SmsDayAccountClearTask {
	
	private static Logger logger = LoggerFactory.getLogger(SmsDayAccountClearTask.class);
	
	@Autowired
	private BaseAccountService baseAccountService;
	
	@Autowired
	private SmsDayReportService smsDayReportService;
	
	//每天 00:10:30执行日账务清算任务
	@Scheduled(cron = "30 10 0 * * ?")
	@Transactional(readOnly = false)
	public void run(){
		try{
			int day = -1;
			
			String tableName = "sms_send_"+DateUtils.getDayOfMonth(day);
			smsDayReportService.saveSmsDayReport(false, tableName, null);//统计昨天的发送报表
			
			SmsDayReport param = new SmsDayReport();
			param.setDay(DateUtils.getDay(-1));
			List<SmsDayReport> list = smsDayReportService.findDaySendCountList(param);//获取昨天用户日放送量
			if(list != null && list.size() > 0){
				tableName = "sms_send_"+DateUtils.getDayOfMonth(0);//获取当前发送表
				param = new SmsDayReport();
				param.setTableName(tableName);
				for (SmsDayReport entity : list) {
					String userid = entity.getUserid();
					String accId = entity.getAccId();
					param.setUserid(userid);
					param.setAccId(accId);
					List<SmsDayReport> smsSendList = smsDayReportService.findSendList(param);//从sms_send_xx表中获取用户日发送量
					int todayCount = 0;
					if(smsSendList != null && smsSendList.size() > 0){//统计今天发送的
						for (SmsDayReport smsSend : smsSendList) {
							todayCount+=smsSend.getSendCount();
						}
					}
					
					String key = AccountCacheUtils.getAmountKey("sms", userid);
					int amount = baseAccountService.findUserMoeny(userid).intValue() - todayCount - entity.getSendCount();
					int code = baseAccountService.rechargeAmount(accId, userid, "XF02", String.valueOf(-entity.getSendCount()), "自消费操作(消费日期："+DateUtils.formatDate(DateUtils.getDay(day), "yyyy-MM-dd") +")", userid);
					if(code == 1)JedisUtils.set(key, String.valueOf(amount), 0);//置值
				}
				
			}
			
		}catch(Exception e){
			logger.error("定时任务,执行日账务清算任务失败",e);
		}
		
		
	}
	
}
