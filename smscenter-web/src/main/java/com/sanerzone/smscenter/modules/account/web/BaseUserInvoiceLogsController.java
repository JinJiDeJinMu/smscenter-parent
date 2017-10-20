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
import com.sanerzone.smscenter.modules.account.entity.BaseUserInvoiceLogs;
import com.sanerzone.smscenter.modules.account.service.BaseUserInvoiceLogsService;

/**
 * 用户发票明细Controller
 * @author zhukc
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/account/baseUserInvoiceLogs")
public class BaseUserInvoiceLogsController extends BaseController {

	@Autowired
	private BaseUserInvoiceLogsService baseUserInvoiceLogsService;
	
	@ModelAttribute
	public BaseUserInvoiceLogs get(@RequestParam(required=false) String id) {
		BaseUserInvoiceLogs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseUserInvoiceLogsService.get(id);
		}
		if (entity == null){
			entity = new BaseUserInvoiceLogs();
		}
		return entity;
	}
	
	@RequiresPermissions("account:baseUserInvoiceLogs:view")
	@RequestMapping(value = {"list", ""})
	public String list(BaseUserInvoiceLogs baseUserInvoiceLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseUserInvoiceLogs.setStatus("-1");//申请开具发票
		Page<BaseUserInvoiceLogs> page = baseUserInvoiceLogsService.findPage(new Page<BaseUserInvoiceLogs>(request, response), baseUserInvoiceLogs); 
		model.addAttribute("page", page);
		return "modules/account/baseUserInvoiceLogsList";
	}
	
	//索取发票历史记录
	@RequiresPermissions("account:baseUserInvoiceLogs:view")
	@RequestMapping(value = "hisList")
	public String hisList(BaseUserInvoiceLogs baseUserInvoiceLogs, HttpServletRequest request, HttpServletResponse response, Model model) {
		baseUserInvoiceLogs.setQueryType("history");;//查询历史记录
		Page<BaseUserInvoiceLogs> page = baseUserInvoiceLogsService.findPage(new Page<BaseUserInvoiceLogs>(request, response), baseUserInvoiceLogs); 
		model.addAttribute("page", page);
		return "modules/account/baseUserInvoiceLogsHisList";
	}

	@RequiresPermissions("account:baseUserInvoiceLogs:view")
	@RequestMapping(value = "form")
	public String form(BaseUserInvoiceLogs baseUserInvoiceLogs, Model model) {
		model.addAttribute("baseUserInvoiceLogs", baseUserInvoiceLogs);
		return "modules/account/baseUserInvoiceLogsForm";
	}

	@RequiresPermissions("account:baseUserInvoiceLogs:edit")
	@RequestMapping(value = "save")
	public String save(BaseUserInvoiceLogs baseUserInvoiceLogs, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, baseUserInvoiceLogs)){
			return form(baseUserInvoiceLogs, model);
		}
		baseUserInvoiceLogsService.save(baseUserInvoiceLogs);
		addMessage(redirectAttributes, "保存用户发票明细成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserInvoiceLogs/?repage";
	}
	
	@RequiresPermissions("account:baseUserInvoiceLogs:edit")
	@RequestMapping(value = "delete")
	public String delete(BaseUserInvoiceLogs baseUserInvoiceLogs, RedirectAttributes redirectAttributes) {
		baseUserInvoiceLogsService.delete(baseUserInvoiceLogs);
		addMessage(redirectAttributes, "删除用户发票明细成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserInvoiceLogs/?repage";
	}
	
	@RequiresPermissions("account:baseUserInvoiceLogs:edit")
	@RequestMapping(value = "updateStatus")
	public String updateStatus(BaseUserInvoiceLogs baseUserInvoiceLogs, RedirectAttributes redirectAttributes) {
		baseUserInvoiceLogsService.updateStatus(baseUserInvoiceLogs);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/account/baseUserInvoiceLogs/?repage";
	}

}