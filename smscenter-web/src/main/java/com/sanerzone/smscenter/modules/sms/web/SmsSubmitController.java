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
import com.sanerzone.smscenter.modules.sms.entity.SmsSubmit;
import com.sanerzone.smscenter.modules.sms.service.SmsSubmitService;

/**
 * 网关提交Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsSubmit")
public class SmsSubmitController extends BaseController {

	@Autowired
	private SmsSubmitService smsSubmitService;
	
	@ModelAttribute
	public SmsSubmit get(@RequestParam(required=false) String id) {
		SmsSubmit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsSubmitService.get(id);
		}
		if (entity == null){
			entity = new SmsSubmit();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsSubmit:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsSubmit smsSubmit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsSubmit> page = smsSubmitService.findPage(new Page<SmsSubmit>(request, response), smsSubmit); 
		model.addAttribute("page", page);
		return "modules/sms/smsSubmitList";
	}

	@RequiresPermissions("sms:smsSubmit:view")
	@RequestMapping(value = "form")
	public String form(SmsSubmit smsSubmit, Model model) {
		model.addAttribute("smsSubmit", smsSubmit);
		return "modules/sms/smsSubmitForm";
	}

	@RequiresPermissions("sms:smsSubmit:edit")
	@RequestMapping(value = "save")
	public String save(SmsSubmit smsSubmit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsSubmit)){
			return form(smsSubmit, model);
		}
		smsSubmitService.save(smsSubmit);
		addMessage(redirectAttributes, "保存网关提交成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsSubmit/?repage";
	}
	
	@RequiresPermissions("sms:smsSubmit:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsSubmit smsSubmit, RedirectAttributes redirectAttributes) {
		smsSubmitService.delete(smsSubmit);
		addMessage(redirectAttributes, "删除网关提交成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsSubmit/?repage";
	}

}