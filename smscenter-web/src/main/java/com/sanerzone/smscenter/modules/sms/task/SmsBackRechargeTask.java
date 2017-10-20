package com.sanerzone.smscenter.modules.sms.task;
import java.util.Date;
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
import com.sanerzone.jeebase.common.utils.SpringContextHolder;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.modules.account.service.BaseAccountService;
import com.sanerzone.smscenter.modules.sms.dao.SmsSendDao;
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;
import com.sanerzone.smscenter.modules.sms.entity.SmsSend;
import com.sanerzone.smscenter.modules.sms.service.SmsDayReportService;
import com.sanerzone.smscenter.modules.sms.service.SmsTaskService;

//短信返充
@Service
@Lazy(false)
public class SmsBackRechargeTask {
	
	private static Logger logger = LoggerFactory.getLogger(SmsBackRechargeTask.class);
	
	@Autowired
	private SmsDayReportService smsDayReportService;
	
	@Autowired
	private BaseAccountService baseAccountService;
	
	@Autowired
	private SmsSendDao smsSendDao;
	
	private static SmsTaskService smsTaskService = SpringContextHolder.getBean(SmsTaskService.class);
	
	//每天 1:30:35执行返充任务
	@Scheduled(cron = "35 30 1 * * ?")
	@Transactional(readOnly = false)
	public void execSmsBack(){
		int day = -4;
		Date date = DateUtils.getDay(day);
		String sDay = DateUtils.formatDate(date,"yyyy-MM-dd");
		String tableName = "sms_send_"+DateUtils.getDayOfMonth(day);
		int count = 0;
		if(count == 0){
			smsDayReportService.saveSmsDayReport(true, tableName, date);
			
			//获取返充列表
			List<SmsDayReport> rechargeList = smsDayReportService.findRechargeList(date);
			if(rechargeList != null && rechargeList.size() >0){
				for (SmsDayReport smsDayReport : rechargeList) {
					int userBackCount = smsDayReport.getUserBackCount();
					String userId = smsDayReport.getUserid();
					//String accId = smsDayReport.getAccId();
					if(userBackCount >0){//用户返充条数
						String key = AccountCacheUtils.getAmountKey("sms", userId);
						JedisUtils.incrBy(key,userBackCount);
						baseAccountService.rechargeAmount("0", userId, "CZ02", String.valueOf(userBackCount),	"失败返充操作【"+sDay+"】", "1");
					}
					
					//修改返充状态
					smsDayReportService.updateBackFlag(smsDayReport);
				}
				
			}
			smsTaskService.smsTaskReport(tableName,date);//统计任务
			
			//清理4天前的数据
			clearSendByDay(date,tableName);
		}else{
			logger.error("短信返充错误，已经返充。操作时间："+DateUtils.formatDateTime(DateUtils.getDay(0)));
		}
	}
	
	//清理数据
	private void clearSendByDay(Date date,String table){
		SmsSend param = new SmsSend();
		String history = "sms_send_history_"+DateUtils.formatDate(date, "yyyyMM");
		param.setTableName(table);
		param.setHistoryName(history);
		smsSendDao.insertHistory(param);
		smsSendDao.clearSmsSend(param);
	}

	
}
