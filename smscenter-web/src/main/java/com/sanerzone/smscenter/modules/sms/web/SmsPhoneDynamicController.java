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
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneDynamic;
import com.sanerzone.smscenter.modules.sms.service.SmsPhoneDynamicService;

/**
 * 营销黑名单Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsPhoneDynamic")
public class SmsPhoneDynamicController extends BaseController {

	@Autowired
	private SmsPhoneDynamicService smsPhoneDynamicService;
	
	@ModelAttribute
	public SmsPhoneDynamic get(@RequestParam(required=false) String id) {
		SmsPhoneDynamic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsPhoneDynamicService.get(id);
		}
		if (entity == null){
			entity = new SmsPhoneDynamic();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsPhoneDynamic:view")
	@RequestMapping(value = {"init", ""})
	public String init(SmsPhoneDynamic smsPhoneDynamic, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sms/smsPhoneDynamicList";
	}

	
	@RequiresPermissions("sms:smsPhoneDynamic:view")
	@RequestMapping(value = "list")
	public String list(SmsPhoneDynamic smsPhoneDynamic, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsPhoneDynamic> page = smsPhoneDynamicService.findPage(new Page<SmsPhoneDynamic>(request, response), smsPhoneDynamic); 
		model.addAttribute("page", page);
		return "modules/sms/smsPhoneDynamicList";
	}

	@RequiresPermissions("sms:smsPhoneDynamic:view")
	@RequestMapping(value = "form")
	public String form(SmsPhoneDynamic smsPhoneDynamic, Model model) {
		model.addAttribute("smsPhoneDynamic", smsPhoneDynamic);
		return "modules/sms/smsPhoneDynamicForm";
	}

	@RequiresPermissions("sms:smsPhoneDynamic:edit")
	@RequestMapping(value = "save")
	public String save(SmsPhoneDynamic smsPhoneDynamic, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsPhoneDynamic)){
			return form(smsPhoneDynamic, model);
		}
		smsPhoneDynamicService.save(smsPhoneDynamic);
		addMessage(redirectAttributes, "保存营销黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneDynamic/?repage";
	}
	
	@RequiresPermissions("sms:smsPhoneDynamic:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsPhoneDynamic smsPhoneDynamic, RedirectAttributes redirectAttributes) {
		smsPhoneDynamicService.delete(smsPhoneDynamic);
		addMessage(redirectAttributes, "删除营销黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneDynamic/?repage";
	}

}