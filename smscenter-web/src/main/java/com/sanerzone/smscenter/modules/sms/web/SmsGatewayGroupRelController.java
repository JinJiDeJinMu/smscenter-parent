/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.util.Set;

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

import com.google.common.collect.Sets;
import com.sanerzone.jeebase.common.config.Global;
import com.sanerzone.jeebase.common.persistence.Page;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsGatewayGroupRel;
import com.sanerzone.smscenter.modules.sms.service.SmsGatewayGroupRelService;

/**
 * 通道分组关系Controller
 * @author zhukc
 * @version 2017-06-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsGatewayGroupRel")
public class SmsGatewayGroupRelController extends BaseController {

	@Autowired
	private SmsGatewayGroupRelService smsGatewayGroupRelService;
	
	@ModelAttribute
	public SmsGatewayGroupRel get(@RequestParam(required=false) String id) {
		SmsGatewayGroupRel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsGatewayGroupRelService.get(id);
		}
		if (entity == null){
			entity = new SmsGatewayGroupRel();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsGatewayGroupRel:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsGatewayGroupRel smsGatewayGroupRel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsGatewayGroupRel> page = smsGatewayGroupRelService.findPage(new Page<SmsGatewayGroupRel>(request, response), smsGatewayGroupRel); 
		model.addAttribute("page", page);
		return "modules/sms/smsGatewayGroupRelList";
	}

	@RequiresPermissions("sms:smsGatewayGroupRel:view")
	@RequestMapping(value = "form")
	public String form(SmsGatewayGroupRel smsGatewayGroupRel, Model model) {
		model.addAttribute("smsGatewayGroupRel", smsGatewayGroupRel);
		return "modules/sms/smsGatewayGroupRelForm";
	}

	@RequiresPermissions("sms:smsGatewayGroupRel:edit")
	@RequestMapping(value = "save")
	public String save(SmsGatewayGroupRel smsGatewayGroupRel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsGatewayGroupRel)){
			return form(smsGatewayGroupRel, model);
		}
		smsGatewayGroupRelService.save(smsGatewayGroupRel);
		smsGatewayGroupRelService.dubboGatewayRel(smsGatewayGroupRel.getGroupId());//dubbo通知
		addMessage(redirectAttributes, "保存通道分组关系成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroupRel/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayGroupRel:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsGatewayGroupRel smsGatewayGroupRel, RedirectAttributes redirectAttributes) {
		smsGatewayGroupRelService.delete(smsGatewayGroupRel);
		smsGatewayGroupRelService.dubboGatewayRel(smsGatewayGroupRel.getGroupId());
		addMessage(redirectAttributes, "删除通道分组关系成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroupRel/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayGroupRel:edit")
	@RequestMapping(value = "batchDelete")
	public String batchDelete(SmsGatewayGroupRel smsGatewayGroupRel, String ids,RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(ids)){
			SmsGatewayGroupRel entity;
			
			Set<String> set = Sets.newHashSet();
			String array[] = ids.split(";");
			for (String param : array) {
				String params[] = param.split("_");
				if(params.length == 2 ){
					entity = new SmsGatewayGroupRel();
					entity.setId(params[0]);
					set.add(params[1]);
					smsGatewayGroupRelService.delete(entity);
				}
			}
			
			if(set != null && set.size() > 0){
				for (String groupId : array) {
					smsGatewayGroupRelService.dubboGatewayRel(groupId);//dubbo通知
				}
			}
		}
		
		addMessage(redirectAttributes, "删除通道分组关系成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroupRel/?repage";
	}
	
	@RequiresPermissions("sms:smsGatewayGroupRel:edit")
	@RequestMapping(value = "refreshCache")
	public String refreshCache(RedirectAttributes redirectAttributes) {
		smsGatewayGroupRelService.refreshCache();
		addMessage(redirectAttributes, "刷新通道分组关系缓存成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsGatewayGroupRel/?repage";
	}

}