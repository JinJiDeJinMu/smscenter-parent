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
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;
import com.sanerzone.smscenter.modules.sms.service.SmsTemplateService;

/**
 * 短信模板ontroller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/smsTemplate")
public class FrontSmsTemplateController extends BaseController {
	
	@Autowired
	private SmsTemplateService smsTemplateService;
	
	//用户发送统计列表
	@RequestMapping(value = "list")
	public String list(SmsTemplate smsTemplate, HttpServletRequest request, HttpServletResponse response) {
		Result<Page<SmsTemplate>> result = new Result<Page<SmsTemplate>>();
		if (!UserUtils.getUser().isAdmin())
		{
			smsTemplate.setCreateBy(UserUtils.getUser());
		}
		Page<SmsTemplate> page = smsTemplateService.findPage(new Page<SmsTemplate>(request, response), smsTemplate); 
		result.setData(page);
		return renderString(response, result);
	}
	
	//用户短信模板添加
	@RequestMapping(value = "save")
	public String save(SmsTemplate smsTemplate, HttpServletResponse response) {
		
		Result<String> result = new Result<String>();
		smsTemplate.setCreateBy(UserUtils.getUser());
		smsTemplate.setStatus("-1");//待审
		smsTemplate.setContent("【"+smsTemplate.getSign()+"】"+smsTemplate.getContent());
		smsTemplateService.save(smsTemplate);
		
		return renderString(response, result);
	}
	
	//用户短信模板删除
	@RequestMapping(value = "delete")
	public String delete(SmsTemplate smsTemplate, HttpServletResponse response) {
		Result<String> result = new Result<String>();
		smsTemplateService.delete(smsTemplate);
		return renderString(response, result);
	}
	
	
}