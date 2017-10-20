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
import com.sanerzone.smscenter.modules.sms.entity.SmsDayReport;
import com.sanerzone.smscenter.modules.sms.service.SmsDayReportService;

/**
 * 日报表Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsDayReport")
public class SmsDayReportController extends BaseController {

	@Autowired
	private SmsDayReportService smsDayReportService;
	
	@ModelAttribute
	public SmsDayReport get(@RequestParam(required=false) String id) {
		SmsDayReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsDayReportService.get(id);
		}
		if (entity == null){
			entity = new SmsDayReport();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsDayReport:view")
	@RequestMapping(value = "list")
	public String list(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsDayReport> page = smsDayReportService.findPage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/smsDayReportList";
	}
	
	@RequiresPermissions("sms:smsDayReport:view")
	@RequestMapping(value = "userBacklist")
	public String userBacklist(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsDayReport> page = smsDayReportService.findByUserPage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/smsDayReportBackList";
	}
	
	@RequiresPermissions("sms:smsDayReport:view")
	@RequestMapping(value = "listByUser")
	public String listByUser(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		String redirect = "smsDayReportUserList";
		if("month".equals(smsDayReport.getQueryType())){
			redirect = "smsMonthReportUserList";
		}
		Page<SmsDayReport> page = smsDayReportService.findByUserPage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/"+redirect;
	}
	
	@RequiresPermissions("sms:smsDayReport:view")
	@RequestMapping(value = "gatewayList")
	public String gatewayList(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		String redirect = "smsDayReportGatewayList";
		if("month".equals(smsDayReport.getQueryType())){
			redirect = "smsMonthReportGatewayList";
		}
		Page<SmsDayReport> page = smsDayReportService.findByGatewayPage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/"+redirect;
	}
	
	//用户通道报表
	@RequiresPermissions("sms:jmsgSmsDayReport:view")
	@RequestMapping(value = "userGatewayList")
	public String userGatewayList(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		String redirect = "smsDayReportUserGatewayList";
		if("month".equals(smsDayReport.getQueryType())){
			redirect = "smsMonthReportUserGatewayList";
		}
		Page<SmsDayReport> page = smsDayReportService.findByUserGatewayPage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/"+redirect;
	}
	
	//用户运营商报表
	@RequiresPermissions("sms:jmsgSmsDayReport:view")
	@RequestMapping(value = "userPhoneTypeList")
	public String userPhoneTypeList(SmsDayReport smsDayReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsDayReport> page = smsDayReportService.findByUserPhoneTypePage(new Page<SmsDayReport>(request, response), smsDayReport); 
		model.addAttribute("page", page);
		return "modules/sms/smsDayReportUserPhoneTypeList";
	}

	@RequiresPermissions("sms:smsDayReport:view")
	@RequestMapping(value = "form")
	public String form(SmsDayReport smsDayReport, Model model) {
		model.addAttribute("smsDayReport", smsDayReport);
		return "modules/sms/smsDayReportForm";
	}

	@RequiresPermissions("sms:smsDayReport:edit")
	@RequestMapping(value = "save")
	public String save(SmsDayReport smsDayReport, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsDayReport)){
			return form(smsDayReport, model);
		}
		smsDayReportService.save(smsDayReport);
		addMessage(redirectAttributes, "保存日报表成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsDayReport/?repage";
	}
	
	@RequiresPermissions("sms:smsDayReport:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsDayReport smsDayReport, RedirectAttributes redirectAttributes) {
		smsDayReportService.delete(smsDayReport);
		addMessage(redirectAttributes, "删除日报表成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsDayReport/?repage";
	}

}