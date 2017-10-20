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
import com.sanerzone.smscenter.modules.sms.entity.SmsKeywords;
import com.sanerzone.smscenter.modules.sms.service.SmsKeywordsService;

/**
 * 敏感词Controller
 * @author zhukc
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsKeywords")
public class SmsKeywordsController extends BaseController {

	@Autowired
	private SmsKeywordsService smsKeywordsService;
	
	@ModelAttribute
	public SmsKeywords get(@RequestParam(required=false) String id) {
		SmsKeywords entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsKeywordsService.get(id);
		}
		if (entity == null){
			entity = new SmsKeywords();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsKeywords:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsKeywords smsKeywords, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsKeywords> page = smsKeywordsService.findPage(new Page<SmsKeywords>(request, response), smsKeywords); 
		model.addAttribute("page", page);
		return "modules/sms/smsKeywordsList";
	}

	@RequiresPermissions("sms:smsKeywords:view")
	@RequestMapping(value = "form")
	public String form(SmsKeywords smsKeywords, Model model) {
		model.addAttribute("smsKeywords", smsKeywords);
		return "modules/sms/smsKeywordsForm";
	}

	@RequiresPermissions("sms:smsKeywords:edit")
	@RequestMapping(value = "save")
	public String save(SmsKeywords smsKeywords, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsKeywords)){
			return form(smsKeywords, model);
		}
		smsKeywordsService.save(smsKeywords);
		addMessage(redirectAttributes, "保存敏感词成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsKeywords/?repage";
	}
	
	@RequiresPermissions("sms:smsKeywords:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsKeywords smsKeywords, RedirectAttributes redirectAttributes) {
		smsKeywordsService.delete(smsKeywords);
		addMessage(redirectAttributes, "删除敏感词成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsKeywords/?repage";
	}

}