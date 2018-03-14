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
import com.sanerzone.smscenter.biz.utils.DateHelper;
import com.sanerzone.smscenter.common.message.Result;
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;
import com.sanerzone.smscenter.modules.sms.service.SmsDayReportService;

/**
 * 账号信息Controller
 * @author zhukc
 * @version 2017-06-25
 */
@Controller
@RequestMapping(value = "/front/report")
public class FrontReportController extends BaseController {
	
	@Autowired
	private SmsDayReportService smsDayReportService;
	
	//用户发送统计列表
	@RequestMapping(value = "list")
	public String list(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response) {
		if (!UserUtils.getUser().isAdmin())
		{
			smsDayReport.setUserid(UserUtils.getUser().getId());
		}
		smsDayReport.setDayZ(DateHelper.getDay(smsDayReport.getDayZ(), 23, 59, 59));
		Result<Page<SmsDayReport>> result = new Result<Page<SmsDayReport>>(); 
		Page<SmsDayReport> page = smsDayReportService.findPage(new Page<SmsDayReport>(request, response), smsDayReport);
		result.setData(page);
		return renderString(response, result);
	}
	
	//用户应用发送统计列表
	@RequestMapping(value = "listByAccid")
	public String listByAccid(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response) {
		if (!UserUtils.getUser().isAdmin())
		{
			smsDayReport.setUserid(UserUtils.getUser().getId());
		}
		smsDayReport.setDayZ(DateHelper.getDay(smsDayReport.getDayZ(), 23, 59, 59));
		Result<Page<SmsDayReport>> result = new Result<Page<SmsDayReport>>(); 
		Page<SmsDayReport> page = smsDayReportService.findByAccidPage(new Page<SmsDayReport>(request, response), smsDayReport);
		result.setData(page);
		return renderString(response, result);
	}
	
	
}