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
import com.sanerzone.smscenter.modules.sms.entity.SmsPhoneInfo;
import com.sanerzone.smscenter.modules.sms.service.SmsPhoneInfoService;

/**
 * 号段Controller
 * @author zhukc
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsPhoneInfo")
public class SmsPhoneInfoController extends BaseController {

	@Autowired
	private SmsPhoneInfoService smsPhoneInfoService;
	
	@ModelAttribute
	public SmsPhoneInfo get(@RequestParam(required=false) String id) {
		SmsPhoneInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsPhoneInfoService.get(id);
		}
		if (entity == null){
			entity = new SmsPhoneInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsPhoneInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsPhoneInfo smsPhoneInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsPhoneInfo> page = smsPhoneInfoService.findPage(new Page<SmsPhoneInfo>(request, response), smsPhoneInfo); 
		model.addAttribute("page", page);
		return "modules/sms/smsPhoneInfoList";
	}

	@RequiresPermissions("sms:smsPhoneInfo:view")
	@RequestMapping(value = "form")
	public String form(SmsPhoneInfo smsPhoneInfo, Model model) {
		model.addAttribute("smsPhoneInfo", smsPhoneInfo);
		return "modules/sms/smsPhoneInfoForm";
	}

	@RequiresPermissions("sms:smsPhoneInfo:edit")
	@RequestMapping(value = "save")
	public String save(SmsPhoneInfo smsPhoneInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsPhoneInfo)){
			return form(smsPhoneInfo, model);
		}
		smsPhoneInfoService.save(smsPhoneInfo);
		addMessage(redirectAttributes, "保存号段成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneInfo/?repage";
	}
	
	@RequiresPermissions("sms:smsPhoneInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsPhoneInfo smsPhoneInfo, RedirectAttributes redirectAttributes) {
		smsPhoneInfoService.delete(smsPhoneInfo);
		addMessage(redirectAttributes, "删除号段成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsPhoneInfo/?repage";
	}

}