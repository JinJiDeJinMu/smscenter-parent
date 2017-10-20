/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.sanerzone.common.support.sequence.MsgId;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.JedisUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.common.utils.AccountCacheUtils;
import com.sanerzone.smscenter.common.utils.KeywordsUtils;
import com.sanerzone.smscenter.common.utils.SignUtils;
import com.sanerzone.smscenter.common.utils.ValidatorUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsTask;
import com.sanerzone.smscenter.modules.sms.service.SmsTaskService;

/**
 * 用户任务列表Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/task")
public class FrontTaskController extends BaseController {
	
	@Autowired
	private SmsTaskService smsTaskService;
	
	//用户任务列表
	@RequestMapping(value = "taskList")
	public String list(SmsTask smsTask, HttpServletRequest request, HttpServletResponse response) {
		Result<Page<SmsTask>> result = new Result<Page<SmsTask>>();
		if (!UserUtils.getUser().isAdmin())
		{
			smsTask.setUserid(UserUtils.getUser().getId());
		}
		smsTask.setSendDatetimeZ(DateHelper.getDay(smsTask.getSendDatetimeZ(), 23, 59, 59));
		Page<SmsTask> page = smsTaskService.findPage(new Page<SmsTask>(request, response), smsTask); 
		result.setData(page);
		return renderString(response, result);
	}
	
	//暂停/继续/终止 任务
	@RequestMapping(value = "updateStatus")
	public String updateStatus(SmsTask smsTask, HttpServletResponse response){
		Result<String> result = new Result<String>();
		String msg = "";
		boolean flag = true;
		String status = smsTask.getSendStatus();
		SmsTask entity = smsTaskService.get(smsTask.getTaskid());
		
		if("3".equals(entity.getSendStatus())){
			flag = false;
			msg = "短信已经发送完成!";
		}else if("9".equals(entity.getSendStatus())){
			flag = false;
			msg = "已经停止发送短信";
		}else{
			if("9".equals(status)){
				msg = "停止发送短信成功!";
			}else if("5".equals(status)){
				msg = "暂停发送短信成功!";
			}else if("8".equals(status)){
				msg = "继续发送短信成功!";
			}
		}

		if(flag){
			smsTaskService.updateStatus(smsTask);
		}else{
			result.setCode("1");
			result.setMsg(msg);	
		}
		
		return renderString(response, result);
	}
	
	//创建任务
	@RequestMapping(value = "save")
	public String save(SmsTask smsTask, HttpServletResponse response) {
		
		Result<String> result = new Result<String>();
		
		User user = UserUtils.getUser();
		String usedFlag = AccountCacheUtils.getStringValue(user.getId(), "accStatus", "0");//判断账号是否禁用
		if(!"1".equals(usedFlag)){//账号禁用
			result.setCode("1");
			result.setMsg("账号禁用");
			return renderString(response, result);
		}
		
		
		if(smsTask.getSendDatetime() == null){//判断时间
			smsTask.setSendDatetime(new Date());
		}else{
			double timeD = DateUtils.getDistanceOfTwoDate(new Date(), smsTask.getSendDatetime());
			if(timeD > 15){
				result.setCode("2");
				result.setMsg("发送时间有误,短信发送只支持15天内");
				return renderString(response, result);
			}else if(timeD < 0){
				smsTask.setSendDatetime(new Date());
			}
		}
		String content = StringEscapeUtils.unescapeHtml4(smsTask.getSmsContent().trim());//发送内容
		content = SignUtils.formatContent(content);
		smsTask.setSmsContent(content);
		
		if(!KeywordsUtils.exits(AccountCacheUtils.getStringValue(user.getId(), "keyword", ""), content)){
			result.setCode("3");
			result.setMsg("短信内容未包含用户关键字");
			return renderString(response, result);
		}
		
		List<String> phoneList = Lists.newArrayList();
        
		int errorCount = 0;
        if (StringUtils.isNotBlank(smsTask.getPhones())){
            String[] phones = smsTask.getPhones().split("\r\n");
            
            for (String string : phones){
            	if(ValidatorUtils.isMobile(string)){//验证是不是手机号
            		phoneList.add(string);
            	}else{
            		errorCount++;
            	}
            }
        }
		
		int smsSize = StringUtils.smsSize(content);
		
		int count = phoneList.size()*smsSize;//支付总条数
		if(count >0){
			String key = AccountCacheUtils.getAmountKey("sms", user.getId());
			if(JedisUtils.decrBy(key, count) < 0){
				JedisUtils.incrBy(key, count);
				result.setCode("4");
				result.setMsg("账户余额不足");
				return renderString(response, result);
			}
		}else{
			result.setCode("5");
			result.setMsg("号码为空不符合要求");
			return renderString(response, result);
		}
		
		
		String noCheck =AccountCacheUtils.getStringValue(user.getId(), "noCheck", "");
		if("2".equals(noCheck)){//自动审核
			if(AccountCacheUtils.getIntegerValue(user.getId(), "reviewCount", 0) >= count){//审核条数大于发送条数
				noCheck = "1";//免审
			}else{
				noCheck="0";//必审
			}
		}
		String taskId = "T"+new MsgId().toString();//生成任务ID
		smsTask.setTaskid(taskId);
		smsTask.setUserid(user.getId());
		smsTask.setAccId(AccountCacheUtils.getStringValue(user.getId(), "accId", "1"));
		smsTask.setSmsContent(content);
		smsTask.setSendCount(phoneList.size());
		smsTask.setPhoneCount(phoneList.size());
		
		smsTask.setSendStatus("0".equals(noCheck) ? "-1" : "1");
		smsTask.setSmsSize(smsSize);
		smsTask.setTaskType("0");//普通
		smsTask.setUpdateBy(user);
		
		if("1".equals(AccountCacheUtils.getStringValue(user.getId(), "filterWordFlag", ""))){//过滤敏感字
			String keywords = KeywordsUtils.keywords(content);
			if(!StringUtils.isBlank(keywords)){
				result.setCode("6");
				result.setMsg("发送内容包含敏感词["+keywords+"]");
				return renderString(response, result);
			}
		}
		
		String msg = smsTaskService.createSmsTask(smsTask, phoneList,errorCount);
		result.setMsg(msg);
		return renderString(response, result);
	}
	
	
}