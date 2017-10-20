/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.sms.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import com.sanerzone.jeebase.common.utils.StringUtils;
import com.sanerzone.jeebase.common.web.BaseController;
import com.sanerzone.jeebase.modules.sys.entity.User;
import com.sanerzone.jeebase.modules.sys.utils.UserUtils;
import com.sanerzone.smscenter.modules.sms.entity.SmsTemplate;
import com.sanerzone.smscenter.modules.sms.service.SmsTemplateService;

/**
 * 短信模板Controller
 * @author zhukc
 * @version 2017-06-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sms/smsTemplate")
public class SmsTemplateController extends BaseController {

	@Autowired
	private SmsTemplateService smsTemplateService;
	
	@ModelAttribute
	public SmsTemplate get(@RequestParam(required=false) String id) {
		SmsTemplate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smsTemplateService.get(id);
		}
		if (entity == null){
			entity = new SmsTemplate();
		}
		return entity;
	}
	
	@RequiresPermissions("sms:smsTemplate:view")
	@RequestMapping(value = "recheckList")
	public String recheckList(SmsTemplate smsTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		smsTemplate.setStatus("-1");//待审
		Page<SmsTemplate> page = smsTemplateService.findPage(new Page<SmsTemplate>(request, response), smsTemplate); 
		model.addAttribute("page", page);
		return "modules/sms/smsTemplateRecheckList";
	}
	
	@RequiresPermissions("sms:smsTask:view")
	@RequestMapping(value ="recheckTemplate")
	public String recheckTemplate(String ids,String status,String recheckRemarks,String templateId, String templateContent, String templateSrc, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) {
		String[] array = ids.split(";");
		if(array != null && array.length > 0){
			SmsTemplate param;
			User user = UserUtils.getUser();
			
			try {
				if(StringUtils.isNotBlank(recheckRemarks)){
					recheckRemarks = URLDecoder.decode(recheckRemarks, "UTF-8");
				}
				if(StringUtils.isNotBlank(templateContent)){
					templateContent = URLDecoder.decode(templateContent, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			for (String id : array) {
				param = new SmsTemplate();
				param.setId(id);
				param.setStatus(status);
				param.setRecheckRemarks(recheckRemarks);
				param.setUser(user);
				param.setTemplateId(templateId);
				param.setTemplateSrc(templateSrc);
				param.setTemplateContent(templateContent);
				smsTemplateService.recheckTemplate(param);
			}
		}
		addMessage(redirectAttributes, "短信模板审核成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTemplate/recheckList";
	}
	
	@RequiresPermissions("sms:smsTemplate:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmsTemplate smsTemplate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmsTemplate> page = smsTemplateService.findPage(new Page<SmsTemplate>(request, response), smsTemplate); 
		model.addAttribute("page", page);
		return "modules/sms/smsTemplateList";
	}

	@RequiresPermissions("sms:smsTemplate:view")
	@RequestMapping(value = "form")
	public String form(SmsTemplate smsTemplate, Model model) {
		model.addAttribute("smsTemplate", smsTemplate);
		return "modules/sms/smsTemplateForm";
	}

	@RequiresPermissions("sms:smsTemplate:edit")
	@RequestMapping(value = "save")
	public String save(SmsTemplate smsTemplate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smsTemplate)){
			return form(smsTemplate, model);
		}
		smsTemplateService.save(smsTemplate);
		addMessage(redirectAttributes, "保存短信模板成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTemplate/?repage";
	}
	
	@RequiresPermissions("sms:smsTemplate:edit")
	@RequestMapping(value = "delete")
	public String delete(SmsTemplate smsTemplate, RedirectAttributes redirectAttributes) {
		smsTemplateService.delete(smsTemplate);
		addMessage(redirectAttributes, "删除短信模板成功");
		return "redirect:"+Global.getAdminPath()+"/sms/smsTemplate/?repage";
	}

}