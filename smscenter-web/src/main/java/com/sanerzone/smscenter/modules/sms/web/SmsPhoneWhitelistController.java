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
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneWhitelist;
import com.sanerzone.smscenter.modules.sms.service.SmsPhoneWhitelistService;

/**
 * 系统白名单Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsPhoneWhitelist")
public class SmsPhoneWhitelistController extends BaseController {

	@Autowired
	private SmsPhoneWhitelistService smsPhoneWhitelistService;
	
	@ModelAttribute
	public SmsPhoneWhitelist get(@RequestParam(required=false) String id) {
		SmsPhoneWhitelist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsPhoneWhitelistService.get(id);
		}
		if (entity == null){
			entity = new SmsPhoneWhitelist();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsPhoneWhitelist:view")
	@RequestMapping(value = {"init", ""})
	public String init(SmsPhoneWhitelist smsPhoneWhitelist, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sms/smsPhoneWhitelistList";
	}
	
	@RequiresPermissions("sms:smsPhoneWhitelist:view")
	@RequestMapping(value = "list")
	public String list(SmsPhoneWhitelist smsPhoneWhitelist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsPhoneWhitelist> page = smsPhoneWhitelistService.findPage(new Page<SmsPhoneWhitelist>(request, response), smsPhoneWhitelist); 
		model.addAttribute("page", page);
		return "modules/sms/smsPhoneWhitelistList";
	}

	@RequiresPermissions("sms:smsPhoneWhitelist:view")
	@RequestMapping(value = "form")
	public String form(SmsPhoneWhitelist smsPhoneWhitelist, Model model) {
		model.addAttribute("smsPhoneWhitelist", smsPhoneWhitelist);
		return "modules/sms/smsPhoneWhitelistForm";
	}

	@RequiresPermissions("sms:smsPhoneWhitelist:edit")
	@RequestMapping(value = "save")
	public String save(SmsPhoneWhitelist smsPhoneWhitelist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsPhoneWhitelist)){
			return form(smsPhoneWhitelist, model);
		}
		smsPhoneWhitelistService.save(smsPhoneWhitelist);
		addMessage(redirectAttributes, "保存系统白名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneWhitelist/?repage";
	}
	
	@RequiresPermissions("sms:smsPhoneWhitelist:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsPhoneWhitelist smsPhoneWhitelist, RedirectAttributes redirectAttributes) {
		smsPhoneWhitelistService.delete(smsPhoneWhitelist);
		addMessage(redirectAttributes, "删除系统白名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneWhitelist/?repage";
	}

}