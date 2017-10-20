/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsReport;
import com.sanerzone.smscenter.modules.sms.service.SmsReportService;

/**
 * 网关状态Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsReport")
public class SmsReportController extends BaseController {

	@Autowired
	private SmsReportService smsReportService;
	
	@ModelAttribute
	public SmsReport get(@RequestParam(required=false) String id) {
		SmsReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsReportService.get(id);
		}
		if (entity == null){
			entity = new SmsReport();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsReport smsReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsReport> page = smsReportService.findPage(new Page<SmsReport>(request, response), smsReport); 
		model.addAttribute("page", page);
		return "modules/sms/smsReportList";
	}

	@RequiresPermissions("sms:smsReport:view")
	@RequestMapping(value = "form")
	public String form(SmsReport smsReport, Model model) {
		model.addAttribute("smsReport", smsReport);
		return "modules/sms/smsReportForm";
	}

	@RequiresPermissions("sms:smsReport:edit")
	@RequestMapping(value = "save")
	public String save(SmsReport smsReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsReport)){
			return form(smsReport, model);
		}
		smsReportService.save(smsReport);
		addMessage(redirectAttributes, "保存网关状态成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsReport/?repage";
	}
	
	@RequiresPermissions("sms:smsReport:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsReport smsReport, RedirectAttributes redirectAttributes) {
		smsReportService.delete(smsReport);
		addMessage(redirectAttributes, "删除网关状态成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsReport/?repage";
	}

}