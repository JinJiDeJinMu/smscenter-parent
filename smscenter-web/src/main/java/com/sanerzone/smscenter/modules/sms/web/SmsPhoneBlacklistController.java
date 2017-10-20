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
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneBlacklist;
import com.sanerzone.smscenter.modules.sms.service.SmsPhoneBlacklistService;

/**
 * 系统黑名单Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsPhoneBlacklist")
public class SmsPhoneBlacklistController extends BaseController {

	@Autowired
	private SmsPhoneBlacklistService smsPhoneBlacklistService;
	
	@ModelAttribute
	public SmsPhoneBlacklist get(@RequestParam(required=false) String id) {
		SmsPhoneBlacklist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsPhoneBlacklistService.get(id);
		}
		if (entity == null){
			entity = new SmsPhoneBlacklist();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsPhoneBlacklist:view")
	@RequestMapping(value = {"init", ""})
	public String init(SmsPhoneBlacklist smsPhoneBlacklist, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sms/smsPhoneBlacklistList";
	}
	
	@RequiresPermissions("sms:smsPhoneBlacklist:view")
	@RequestMapping(value = "list")
	public String list(SmsPhoneBlacklist smsPhoneBlacklist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsPhoneBlacklist> page = smsPhoneBlacklistService.findPage(new Page<SmsPhoneBlacklist>(request, response), smsPhoneBlacklist); 
		model.addAttribute("page", page);
		return "modules/sms/smsPhoneBlacklistList";
	}

	@RequiresPermissions("sms:smsPhoneBlacklist:view")
	@RequestMapping(value = "form")
	public String form(SmsPhoneBlacklist smsPhoneBlacklist, Model model) {
		model.addAttribute("smsPhoneBlacklist", smsPhoneBlacklist);
		return "modules/sms/smsPhoneBlacklistForm";
	}

	@RequiresPermissions("sms:smsPhoneBlacklist:edit")
	@RequestMapping(value = "save")
	public String save(SmsPhoneBlacklist smsPhoneBlacklist, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsPhoneBlacklist)){
			return form(smsPhoneBlacklist, model);
		}
		smsPhoneBlacklistService.save(smsPhoneBlacklist);
		addMessage(redirectAttributes, "保存系统黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneBlacklist/?repage";
	}
	
	@RequiresPermissions("sms:smsPhoneBlacklist:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsPhoneBlacklist smsPhoneBlacklist, RedirectAttributes redirectAttributes) {
		smsPhoneBlacklistService.delete(smsPhoneBlacklist);
		addMessage(redirectAttributes, "删除系统黑名单成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneBlacklist/?repage";
	}

}