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
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayQueueid;
import com.sanerzone.smscenter.modules.sms.service.SmsGatewayQueueidService;

/**
 * 网关队列Controller
 * @author zhukc
 * @version 2017-07-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsGatewayQueueid")
public class SmsGatewayQueueidController extends BaseController {

	@Autowired
	private SmsGatewayQueueidService smsGatewayQueueidService;
	
	@ModelAttribute
	public SmsGatewayQueueid get(@RequestParam(required=false) String id) {
		SmsGatewayQueueid entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsGatewayQueueidService.get(id);
		}
		if (entity == null){
			entity = new SmsGatewayQueueid();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsGatewayQueueid:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsGatewayQueueid smsGatewayQueueid, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsGatewayQueueid> page = smsGatewayQueueidService.findPage(new Page<SmsGatewayQueueid>(request, response), smsGatewayQueueid); 
		model.addAttribute("page", page);
		return "modules/sms/smsGatewayQueueidList";
	}

	@RequiresPermissions("sms:smsGatewayQueueid:view")
	@RequestMapping(value = "form")
	public String form(SmsGatewayQueueid smsGatewayQueueid, Model model) {
		model.addAttribute("smsGatewayQueueid", smsGatewayQueueid);
		return "modules/sms/smsGatewayQueueidForm";
	}

	@RequiresPermissions("sms:smsGatewayQueueid:edit")
	@RequestMapping(value = "save")
	public String save(SmsGatewayQueueid smsGatewayQueueid, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsGatewayQueueid)){
			return form(smsGatewayQueueid, model);
		}
		smsGatewayQueueidService.save(smsGatewayQueueid);
		addMessage(redirectAttributes, "保存网关队列成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayQueueid/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayQueueid:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsGatewayQueueid smsGatewayQueueid, RedirectAttributes redirectAttributes) {
		smsGatewayQueueidService.delete(smsGatewayQueueid);
		addMessage(redirectAttributes, "删除网关队列成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayQueueid/?repage";
	}

}