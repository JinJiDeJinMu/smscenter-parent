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
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroup;
import com.sanerzone.smscenter.modules.sms.service.SmsGatewayGroupService;

/**
 * 通道分组Controller
 * @author zhukc
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsGatewayGroup")
public class SmsGatewayGroupController extends BaseController {

	@Autowired
	private SmsGatewayGroupService smsGatewayGroupService;
	
	@ModelAttribute
	public SmsGatewayGroup get(@RequestParam(required=false) String id) {
		SmsGatewayGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsGatewayGroupService.get(id);
		}
		if (entity == null){
			entity = new SmsGatewayGroup();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsGatewayGroup:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsGatewayGroup smsGatewayGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsGatewayGroup> page = smsGatewayGroupService.findPage(new Page<SmsGatewayGroup>(request, response), smsGatewayGroup); 
		model.addAttribute("page", page);
		return "modules/sms/smsGatewayGroupList";
	}

	@RequiresPermissions("sms:smsGatewayGroup:view")
	@RequestMapping(value = "form")
	public String form(SmsGatewayGroup smsGatewayGroup, Model model) {
		model.addAttribute("smsGatewayGroup", smsGatewayGroup);
		return "modules/sms/smsGatewayGroupForm";
	}

	@RequiresPermissions("sms:smsGatewayGroup:edit")
	@RequestMapping(value = "save")
	public String save(SmsGatewayGroup smsGatewayGroup, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsGatewayGroup)){
			return form(smsGatewayGroup, model);
		}
		smsGatewayGroupService.save(smsGatewayGroup);
		addMessage(redirectAttributes, "保存通道分组成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroup/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayGroup:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsGatewayGroup smsGatewayGroup, RedirectAttributes redirectAttributes) {
		smsGatewayGroupService.delete(smsGatewayGroup);
		addMessage(redirectAttributes, "删除通道分组成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroup/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayGroup:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(SmsGatewayGroup smsGatewayGroup, String oldStatus, RedirectAttributes redirectAttributes) {
		String status = "1";
		String msg = "启用";
		if("1".equals(oldStatus)){
			status = "0";
			msg = "禁用";
		}
		smsGatewayGroup.setStatus(status);
		smsGatewayGroupService.updateStatus(smsGatewayGroup);
		addMessage(redirectAttributes, msg+"通道分组成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroup/?repage";
	}

}