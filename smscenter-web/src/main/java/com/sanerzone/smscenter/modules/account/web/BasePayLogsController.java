/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.sanerzone.smscenter.modules.account.web;

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
import com.sanerzone.smscenter.modules.account.entity.BasePayLogs;
import com.sanerzone.smscenter.modules.account.service.BasePayLogsService;

/**
 * 自助充值日志Controller
 * @author zhukc
 * @version 2017-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/account/basePayLogs")
public class BasePayLogsController extends BaseController {

	@Autowired
	private BasePayLogsService basePayLogsService;
	
	@ModelAttribute
	public BasePayLogs get(@RequestParam(required=false) String id) {
		BasePayLogs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = basePayLogsService.get(id);
		}
		if (entity == null){
			entity = new BasePayLogs();
		}
		return entity;
	}
	
	@RequiresPermissions("account:basePayLogs:view")
	@RequestMapping(value = {"list", ""})
	public String list(BasePayLogs basePayLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BasePayLogs> page = basePayLogsService.findPage(new Page<BasePayLogs>(request, response), basePayLogs); 
		model.addAttribute("page", page);
		return "modules/account/basePayLogsList";
	}

	@RequiresPermissions("account:basePayLogs:view")
	@RequestMapping(value = "form")
	public String form(BasePayLogs basePayLogs, Model model) {
		model.addAttribute("basePayLogs", basePayLogs);
		return "modules/account/basePayLogsForm";
	}

	@RequiresPermissions("account:basePayLogs:edit")
	@RequestMapping(value = "save")
	public String save(BasePayLogs basePayLogs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, basePayLogs)){
			return form(basePayLogs, model);
		}
		basePayLogsService.save(basePayLogs);
		addMessage(redirectAttributes, "保存自助充值日志成功");
		return "redirect:"+Global.getAdminPath()+"/account/basePayLogs/?repage";
	}
	
	@RequiresPermissions("account:basePayLogs:edit")
	@RequestMapping(value = "delete")
	public String delete(BasePayLogs basePayLogs, RedirectAttributes redirectAttributes) {
		basePayLogsService.delete(basePayLogs);
		addMessage(redirectAttributes, "删除自助充值日志成功");
		return "redirect:"+Global.getAdminPath()+"/account/basePayLogs/?repage";
	}

}