/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.modules.sms.entity.SmsReceive;
import com.sanerzone.smscenter.modules.sms.entity.SmsSend;
import com.sanerzone.smscenter.modules.sms.service.SmsReceiveService;
import com.sanerzone.smscenter.modules.sms.service.SmsSendService;

/**
 * 用户发送明细Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/send")
public class FrontSendController extends BaseController {
	
	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private SmsReceiveService smsReceiveService;
	
	//用户已发短信列表
	@RequestMapping(value = "sendList")
	public String sendList(SmsSend smsSend, HttpServletRequest request, HttpServletResponse response) {
		Result<Page<SmsSend>> result = new Result<Page<SmsSend>>();
		if (!UserUtils.getUser().isAdmin())
		{
			smsSend.setUserid(UserUtils.getUser().getId());
		}
		
		String tableName = "sms_send_"+DateUtils.getTableIndex(smsSend.getSendTimeQ());
		smsSend.setTableName(tableName);
		smsSend.setSendTimeZ(DateHelper.getDay(smsSend.getSendTimeQ(), 23, 59, 59));
		
		Page<SmsSend> page = smsSendService.findPage(new Page<SmsSend>(request, response), smsSend);
		result.setData(page);
		return renderString(response, result);
	}
	
	//用户已发短信列表
	@RequestMapping(value = "receiveList")
	public String receiveList(SmsReceive smsReceive, HttpServletRequest request, HttpServletResponse response) {
		Result<Page<SmsReceive>> result = new Result<Page<SmsReceive>>();
		if (!UserUtils.getUser().isAdmin())
		{
			smsReceive.setUserid(UserUtils.getUser().getId());
		}
		
		String tableName = "sms_receive_"+DateUtils.getDay(smsReceive.getCreatetimeQ());
		smsReceive.setTableName(tableName);
		smsReceive.setCreatetimeZ(DateHelper.getDay(smsReceive.getCreatetimeQ(), 23, 59, 59));
		Page<SmsReceive> page = smsReceiveService.findPage(new Page<SmsReceive>(request, response), smsReceive);
		result.setData(page);
		return renderString(response, result);
	}
	
}