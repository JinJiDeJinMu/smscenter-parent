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
import com.sanerzone.jeebase.common.utils.DateUtils;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsReceive;
import com.sanerzone.smscenter.modules.sms.service.SmsReceiveService;

/**
 * 上行短信记录Controller
 * @author zhukc
 * @version 2017-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsReceive")
public class SmsReceiveController extends BaseController {

	@Autowired
	private SmsReceiveService smsReceiveService;
	
	@ModelAttribute
	public SmsReceive get(@RequestParam(required=false) String id) {
		SmsReceive entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsReceiveService.get(id);
		}
		if (entity == null){
			entity = new SmsReceive();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsReceive:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsReceive smsReceive, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsReceive> page = smsReceiveService.findPage(new Page<SmsReceive>(request, response), smsReceive); 
		model.addAttribute("page", page);
		return "modules/sms/smsReceiveList";
	}

	@RequiresPermissions("sms:smsReceive:view")
	@RequestMapping(value = "form")
	public String form(SmsReceive smsReceive, Model model) {
		model.addAttribute("smsReceive", smsReceive);
		return "modules/sms/smsReceiveForm";
	}

	@RequiresPermissions("sms:smsReceive:edit")
	@RequestMapping(value = "save")
	public String save(SmsReceive smsReceive, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsReceive)){
			return form(smsReceive, model);
		}
		smsReceiveService.save(smsReceive);
		addMessage(redirectAttributes, "保存上行短信记录成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsReceive/?repage";
	}
	
	@RequiresPermissions("sms:smsReceive:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsReceive smsReceive, RedirectAttributes redirectAttributes) {
		smsReceiveService.delete(smsReceive);
		addMessage(redirectAttributes, "删除上行短信记录成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsReceive/?repage";
	}

}